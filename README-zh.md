<p align="center">
  初心只是懒，为了不写SQL语句
</p>



## 由来 | Origin

基于Mybatis-Plus的工具包 - 简化`XML`文件中的`foreach`操作
Mybatis-Plus强大的工具包中似乎对批量操作的方法不怎么走心，刚好手中项目里有大量的数据库表需要批量操作，为了偷懒，手写一个简陋的工具包作为更懒的依托



## 简介 | Intro

- **主键策略**：提供四种主键策略（用户输入、自增ID、雪花ID、UUID）
- **时间类型**：提供四种生成策略
- **逻辑删除**：提供默认的逻辑删除值或自定义
- **简化操作**：通过添加实体类注解即可达到效果



## 说明 | Instructions

- @TableColumns 表名注解
- @TableId 主键注解（value = "数据库列名", IdType = 主键策略）
- @TableColumns 列名注解（value = "数据库列名"）
- @TableDate 日期类型注解（value = "数据库列名", type = 日期策略）
- @TableLogic 逻辑删除注解（value = "数据库列名", before = "删除前的值", after = "删除后的值"）



## 演示 | demonstration

```
    @Data
    @Accessors(chain = true)
    @TableName(value = "user")
    public class User {
    
        @TableId(value = "id", type = IdType.ASSIGN_ID)
        private Long id;
    
        @TableColumns(value = "name")
        private String name;
    
        @TableColumns(value = "age")
        private int age;
    
        @TableColumns(value = "msg")
        private String msg;
    
        @TableColumns(value = "sex")
        private int sex;
    
        @TableDate(value = "insert_data", type = DateType.NOW)
        private Date insertData;
    
        @TableLogic(value = "is_del")
        private int isDel;
    }  
```

```
    public Boolean test() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            list.add(new User().setAge(i).setMsg("测试" + i).setName("赵四" + i).setSex(i));
        }

        BatchService batchService = new BatchServiceImpl();
        return batchService.insertBatch(list);
    }
```

```xml
<dependency>
    <groupId>com.github.Ling2099</groupId>
    <artifactId>mybatis-plus-batch</artifactId>
    <version>Latest Version</version>
</dependency>
```



## 期望 | Futures

> 欢迎提出更好的意见，帮助完善各个功能

