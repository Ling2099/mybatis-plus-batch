package com.huoguo.mybatisplus.batch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author LZH
 * @since 2020-12-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Test extends BaseEntities<Test> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ids", type = IdType.AUTO)
    private Integer ids;

    private String name;

    private Integer age;

    @TableLogic
    private Boolean isDel;

    @Override
    protected Serializable pkVal() {
        return this.ids;
    }

}
