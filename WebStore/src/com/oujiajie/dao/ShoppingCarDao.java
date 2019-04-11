package com.oujiajie.dao;

import com.oujiajie.model.Shoppingcar;
import com.oujiajie.model.Shoppingitem;
import com.oujiajie.model.User;
import com.oujiajie.utils.PageHelper;

import java.sql.SQLException;

public interface ShoppingCarDao {
    Shoppingcar getShoppingCar(String uid) throws SQLException;

    boolean getShoppingitem(int sid, String pid, String snum) throws SQLException;

    PageHelper<Shoppingitem> getUserShoppingCart(User user, String num) throws SQLException;

    boolean delete(String uid, String itemid) throws SQLException;


    boolean deleteshoppingitem(int sid, String pid) throws SQLException;
}
