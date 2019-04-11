package com.oujiajie.service;

import com.oujiajie.model.Category;
import com.oujiajie.utils.PageHelper;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {

    public Boolean addCateGory(Category category) throws SQLException;
    public Boolean deleteCategory(int cid) throws SQLException;
    public Boolean updateCategory(Category category) throws SQLException;
    public PageHelper<Category> findAllCategory(String num) throws SQLException;
    public Boolean deleteMulti(int[] cids) throws SQLException;
    public Boolean isCategoryNameAvailable(String cname) throws SQLException;

    boolean isExist(String cname) throws SQLException;
}
