package com.huoguo.mybatisplus.batch.factory;


import com.huoguo.mybatisplus.batch.enums.StatusEnum;
import com.huoguo.mybatisplus.batch.strategy.StitchingSqlService;

/**
 * @ClassName: StatusFactory
 * @Description: 策略模式的工厂方法，用于实例化对应的接口实现类
 * @Author: LZH
 * @Date: 2020/12/5 19:27
 * @Version: 1.0
 */
public class StatusFactory {

    /**
     * @Author LZH
     * @Description 获取实现类类路径
     * @Date 2020/12/25 11:44
     * @Param type 主键类型数值
     * @return com.huoguo.mybatisplus.batch.strategy.StitchingSqlService
     **/
    public static StitchingSqlService getServicePath(int type) {
        try {
            String path = StatusEnum.getPath(type);
            return (StitchingSqlService)Class.forName(path).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
