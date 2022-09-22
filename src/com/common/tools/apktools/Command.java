package com.common.tools.apktools;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Command {

    private static final String SPACE = " ";
    private static final String LINE = "\n";
    private static Runtime runtime;
    private static Process process;

    private static final String JAVA_COMMACND = "java -jar";

    private static final String BASE_DIR = "/usr/local/bin/commontools";
    private static final String TOOLS_DIR = BASE_DIR + "/tools";

    private static final String KEYSTORE_DIR = BASE_DIR + "/keystore/cyou.keystore";//默认的签名文件
    private static final String KEYSTORE_ALIAS = "cymgsdk";//默认的签名文件别名
    private static final String KEYSTORE_PASS = "cymgsdk";//默认的签名文件密码

    private static final String APKTOOL = TOOLS_DIR + "/apktool/apktool_2.6.1.jar";
    private static final String BACK_SMALI = TOOLS_DIR + "/dex2smali_2.5.2/baksmali.jar";
    private static final String SMALI = TOOLS_DIR + "/dex2smali_2.5.2/smali.jar";
    private static final String CAMILLE = TOOLS_DIR + "/camille/camille.py";
    private static final String AAPT = TOOLS_DIR + "/buildtools/aapt";
    private static final String APK_SIGNER = TOOLS_DIR + "/buildtools/apksigner.jar";
    private static final String APK_ZIPALIGN = TOOLS_DIR + "/buildtools/zipalign";
    private static final String DEX_JAR = TOOLS_DIR + "/dex2jar_2.1/d2j-dex2jar.sh";

    /**
     * 查看apk信息
     * command: apkinfo xxx.apk
     * @param apkPath
     */
    public static void apkInfo(String apkPath){
        String command = AAPT + " dump badging " + apkPath;
        startExec(command);
        apkSignInfo(apkPath);
    }

    /**
     * 反编译apk，smali 文件格式
     * command: apksmali xxx.apk
     * @param apkPath
     */
    public static void apkToSmali(String apkPath){
        String command = JAVA_COMMACND + SPACE + APKTOOL + " d " + apkPath + " -f -o " + CommonUtils.getApkOutputFilePath(apkPath);
        startExec(command);
    }

    /**
     * 反编译apk，dex 文件格式
     * command: apkdex xxx.apk
     * @param apkPath
     */
    public static void apkToDex(String apkPath){
        String command = JAVA_COMMACND + SPACE + APKTOOL + " d -s " + apkPath + " -f -o " + CommonUtils.getApkOutputFilePath(apkPath);
        startExec(command);
    }

    /**
     * 回编apk
     * @param apkPath
     * command: build xxx
     */
    public static void buildApk(String apkPath){
        String command = JAVA_COMMACND + SPACE + APKTOOL + " b " + apkPath + " -f -o " + CommonUtils.getBuildApkPath(apkPath);
        startExec(command);
    }

    /**
     * apk签名
     * @param tasks
     * command: sign intput.apk -o output.apk -k 签名文件 -a 别名 -p 密码（不带-k，表示使用默认的签名文件）
     * apksigner sign --ks 签名文件 --ks-key-alias 别名 --ks-pass pass: 密码 --out 签名后的apk 待签名的apk
     */
    public static void signApk(String[] tasks){
        String inputApkPath = tasks[1];
        String zipInputApkPath = CommonUtils.getZipApkAndSignApkPath(tasks,inputApkPath,"zipalign.apk");

        //先对其apk
        String zipalignCommand = APK_ZIPALIGN + SPACE + "-p -f -v 4" + SPACE + inputApkPath + SPACE + zipInputApkPath;
        startExec(zipalignCommand);

        //再对apk进行签名
        String command = JAVA_COMMACND + SPACE + APK_SIGNER
                + SPACE + "sign --ks" + SPACE + CommonUtils.getKeystore(tasks,KEYSTORE_DIR)//签名文件
                + SPACE + "-v"//输出日志
                + SPACE + "--ks-key-alias" + SPACE + CommonUtils.getAliasName(tasks,KEYSTORE_ALIAS)//签名别名
                + SPACE + "--ks-pass pass:" + CommonUtils.getKeyStorePass(tasks,KEYSTORE_PASS)//签名密码
                + SPACE + "--v1-signing-enabled true --v2-signing-enabled true"//默认使用v1v2签名
                + SPACE + "--out" + SPACE + CommonUtils.getZipApkAndSignApkPath(tasks,inputApkPath,"sign.apk")//输出签名后的apk
                + SPACE + zipInputApkPath;//对其后的apk
        startExec(command);

        //删除zipalign包
        FileUtil.del(zipInputApkPath);
    }

    /**
     * 验证签名是否成功
     * @param apkPath
     */
    public static void verifySign(String apkPath){
        String command = "jarsigner  -verify  -verbose  -certs " + apkPath;
        startExec(command);
    }

    /**
     * 验证是否使用了V1和V2签名
     * command: vv xxx.apk
     * @param apkPath
     */
    public static void verifyV1V2(String apkPath){
        String command = JAVA_COMMACND + SPACE + APK_SIGNER + " verify -v --print-certs " + apkPath;
        startExec(command);
    }

    /**
     * 查看keystore的sha1、md5值
     * command: keyinfo xxx.keystore
     * @param keystorePath
     */
    public static void keyStoreInfo(String keystorePath){
        String command = "keytool -v -list -keystore " + keystorePath;
        startExec(command);
    }

    /**
     * apk的sha1、md5值
     * command: signinfo xxx.apk
     * @param apkPath
     */
    public static void apkSignInfo(String apkPath){
        String command = "keytool -printcert -jarfile " + apkPath;
        startExec(command);
    }

    /**
     * dex文件转smali
     * command：smali xxx.dex -o smali文件路径
     * @param tasks
     */
    public static void dexToSmali(String[] tasks){
        String command = JAVA_COMMACND + SPACE + BACK_SMALI + " disassemble " + tasks[1] + " -o " + CommonUtils.getOutputFilePath(tasks,"smali");
        startExec(command);
    }

    /**
     * smali转dex
     * command: dex xxx -o dex文件路径
     * @param tasks
     */
    public static void smaliToDex(String[] tasks){
        String command = JAVA_COMMACND + SPACE + SMALI + " assemble " + tasks[1] + " -o " + CommonUtils.getOutputFilePath(tasks,"classes.dex");
        startExec(command);
    }

    /**
     * dex转jar
     * command: jar xxx.dex -o xxx.jar（不带-o，默认输出到与dex同目录）
     * @param tasks
     */
    public static void dexToJar(String[] tasks){
        String dex = tasks[1];
        String command = DEX_JAR + SPACE + dex + " -o " + CommonUtils.getOutputFilePath(tasks,"classes.jar");
        startExec(command);
    }

    /**
     * 检测隐私权限
     * command: camille com.test.demo -o 检测结果.xls（不带-o，默认输出excel文件到桌面）
     * @param tasks
     */
    public static void camille(String[] tasks){
        String packageName = tasks[1];
        String command = "python3" + SPACE + CAMILLE + SPACE
                + packageName
                + SPACE
                + "-f"
                + SPACE
                + CommonUtils.getOutputFilePath(tasks,Contant.DesktopPath+"检测结果.xls");
        startExec(command);
    }

    /**
     * 压缩为jar文件
     * command: zip xxx/yyy/zzz -o xxx/yyy/zzz/file.zip（不加-o，默认输出到同级目录）
     * @param tasks
     */
    public static void zip(String[] tasks){
        String dir = tasks[1];
        String zipFilePath =  CommonUtils.getOutputFilePath(tasks,"file.zip");
        if (CommonUtils.isWindows()){
            ZipUtil.zip(dir,zipFilePath);
        }else {
            String command = "zip -r " + zipFilePath + SPACE + dir + SPACE + "-x" + SPACE + "\"*.DS_Store\"";
            startExec(command);
        }
    }



    /**
     * 执行终端命令
     * @param executor
     */
    public static void startExec(String executor){
        try {
            final StringBuilder command = new StringBuilder(CommonUtils.isWindows() ? "cmd /c " : "");
            command.append(executor);
            System.out.println("command: "+command.toString()+"\n");

            //执行终端命令
            runtime = Runtime.getRuntime();
            process = runtime.exec(command.toString());
            final StringBuilder resStr = new StringBuilder();
            String charset = CommonUtils.isWindows() ? "GBK" : "UTF-8";
            final BufferedReader bfRead = new BufferedReader(new InputStreamReader(process.getInputStream(), charset));
            final BufferedReader errRead = new BufferedReader(new InputStreamReader(process.getErrorStream(), charset));
            new Thread(() -> {
                try {
                    readLog(command.toString(),resStr, errRead);
                } catch (IOException e) {
                    new Throwable(e);
                }
            }).start();
            readLog(command.toString(),resStr, bfRead);
            String log = "=================================================================================================================" +"\n" +resStr.toString();
            System.out.println(log);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e);
        }
    }

    /**
     * 读取日志
     * @param command
     * @param builder
     * @param reader
     * @throws IOException
     */
    private static void readLog(String command, StringBuilder builder, BufferedReader reader) throws IOException {
        String read;
        String str;
        while ((read = reader.readLine()) != null) {//一行一行读
            if (read.length() > 0) {
                if (command.contains("aapt dump badging")){
                    str = readApkInfo(read);
                }else {
                    str = read;
                }
                if (str.length() > 0){
                    //拼接编译信息
                    builder.append(str).append("\n");
                }
            }
        }
    }

    private static String readApkInfo(String read) {
        StringBuilder builder = new StringBuilder();
        if (read.startsWith("package") || read.startsWith("application:")){
            String[] strs = read.split("\\s+");
            for (int i=0; i<strs.length; i++){
                builder.append(strs[i]).append("\n");
            }
            return "\n"+builder.toString();
        }else if (read.startsWith("sdkVersion")){
            return read.replace(":","=");
        }else if (read.startsWith("targetSdkVersion")){
            return read.replace(":","=")+"\n";
        }else if (read.startsWith("application-") || read.startsWith("locales") || read.startsWith("feature-group") || read.contains("uses-feature") || read.contains("uses-implied")){
            return "";
        }
        return read;
    }

    /**
     * 打印帮助信息
     * @return
     */
    public static void getHelpInfo(){
        StringBuilder builder = new StringBuilder();
        builder.append("apk常用命令工具当前版本：").append(Contant.VERSION)
                .append(LINE)
                .append("帮助信息：")
                .append(LINE)
                .append("apkinfo xxx.apk").append(SPACE).append(SPACE).append("【查看apk信息】")
                .append(LINE)
                .append("apksmali xxx.apk").append(SPACE).append(SPACE).append("【反编译apk，smali文件格式】")
                .append(LINE)
                .append("apkdex xxx.apk").append(SPACE).append(SPACE).append("【反编译apk，dex文件格式】")
                .append(LINE)
                .append("build xxx").append(SPACE).append(SPACE).append("【回编apk】")
                .append(LINE)
                .append("vv xxx.apk").append(SPACE).append(SPACE).append("【验证是否使用了V1和V2签名】")
                .append(LINE)
                .append("signinfo xxx.apk").append(SPACE).append(SPACE).append("【查看apk的sha1、md5值】")
                .append(LINE)
                .append("smali xxx.dex -o smali文件路径").append(SPACE).append(SPACE).append("【dex文件转smali，不加-o输出与dex同目录】")
                .append(LINE)
                .append("jar xxx.dex -o xxx.jar").append(SPACE).append(SPACE).append("【dex文件转jar，不加-o输出与dex同目录】")
                .append(LINE)
                .append("camille xxx.yyy.zzz -o 检测结果.xls").append(SPACE).append(SPACE).append("【检测隐私权限，不加-o，默认输出excel文件到桌面】")
                .append(LINE)
                .append("zip xxx/yyy/zzz -o file.zip").append(SPACE).append(SPACE).append("【zip压缩文件，不加-o，默认输出到同级目录】");
        System.out.println(builder.toString());
    }


}
