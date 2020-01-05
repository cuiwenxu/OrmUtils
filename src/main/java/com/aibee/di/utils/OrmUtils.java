package com.aibee.di.utils;

import com.aibee.di.model.Table;

import java.lang.reflect.Field;

public class OrmUtils {

    /*---------------------------------添加对象到数据库---------------------------------*/

    /**
     * 添加一个对象
     *
     * @param element 要添加的对象
     * @return 添加成功返回 1，否则返回 0
     */
//    public int add(E element) {
//        if (element == null)
//            throw new IllegalArgumentException("插入的元素为空.");
//        Class clazz = element.getClass();
//        String tableName = getTableName(clazz);
//        Field[] fields = clazz.getDeclaredFields();
//        if (fields == null || fields.length == 0)
//            throw new RuntimeException(element + "没有属性.");
//        String sql = getInsertSql(tableName, fields.length);
//        Object[] params = getSqlParams(element, fields);
//        System.out.println("insertSql = " + sql);
//        System.out.println(Arrays.toString(params));
//        return JdbcUtils.excuteUpdate(sql, params);
//    }

    /**
     * 根据对象获取sql语句的参数
     *
     * @param element 值对象
     * @param fields  值对象包含的Field
     * @return sql 的参数
     */
    public Object[] getSqlParams(Object element, Field[] fields) {
        Object[] params = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            try {
                params[i] = fields[i].get(element);
            } catch (IllegalAccessException e) {
                System.out.println(e.getMessage());
                System.out.println("获取" + element + "的属性值失败！");
                e.printStackTrace();
            }
        }
        return params;
    }

    /**
     * 插入对象的sql语句
     *
     * @param tableName 表名称
     * @param length    字段长度
     * @return 插入记录的sql语句
     */
    public String getInsertSql(String tableName, Field[] fields, int length) {
        StringBuilder sql = new StringBuilder();
        StringBuilder insertSQL = sql.append("insert into ").append(tableName).append(" (");
        int index = 1;
        for (Field f : fields) {
            if (index < fields.length) {
                index++;
                insertSQL = insertSQL.append(f.getName()).append(",");
            } else {
                insertSQL = insertSQL.append(f.getName()).append(")");
            }
        }
        insertSQL.append(" values(");
        for (int i = 0; i < length; i++)  // 添加参数占位符?
            sql.append("?,");
        sql.deleteCharAt(sql.length() - 1);
        sql.append(")");
        return sql.toString();
    }

    /**
     * 根据值对象的注解获取其对应的表名称
     *
     * @param clazz 值对象的字节码
     * @return 表名称
     */
    public String getTableName(Class<Object> clazz) {
        boolean existTableAnno = clazz.isAnnotationPresent(Table.class);
        if (!existTableAnno)
            throw new RuntimeException(clazz + " 没有Table注解.");
        Table tableAnno = (Table) clazz.getAnnotation(Table.class);
        return tableAnno.name();
    }

}
