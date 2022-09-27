package com.common.tools.apktools;



import cn.hutool.core.io.FileUtil;

import java.io.File;

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
        if (apkPath.contains(".apk")){
            int index = apkPath.lastIndexOf(".apk");
            return apkPath.substring(0,index) + "_" + defalutApkName;
        }
        return apkPath + "_" + defalutApkName;
    }

    /**
     * 获取签名文件路径
     * @param tasks
     * @param keyStoreDir
     * @return
     */
    public static String getKeystore(String[] tasks, String keyStoreDir) {
        for (int i=0; i<tasks.length; i++){
            if (tasks[i].equals("-k")){
                return tasks[i+1];
            }
        }
        //读取默认的签名文件
        return getKeystorePath(keyStoreDir);
    }

    /**
     * 获取签名别名
     * @param tasks
     * @param keyStoreTxtDir
     * @return
     */
    public static String getAliasName(String[] tasks, String keyStoreTxtDir) {
        for (int i=0; i<tasks.length; i++){
            if (tasks[i].equals("-a")){
                return tasks[i+1];
            }
        }
        return getKeystoreAliasPass(keyStoreTxtDir, "alias");
    }

    /**
     * 获取签名密码
     * @param tasks
     * @param keyStoreTxtDir
     * @return
     */
    public static String getPassWord(String[] tasks, String keyStoreTxtDir) {
        for (int i=0; i<tasks.length; i++){
            if (tasks[i].equals("-p")){
                return tasks[i+1];
            }
        }
        return getKeystoreAliasPass(keyStoreTxtDir, "password");
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

    /**
     * 读取默认目录下的签名文件
     * @param path
     * @return
     */
    private static String getKeystorePath(String path){
        File keyDir = new File(path);
        if (FileUtil.isDirectory(keyDir)){
            File[] files = keyDir.listFiles();
            for (File f : files) {
                if (f.getName().endsWith(".keystore") || f.getName().endsWith(".jks")){
                    System.out.println("读取keystore文件："+f.getAbsolutePath());
                    return f.getAbsolutePath();
                }
            }
        }
        return null;
    }

    /**
     * 读取默认目录下txt中的签名alias
     *
     * @param keyStoreTxtDir
     * @param type
     * @return
     */
    private static String getKeystoreAliasPass(String keyStoreTxtDir, String type){
        String txtFilePath = null;
        File keyDir = new File(keyStoreTxtDir);
        if (FileUtil.isDirectory(keyDir)){
            File[] files = keyDir.listFiles();
            assert files != null;
            for (File f : files) {
                if (f.getName().endsWith(".txt")){
                    System.out.println("读取签名"+type+"："+f.getAbsolutePath());
                    txtFilePath = f.getAbsolutePath();
                    break;
                }
            }
        }
        String txt = FileUtil.readUtf8String(txtFilePath);
        //分割每行文本
        String[] content = txt.split("\n");
        for (String line : content) {
            if (line.contains(type)){
                String[] key = line.split("=");
                System.out.println("签名" + type + " = "+ key[1]);
                return key[1];
            }
        }
        return "";
    }


}
