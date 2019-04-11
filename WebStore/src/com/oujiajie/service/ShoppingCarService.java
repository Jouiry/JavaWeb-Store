package com.oujiajie.service;

import com.oujiajie.model.Shoppingcar;
import com.oujiajie.model.Shoppingitem;
import com.oujiajie.model.User;
import com.oujiajie.utils.PageHelper;

import java.sql.SQLException;

public interface ShoppingCarService {
    Shoppingcar getShoppingCar(String uid) throws SQLException;

    boolean getShopingitem(int sid, String pid, String snum) throws SQLException;


    PageHelper<Shoppingitem> getUserShoppingCart(User user, String num) throws SQLException;

    boolean delete(String uid, String itemid) throws SQLException;
}
