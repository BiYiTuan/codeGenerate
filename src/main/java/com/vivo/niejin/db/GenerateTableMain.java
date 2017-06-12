package com.vivo.niejin.db;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Created by NieJin on 2017/6/8.
 */
public class GenerateTableMain {

    public static void main( String[] args )
    {
        String jarPath = GenerateTableMain.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        if(!jarPath.endsWith("jar")){
            System.setProperty("Env","local");
        }
        File jarFile = new File(jarPath);
        File jarParent = jarFile.getParentFile();
        System.setProperty("CodeDir",jarParent.getAbsolutePath());

       new com.vivo.niejin.db.mysql.MySqlTableGenerate().run();

    }
}
