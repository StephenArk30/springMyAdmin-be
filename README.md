# SpringMyAdmin backend

## Installation



## API List

### 查询
- 登录
-[x] 读取所有数据库

    ```show databases;```
-[x] 读取数据库下所有表

    ```show tables;```
-[x] 读取所有属性

    ```select COLUMN_NAME from information_schema.COLUMNS where table_name={} and table_schema={};```
-[x] 读取表所有数据

    ```select * from {}```
-[x] 按属性搜索

    ```select * from {} where {}```

### 增加
-[x] 新建数据库

    ```create database {}```
-[x] 新建表

    ```
    create table {}
    (
    {COLUMN_NAME} {COLUMN_TYPE} {IS_NULLABLE} default {COLUMN_DEFAULT} comment {COLUMN_COMMENT},
    ......
    {COLUMN_KEY}
    )
    ```
-[x] 插入一条数据

    ```insert into {table} ({column1}, {column2},...) VALUES ({value1}, {value2},....)```
### 删除
-[x] 删除一个数据库

    ```drop database {}```
-[x] 删除一个表
    ```drop table {}```
-[x] 删除一条数据

    ```delete from {} where {}```

### 修改
-[x] 修改一条数据

    ```update {table} set {column}={value} where {}```