package com.huoguo.mybatisplus.batch.service;

import java.util.List;

/**
 * @ClassName BatchService
 * @Description TODO
 * @Author LZH
 * @Date 2020/12/24 16:39
 * @Version 1.0
 */
public interface BatchService {

    Boolean insert(List<?> list);
}
