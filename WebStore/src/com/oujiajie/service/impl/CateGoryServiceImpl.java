package com.oujiajie.service.impl;

import com.oujiajie.dao.CategoryDao;
import com.oujiajie.dao.impl.CategoryDaoImpl;
import com.oujiajie.model.Category;
import com.oujiajie.service.CategoryService;
import com.oujiajie.utils.PageHelper;

import java.sql.SQLException;
import java.util.List;

public class CateGoryServiceImpl implements CategoryService {

    CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public Boolean addCateGory(Category category) throws SQLException {
        if(!isCategoryNameAvailable(category.getCname())) return false;
        return categoryDao.addCateGory(category);
    }

    @Override
    public Boolean deleteCategory(int cid) throws SQLException {

        return categoryDao.deleteCategory(cid);
    }

    @Override
    public Boolean updateCategory(Category category) throws SQLException {
        if(!isCategoryNameAvailable(category.getCname())) return false;
        return categoryDao.updateCategory(category);
    }

    @Override
    public PageHelper<Category> findAllCategory(String num) throws SQLException {

        return categoryDao.findAllCategory(num);
    }

    @Override
    public Boolean deleteMulti(int[] cids) throws SQLException {

        return categoryDao.deleteMulti(cids);
    }

    @Override
    public Boolean isCategoryNameAvailable(String cname) throws SQLException {
        return categoryDao.isCategoryNameAvailable(cname);
    }

    @Override
    public boolean isExist(String cname) throws SQLException {
        return categoryDao.isExist(cname);
    }

}
