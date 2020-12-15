package com.huoguo.mybatisplus.batch.service;

import java.util.List;

/**
 * @ClassName: BatchService
 * @Description: TODO
 * @Author: LZH
 * @Date: 2020/12/13 16:54
 * @Version: 1.0
 */
public interface BatchService<T> {

    boolean saveBatch(List<T> entityList);

    boolean saveBatch(List<T> entityList, int val1);
}
