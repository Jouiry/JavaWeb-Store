package com.oujiajie.controller;


import com.oujiajie.model.User;
import com.oujiajie.service.UserService;
import com.oujiajie.service.impl.UserServiceImpl;
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

@WebServlet(name = "Servlet6",urlPatterns = "/UserServlet")
public class UserServlet extends HttpServlet {

    UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");

        if(op != null && !op.isEmpty()) {

            switch (op) {

                case "regist":
                    regist(request,response);
                    break;
                case "login":
                    login(request,response);
                    break;
                case "logout":
                    logout(request,response);
                    break;
                case "isExist":
                    isExist(request,response);
                case "activate":
                    activate(request,response);
                default:
                    break;
            }
        }
    }

    private void activate(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        try {
            String contextPath = request.getContextPath();
            boolean result = userService.getUser(username);
            if(result) {
                response.getWriter().println("激活成功！");
                response.setHeader("refresh","0;url='"+contextPath+"/index.jsp'");
            } else {
                response.getWriter().println("激活失败，请重新激活！");
                response.setHeader("refresh","0;url='"+contextPath+"/index.jsp'");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void isExist(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        try {
            boolean result = userService.isExist(username);
            if(result) {
                response.getWriter().print("exist");
            } else {
                response.getWriter().print("false");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        String contextPath = request.getContextPath();
        response.setHeader("refresh","0;url='"+contextPath+"/index.jsp'");
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String verifyCode = request.getParameter("verifyCode");
        String checkcode_session = (String) request.getSession().getAttribute("checkcode_session");
        String contextPath = request.getContextPath();
        try {
            if(verifyCode == null || verifyCode.isEmpty() || !verifyCode.equals(checkcode_session)) {
                response.getWriter().println("验证码错误！请重新输入！");
                response.setHeader("refresh","1;url='"+contextPath+"/user/login.jsp'");
            } else {
                User login = userService.login(username, password);
                if(login != null) {
                    response.getWriter().println("登陆成功!");
                    request.getSession().setAttribute("user",login);
                    response.setHeader("refresh","1;url='"+contextPath+"/index.jsp'");
                } else {
                    request.setAttribute("msg","用户名或密码错误");
                    request.getRequestDispatcher("/user/login.jsp").forward(request, response);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    private void regist(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        String email = request.getParameter("email");
        try {
            BeanUtils.populate(user,request.getParameterMap());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        user.setState(0);
        System.out.println(user);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String updateTime = df.format(new Date());
        user.setUpdatetime(updateTime);
        System.out.println(updateTime);// new Date()为获取当前系统时间
        try{
            boolean regist = userService.regist(user);
            if(regist) {
                response.getWriter().println("注册成功!");
                String content = "<a href=\"http://192.168.3.47/UserServlet?op=activate&username="+user.getUsername()+"\">点我激活！</a>";
                SendJMail.sendMail(email,content);
                String contextPath = request.getContextPath();
                response.setHeader("refresh","1;url='"+contextPath+"/user/login.jsp'");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
