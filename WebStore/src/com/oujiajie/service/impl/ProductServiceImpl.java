package com.oujiajie.service.impl;

import com.oujiajie.dao.ProductDao;
import com.oujiajie.dao.impl.ProductDaoImpl;
import com.oujiajie.model.Category;
import com.oujiajie.model.Product;
import com.oujiajie.service.ProductService;
import com.oujiajie.utils.PageHelper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class ProductServiceImpl implements ProductService {

    ProductDao productDao = new ProductDaoImpl();

    @Override
    public Boolean addProduct(Product product) throws SQLException {

        return productDao.addProduct(product);
    }

    @Override
    public Boolean deleteProduct(String pid) throws SQLException {
        return productDao.deleteProduct(pid);
    }

    @Override
    public Product findProduct(String pid) throws SQLException {
        return productDao.findProduct(pid);
    }

    @Override
    public PageHelper<Product> findAllProduct(String num, String pid, String pname, String cid, String minprice, String maxprice) throws SQLException {
        return productDao.findAllProduct(num,pid,pname,cid,minprice,maxprice);
    }

    @Override
    public Product getProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException {

        HttpSession session = request.getSession();
        DiskFileItemFactory factory = new DiskFileItemFactory();

        File repository = (File) request.getServletContext().getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);

        ServletFileUpload fileUpload = new ServletFileUpload(factory);


        try {
            List<FileItem> items = fileUpload.parseRequest(request);
            Iterator<FileItem> iterator = items.iterator();
            while (iterator.hasNext()) {
                FileItem item = iterator.next();
                if(item.isFormField()) {
                    processFormField(item, session);
                } else {
                    String realPath = request.getServletContext().getRealPath("");
                    processUploadFile(item,session,realPath);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        String pid = (String)session.getAttribute("pid");
        String pname = (String)session.getAttribute("pname");
        double estoreprice = Double.parseDouble((String)session.getAttribute("estoreprice"));
        double markprice = Double.parseDouble((String)session.getAttribute("markprice"));
        int pnum = Integer.parseInt((String)session.getAttribute("pnum"));
        int cid = Integer.parseInt((String)session.getAttribute("cid"));
        String imgurl = (String)session.getAttribute("imgurl");
        if(imgurl == null || imgurl.isEmpty()) {
            imgurl = (String)session.getAttribute("rawimgurl");
        }
        String desc = (String)session.getAttribute("desc");
        Product product = new Product(pid, pname, estoreprice, markprice, pnum, cid, imgurl, desc);
        session.invalidate();
        return product;
    }

    @Override
    public List<Category> getCategories() throws SQLException {
        return productDao.getCategories();
    }

    @Override
    public boolean isExist(String pid) throws SQLException {
        return productDao.isExist(pid);
    }

    private void processUploadFile(FileItem item, HttpSession session, String realPath) {
        String name = item.getName();
//        int hashCode = name.hashCode(); //得到一个int类型的数字 4个字节 32bit
//        String string = Integer.toHexString(hashCode); //长度是多少 8 /2/a/4/f/s/j/g/d/
//        char[] chars = string.toCharArray(); //将string拆开
//        for (char ch:
//                chars) { // 遍历每一个char ， 新建8个目录
//            realPath = realPath + "/" + ch;
//        }

//        UUID uuid = UUID.randomUUID();
//        name = uuid + "-" + name;
        session.setAttribute("imgurl",name);
        realPath = realPath + "/image/" + name;

        File uploadedFile = new File(realPath);
        if(!uploadedFile.getParentFile().exists()){
            uploadedFile.getParentFile().mkdirs();
        }
        try {
            item.write(uploadedFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processFormField(FileItem item, HttpSession session) {
        String name = item.getFieldName();
        String value = null;
        try {
            value = item.getString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        session.setAttribute(name,value);

    }
}
