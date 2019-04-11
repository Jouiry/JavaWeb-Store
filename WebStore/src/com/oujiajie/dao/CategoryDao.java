package com.oujiajie.dao;

import com.oujiajie.model.Category;
import com.oujiajie.utils.PageHelper;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {

    public Boolean addCateGory(Category category) throws SQLException;
    public Boolean deleteCategory(int cid) throws SQLException;
    public Boolean updateCategory(Category category) throws SQLException;
    public PageHelper<Category> findAllCategory(String num) throws SQLException;
    public Boolean deleteMulti(int[] cids) throws SQLException;
    public Boolean isCategoryNameAvailable(String cname) throws SQLException;

    List<Category> findAllCategory() throws SQLException;

    boolean isExist(String cname) throws SQLException;
}
