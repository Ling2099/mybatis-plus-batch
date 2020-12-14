package com.huoguo.mybatisplus.batch.service;

import java.util.List;

/**
 * @ClassName: IService
 * @Description: TODO
 * @Author: LZH
 * @Date: 2020/12/13 16:54
 * @Version: 1.0
 */
public interface IService<T> {

    boolean saveBatch(List<T> entityList);

    boolean saveBatch(List<T> entityList, int val1);
}
