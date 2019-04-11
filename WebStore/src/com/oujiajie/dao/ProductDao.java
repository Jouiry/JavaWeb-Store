package com.oujiajie.dao;

import com.oujiajie.model.Category;
import com.oujiajie.model.Product;
import com.oujiajie.utils.PageHelper;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    Boolean addProduct(Product product) throws SQLException;
    Boolean deleteProduct(String pid) throws SQLException;
    Product findProduct(String pid) throws SQLException;
    PageHelper<Product> findAllProduct(String num,String pid,String pname,String cid,String minprice,String maxprice) throws SQLException;
    List<Category> getCategories() throws SQLException;

    boolean isExist(String pid) throws SQLException;

    boolean deleteProductNum(String pid, int buynum) throws SQLException;

    boolean rebackNum(int itemid, int buynum) throws SQLException;
}
