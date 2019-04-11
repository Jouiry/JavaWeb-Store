package com.oujiajie.controller;

import com.oujiajie.model.Category;
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

@WebServlet(name = "Servlet2",urlPatterns = "/admin/ProductServlet")
public class ProductServlet extends HttpServlet {

    ProductService productService = new ProductServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String op = request.getParameter("op");

        if(op != null && !op.isEmpty()) {

            switch (op) {
                case "addProduct":
                    addProduct(request,response);
                    break;
                case "findAllProduct":
                    findAllProduct(request,response);
                    break;
                case "deleteMulti":
                    deleteMulti(request,response);
                    break;
                case "findProduct":
                    findProduct(request,response);
                    break;
                case "deleteOne":
                    deleteOne(request,response);
                    break;
                case "getCategories":
                    getCategories(request,response);
                    break;
                case "searchProduct":
                    searchProduct(request,response);
                case "isExist":
                    isExist(request,response);
                default:
                    break;
            }

        }

    }

    private void isExist(HttpServletRequest request, HttpServletResponse response) {
        String pid = request.getParameter("pid");
        try {
            boolean result = productService.isExist(pid);
            if(!result) {
                response.getWriter().print("false");
            } else {
                response.getWriter().print("exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchProduct(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Category> categories = productService.getCategories();
            Category category = new Category(-1, "无");
            categories.add(category);
            request.setAttribute("categories",categories);
            request.getRequestDispatcher("/admin/product/searchProduct.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void findProduct(HttpServletRequest request, HttpServletResponse response) {
        try {
            Product product = productService.findProduct(request.getParameter("pid"));
            List<Category> categories = productService.getCategories();
            request.setAttribute("product",product);
            request.setAttribute("categories",categories);
            request.getRequestDispatcher("/admin/product/updateProduct.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void findAllProduct(HttpServletRequest request, HttpServletResponse response) {
        String num = request.getParameter("num");
        String pid = request.getParameter("pid");
        String pname = request.getParameter("pname");
        String cid = request.getParameter("cid");
        String minprice = request.getParameter("minprice");
        String maxprice = request.getParameter("maxprice");
        try {
            PageHelper<Product> pageHelper = productService.findAllProduct(num,pid,pname,cid,minprice,maxprice);
            List<Product> allCategory = pageHelper.getCategoryList();
            request.setAttribute("products",allCategory);
            request.setAttribute("page",pageHelper);
            request.setAttribute("pid",pid);
            request.setAttribute("pname",pname);
            request.setAttribute("cid",cid);
            request.setAttribute("minprice",minprice);
            request.setAttribute("maxprice",maxprice);
            request.getRequestDispatcher("/admin/product/productList.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) {

        try {
            String rawpid = request.getParameter("rawpid");
            Product product = productService.getProduct(request,response);
            if(rawpid == null) {
                Boolean aBoolean = productService.addProduct(product);
                if(aBoolean){
                    response.getWriter().println("添加成功！");
                    String contextPath = request.getContextPath();
                    response.setHeader("refresh","1;url='"+contextPath+"/admin/ProductServlet?op=getCategories'");
                }
            } else {
                productService.deleteProduct(rawpid);
                Boolean aBoolean = productService.addProduct(product);
                if (aBoolean) {
                    response.getWriter().println("修改成功");
                    String contextPath = request.getContextPath();
                    response.setHeader("refresh","1;url='"+contextPath+"/admin/ProductServlet?op=findAllProduct&num=1'");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void deleteOne(HttpServletRequest request, HttpServletResponse response) {

        Boolean aBoolean = null;
        try {
            aBoolean = productService.deleteProduct(request.getParameter("pid"));

            if(aBoolean) {
                String contextPath = request.getContextPath();
                response.getWriter().println("删除成功！");
                response.setHeader("refresh","1;url='"+contextPath+"/admin/ProductServlet?op=findAllProduct&num=1'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void deleteMulti(HttpServletRequest request, HttpServletResponse response) {

        String[] pids = request.getParameterValues("pid");

        try {
            for (String pid : pids) {
                productService.deleteProduct(pid);
            }
            String contextPath = request.getContextPath();
            response.getWriter().println("删除成功！");
            response.setHeader("refresh","1;url='"+contextPath+"/admin/ProductServlet?op=findAllProduct&num=1'");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void getCategories(HttpServletRequest request, HttpServletResponse response) {

        try {
            List<Category> allCategory = productService.getCategories();
            request.setAttribute("categories",allCategory);
            request.getRequestDispatcher("/admin/product/addProduct.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
