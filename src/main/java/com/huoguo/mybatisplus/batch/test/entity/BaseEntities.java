package com.huoguo.mybatisplus.batch.test.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @ClassName BaseEntities
 * @Description Mybatis plus 自动填充 公共字段
 * @Author LZH
 * @Date 2020/9/8 15:24
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
public abstract class BaseEntities<T extends Model> extends Model {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


    @TableField(fill = FieldFill.INSERT)
    private String creator;

    @TableField(fill = FieldFill.INSERT)
    private Long creatorId;


    @TableField(fill = FieldFill.INSERT)
    private Long deptId;
}
