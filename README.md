# CommonTools
常用打包相关命令工具

用法：
apkinfo xxx.apk  【查看apk信息】
apksmali xxx.apk 【反编译apk，smali文件格式】
apkdex xxx.apk   【反编译apk，dex文件格式】
build xxx        【回编apk】
vv xxx.apk       【验证是否使用了V1和V2签名】
signinfo xxx.apk 【查看apk的sha1、md5值】
smali xxx.dex -o smali文件路径  【dex文件转smali，不加-o输出与dex同目录】
jar xxx.dex -o xxx.jar  【dex文件转jar，不加-o输出与dex同目录】
camille com.demo.test -o 检测结果.xls  【检测隐私权限，不加-o，默认输出excel文件到桌面】
zip test -o file.zip  【zip压缩文件，不加-o，默认输出到同级目录】










