package com.aibee.test;

import com.aibee.di.model.TaskInfo;
import com.aibee.di.utils.JdbcUtils;
import com.aibee.di.utils.OrmUtils;

import java.lang.reflect.Field;
import java.util.Arrays;

public class TestSQL {

    public static void main(String[] args) {
        OrmUtils ormUtils = new OrmUtils();
        TaskInfo taskInfo = new TaskInfo("aegean", "suzhou", "wujiang", "2020-01-02", "test", 1);
        Class clazz = taskInfo.getClass();
        String tableName = "taskinfo";
        Field[] fields = clazz.getDeclaredFields();
        for (Field each : fields) {
            System.out.println(each.getName());
        }
        if (fields == null || fields.length == 0)
            throw new RuntimeException(ormUtils + "没有属性.");
        String sql = ormUtils.getInsertSql(tableName, fields, fields.length);
        Object[] params = ormUtils.getSqlParams(taskInfo, fields);
        System.out.println("insertSql = " + sql);
        System.out.println(Arrays.toString(params));
        JdbcUtils.excuteUpdate(sql, params);
    }


}
