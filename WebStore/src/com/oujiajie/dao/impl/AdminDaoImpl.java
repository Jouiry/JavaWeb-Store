package com.oujiajie.dao.impl;

import com.oujiajie.dao.AdminDao;
import com.oujiajie.model.Admin;
import com.oujiajie.utils.DBUtils;
import com.oujiajie.utils.PageHelper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class AdminDaoImpl implements AdminDao {


    @Override
    public PageHelper<Admin> findAllAdmin(String num) throws SQLException {
        int currentPageNum = Integer.parseInt(num);
        PageHelper<Admin> pageHelper = new PageHelper<>();

        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        List<Admin> allquery = queryRunner.query("select * from `admin`;", new BeanListHandler<>(Admin.class));

        int limitNumber = 2;
        int size = allquery.size();
        int pageNum = (size/limitNumber) + (size % limitNumber == 0 ? 0 : 1);
        if (currentPageNum < 1) currentPageNum = 1;
        if (currentPageNum > pageNum) currentPageNum = pageNum;
        int offset = (currentPageNum - 1) * limitNumber;
        pageHelper.setTotalPageNum(pageNum);
        pageHelper.setTotalRecordsNum(size);
        pageHelper.setPrevPageNum(currentPageNum-1);
        pageHelper.setNextPageNum(currentPageNum+1);
        List<Admin> query = queryRunner.query("select * from `admin` limit ?,?;", new BeanListHandler<>(Admin.class),offset,limitNumber);
        pageHelper.setCategoryList(query);
        pageHelper.setCurrentPageNum(currentPageNum);
        return pageHelper;
    }

    @Override
    public boolean addAdmin(String username, String password) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        int update = queryRunner.update("insert into `admin` values (null,?,?);", username, password);
        return update == 1;
    }

    @Override
    public boolean deleteOne(String aid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        int update = queryRunner.update("delete from `admin` where aid = ?;", aid);
        return update == 1;
    }

    @Override
    public boolean updateAdmin(String username, String password) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        int update = queryRunner.update("update `admin` set password = ? where username = ?;", password,username);
        return update == 1;
    }

    @Override
    public Admin login(String username, String password) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        Admin update = queryRunner.query("select * from `admin` where username = ? and password = ?;",new BeanHandler<>(Admin.class), username,password);
        return update;
    }
}
