# liquibase-ext-hive2
liquibase 的 hive2 扩展

基于 https://github.com/eselyavka/liquibase-impala 改造适配而来
适配 liquibase 4.x，验证过4.8，其他版本请自行验证。
适配 hive 2.x~hive 3.1.x

cloudera hive驱动不在中央仓库，需要自行下载 

# 使用步骤



## 1，下载 HiveJDBC41.jar  :
    https://www.cloudera.com/downloads/connectors/hive/jdbc/2-6-21.html
    
## 2， 安装HiveJDBC41.jar 到 本地 maven 仓库：

| file                     | groupId                   | artifactId       | version |
| ------------------------ | ------------------------- | ---------------- | ------- |
| HiveJDBC41.jar           | com.cloudera.hive.jdbc    | HiveJDBC41       | 2.6.2   |
``` shell
    mvn install:install-file -Dfile=${file} -DgroupId=${groupId} -DartifactId=${artifactId} -Dversion=${version} -Dpackaging=jar    
```
2.6.21为例
``` shell
mvn install:install-file -Dfile=HiveJDBC41.jar -DgroupId=com.cloudera.hive.jdbc -DartifactId=HiveJDBC41 -Dversion=2.6.21 -Dpackaging=jar
```
## 3，clone 本项目并 install
```shell
mvn install
```

## 4，引入自己的项目
```xml
    <dependency>
        <groupId>org.liquibase</groupId>
        <artifactId>liquibase-core</artifactId>
        <version>4.8.0</version>
    </dependency>
    <dependency>
        <groupId>com.cloudera.hive.jdbc</groupId>
        <artifactId>HiveJDBC41</artifactId>
        <version>2.6.21</version>
    </dependency>
    <dependency>
        <groupId>xiananliu</groupId>
        <artifactId>liquibase-ext-hive2</artifactId>
        <version>1.0.0</version>
    </dependency>
```


