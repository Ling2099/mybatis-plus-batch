package com.huoguo.mybatisplus.batch.mapper;

import org.apache.ibatis.annotations.Insert;

import java.util.List;

/**
 * @ClassName BaseMapper
 * @Description 基础Mapper接口
 * @Author LZH
 * @Date 2020/12/24 16:38
 * @Version 1.0
 */
public interface BaseMapper {

    @Insert("insert into #{tableName} (#{columns}) values #{val}")
    Boolean insert(List<?> list);
}
