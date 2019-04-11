package com.oujiajie.controller;

import com.oujiajie.model.Shoppingcar;
import com.oujiajie.model.Shoppingitem;
import com.oujiajie.model.User;
import com.oujiajie.service.ShoppingCarService;
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

@WebServlet(name = "Servlet7",urlPatterns = "/CartServlet")
public class CartServlet extends HttpServlet {

    ShoppingCarService shoppingCarService = new ShoppingCarServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String op = request.getParameter("op");

        if(op != null && !op.isEmpty()) {

            switch (op) {

                case "findCart":
                    findCart(request,response);
                    break;
                case "addCart":
                    addCart(request,response);
                    break;
                case "delItem":
                    delItem(request,response);
                    break;
                default:
                    break;
            }
        }

    }

    private void delItem(HttpServletRequest request, HttpServletResponse response) {
        String uid = request.getParameter("uid");
        String itemid = request.getParameter("itemid");
        try {
            boolean result = shoppingCarService.delete(uid,itemid);
            if(result)  {
                response.getWriter().println("删除成功");
                String contextPath = request.getContextPath();
                response.setHeader("refresh","1;url='"+contextPath+"/CartServlet?op=findCart'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addCart(HttpServletRequest request, HttpServletResponse response) {
        String snum = request.getParameter("snum");
        if(snum == null || snum.isEmpty()) {
            snum = "1";
        }
        String pid = request.getParameter("pid");
        String uid = request.getParameter("uid");
        try {
            Shoppingcar shoppingcar = shoppingCarService.getShoppingCar(uid);
            boolean result = shoppingCarService.getShopingitem(shoppingcar.getSid(),pid,snum);
            if(result) {
                response.getWriter().println("添加成功！");
                String contextPath = request.getContextPath();
                response.setHeader("refresh","1;url='"+contextPath+"/CartServlet?op=findCart'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void findCart(HttpServletRequest request, HttpServletResponse response) {
        String num = request.getParameter("num");
        if(num == null || num.isEmpty()) {
            num = "1";
        }
        User user = (User) request.getSession().getAttribute("user");

        try {
            if(user == null) {
                request.getRequestDispatcher("/user/login.jsp").forward(request, response);
            } else {
                QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
                Shoppingcar shoppingcar = queryRunner.query("select * from shoppingcar  where uid = ?;", new BeanHandler<>(Shoppingcar.class), user.getUid());
                PageHelper<Shoppingitem> pageHelper = shoppingCarService.getUserShoppingCart(user, num);
                shoppingcar.setShoppingItems(pageHelper.getCategoryList());
                request.setAttribute("shoppingCar",shoppingcar);
                request.setAttribute("page",pageHelper);
                request.getRequestDispatcher("/shoppingcart.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
