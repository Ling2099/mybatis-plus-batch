package com.huoguo.mybatisplus.batch.demo;

import com.huoguo.mybatisplus.batch.model.Splicer;
import com.huoguo.mybatisplus.batch.service.BatchService;
import com.huoguo.mybatisplus.batch.service.impl.BatchServiceImpl;
import com.huoguo.mybatisplus.batch.template.child.BatchInsertTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 2222
 * @author 1111
 */
public class Test {

    public static void main(String[] args) {
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
//
//        BatchService batchService = new BatchServiceImpl();
//        batchService.deleteBatch(list, User.class);

//        List<User> list = new ArrayList<>();
//        User u = new User();
//        u.setRelationId(1L);
//        list.add(u);
//
//
//        User u2 = new User();
//        u2.setRelationId(1L);
//        list.add(u2);
//
//        User u3 = new User();
//        u3.setRelationId(1L);
//        list.add(u3);

        BatchService batchService = new BatchServiceImpl();
        // batchService.deleteBatch(list);

         Splicer splicer = new Splicer().where().and("relation_id", "").and("name", "王国华");
         batchService.deleteBatch(list, splicer, User.class);
    }
}
