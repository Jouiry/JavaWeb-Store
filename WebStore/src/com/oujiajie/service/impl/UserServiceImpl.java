package com.oujiajie.service.impl;

import com.oujiajie.dao.UserDao;
import com.oujiajie.dao.impl.UserDaoImpl;
import com.oujiajie.model.User;
import com.oujiajie.service.UserService;
import com.oujiajie.utils.PageHelper;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    UserDao userDao = new UserDaoImpl();

    @Override
    public User login(String username, String password) throws SQLException {
        return userDao.login(username,password);
    }

    @Override
    public boolean regist(User user) throws SQLException {
        return userDao.regist(user);
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

    @Override
    public boolean isExist(String username) throws SQLException {
        return userDao.isExist(username);
    }

    @Override
    public PageHelper<User> findAllCategory(String currentPageNum) throws SQLException {

        return userDao.findAllUser(currentPageNum);
    }

    @Override
    public boolean getUser(String uid) throws SQLException {
        return userDao.getUser(uid);
    }
}
