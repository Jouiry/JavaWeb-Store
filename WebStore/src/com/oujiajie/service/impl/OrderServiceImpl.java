package com.oujiajie.service.impl;

import com.oujiajie.dao.OrderDao;
import com.oujiajie.dao.ProductDao;
import com.oujiajie.dao.ShoppingCarDao;
import com.oujiajie.dao.impl.OrderDaoImpl;
import com.oujiajie.dao.impl.ProductDaoImpl;
import com.oujiajie.dao.impl.ShoppingCarDaoImpl;
import com.oujiajie.model.Order;
import com.oujiajie.model.Orderitem;
import com.oujiajie.model.Shoppingcar;
import com.oujiajie.service.OrderService;
import com.oujiajie.utils.PageHelper;
import com.oujiajie.utils.TransactionUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {

    OrderDao orderDao = new OrderDaoImpl();
    ProductDao productDao = new ProductDaoImpl();
    ShoppingCarDao shoppingCarDao = new ShoppingCarDaoImpl();
    @Override
    public boolean placeOrder(Order order, String[] ids) {
        boolean result = true;
        try {
            TransactionUtils.startTransaction();
            Connection connection = TransactionUtils.getConnection();
            boolean createorder = orderDao.placeOrder(order);
            if(createorder) {
                for (String pid : ids) {
                    int buynum = orderDao.getBuyNum(pid);
                    boolean placeOrder = orderDao.placeOrder(order.getOid(),pid,buynum);
                    Shoppingcar shoppingCar = shoppingCarDao.getShoppingCar(String.valueOf(order.getUid()));
                    boolean deleteshoppingitem = shoppingCarDao.deleteshoppingitem(shoppingCar.getSid(),pid);
                    boolean deleteproductNum = productDao.deleteProductNum(pid,buynum);
                    if(!placeOrder || !deleteshoppingitem || !deleteproductNum) {
                        result = false;
                        connection.rollback();
                        break;
                    }
                }
                connection.commit();
                connection.close();
                TransactionUtils.removeConnection();
            } else {
                result = false;
                connection.rollback();
            }

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
    public boolean updateOrderState(int state) {
        return false;
    }

    @Override
    public List<Order> findAllProduct(int uid) throws SQLException {

        return orderDao.findAllProduct(uid);
    }

    @Override
    public Order findOrderById(String oid) {
        return null;
    }

    @Override
    public Order createOrder(String recipients, String tel, String address, int uid, String money){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String orderTime = df.format(new Date());
        UUID uuid = UUID.randomUUID();
        int oid = uuid.hashCode();
        if(oid < 0) {
            oid = -oid;
        }
        Order order = new Order();
        order.setOid(oid);
        order.setMoney(Double.parseDouble(money));
        order.setAddress(address);
        order.setTel(tel);
        order.setOrdertime(orderTime);
        order.setRecipients(recipients);
        order.setState(1);
        order.setUid(uid);
        return order;
    }

    @Override
    public boolean cancelOrder(String oid, String state) {
        boolean result = true;
        try {
            TransactionUtils.startTransaction();
            Connection connection = TransactionUtils.getConnection();
            boolean updateOrderState = orderDao.updateOrderState(oid, state);
            if(updateOrderState) {
                List<Orderitem> orderitemList = orderDao.getorderitem(oid);
                for (Orderitem orderitem : orderitemList) {
                    boolean reback = productDao.rebackNum(Integer.parseInt(orderitem.getPid()),orderitem.getBuynum());
                    if(!reback) {
                        result = false;
                        connection.rollback();
                        break;
                    }
                }
                connection.commit();
                connection.close();
                TransactionUtils.removeConnection();
            } else {
                result = false;
                connection.rollback();
            }
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
    public PageHelper<Order> findAllCategory(String currentPageNum) throws SQLException {
        return orderDao.findAllOrder(currentPageNum);
    }

    @Override
    public boolean deleOrder(String oid){
        return orderDao.deleteOrder(oid);
    }

    @Override
    public List<Orderitem> findAllOrderitemByoid(String oid) throws SQLException {
        return orderDao.findAllOrderitemByoid(oid);
    }

    @Override
    public boolean deleteitem(String itemid) throws SQLException {
        return orderDao.deleteitem(itemid);
    }
}
