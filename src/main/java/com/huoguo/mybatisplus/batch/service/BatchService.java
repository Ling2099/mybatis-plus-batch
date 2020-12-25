package com.huoguo.mybatisplus.batch.service;

import java.util.List;

/**
 * @ClassName BatchService
 * @Description TODO
 * @Author LZH
 * @Date 2020/12/25 17:20
 * @Version 1.0
 */
public interface BatchService {

    Boolean insertBatch(List<?> list);

    Boolean insertBatch(List<?> list, int size);
}
