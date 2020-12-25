package com.huoguo.mybatisplus.batch.strategy.impl;

import com.huoguo.mybatisplus.batch.constant.DefaultConstants;
import com.huoguo.mybatisplus.batch.strategy.StitchingSqlService;

import java.util.List;
import java.util.Map;

/**
 * @ClassName AutoServiceImpl
 * @Description 主键自增长实现类
 * @Author LZH
 * @Date 2020/12/25 11:35
 * @Version 1.0
 */
public class AutoServiceImpl implements StitchingSqlService {

    @Override
    public String getSqlString(String column, List<Map<String, Object>> list) {

        StringBuilder stringBuilder = new StringBuilder();

        int size = list.size();
        for (int k = 0; k < size; k ++) {
            stringBuilder.append(DefaultConstants.LEFT_PARENTHESIS);
            String[] str = column.split(DefaultConstants.DEFAULT_COMMA_BLANK);
            int len = str.length;
            for (int i = 0; i < len; i ++) {
                stringBuilder.append(list.get(k).get(str[i]));
                if (i != len - 1) {
                    stringBuilder.append(DefaultConstants.DEFAULT_COMMA);
                }
            }
            stringBuilder.append(DefaultConstants.RIGHT_PARENTHESIS);
            if (k != size - 1) {
                stringBuilder.append(DefaultConstants.DEFAULT_COMMA);
            }
        }
        return stringBuilder.toString();
    }
}
