package com.common.tools.apktools;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception{
	    // write your code here
        try {
            if (args == null || args.length == 0){
                System.out.println(System.getProperty("user.dir"));
                System.out.println("请输入：");
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNext()){ //判断是否有输入
                    String str = scanner.nextLine();//将输入的值赋值给str
                    String[] tasks = str.split("\\s+");
                    startTask(tasks);
                    scanner.close();
                }
            }else {
                startTask(args);
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * 任务类型
     * @param tasks
     */
    private static void startTask(String[] tasks) {
        String taskType = tasks[0];
        if (taskType.equals(Contant.TASK_HELP)){//查看帮助信息
            Command.getHelpInfo();
        }else if (taskType.equals(Contant.TASK_VERSION)){//查看版本信息
            System.out.println(Contant.VERSION);
        }else if (taskType.equals(Contant.TASK_APK_INFO)){//查看apk信息
            Command.apkInfo(tasks[1]);
        }else if (taskType.equals(Contant.TASK_APK_SMALI)){//反编译apk，smali 文件格式
            Command.apkToSmali(tasks[1]);
        }else if (taskType.equals(Contant.TASK_APK_DEX)){//反编译apk，dex文件格式
            Command.apkToDex(tasks[1]);
        }else if (taskType.equals(Contant.TASK_BUILD_APK)){//回编apk
            Command.buildApk(tasks[1]);
        }else if (taskType.equals(Contant.TASK_SIGN_APK)){//回编apk，带签名
            Command.signApk(tasks);
        }else if (taskType.equals(Contant.TASK_SMALI)){//dex转smali
            Command.dexToSmali(tasks);
        }else if (taskType.equals(Contant.TASK_DEX)){//smali转dex
            Command.smaliToDex(tasks);
        }else if (taskType.equals(Contant.TASK_KEY_INFO)){//keystore 签名信息，md5、sha1
            Command.keyStoreInfo(tasks[1]);
        }else if (taskType.equals(Contant.TASK_VIV2)){//验证是否使用了V1和V2签名
            Command.verifyV1V2(tasks[1]);
        }else if (taskType.equals(Contant.TASK_SIGN_INFO)){//apk的签名信息，md5、sha1
            Command.apkSignInfo(tasks[1]);
        }else if (taskType.equals(Contant.TASK_CAMILLE)){//检测隐私权限
            Command.camille(tasks);
        }else if (taskType.equals(Contant.TASK_JAR)){//dex转jar
            Command.dexToJar(tasks);
        }else if (taskType.equals(Contant.TASK_ZIP)){//zip压缩文件
            Command.zip(tasks);
        }else {
            System.out.println("没有此任务类型！！！ \n");
        }
    }


}
