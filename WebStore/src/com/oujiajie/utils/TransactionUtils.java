package com.oujiajie.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionUtils {

    static   ThreadLocal<Connection> threadLocal =new ThreadLocal<>();

    //开启事务

    public static void  startTransaction() throws SQLException {

        Connection connection = threadLocal.get();
        if (connection==null){

            connection = DBUtils.getConnection();
            threadLocal.set(connection);
        }

        connection.setAutoCommit(false);
    }


    //获取一个连接

    public static Connection getConnection() throws SQLException {


        Connection connection = threadLocal.get();

        if (connection==null){

            connection = DBUtils.getConnection();
            threadLocal.set(connection);
        }
        return  connection;

    }
    public static void removeConnection() {
        threadLocal.remove();
    }


}
