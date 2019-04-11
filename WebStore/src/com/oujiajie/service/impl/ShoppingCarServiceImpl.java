package com.oujiajie.service.impl;

import com.oujiajie.dao.ShoppingCarDao;
import com.oujiajie.dao.impl.ShoppingCarDaoImpl;
import com.oujiajie.model.Shoppingcar;
import com.oujiajie.model.Shoppingitem;
import com.oujiajie.model.User;
import com.oujiajie.service.ShoppingCarService;
import com.oujiajie.utils.PageHelper;

import java.sql.SQLException;

public class ShoppingCarServiceImpl implements ShoppingCarService {

    ShoppingCarDao shoppingCarDao = new ShoppingCarDaoImpl();

    @Override
    public Shoppingcar getShoppingCar(String uid) throws SQLException {
        return shoppingCarDao.getShoppingCar(uid);
    }

    @Override
    public boolean getShopingitem(int sid, String pid, String snum) throws SQLException {
        return shoppingCarDao.getShoppingitem(sid,pid,snum);
    }

    @Override
    public PageHelper<Shoppingitem> getUserShoppingCart(User user, String num) throws SQLException {
        return shoppingCarDao.getUserShoppingCart(user,num);
    }

    @Override
    public boolean delete(String uid, String itemid) throws SQLException {
        return shoppingCarDao.delete(uid,itemid);
    }
}
