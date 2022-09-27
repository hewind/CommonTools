# CommonTools
常用打包相关命令工具

命令用法：
apkinfo xxx.apk  【查看apk信息】
apksmali xxx.apk 【反编译apk，smali文件格式】
apkdex xxx.apk   【反编译apk，dex文件格式】
build xxx        【回编apk】
vv xxx.apk       【验证是否使用了V1和V2签名】
signinfo xxx.apk 【查看apk的sha1、md5值】

smali xxx.dex -o smali文件路径  【dex文件转smali，不加-o输出与dex同目录】
jar xxx.dex -o xxx.jar         【dex文件转jar，不加-o输出与dex同目录】
zip test -o file.zip           【zip压缩文件，不加-o，默认输出到同级目录】

camille com.demo.test -o 检测结果.xls                     【检测隐私权限，不加-o，默认输出excel文件到桌面】
sign intput.apk -o output.apk -k 签名文件 -a 别名 -p 密码  【给apk签名，不带-o，默认输出到与未签名apk同目录。不带-k、-a、-p，表示使用默认的签名文件】
buildsign xxx -o output.apk -k 签名文件 -a 别名 -p 密码    【回编apk同时给apk签名，不带-o，默认输出到与未签名apk同目录。不带-k、-a、-p，表示使用默认的签名文件】



安装说明：
下载最新的CommonTools.zip包，解压后，配置脚本文件cc的环境变量。在终端上打出cc即可使用。


具体使用：
查看apk信息：
cc apkinfo xxx.apk
或者直接打cc，根据提示在下一步输入命令 apkinfo xxx.apk


配置签名文件说明：
将.keystore文件或者.jks文件放到keystore文件夹内，在keystore文件目录下的签名.txt中，按照固定格式填写alias和password即可使用默认的签名，
需要打包并签名apk的时候如果不在命令行中输入，会读取默认配置的签名文件。
例：
cc sign xxx.apk  【该种方式会默认使用keytore文件夹内的签名文件】
cc sign xxx.apk -k xxx.keystore -a 别名 -k 密码  【该种方式会使用命令中输入的签名文件及信息】









