package com.oujiajie.test;

import com.oujiajie.dao.impl.ProductDaoImpl;
import com.oujiajie.dao.impl.UserDaoImpl;
import com.oujiajie.model.Product;
import com.oujiajie.utils.PageHelper;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

public class ProductTest {


    @Test
    public void productTest() throws SQLException {
        ProductDaoImpl productDao = new ProductDaoImpl();
        PageHelper<Product> allProduct = productDao.findAllProduct("1", "", "", "", "10", "40");
        System.out.println(allProduct.getCategoryList().size());

    }

    @Test
    public void isExistTest() throws SQLException {
        UserDaoImpl userDao = new UserDaoImpl();
        boolean exist = userDao.isExist("admin");
        Assert.assertTrue(!exist);
    }


}
