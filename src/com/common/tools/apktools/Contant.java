package com.common.tools.apktools;

public class Contant {

    public static final String VERSION = "v1.0.1";

    public static final String DesktopPath = "/Users/heshuiguang/Desktop/";

    public static final String TASK_HELP = "-h";//帮助信息
    public static final String TASK_VERSION = "-v";//版本信息
    public static final String TASK_LOCATION = "-l";//安装目录

    public static final String TASK_APK_INFO = "apkinfo";//apk信息
    public static final String TASK_APK_SMALI = "apksmali";//反编译apk，以smali格式查看
    public static final String TASK_APK_DEX = "apkdex";//反编译apk，以dex格式查看
    public static final String TASK_BUILD_APK = "build";//回编apk
    public static final String TASK_SIGN_APK = "sign";//签名apk
    public static final String TASK_BUILDSIGN_APK = "buildsign";//回编apk，带签名
    public static final String TASK_SMALI = "smali";//dex转smali
    public static final String TASK_DEX = "dex";//smali转dex
    public static final String TASK_KEY_INFO = "keyinfo";//keystore 签名信息，md5、sha1
    public static final String TASK_VIV2 = "vv";//验证是否使用了V1和V2签名
    public static final String TASK_SIGN_INFO = "signinfo";//apk的签名信息，md5、sha1
    public static final String TASK_CAMILLE = "camille";//检测隐私权限
    public static final String TASK_JAR = "jar";//dex转jar
    public static final String TASK_ZIP = "zip";//zip压缩文件

}
