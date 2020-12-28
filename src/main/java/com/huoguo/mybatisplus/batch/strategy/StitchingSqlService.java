package com.huoguo.mybatisplus.batch.strategy;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @ClassName StitchingSqlService
 * @Description TODO
 * @Author LZH
 * @Date 2020/12/25 11:31
 * @Version 1.0
 */
public interface StitchingSqlService {

    String getSqlString(List<?> list, Field[] fields, String id);
}
