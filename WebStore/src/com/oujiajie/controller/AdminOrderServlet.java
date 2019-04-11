package com.oujiajie.controller;

import com.oujiajie.model.Order;
import com.oujiajie.model.Orderitem;
import com.oujiajie.model.User;
import com.oujiajie.service.OrderService;
import com.oujiajie.service.UserService;
import com.oujiajie.service.impl.OrderServiceImpl;
import com.oujiajie.service.impl.UserServiceImpl;
import com.oujiajie.utils.PageHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created By Jou
 * Date 2019/3/17 0017 Time 11:53
 */
@WebServlet(name = "Servlet11",urlPatterns = "/admin/OrderServlet")
public class AdminOrderServlet extends HttpServlet {

   OrderService orderService = new OrderServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");

        if(op != null && !op.isEmpty()) {

            switch (op) {

                case "findAllOrder":
                    findAllOrder(request,response);
                    break;
                case "delOrder":
                    delOrder(request,response);
                    break;
                case "orderDetail":
                    orderDetail(request,response);
                case "deleleitem":
                    deleleitem(request,response);
                default:
                    break;
            }
        }
    }

    private void deleleitem(HttpServletRequest request, HttpServletResponse response) {
        String itemid = request.getParameter("itemid");
        String oid = request.getParameter("oid");
        try {
            boolean result = orderService.deleteitem(itemid);
            String contextPath = request.getContextPath();
            if(result){
                response.getWriter().println("删除成功！");
                response.setHeader("refresh","1;url='"+contextPath+"/admin/OrderServlet?op=orderDetail&oid="+oid+"'");
            } else {
                response.getWriter().println("删除失败！");
                response.setHeader("refresh","1;url='"+contextPath+"/admin/OrderServlet?op=orderDetail&oid="+oid+"'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void orderDetail(HttpServletRequest request, HttpServletResponse response) {
        String oid = request.getParameter("oid");
        try {
            List<Orderitem> orderitems = orderService.findAllOrderitemByoid(oid);
            request.setAttribute("orderitems",orderitems);
            request.getRequestDispatcher("/admin/order/orderDetails.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void delOrder(HttpServletRequest request, HttpServletResponse response) {
        String oid = request.getParameter("oid");
        try {
            String contextPath = request.getContextPath();
            boolean result = orderService.deleOrder(oid);
            if(result) {
                response.getWriter().println("删除成功！");
                response.setHeader("refresh","1;url='"+contextPath+"/admin/OrderServlet?op=findAllOrder&num=1'");
            } else {
                response.getWriter().println("删除失败！");
                response.setHeader("refresh","1;url='"+contextPath+"/admin/OrderServlet?op=findAllOrder&num=1'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findAllOrder(HttpServletRequest request, HttpServletResponse response) {
        String currentPageNum = request.getParameter("num");

        try {
            PageHelper<Order> pageHelper = orderService.findAllCategory(currentPageNum);
            List<Order> allCategory = pageHelper.getCategoryList();
            request.setAttribute("orders",allCategory);
            request.setAttribute("page",pageHelper);
            request.getRequestDispatcher("/admin/order/orderList.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
