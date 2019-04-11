package com.oujiajie.controller;

import com.oujiajie.model.Admin;
import com.oujiajie.service.AdminService;
import com.oujiajie.service.impl.AdminServiceImpl;
import com.oujiajie.utils.PageHelper;
import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "Servlet3",urlPatterns = "/admin/AdminServlet")
public class AdminServlet extends HttpServlet {

    AdminService adminService = new AdminServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String op = request.getParameter("op");

        if(op != null && !op.isEmpty()) {

            switch (op) {
                case "addAdmin":
                    addAdmin(request,response);
                    break;
                case "deleteOne":
                    deleteOne(request,response);
                    break;
                case "findAllAdmin":
                    findAllAdmin(request,response);
                    break;
                case "updateAdmin":
                    updateAdmin(request,response);
                    break;
                case "login":
                    login(request,response);
                    break;
                case "logout":
                    logout(request,response);
                default:
                    break;
            }

        }


    }

    private void logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        String contextPath = request.getContextPath();
        try {
            response.sendRedirect(contextPath + "/admin/index.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String contextPath = request.getContextPath();
        try {
            if(username == null || password == null || username.isEmpty() || password.isEmpty()) {
                response.getWriter().println("用户名密码不能为空！");
                response.setHeader("refresh","1;url='"+contextPath+"/admin/index.jsp'");
            }
            Admin admin = adminService.login(username, password);
            if(admin != null) {
                request.getSession().setAttribute("admin",admin);
                request.getRequestDispatcher("/admin/main.jsp").forward(request, response);
            } else {
                response.getWriter().println("用户名或密码有误！请重写输入！");
                response.setHeader("refresh","1;url='"+contextPath+"/admin/index.jsp'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void deleteOne(HttpServletRequest request, HttpServletResponse response) {
        String aid = request.getParameter("aid");
        try {
            boolean result = adminService.deleteOne(aid);
            if(result) {
                String contextPath = request.getContextPath();
                response.getWriter().println("删除成功！");
                response.setHeader("refresh","1;url='"+contextPath+"/admin/AdminServlet?op=findAllAdmin&num=1'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateAdmin(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password1 = request.getParameter("password1");
        String contextPath = request.getContextPath();
        try {
            if(username.isEmpty() || password.isEmpty() || password1.isEmpty() || username == null || password == null || password1 == null) {
                response.getWriter().println("密码不能为空！");
                request.getRequestDispatcher("/admin/admin/updateAdmin.jsp").forward(request, response);
            } else if(!password.equals(password1)){
                response.getWriter().println("密码不一致！");
                request.getRequestDispatcher("/admin/admin/updateAdmin.jsp").forward(request, response);
            }
            boolean a = adminService.updateAdmin(username,password);
            if(a) {
                response.getWriter().println("修改成功！");
                response.setHeader("refresh","1;url='"+contextPath+"/admin/AdminServlet?op=findAllAdmin&num=1'");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void findAllAdmin(HttpServletRequest request, HttpServletResponse response) {

        String currentPageNum = request.getParameter("num");

        try {
            PageHelper<Admin> pageHelper = adminService.findAllAdmin(currentPageNum);
            List<Admin> allCategory = pageHelper.getCategoryList();
            request.setAttribute("admins",allCategory);
            request.setAttribute("page",pageHelper);
            request.getRequestDispatcher("/admin/admin/adminList.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void addAdmin(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password1 = request.getParameter("password1");
        String contextPath = request.getContextPath();
        try {
            if(username.isEmpty() || password.isEmpty() || password1.isEmpty() || username == null || password == null || password1 == null) {
                response.getWriter().println("用户名或密码不能为空！");
                response.setHeader("refresh","1;url='"+contextPath+"/admin/admin/addAdmin.jsp'");
            } else if(!password.equals(password1)){
                response.getWriter().println("密码不一致！");
                response.setHeader("refresh","1;url='"+contextPath+"/admin/admin/addAdmin.jsp'");
            }
            boolean a = adminService.addAdmin(username,password);
            if(a) {
                response.getWriter().println("添加成功！");
                response.setHeader("refresh","1;url='"+contextPath+"/admin/admin/addAdmin.jsp'");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
