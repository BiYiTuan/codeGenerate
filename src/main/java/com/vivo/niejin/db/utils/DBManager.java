package com.vivo.niejin.db.utils;

import java.sql.*;
import java.util.Properties;

public class DBManager {

    // 用户名

    private String user = "";

    // 密码

    private String password = "";

    // 主机

    private String host = "";

    // 数据库名字

    private String database = "";

    private String port = "";

	/*
     *
	 * private String
	 * url="jdbc:mysql://"+host+"/"+"useUnicode=true&characterEncoding=GB2312";
	 * 
	 */

    private String url = "";

    private String driverName = "";

    private Connection con = null;

    Statement stmt;

    public DBManager(String dburl, String driverName)
            throws ClassNotFoundException, SQLException {
        this.driverName = driverName;
        if (dburl != null) {
            String[] urls = dburl.split(";");
            for (int i = 0; i < urls.length; i++) {
                String keyValue = urls[i];
                String[] keyValues = keyValue.split("=");
                if (keyValues.length == 2) {
                    String key = keyValues[0];
                    String value = keyValues[1];
                    if (key.equalsIgnoreCase("user")) {
                        this.user = value;
                    } else if (key.equalsIgnoreCase("password")) {
                        this.password = value;
                    } else if (key.equalsIgnoreCase("host")) {
                        this.host = value;
                    } else if (key.equalsIgnoreCase("port")) {
                        this.port = value;
                    } else if (key.equalsIgnoreCase("database")) {
                        this.database = value;
                    }
                }
            }
        }
        init();
    }

    /**
     * 根据主机、数据库名称、数据库用户名、数据库用户密码取得连接。
     *
     * @param host     String
     * @param database String
     * @param user     String
     * @param password String
     * @throws SQLException
     * @throws ClassNotFoundException
     */

    public DBManager(String host, String database, String driverName,
                     String user, String password) throws ClassNotFoundException,
            SQLException {

        this.host = host;

        this.database = database;

        this.user = user;

        this.password = password;

        this.driverName = driverName;

        init();
    }

    private boolean init() throws ClassNotFoundException, SQLException {

        boolean success = false;

        this.url = "jdbc:mysql://"
                + host
                + "/"
                + database
                + "?useUnicode=true&amp;characterEncoding=utf8&amp;noAccessToProcedureBodies=true";

        Class.forName(this.driverName);

        con = DriverManager.getConnection(this.url, this.user, this.password);

        stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,

                ResultSet.CONCUR_READ_ONLY);

        success = true;

        return success;
    }

    /**
     * 返回取得的连接
     */

    public Connection getCon() {

        return con;

    }

    /**
     * 执行一条简单的查询语句
     * <p>
     * 返回取得的结果集
     */

    public ResultSet executeQuery(String sql) {

        ResultSet rs = null;

        try {

            rs = stmt.executeQuery(sql);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return rs;

    }

    /**
     * 执行一条简单的更新语句
     * <p>
     * 执行成功则返回true
     */

    public boolean executeUpdate(String sql) {

        boolean v = false;

        try {

            v = stmt.executeUpdate(sql) > 0 ? true : false;

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            return v;

        }

    }

    public void close(){
        if(stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(con != null){
            try{
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public String getDatabase() {
        return database;
    }

    public String getUrl() {
        return url;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

}
