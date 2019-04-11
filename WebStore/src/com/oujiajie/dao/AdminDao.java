package com.oujiajie.dao;

import com.oujiajie.model.Admin;
import com.oujiajie.utils.PageHelper;

import java.sql.SQLException;

public interface AdminDao {

    PageHelper<Admin> findAllAdmin(String currentPageNum) throws SQLException;

    boolean addAdmin(String username, String password) throws SQLException;

    boolean deleteOne(String aid) throws SQLException;

    boolean updateAdmin(String username, String password) throws SQLException;

    Admin login(String username, String password) throws SQLException;
}
