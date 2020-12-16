package com.huoguo.mybatisplus.batch.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName MetaObjectHandlers
 * @Description Mybatis Plus 自动填充实现类
 * @Author LZH
 * @Date 2020/9/8 16:30
 * @Version 1.0
 */
@Component
public class MybatisPlusObjectHandler implements MetaObjectHandler {

    /**
     * @Author LZH
     * @Description 新增时自动填充
     * @Table
     * @Date 2020/9/8 18:36
     * @Param [metaObject]
     * @return void
     **/
    @Override
    public void insertFill(MetaObject metaObject) {
        this.fillStrategy(metaObject, "createTime", new Date());
        this.fillStrategy(metaObject, "creator", "张三");
        this.fillStrategy(metaObject, "creatorId", 123);
        this.fillStrategy(metaObject, "deptId", 11);

        // this.fillStrategy(metaObject, "updateTime", new Date());
        // this.fillStrategy(metaObject, "updater", "张三");
        // this.fillStrategy(metaObject, "updaterId", 11);
    }

    /**
     * @Author LZH
     * @Description 更新时自动填充
     * @Table
     * @Date 2020/9/8 18:36
     * @Param [metaObject]
     * @return void
     **/
    @Override
    public void updateFill(MetaObject metaObject) {
        // this.fillStrategy(metaObject, "updateTime", new Date());
        // this.fillStrategy(metaObject, "updater", SecurityUtils.getUsername());
        // this.fillStrategy(metaObject, "updaterId", SecurityUtils.getUserId());
    }
}
