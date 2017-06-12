/**  
 *FileUtil.java         2017年5月22日下午12:05:34
 *@Copyright:Copyright © VIVO Communication Technology Co., Ltd. All rights reserved.
 *@Company:http://www.vivo.com.cn/
 * 
 */
package com.vivo.niejin.db.utils;

import java.io.File;
import java.io.IOException;

/**
 * @Author:N.Jin
 * @Since:2017年5月22日
 * @Version:1.0
 */
public class FileUtil {

    /**
     * @Author:N.Jin
     * @since: 1.0
     * @Create Date:2017年5月22日
     */
    public static boolean isExist(String string) {
        File file = new File(string);
        return file.exists();
    }

    /**
     * @Author:N.Jin
     * @since: 1.0
     * @Create Date:2017年5月22日
     */
    public static void makeDirectory(String string) {
        File file = new File(string);
        file.mkdir();

    }

    /**
     * @Author:N.Jin
     * @since: 1.0
     * @Create Date:2017年5月22日
     */
    public static void moveFile(String string, String string2) {
        // TODO Auto-generated method stub

    }

    
    public static boolean createFile(String destFileName,boolean isCover) {
        File file = new File(destFileName);

        if(isCover){
            file.delete();
        }

        if(file.exists()) {
            System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
            return false;
        }

        if (destFileName.endsWith(File.separator)) {
            System.out.println("创建单个文件" + destFileName + "失败，目标文件不能为目录！");  
            return false;  
        }  
        //判断目标文件所在的目录是否存在  
        if(!file.getParentFile().exists()) {  
            //如果目标文件所在的目录不存在，则创建父目录  
            System.out.println("目标文件所在目录不存在，准备创建它！");  
            if(!file.getParentFile().mkdirs()) {  
                System.out.println("创建目标文件所在目录失败！");  
                return false;  
            }  
        }  
        //创建目标文件  
        try {  
            if (file.createNewFile()) {  
                System.out.println("创建单个文件" + destFileName + "成功！");  
                return true;  
            } else {  
                System.out.println("创建单个文件" + destFileName + "失败！");  
                return false;  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
            System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());  
            return false;  
        }  
    }  
     
     
    public static boolean createDir(String destDirName) {  
        File dir = new File(destDirName);  
        if (dir.exists()) {  
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");  
            return false;  
        }  
        if (!destDirName.endsWith(File.separator)) {  
            destDirName = destDirName + File.separator;  
        }  
        //创建目录  
        if (dir.mkdirs()) {  
            System.out.println("创建目录" + destDirName + "成功！");  
            return true;  
        } else {  
            System.out.println("创建目录" + destDirName + "失败！");  
            return false;  
        }  
    }  
     
     
    public static String createTempFile(String prefix, String suffix, String dirName) {  
        File tempFile = null;  
        if (dirName == null) {  
            try{  
                //在默认文件夹下创建临时文件  
                tempFile = File.createTempFile(prefix, suffix);  
                //返回临时文件的路径  
                return tempFile.getCanonicalPath();  
            } catch (IOException e) {  
                e.printStackTrace();  
                System.out.println("创建临时文件失败！" + e.getMessage());  
                return null;  
            }  
        } else {  
            File dir = new File(dirName);  
            //如果临时文件所在目录不存在，首先创建  
            if (!dir.exists()) {  
                if (!FileUtil.createDir(dirName)) {  
                    System.out.println("创建临时文件失败，不能创建临时文件所在的目录！");  
                    return null;  
                }  
            }  
            try {  
                //在指定目录下创建临时文件  
                tempFile = File.createTempFile(prefix, suffix, dir);  
                return tempFile.getCanonicalPath();  
            } catch (IOException e) {  
                e.printStackTrace();  
                System.out.println("创建临时文件失败！" + e.getMessage());  
                return null;  
            }  
        }  
    }  
}
