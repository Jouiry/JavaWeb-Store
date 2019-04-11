package com.oujiajie.dao;

import com.oujiajie.model.Order;
import com.oujiajie.model.Orderitem;
import com.oujiajie.utils.PageHelper;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    boolean updateOrderState(String oid, String state) throws SQLException;
    List<Order> findAllProduct(int uid) throws SQLException;
    Order findOrderById(String oid);

    int getBuyNum(String pid) throws SQLException;

    boolean placeOrder(int oid, String pid, int buynum) throws SQLException;

    boolean placeOrder(Order order) throws SQLException;

    List<Orderitem> getorderitem(String oid) throws SQLException;

    PageHelper<Order> findAllOrder(String currentPageNum) throws SQLException;

    boolean deleteOrder(String oid);

    List<Orderitem> findAllOrderitemByoid(String oid) throws SQLException;

    boolean deleteitem(String itemid) throws SQLException;
}
