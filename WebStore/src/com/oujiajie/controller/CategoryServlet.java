package com.oujiajie.controller;

import com.oujiajie.model.Category;
import com.oujiajie.service.impl.CateGoryServiceImpl;
import com.oujiajie.service.CategoryService;
import com.oujiajie.utils.PageHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "Servlet", urlPatterns = "/admin/CategoryServlet")
public class CategoryServlet extends HttpServlet {

    CategoryService categoryService = new CateGoryServiceImpl();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String op = request.getParameter("op");

        if(op != null && !op.isEmpty()) {

            switch (op) {
                case "addCategory":
                    addCategory(request,response);
                    break;
                case "updateCategory":
                    updateCategory(request,response);
                    break;
                case "deleteMulti":
                    deleteMulti(request,response);
                    break;
                case "findAllCategory":
                    findAllCategory(request,response);
                    break;
                case "deleteOne":
                    deleteOne(request,response);
                    break;
                case "findCategoryByUpdate":
                    findCategoryByUpdate(request,response);
                    break;
                case "isExist":
                    isExist(request,response);
                default:
                    break;
            }


    }

}

    /**
     * 判断类别名是否重复
     * @param request
     * @param response
     */
    private void isExist(HttpServletRequest request, HttpServletResponse response) {
        String cname = request.getParameter("cname");
        boolean result = false;
        try {
            result = categoryService.isExist(cname);
            if(result) {
                response.getWriter().print("exist");
            } else {
                response.getWriter().print("false");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("CategorySevlet isExist : 查询数据库出错");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);

    }

    /**
     * 修改类别名
     * @param request
     * @param response
     */
    private void updateCategory(HttpServletRequest request, HttpServletResponse response) {

        Category category = new Category();
        String cid = request.getParameter("cid");
        String cname = request.getParameter("cname");
        category.setCid(Integer.parseInt(cid));
        category.setCname(cname);
        request.setAttribute("category",category);
        Boolean aBoolean = null;
        try {
            aBoolean = categoryService.updateCategory(category);
            String contextPath = request.getContextPath();
            if(aBoolean) {
                response.getWriter().println("修改成功！");
                response.setHeader("refresh","1;url='"+contextPath+"/admin/CategoryServlet?op=findAllCategory&num=1'");
            } else {
                response.getWriter().println("类型名重复！");
                request.getRequestDispatcher("/admin/category/updateCategory.jsp").forward(request, response);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 添加类别名
     * @param request
     * @param response
     */
    private void addCategory(HttpServletRequest request, HttpServletResponse response) {
        String cname = request.getParameter("cname");
        String contextPath = request.getContextPath();

        try {
            if(cname == null || cname.isEmpty()) {
                response.getWriter().println("请输入信息，再提交！");
                response.setHeader("refresh","1;url='"+contextPath+"/admin/category/addCategory.jsp'");
            } else {
                Category category = new Category();
                category.setCname(cname);
                Boolean aBoolean = categoryService.addCateGory(category);
                if(aBoolean){
                    response.getWriter().println("添加成功！");
                    response.setHeader("refresh","1;url='"+contextPath+"/admin/CategoryServlet?op=findAllCategory&num=1'");
                } else {
                    response.getWriter().println("类型名重复！");
                    response.setHeader("refresh","1;url='"+contextPath+"/admin/category/addCategory.jsp'");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("添加类别出现异常");
        }
    }


    /**
     *
     * @param request
     * @param response
     */
    private void findCategoryByUpdate(HttpServletRequest request, HttpServletResponse response) {

        try {
            request.getRequestDispatcher("/admin/category/updateCategory.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除类别名
     * @param request
     * @param response
     */
    private void deleteOne(HttpServletRequest request, HttpServletResponse response) {

        Boolean aBoolean = null;
        try {
            aBoolean = categoryService.deleteCategory(Integer.valueOf(request.getParameter("cid")));

            if(aBoolean) {
                String contextPath = request.getContextPath();
                response.setHeader("refresh","0;url='"+contextPath+"/admin/CategoryServlet?op=findAllCategory&num=1'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * 查找所有的类别名
     * @param request
     * @param response
     */
    private void findAllCategory(HttpServletRequest request, HttpServletResponse response) {


        String currentPageNum = request.getParameter("num");

        try {
            PageHelper<Category> pageHelper = categoryService.findAllCategory(currentPageNum);
            List<Category> allCategory = pageHelper.getCategoryList();
            request.setAttribute("categories",allCategory);
            request.setAttribute("page",pageHelper);
            request.getRequestDispatcher("/admin/category/categoryList.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    /**
     * 删除多个类别名
     * @param request
     * @param response
     */
    private void deleteMulti(HttpServletRequest request, HttpServletResponse response) {

        String[] cidString = request.getParameterValues("cid");
        int[] cids = new int[cidString.length];
        int i =0;
        for (String s : cidString) {
            int num = Integer.parseInt(s);
            cids[i++] = num;
        }

        try {
            Boolean aBoolean = categoryService.deleteMulti(cids);
            if(aBoolean){
                String contextPath = request.getContextPath();
                response.setHeader("refresh","0;url='"+contextPath+"/admin/CategoryServlet?op=findAllCategory&num=1'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
