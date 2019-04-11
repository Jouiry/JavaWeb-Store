package com.oujiajie.service;

import com.oujiajie.model.Order;
import com.oujiajie.model.Orderitem;
import com.oujiajie.utils.PageHelper;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {

    boolean placeOrder(Order order, String[] ids);
    boolean updateOrderState(int state);
    List<Order> findAllProduct(int uid) throws SQLException;
    Order findOrderById(String oid);

    Order createOrder(String recipients, String tel, String address, int uid, String money);

    boolean cancelOrder(String oid, String state);

    PageHelper<Order> findAllCategory(String currentPageNum) throws SQLException;

    boolean deleOrder(String oid) throws SQLException;

    List<Orderitem> findAllOrderitemByoid(String oid) throws SQLException;

    boolean deleteitem(String itemid) throws SQLException;
}
