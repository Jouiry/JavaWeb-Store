package com.oujiajie.dao;

import com.oujiajie.model.User;
import com.oujiajie.utils.PageHelper;

import java.sql.SQLException;

public interface UserDao {

    User login (String username, String password) throws SQLException;
    boolean regist(User user) throws SQLException;
    boolean updateUser(User user);

    boolean isExist(String username) throws SQLException;

    PageHelper<User> findAllUser(String currentPageNum) throws SQLException;

    boolean getUser(String uid) throws SQLException;
}
