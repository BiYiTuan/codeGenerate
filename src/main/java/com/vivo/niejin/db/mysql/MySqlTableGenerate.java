package com.vivo.niejin.db.mysql;

import com.vivo.niejin.db.model.TableModel;
import com.vivo.niejin.db.utils.DBManager;
import com.vivo.niejin.db.utils.FileUtil;
import com.vivo.niejin.db.utils.FreemarkerTemplateUtil;
import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by NieJin on 2017/6/8.
 */
public class MySqlTableGenerate {

    private static Properties config;

    //final static String rootPath = System.getProperty("CodeDir");
    private final static Boolean isLocal = System.getProperty("Env") == "local" ? Boolean.TRUE : Boolean.FALSE;

    private static String ftlDir= null;

    static{
        config = new Properties();

        InputStream in = null;
        try {
            if(!isLocal){
                in = new FileInputStream(new File(System.getProperty("CodeDir") + File.separator + "codeGenerate.properties"));
            }else{
                in = MySqlTableGenerate.class.getClassLoader().getResourceAsStream("codeGenerate.properties");
            }

            if(in != null && in.available() > 0){
                config.load(in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        ftlDir = config.getProperty("templateDir");
    }

    private String mysqlDriver = "com.mysql.jdbc.Driver";

    private MysqlDA mysqlInstance = new MysqlDA();


    public void run(){
        String url = config.getProperty("mysqlUrl");
        String db = config.getProperty("mysqlDB");
        String user = config.getProperty("mysqlUser");
        String pwd = config.getProperty("mysqlPwd");

        if(StringUtils.isBlank(url) || StringUtils.isBlank(db) || StringUtils.isBlank(user) || StringUtils.isBlank(pwd)){
            return;
        }

        try {
            DBManager mysqlDBManager = new DBManager(url,db,mysqlDriver,user,pwd);

            List<String> tablesName = mysqlInstance.getAllTableNames(mysqlDBManager);

            List<TableModel> tables = new ArrayList<TableModel>();
            for(String tableName : tablesName){
                TableModel table = mysqlInstance.getTableModel(mysqlDBManager, tableName);
                tables.add(table);

            }

            if(config.getProperty("genModel").equalsIgnoreCase("true")){
                try {
                    this.genCommonModel(tables,config);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(config.getProperty("genDao").equalsIgnoreCase("true")){
                try {
                    this.genCommonDao(tables,config);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(config.getProperty("genService").equalsIgnoreCase("true")){
                try {
                    this.genCommonService(tables,config);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void genCommonModel(List<TableModel> tables, Properties config) throws URISyntaxException {
        String modelTemplateFileName ="model.ftl";

        String path = isLocal ? path = this.getClass().getResource("/").toURI().getPath() : System.getProperty("CodeDir");

        if(path.endsWith(File.separator)){
            path = path + ftlDir;
        }else{
            path = path + File.separator + ftlDir;
        }


        for(TableModel table : tables){
            SimpleHash data = this.builSimpleHash(table,config);
            try {
                String templateContent = FreemarkerTemplateUtil.templateContext(data,path,modelTemplateFileName);

                String saveFileName = table.getJavaName()+".java";
                String saveDir = config.getProperty("saveDir","c:\\data\\code\\");

                String modelSaveDir = saveDir + "model";
                if(!FileUtil.isExist(modelSaveDir)){
                    FileUtil.makeDirectory(modelSaveDir);
                }

                String saveFilePath = modelSaveDir + File.separator + saveFileName;

                boolean isCreate = FileUtil.createFile(saveFilePath,true);
                System.out.println(isCreate+":"+templateContent.length());

                File saveFile = new File(saveFilePath);

                //BufferedWriter out = new BufferedWriter(new FileWriter(saveFile));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveFile,true),"UTF-8"));
                out.write(templateContent);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }

        }

    }

    private void genCommonDao(List<TableModel> tables, Properties config) throws URISyntaxException, IOException, TemplateException {

        String mapperTemplateFile = "mapper.ftl";
        String mapperXmlTemplateFile = "mapper_xml.ftl";

        String path = isLocal ? path = this.getClass().getResource("/").toURI().getPath() : System.getProperty("CodeDir");

        if(path.endsWith(File.separator)){
            path = path + ftlDir;
        }else{
            path = path + File.separator + ftlDir;
        }


        for(TableModel table : tables){
            SimpleHash data = this.builSimpleHash(table,config);

            String mapperTemplateContent = FreemarkerTemplateUtil.templateContext(data,path,mapperTemplateFile);
            String mapperXmlTemplateContent = FreemarkerTemplateUtil.templateContext(data,path,mapperXmlTemplateFile);

            if(StringUtils.isNotBlank(mapperTemplateContent)){
                try {
                    String saveFileName = table.getJavaName() + "Mapper.java";
                    String saveDir = config.getProperty("saveDir","c:\\data\\code\\");

                    String mapperSaveDir = saveDir + "mapper";

                    if(!FileUtil.isExist(mapperSaveDir)){
                        FileUtil.makeDirectory(mapperSaveDir);
                    }

                    String saveFilePath = mapperSaveDir + File.separator + saveFileName;

                    FileUtil.createFile(saveFilePath,true);

                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveFilePath,true),"UTF-8"));
                    out.write(mapperTemplateContent);
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(StringUtils.isNotBlank(mapperXmlTemplateContent)){
                try {
                    String saveFileName = table.getJavaName() + "Mapper.xml";
                    String saveDir = config.getProperty("saveDir","c:\\data\\code\\");

                    String mapperSaveDir = saveDir + "mapper" + File.separator + "xml";

                    if(!FileUtil.isExist(mapperSaveDir)){
                        FileUtil.makeDirectory(mapperSaveDir);
                    }

                    String saveFilePath = mapperSaveDir + File.separator + saveFileName;

                    FileUtil.createFile(saveFilePath,true);

                    //BufferedWriter out = new BufferedWriter(new FileWriter(saveFilePath,true));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveFilePath,true),"UTF-8"));
                    out.write(mapperXmlTemplateContent);
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }

    }

    private void genCommonService(List<TableModel> tables, Properties config) throws URISyntaxException, IOException, TemplateException {
        String serviceTemplateFile = "service.ftl";
        String serviceImplTemplateFile = "serviceImpl.ftl";

        String path = isLocal ? path = this.getClass().getResource("/").toURI().getPath() : System.getProperty("CodeDir");

        if(path.endsWith(File.separator)){
            path = path + ftlDir;
        }else{
            path = path + File.separator + ftlDir;
        }


        for(TableModel table : tables){
            SimpleHash data = this.builSimpleHash(table,config);

            String serviceTemplateContent = FreemarkerTemplateUtil.templateContext(data,path,serviceTemplateFile);
            String serviceImplTemplateContent = FreemarkerTemplateUtil.templateContext(data,path,serviceImplTemplateFile);

            if(StringUtils.isNotBlank(serviceTemplateContent)){
                try {
                    String saveFileName = table.getJavaName() + "Service.java";
                    String saveDir = config.getProperty("saveDir","c:\\data\\code\\");

                    String mapperSaveDir = saveDir + "service";

                    if(!FileUtil.isExist(mapperSaveDir)){
                        FileUtil.makeDirectory(mapperSaveDir);
                    }

                    String saveFilePath = mapperSaveDir + File.separator + saveFileName;

                    FileUtil.createFile(saveFilePath,true);

                    //BufferedWriter out = new BufferedWriter(new FileWriter(saveFilePath,true));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveFilePath,true),"UTF-8"));
                    out.write(serviceTemplateContent);
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(StringUtils.isNotBlank(serviceImplTemplateContent)){
                try {
                    String saveFileName = table.getJavaName() + "ServiceImpl.java";
                    String saveDir = config.getProperty("saveDir","c:\\data\\code\\");

                    String mapperSaveDir = saveDir + "service" + File.separator + "impl";

                    if(!FileUtil.isExist(mapperSaveDir)){
                        FileUtil.makeDirectory(mapperSaveDir);
                    }

                    String saveFilePath = mapperSaveDir + File.separator + saveFileName;

                    FileUtil.createFile(saveFilePath,true);

                    //BufferedWriter out = new BufferedWriter(new FileWriter(saveFilePath,true));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveFilePath,true),"UTF-8"));
                    out.write(serviceImplTemplateContent);
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private SimpleHash builSimpleHash(TableModel tables,Properties config){
        SimpleHash data = new SimpleHash(ObjectWrapper.BEANS_WRAPPER);
        data.put("tableModel",tables);
        data.put("config",config);
        data.put("serialVersionUID",System.currentTimeMillis());
        data.put("date",new Date());
        return data;
    }
}
