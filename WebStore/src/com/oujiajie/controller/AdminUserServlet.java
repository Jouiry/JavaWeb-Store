package com.oujiajie.controller;

import com.oujiajie.model.User;
import com.oujiajie.service.UserService;
import com.oujiajie.service.impl.UserServiceImpl;
import com.oujiajie.utils.PageHelper;
import com.oujiajie.utils.SendJMail;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created By Jou
 * Date 2019/3/17 0017 Time 11:16
 */
@WebServlet(name = "Servlet10",urlPatterns = "/admin/UserServlet")
public class AdminUserServlet extends HttpServlet {

    UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");

        if(op != null && !op.isEmpty()) {

            switch (op) {

                case "findAllUser":
                    findAllUser(request,response);
                    break;
                case "adduser":
                    adduser(request,response);
                    break;
                case "isExist":
                    isExist(request,response);
                default:
                    break;
            }
        }
    }

    private void isExist(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        try {
            boolean exist = userService.isExist(username);
            if(!exist) {
                response.getWriter().print("exist");
            } else {
                response.getWriter().print("false");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void adduser(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        try {
            BeanUtils.populate(user,request.getParameterMap());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String updateTime = df.format(new Date());
        user.setUpdatetime(updateTime);
        String contextPath = request.getContextPath();
        try{
            boolean regist = userService.regist(user);
            if(regist) {
                response.getWriter().println("添加成功");
                response.setHeader("refresh","1;url='"+contextPath+"/admin/user/addUser.jsp'");
            } else {
                response.getWriter().println("用户名重复，请重复输入");
                response.setHeader("refresh","1;url='"+contextPath+"/admin/user/addUser.jsp'");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void findAllUser(HttpServletRequest request, HttpServletResponse response) {
        String currentPageNum = request.getParameter("num");

        try {
            PageHelper<User> pageHelper = userService.findAllCategory(currentPageNum);
            List<User> allCategory = pageHelper.getCategoryList();
            request.setAttribute("users",allCategory);
            request.setAttribute("page",pageHelper);
            request.getRequestDispatcher("/admin/user/userList.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
