package com.oujiajie.controller;

import com.oujiajie.model.Order;
import com.oujiajie.model.Shoppingcar;
import com.oujiajie.model.Shoppingitem;
import com.oujiajie.model.User;
import com.oujiajie.service.OrderService;
import com.oujiajie.service.ShoppingCarService;
import com.oujiajie.service.impl.OrderServiceImpl;
import com.oujiajie.service.impl.ShoppingCarServiceImpl;
import com.oujiajie.utils.DBUtils;
import com.oujiajie.utils.PageHelper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "Servlet9",urlPatterns = "/OrderServlet")
public class OrderServlet extends HttpServlet {

    OrderService orderService = new OrderServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");

        if(op != null && !op.isEmpty()) {

            switch (op) {

                case "placeOrder":
                    placeOrder(request,response);
                    break;
                case "createOrder":
                    createOrder(request,response);
                    break;
                case "myoid":
                    myoid(request,response);
                    break;
                case "cancelOrder":
                    cancelOrder(request,response);
                default:
                    break;
            }
        }


    }

    private void cancelOrder(HttpServletRequest request, HttpServletResponse response) {
        String oid = request.getParameter("oid");
        String state = request.getParameter("state");
        boolean result = orderService.cancelOrder(oid,state);
        String contextPath = request.getContextPath();
        try {
            if(result) {
                response.getWriter().println("订单已取消！");
            } else {
                response.getWriter().println("订单取消失败！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setHeader("refresh","1;url='"+contextPath+"/OrderServlet?op=myoid'");
    }

    private void myoid(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        try {
            List<Order> allProduct = orderService.findAllProduct(user.getUid());
            request.setAttribute("orders",allProduct);
            request.getRequestDispatcher("/myOrders.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createOrder(HttpServletRequest request, HttpServletResponse response) {
        ShoppingCarService shoppingCarService = new ShoppingCarServiceImpl();
        String num = request.getParameter("num");
        if(num == null || num.isEmpty()) {
            num = "1";
        }
        User user = (User) request.getSession().getAttribute("user");
        String contextPath = request.getContextPath();
        try {
            if(user.getState() == 0) {
                response.getWriter().println("用户还没有激活，无法购买商品，请前往邮箱激活");
                response.setHeader("refresh","1;url='"+contextPath+"/user/personal.jsp'");
                return;
            }
            QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
            Shoppingcar shoppingcar = queryRunner.query("select * from shoppingcar  where uid = ?;", new BeanHandler<>(Shoppingcar.class), user.getUid());
            PageHelper<Shoppingitem> pageHelper = shoppingCarService.getUserShoppingCart(user, num);
            shoppingcar.setShoppingItems(pageHelper.getCategoryList());
            request.setAttribute("shoppingCar",shoppingcar);
            request.setAttribute("page",pageHelper);
            request.getRequestDispatcher("/placeOrder.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void placeOrder(HttpServletRequest request, HttpServletResponse response) {
        String money = request.getParameter("money");
        String recipients = request.getParameter("recipients");
        String tel = request.getParameter("tel");
        String address = request.getParameter("address");
        User user = (User) request.getSession().getAttribute("user");
        String[] ids = request.getParameterValues("ids");
        int uid = user.getUid();
        Order order = orderService.createOrder(recipients,tel,address,uid,money);
        boolean placeOrder = orderService.placeOrder(order, ids);
        String contextPath = request.getContextPath();
        try{
            if(placeOrder) {
                response.getWriter().println("下单成功");
                response.setHeader("refresh","1;url='"+contextPath+"/OrderServlet?op=myoid'");
            } else {
                response.getWriter().println("下单失败，请重新下单！");
                response.setHeader("refresh","1;url='"+contextPath+"/CartServlet?op=findCart'");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
