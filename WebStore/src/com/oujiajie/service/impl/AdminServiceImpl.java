package com.oujiajie.service.impl;

import com.oujiajie.dao.AdminDao;
import com.oujiajie.dao.impl.AdminDaoImpl;
import com.oujiajie.model.Admin;
import com.oujiajie.service.AdminService;
import com.oujiajie.utils.PageHelper;

import java.sql.SQLException;

public class AdminServiceImpl implements AdminService {

    AdminDao adminDao = new AdminDaoImpl();

    @Override
    public Admin login(String username, String password) throws SQLException {
        return adminDao.login(username,password);
    }

    @Override
    public PageHelper<Admin> findAllAdmin(String currentPageNum) throws SQLException {
        return adminDao.findAllAdmin(currentPageNum);
    }

    @Override
    public boolean addAdmin(String username, String password) throws SQLException {
        return adminDao.addAdmin(username,password);
    }

    @Override
    public boolean deleteOne(String aid) throws SQLException {
        return adminDao.deleteOne(aid);
    }

    @Override
    public boolean updateAdmin(String username, String password) throws SQLException {
        return adminDao.updateAdmin(username,password);
    }
}
