package com.vivo.niejin.db.utils;

import freemarker.template.*;
import net.sf.cglib.core.Local;

import java.io.*;
import java.util.Locale;

/**
 * Created by NieJin on 2017/6/8.
 */
public class FreemarkerTemplateUtil {

    public static String  templateContext(SimpleHash data,String path,String fileName) throws IOException, TemplateException {
        Configuration config  = new Configuration();
        config.setDefaultEncoding("UTF-8");
       // config.setEncoding(Locale.getDefault(),"UTF-8");
        config.setDirectoryForTemplateLoading(new File(path));
        config.setObjectWrapper(new DefaultObjectWrapper());
        config.setCacheStorage(new freemarker.cache.MruCacheStorage(20,250));

        Template template = config.getTemplate(fileName);
        template.setEncoding("UTF-8");

        StringWriter stringWriter = new StringWriter();
        Writer out = new BufferedWriter(stringWriter);

        template.process(data,out);

        out.flush();

        return stringWriter.toString();
    }
}
