package com.oujiajie.dao.impl;

import com.oujiajie.dao.ShoppingCarDao;
import com.oujiajie.model.Product;
import com.oujiajie.model.Shoppingcar;
import com.oujiajie.model.Shoppingitem;
import com.oujiajie.model.User;
import com.oujiajie.utils.DBUtils;
import com.oujiajie.utils.PageHelper;
import com.oujiajie.utils.TransactionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.*;

public class ShoppingCarDaoImpl implements ShoppingCarDao {
    @Override
    public Shoppingcar getShoppingCar(String uid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());

        Shoppingcar query = queryRunner.query("select * from shoppingcar  where uid = ?;", new BeanHandler<>(Shoppingcar.class), uid);

        if(query == null) {
            int update = queryRunner.update("insert into shoppingcar values (null,?)", uid);
            Shoppingcar query1 = queryRunner.query("select * from shoppingcar  where uid = ?;", new BeanHandler<>(Shoppingcar.class), uid);
            return query1;
        }

        return query;

    }

    @Override
    public boolean getShoppingitem(int sid, String pid,String snum) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        int num = Integer.parseInt(snum);
        Shoppingitem shoppingitem = queryRunner.query("select * from shoppingitem where sid = ? and pid = ?;", new BeanHandler<>(Shoppingitem.class), sid, pid);
        int update = 0;
        if(shoppingitem== null) {
            update = queryRunner.update("insert into shoppingitem values (null,?,?,?);", sid, pid,snum);
            return update == 1;
        }
        num += shoppingitem.getSnum();
        update = queryRunner.update("update shoppingitem set snum = ? where sid = ? and pid = ?;", num, sid, pid);
        return update == 1;
    }

    @Override
    public PageHelper<Shoppingitem> getUserShoppingCart(User user,String num) throws SQLException {
        int currentPageNum = Integer.parseInt(num);
        PageHelper<Shoppingitem> pageHelper = new PageHelper<>();
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        Shoppingcar shoppingcar = queryRunner.query("select * from shoppingcar  where uid = ?;", new BeanHandler<>(Shoppingcar.class), user.getUid());
        List<Shoppingitem> shoppingitems1 = queryRunner.query("select * from shoppingitem where sid = ?;", new BeanListHandler<>(Shoppingitem.class), shoppingcar.getSid());
        int limitNumber = 6;
        int size = shoppingitems1.size();
        int pageNum = (size/limitNumber) + (size % limitNumber == 0 ? 0 : 1);
        if (currentPageNum > pageNum) currentPageNum = pageNum;
        if (currentPageNum < 1) currentPageNum = 1;
        int offset = (currentPageNum - 1) * limitNumber;
        pageHelper.setTotalPageNum(pageNum);
        pageHelper.setTotalRecordsNum(size);
        pageHelper.setPrevPageNum(currentPageNum-1);
        pageHelper.setNextPageNum(currentPageNum+1);
        List<Shoppingitem> shoppingitems = queryRunner.query("select * from shoppingitem where sid = ? limit ?,?;", new BeanListHandler<>(Shoppingitem.class),shoppingcar.getSid(),offset, limitNumber);
        for (Shoppingitem shoppingitem : shoppingitems) {
            Product product = queryRunner.query("select * from product where pid = ?;", new BeanHandler<>(Product.class), shoppingitem.getPid());
            shoppingitem.setProduct(product);
        }
        pageHelper.setCategoryList(shoppingitems);
        pageHelper.setCurrentPageNum(currentPageNum);
        return pageHelper;

    }

    @Override
    public boolean delete(String uid, String itemid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        int update = queryRunner.update("delete from shoppingitem where itemid = ?;", itemid);
        return update == 1;

    }

    @Override
    public boolean deleteshoppingitem(int sid, String pid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        int update = queryRunner.update(TransactionUtils.getConnection(), "delete from shoppingitem where sid = ? and pid = ?;", sid, pid);
        return update == 1;
    }


}
