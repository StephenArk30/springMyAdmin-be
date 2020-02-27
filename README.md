# SpringMyAdmin backend

## Installation

```bash
cd springmyadmin-be
mvn clean install
mvn clean package
java -jar target/springmyadmin-0.0.1-SNAPSHOT.jar
```



## API List

### 查询
- [x] 登录

- [x] 读取所有数据库

```sql
show databases;
```

- [x] 读取数据库下所有表

```sql
show tables;
```

- [x] 读取所有属性

```sql
select COLUMN_NAME from information_schema.COLUMNS where table_name={} and table_schema={};
```

- [x] 读取表所有数据

```sql
select * from {};
```

- [x] 按属性搜索

```sql
select * from {} where {} order by {} limit {} offset {};
```

### 增加
- [x] 新建数据库

```sql
create database {};
```
- [x] 新建表

```sql
create table {}
(
{COLUMN_NAME} {COLUMN_TYPE} {IS_NULLABLE} default {COLUMN_DEFAULT} comment {COLUMN_COMMENT},
......
{COLUMN_KEY}
);
```
- [x] 插入一条数据

```sql
insert into {table} ({column1}, {column2},...) VALUES ({value1}, {value2},....);
```
### 删除
- [x] 删除一个数据库

```sql
drop database {};
```
- [x] 删除一个表

```sql
drop table {};
```



- [x] 删除一条数据

```sql
delete from {} where {};
```

### 修改
- [x] 修改一条数据

```sql
update {table} set {column}={value} where {};
```