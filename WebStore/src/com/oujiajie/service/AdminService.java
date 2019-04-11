package com.oujiajie.service;

import com.oujiajie.model.Admin;
import com.oujiajie.utils.PageHelper;

import java.sql.SQLException;

public interface AdminService {

    Admin login(String username, String password) throws SQLException;

    PageHelper<Admin> findAllAdmin(String currentPageNum) throws SQLException;

    boolean addAdmin(String username, String password) throws SQLException;

    boolean deleteOne(String aid) throws SQLException;

    boolean updateAdmin(String username, String password) throws SQLException;
}
