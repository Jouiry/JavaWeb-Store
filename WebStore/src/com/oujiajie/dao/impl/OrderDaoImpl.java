package com.oujiajie.dao.impl;

import com.oujiajie.dao.OrderDao;
import com.oujiajie.model.Order;
import com.oujiajie.model.Orderitem;
import com.oujiajie.model.Shoppingitem;
import com.oujiajie.model.User;
import com.oujiajie.utils.DBUtils;
import com.oujiajie.utils.PageHelper;
import com.oujiajie.utils.TransactionUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl implements OrderDao {


    @Override
    public boolean updateOrderState(String oid, String state) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        Connection connection = TransactionUtils.getConnection();
        int update = queryRunner.update(connection, "update `order` set state = ? where oid = ?;", state, oid);
        return update == 1;
    }

    @Override
    public List<Order> findAllProduct(int uid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        List<Order> orders= queryRunner.query( "select * from `order` where uid = ?;", new BeanListHandler<>(Order.class), uid);

        return orders;
    }

    @Override
    public Order findOrderById(String oid) {
        return null;
    }

    @Override
    public int getBuyNum(String pid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        Connection connection = TransactionUtils.getConnection();
        Shoppingitem shoppingitem = queryRunner.query(connection, "select * from shoppingitem where pid = ?;", new BeanHandler<>(Shoppingitem.class), pid);
        return shoppingitem.getSnum();

    }

    @Override
    public boolean placeOrder(int oid, String pid, int buynum) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        Connection connection = TransactionUtils.getConnection();
        int update = queryRunner.update(connection, "insert into orderitem values (null,?,?,?);", oid, pid, buynum);
        return update == 1;
    }

    @Override
    public boolean placeOrder(Order order) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        Connection connection = TransactionUtils.getConnection();
        int update = queryRunner.update(connection, "insert into `order` values (?,?,?,?,?,?,?,?);", order.getOid(),order.getMoney(),order.getRecipients(),order.getTel(),order.getAddress(), order.getState(),order.getOrdertime(),order.getUid());
        return update == 1;
    }

    @Override
    public List<Orderitem> getorderitem(String oid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        Connection connection = TransactionUtils.getConnection();
        List<Orderitem> orderitems = queryRunner.query(connection, "select * from orderitem where oid = ?;", new BeanListHandler<>(Orderitem.class), oid);
        return orderitems;
    }

    @Override
    public PageHelper<Order> findAllOrder(String num) throws SQLException {
        int currentPageNum = Integer.parseInt(num);
        PageHelper<Order> pageHelper = new PageHelper<>();

        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        List<Order> allquery = queryRunner.query("select * from `order`;", new BeanListHandler<>(Order.class));

        int size = allquery.size();
        int pageNum = (size/3) + (size % 3 == 0 ? 0 : 1);
        if (currentPageNum < 1) currentPageNum = 1;
        if (currentPageNum > pageNum) currentPageNum = pageNum;
        int limitNumber = 3;
        int offset = (currentPageNum - 1) * limitNumber;
        pageHelper.setTotalPageNum(pageNum);
        pageHelper.setTotalRecordsNum(size);
        pageHelper.setPrevPageNum(currentPageNum-1);
        pageHelper.setNextPageNum(currentPageNum+1);
        List<Order> query = queryRunner.query("select * from `order` limit ?,?;", new BeanListHandler<>(Order.class),offset,limitNumber);
        for (Order order : query) {
            User user = queryRunner.query("select * from user where uid = ?;", new BeanHandler<>(User.class), order.getUid());
            order.setUser(user);
        }
        pageHelper.setCategoryList(query);
        pageHelper.setCurrentPageNum(currentPageNum);

        return pageHelper;

    }

    @Override
    public boolean deleteOrder(String oid) {
        boolean result = true;
        try {
            TransactionUtils.startTransaction();
            Connection connection = TransactionUtils.getConnection();
            QueryRunner queryRunner = new QueryRunner();
            int update1 = queryRunner.update(connection, "delete from orderitem where oid = ?", oid);
            int update2 = queryRunner.update(connection,"delete from `order` where oid = ?;", oid);
            if(update2 != 1) {
                result = false;
                connection.rollback();
            }
            connection.commit();
            connection.close();
            TransactionUtils.removeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
            try {
                TransactionUtils.getConnection().rollback();
                TransactionUtils.getConnection().close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public List<Orderitem> findAllOrderitemByoid(String oid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        List<Orderitem> query = queryRunner.query("select * from orderitem where oid = ?;", new BeanListHandler<>(Orderitem.class), oid);

        return query;
    }

    @Override
    public boolean deleteitem(String itemid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        int update = queryRunner.update("delete from orderitem where itemid = ?;", itemid);
        return update == 1;
    }


}
