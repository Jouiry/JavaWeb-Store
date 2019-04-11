package com.oujiajie.dao.impl;

import com.oujiajie.dao.CategoryDao;
import com.oujiajie.model.Category;
import com.oujiajie.utils.DBUtils;
import com.oujiajie.utils.PageHelper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;


import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    @Override
    public Boolean addCateGory(Category category) throws SQLException {

        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        int update = queryRunner.update("insert into category values (null,?);", category.getCname());

        return update == 1;
    }

    @Override
    public Boolean deleteCategory(int cid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        int update = queryRunner.update("delete from category where cid = ?;", cid);
        return update == 1;
    }

    @Override
    public Boolean updateCategory(Category category) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        int update = queryRunner.update(" update category set cname = ? where cid = ?;", category.getCname(), category.getCid());
        return update == 1;
    }

    @Override
    public PageHelper<Category> findAllCategory(String num) throws SQLException {
        int currentPageNum = Integer.parseInt(num);
        PageHelper<Category> pageHelper = new PageHelper<>();

        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        List<Category> allquery = queryRunner.query("select * from category;", new BeanListHandler<>(Category.class));

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
        List<Category> query = queryRunner.query("select * from category limit ?,?;", new BeanListHandler<>(Category.class),offset,limitNumber);
        pageHelper.setCategoryList(query);
        pageHelper.setCurrentPageNum(currentPageNum);



        return pageHelper;
    }


    @Override
    public Boolean deleteMulti(int[] cids) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        int update = -1;
        for (int cid : cids) {
            update = queryRunner.update("delete from category where cid = ?;", cid);
        }
        return update != -1;
    }

    @Override
    public Boolean isCategoryNameAvailable(String cname) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        Category query = queryRunner.query("select * from category where cname = ?;", new BeanHandler<>(Category.class), cname);
        return query == null;
    }

    @Override
    public List<Category> findAllCategory() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        List<Category> query = queryRunner.query("select * from category;", new BeanListHandler<Category>(Category.class));
        return query;
    }

    @Override
    public boolean isExist(String cname) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        Category query = queryRunner.query("select * from category where cname = ?;", new BeanHandler<Category>(Category.class),cname);
        return query != null;
    }
}
