package com.huoguo.mybatisplus.batch.util;

import com.huoguo.mybatisplus.batch.constant.DefaultConstants;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @ClassName CollectionUtils
 * @Description 集合工具类
 * @Author LZH
 * @Date 2020/12/25 15:47
 * @Version 1.0
 */
public class CollectionUtils {

    public static List<?> tearList(List<?> list, int size) {
        int total = list.size();

        if(total <= DefaultConstants.DEFAULT_BATCH_SIZE){
            // TODO
        }else{
            // 拆分集合，每个大小为500
            int limit = total / DefaultConstants.DEFAULT_BATCH_SIZE;
            // 计算剩余集合大小
            int residue = total % DefaultConstants.DEFAULT_BATCH_SIZE;
            // 开始批量入库
            for(int i = 0 ; i < limit; i ++){
                // doInsert(clazzPath, list.subList(i * 500, 500 * (i + 1)));
            }
            if(residue > 0){
                // doInsert(clazzPath, list.subList(limit * 500, total));
            }
        }
        return null;
    }

    public static List<Map<String, Object>> toMaps(List<?> entityList, Field[] fields) {
        List<Map<String, Object>> list = new ArrayList<>();

        try {
            int len = fields.length;
            for (Object obj : entityList) {
                Map<String, Object> map = new ConcurrentHashMap<>(len);
                for (Field field : fields) {
                    field.setAccessible(true);
                    map.put(field.getName(), field.get(obj));
                }
                list.add(map);
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

//        try {
//            int size = entityList.size();
//            int len = fields.length;
//            for (int i = 0; i < size; i ++) {
//                Map<String, Object> map = new ConcurrentHashMap<>(len);
//                Object obj = entityList.get(i);
//                for (Field field : fields) {
//                    field.setAccessible(true);
//                    map.put(field.getName(), field.get(obj));
//                }
//                list.add(map);
//            }
//        } catch (IllegalArgumentException | IllegalAccessException e) {
//            e.printStackTrace();
//        }



//        entityList.stream().forEach(item -> {
//            Field[] fields = clazz.getDeclaredFields();
//            System.out.println(fields);
//            list.add(
//                    Arrays.stream(fields).collect(Collectors.toMap(Field::getName, field -> {
//                        Object obj = null;
//                        field.setAccessible(true);
//                        try {
//                            obj = field.get(item);
//                        } catch (IllegalArgumentException | IllegalAccessException e) {
//                            e.printStackTrace();
//                        }
//                        return Optional.ofNullable(obj).orElse(0);
//                    }, (k1, k2) -> k2)));
//        });
        return list;
    }
}
