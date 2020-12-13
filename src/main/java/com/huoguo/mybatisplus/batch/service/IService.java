package com.huoguo.mybatisplus.batch.service;

import java.util.Collection;

/**
 * @ClassName: IService
 * @Description: TODO
 * @Author: LZH
 * @Date: 2020/12/13 16:54
 * @Version: 1.0
 */
public interface IService<T> {

    boolean executeBatch(Collection<T> entityList);

    boolean executeBatch(Collection<T> entityList, int val1);
}
