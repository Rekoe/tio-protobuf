# 简介

本压缩包是一个maven工程, eclipse/idea均可按maven项目导入

MainLauncher是入口,启动即可

## 环境要求

* 必须JDK8+
* eclipse或idea等IDE开发工具,可选

## 配置信息位置

数据库配置信息,jetty端口等配置信息,均位于src/main/resources/application.properties

## 命令下启动

仅供测试用,使用mvn命令即可

```
// for windows
set MAVEN_OPTS="-Dfile.encoding=UTF-8"
mvn compile nutzboot:run

// for *uix
export MAVEN_OPTS="-Dfile.encoding=UTF-8"
mvn compile nutzboot:run
```

## 项目打包

```
mvn clean package nutzboot:shade
```

请注意,当前需要package + nutzboot:shade, 单独执行package或者nutzboot:shade是不行的

# tio + protobuf3 最佳实践
1. tio+protobuf3多类型传输实现
2. 优雅的实现消息分发

## 使用步骤:
1. 先运行`protoGen.bat`生成protobuf实体类，如果是*nix平台直接把bat改成sh即可(项目中已经生成)
2. 运行`com.onemena.game.generator.HandlerGenerator`生成对应类型`handler`类
3. 在生成的`handler`中填写业务逻辑
4. 运行`com.onemena.game.MainLauncher`中的主方法启动服务端
5. 运行`com.onemena.game.client.ClientServer`中的主方法启动客户端循环发送数据
