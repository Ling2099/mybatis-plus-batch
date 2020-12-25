package com.huoguo.mybatisplus.batch.strategy;

import java.util.List;
import java.util.Map;

/**
 * @ClassName StitchingSqlService
 * @Description TODO
 * @Author LZH
 * @Date 2020/12/25 11:31
 * @Version 1.0
 */
public interface StitchingSqlService {

    String getSqlString(String column, List<Map<String, Object>> list);
}
