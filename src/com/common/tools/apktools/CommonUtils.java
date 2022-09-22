package com.common.tools.apktools;


import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class CommonUtils {

    public static boolean isWindows() {
        String os = System.getProperty("os.name");
        return os.toLowerCase().contains("windows");
    }

    public static String getApkOutputFilePath(String apkPath) {
        int index = apkPath.lastIndexOf(".apk");
        return apkPath.substring(0,index);
    }

    public static String getBuildApkPath(String apkPath) {
        return apkPath + "_new.apk";
    }

    /**
     * 对其和签名后生成的apk路径
     * @param tasks
     * @param apkPath
     * @param defalutApkName
     * @return
     */
    public static String getZipApkAndSignApkPath(String[] tasks, String apkPath, String defalutApkName) {
        for (int i=0; i<tasks.length; i++){
            if (tasks[i].equals("-o")){
                return tasks[i+1];
            }
        }
        int index = apkPath.lastIndexOf(".apk");
        return apkPath.substring(0,index) + "_" + defalutApkName;
    }

    /**
     * 获取签名文件路径
     * @param tasks
     * @param defaultKey
     * @return
     */
    public static String getKeystore(String[] tasks, String defaultKey) {
        for (int i=0; i<tasks.length; i++){
            if (tasks[i].equals("-k")){
                return tasks[i+1];
            }
        }
        return defaultKey;
    }

    /**
     * 获取签名别名
     * @param tasks
     * @param keystoreAlias
     * @return
     */
    public static String getAliasName(String[] tasks, String keystoreAlias) {
        for (int i=0; i<tasks.length; i++){
            if (tasks[i].equals("-a")){
                return tasks[i+1];
            }
        }
        return keystoreAlias;
    }

    /**
     * 获取签名密码
     * @param tasks
     * @param keystorePass
     * @return
     */
    public static String getKeyStorePass(String[] tasks, String keystorePass) {
        for (int i=0; i<tasks.length; i++){
            if (tasks[i].equals("-p")){
                return tasks[i+1];
            }
        }
        return keystorePass;
    }

    public static String getOutputFilePath(String[] tasks, String defaultFileName) {
        for (int i=0; i<tasks.length; i++){
            if (tasks[i].equals("-o")){
                return tasks[i+1];
            }
        }
        if (tasks[1].endsWith("./")){
            return System.getProperty("user.dir") + "/" + defaultFileName;
        }
        File file = new File(tasks[1]);
        if (file.isFile()){
            if (tasks[1].contains("/")){
                int index = tasks[1].lastIndexOf("/");
                return tasks[1].substring(0,index) + "/" + defaultFileName;
            }
        }
        return defaultFileName;
    }


}
