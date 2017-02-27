# HyperPump

[![Build Status](https://travis-ci.org/google/guava.svg?branch=master)](https://travis-ci.org/google/guava)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.google.guava/guava/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.google.guava/guava)

HyperPump，中文译为“超泵”，意为超强力的水泵

HyperPump具有强大的任务处理能力，是一个分布式的、多中心的任务调度框架

## 正式版本

当前正式版本为1.0.0，发布于2017年2月24日

由于核心jar包并未加入到Maven的核心仓库，为了使用HyperPump，首先需要进行私有库的设置

```xml
<!-- private remote libs repository -->
<repositories>
    <repository>
        <id>flysoloing-maven-libs-repo</id>
        <name>FlySoloing Maven Libs Repository</name>
        <url>http://flysoloing.github.io/repo/libs</url>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
</repositories>
```

然后在Maven的`pom.xml`文件中添加`hyperpump-core`依赖项

```xml
<dependency>
    <groupId>com.flysoloing.hyperpump</groupId>
    <artifactId>hyperpump-core</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 快照版本

如果要使用HyperPump的当前快照版本，请将`pom.xml`文件中HyperPump依赖的`<version>`内容改为1.0-SNAPSHOT

- [1.0-SNAPSHOT][1.0-SNAPSHOT-version]

## 快速开始

1. 首先下载[zookeeper安装包][zookeeper安装包]，然后配置zookeeper环境并启动，具体如何配置可以参考[这篇文章][zookeeper配置]

2. 下载[hyperpump-example][hyperpump-example]示例，下载hyperpump-console控制台

3. 运行hyperpump-example示例，然后运行hyperpump-console，监控任务调度和执行情况

## 参考及定义

- [GitHub project](https://github.com/google/guava)
- [Issue tracker: Report a defect or feature request](https://github.com/google/guava/issues/new)

## 版权申明

[1.0-REALEASE-version]: https://xx.com
[1.0-SNAPSHOT-version]: https://xx.com
[zookeeper安装包]: http://www.apache.org/dyn/closer.cgi/zookeeper/
[zookeeper配置]: http://coolxing.iteye.com/blog/1871009
[hyperpump-example]: https://github.com/flysoloing/repo/blob/gh-pages/libs/com/flysoloing/common/flysoloing-common/1.0-SNAPSHOT/flysoloing-common-1.0-SNAPSHOT.jar