package com.huoguo.mybatisplus.batch.strategy;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName StitchingSqlService
 * @Description TODO
 * @Author LZH
 * @Date 2020/12/25 11:31
 * @Version 1.0
 */
public interface StitchingSqlService {

    String getSqlString(List<?> list, String id, ConcurrentHashMap<String, Object> map);
}
