package com.oujiajie.service;

import com.oujiajie.model.Category;
import com.oujiajie.model.Product;
import com.oujiajie.utils.PageHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    
    Boolean addProduct(Product product) throws SQLException;
    Boolean deleteProduct(String pid) throws SQLException;
    Product findProduct(String pid) throws SQLException;
    PageHelper<Product> findAllProduct(String num,String pid,String pname,String cid,String minprice,String maxprice) throws SQLException;
    Product getProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException;
    List<Category> getCategories() throws SQLException;

    boolean isExist(String pid) throws SQLException;
}
