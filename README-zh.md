<p align="center">
  初心只是懒，懒到什么都不想干的程度
</p>



## 由来 | Origin

摆脱头疼的`XML`文件、繁琐的SQL语句，代替`foreach`操作  
让这个简陋的工具包依托代替你更懒的心里寄托！别让再时间悄悄从指缝中流过



## 简介 | Intro

- **主键策略**：提供四种主键策略（用户输入、自增ID、雪花ID、UUID）
- **自动填充**：提供字段值的自动填充支持
- **逻辑删除**：提供默认的逻辑删除值或自定义
- **简化操作**：通过添加实体类注解即可达到效果



## 说明 | Instructions

- @BatchName 表名注解
- @BatchId 主键注解（value = "数据库列名", IdType = 主键策略）
- @BatchColumns 列名注解（value = "数据库列名"）
- @BatchFill 自动填充注解（value = "数据库列名", insert = true, update = true）
- @BatchLogic 逻辑删除注解（value = "数据库列名", before = "删除前的值", after = "删除后的值"）
- @BatchIgnore 需要忽略字段注解
- @BatchSuper 公共父类的类注解



## 演示 | demonstration


```
    @Autowired
    private DataSource dataSource;
    
    @Bean("BatchSource")
    public BatchSource getDataSource() {
        return new BatchSource(dataSource);
    }
    
    // 或
    
    @Bean("BatchSource")
    public BatchSource getDataSource() {
        return new BatchSource(url, usr, password, driver);
    }

```

初始化数据库连接信息

```
    @Data
    @Accessors(chain = true)
    @BatchSuper
    public class BaseEntities {
        
        @BatchFill(value = "create_time", insert = true)
        private Date createTime;
        
        @BatchFill(value = "update_time", insert = true, update = true)
        private Date updateTime;
        
        @BatchFill(value = "creator", insert = true)
        private String creator;
        
        @BatchFill(value = "creator_id", insert = true)
        private Long creatorId;
        
        @BatchFill(value = "updater", insert = true, update = true)
        private String updater;
        
        @BatchFill(value = "updater_id", insert = true, update = true)
        private Long updaterId;
        
        @BatchFill(value = "dept_id", insert = true)
        private Long deptId;
    }

```

1.公共的父实体类，批量操作时需要维护的自动填充字段  
2.@BatchFill注解
- value为数据库字段名
- insert 新增时是否需要自动填充，默认为false
- update 编辑时是否需要自动填充，默认为false

```
    @Component
    public class MyHandler implements BatchFillService {
    
        @Bean("insert")
        @Override
        public Map<String, HotPot> batchInsertFill() {
            Map<String, HotPot> map = new ConcurrentHashMap<>(8);
            map.put("create_time", new HotPot(DateUtils.class, "getTime", null));
            map.put("creator", new HotPot(SecurityUtils.class, "getNickName", null));
            map.put("creator_id", new HotPot(SecurityUtils.class, "getUserId", null));
            map.put("dept_id", new HotPot(SecurityUtils.class, "getDeptId", null));
    
            map.put("update_time", new HotPot(DateUtils.class, "getTime", null));
            map.put("updater", new HotPot(SecurityUtils.class, "getNickName", null));
            map.put("updater_id", new HotPot(SecurityUtils.class, "getUserId", null));
    
            return map;
        }
        
        @Bean("update")
        @Override
        public Map<String, HotPot> batchUpdateFill() {
            // 暂未完善
            return null;
        }
    }
    

```
1.如果使用自动填充功能，需要实现BatchFillService接口，配置需要自动填充的字段名、执行类、执行方法或值，并注入Bean，其名称为insert/update  
2.将BatchBean类注入Spring容器中
3.HotPot对象
- 参数一：需要填充字段值的类Class，可以为null，代表给其字段死值，而非程序运行时计算得到
- 参数二：需要填充字段值的类方法名，可以为null，同上
- 参数三：若前两个参数为null时，代表其值是固定的，填上即可  


```
    @Data
    @Accessors(chain = true)
    @BatchName(value = "user")
    public class User extends BaseEntities {
    
        @BatchId(value = "id", type = BatchIdEnum.ASSIGN_ID)
        private Long id;
    
        @BatchColumns(value = "name")
        private String name;
    
        @BatchColumns(value = "age")
        private int age;
    
        @BatchColumns(value = "msg")
        private String msg;
    
        @BatchColumns(value = "sex")
        private int sex;
    
        @BatchIgnore
        private String card;
    
        @BatchLogic(value = "is_del")
        private int isDel;
    }  
```
1.具体的业务实体类，类注解@BatchName需要添加该实体类所映射的数据库表名  
2.@BatchId注解：value主键ID的数据库字段名，type可以选择生成策略（自增、用户输入、雪花ID、UUID）
3.@TableColumns注解：数据库字段名
4.@BatchIgnore注解：在批量操作时，需要忽略的字段名
5.@BatchLogic注解：逻辑删除字段默认删除前为0，删除后为1；可自定义before/after

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
- 批量新增

```
    public Boolean test(String[] ids) {
        BatchService batchService = new BatchServiceImpl();
        return batchService.deleteBatch(Arrays.asList(ids), User.class);
    }
```
- 批量删除

```xml
<dependency>
    <groupId>com.github.Ling2099</groupId>
    <artifactId>mybatis-plus-batch</artifactId>
    <version>Latest Version</version>
</dependency>
```



## 期望 | Futures

> 欢迎提出更好的意见，帮助完善各个功能；目前暂不支持SQL打印功能

