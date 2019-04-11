package com.oujiajie.dao.impl;

import com.oujiajie.dao.UserDao;
import com.oujiajie.model.User;
import com.oujiajie.utils.DBUtils;
import com.oujiajie.utils.PageHelper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public User login(String username, String password) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        User query = queryRunner.query("select * from user where username = ? and password = ?;", new BeanHandler<>(User.class), username, password);
        return query;
    }

    @Override
    public boolean regist(User user) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        int update = queryRunner.update("insert into user values (null,?,?,?,?,?,?,?)", user.getUsername(), user.getNickname(), user.getPassword(), user.getEmail(), user.getBirthday(), user.getUpdatetime(),user.getState());
        return update == 1;
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

    @Override
    public boolean isExist(String username) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        User query = queryRunner.query("select * from user where username = ?;", new BeanHandler<>(User.class), username);
        return query == null;
    }

    @Override
    public PageHelper<User> findAllUser(String num) throws SQLException {
        int currentPageNum = Integer.parseInt(num);
        PageHelper<User> pageHelper = new PageHelper<>();

        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        List<User> allquery = queryRunner.query("select * from user;", new BeanListHandler<>(User.class));

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
        List<User> query = queryRunner.query("select * from user limit ?,?;", new BeanListHandler<>(User.class),offset,limitNumber);
        pageHelper.setCategoryList(query);
        pageHelper.setCurrentPageNum(currentPageNum);

        return pageHelper;

    }

    @Override
    public boolean getUser(String username) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        int update = queryRunner.update("update user set state = 1 where username = ?;", username);

        return update == 1;
    }
}
