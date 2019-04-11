package com.oujiajie.controller;

import com.oujiajie.model.Product;
import com.oujiajie.service.ProductService;
import com.oujiajie.service.impl.ProductServiceImpl;
import com.oujiajie.utils.PageHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "Servlet5",urlPatterns = "/ProductServlet")
public class ShowProductsServlet extends HttpServlet {

    ProductService productService = new ProductServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");

        if(op != null && !op.isEmpty()) {

            switch (op) {

                case "findProByName":

                case "byCid":
                    findProductbyCid(request,response);
                    break;
                case "findProductById":
                    findProductById(request,response);
                    break;
                default:
                    break;
            }

        }
    }


    private void findProductById(HttpServletRequest request, HttpServletResponse response) {

        try {
            Product product = productService.findProduct(request.getParameter("pid"));
            request.setAttribute("product",product);
            request.getRequestDispatcher("/productdetail.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void findProductbyCid(HttpServletRequest request, HttpServletResponse response) {
        String num = request.getParameter("num");
        if(num == null || num.isEmpty()) {
            num = "1";
        }
        String pname = request.getParameter("pname");
        String cid = request.getParameter("cid");
        try {
            PageHelper<Product> pageHelper = productService.findAllProduct(num,null,pname,cid,null,null);
            List<Product> allCategory = pageHelper.getCategoryList();
            request.setAttribute("products",allCategory);
            request.setAttribute("page",pageHelper);
            request.setAttribute("pname",pname);
            request.setAttribute("cid",cid);
            request.getRequestDispatcher("/products.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
