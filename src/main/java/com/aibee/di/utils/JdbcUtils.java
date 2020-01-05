package com.aibee.di.utils;

import java.sql.*;

public class JdbcUtils {

    public static Connection getConn(){
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/xxx", "root", "xxx");
        }catch(Exception e){
            System.out.println("获取连接对象失败.");
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 增删改
     *
     * @param sql    预编译SQL语句
     * @param params 参数
     * @return 受影响的记录数目
     */
    public static int excuteUpdate(String sql, Object[] params) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        int result = -1;
        try {
            connection = getConn();
            pstmt = connection.prepareStatement(sql);
            for(int h=0;h<1000;h++){
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
                pstmt.addBatch();
            }
            int[] resList=pstmt.executeBatch();
            for (Integer each:resList) {
                System.out.println(each+"------------");
            }
        } catch (SQLException e) {
            System.out.println("更新数据出现异常.");
            System.out.println(e.getMessage());
        } finally {

        }
        return result;  // 更新数据失败
    }

}
