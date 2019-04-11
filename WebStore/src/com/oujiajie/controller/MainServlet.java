package com.oujiajie.controller;

import com.oujiajie.model.Category;
import com.oujiajie.model.Product;
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

@WebServlet(name = "Servlet4",urlPatterns = "/MainServlet",loadOnStartup = 1)
public class MainServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        ProductServiceImpl productService = new ProductServiceImpl();
        try {
            List<Category> categories = productService.getCategories();
            PageHelper<Product> allProduct = productService.findAllProduct("1",null,null,null,null,null);
            List<Product> productTop = allProduct.getCategoryList();
            PageHelper<Product> allProduct1 = productService.findAllProduct("2",null,null,null,null,null);
            List<Product> hotproduct = allProduct1.getCategoryList();
            getServletContext().setAttribute("categories",categories);
            getServletContext().setAttribute("productTop",productTop);
            getServletContext().setAttribute("hotProducts",hotproduct);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
