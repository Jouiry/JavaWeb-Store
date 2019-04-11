package com.oujiajie.dao.impl;

import com.oujiajie.dao.ProductDao;
import com.oujiajie.model.Category;
import com.oujiajie.model.Product;
import com.oujiajie.utils.DBUtils;
import com.oujiajie.utils.PageHelper;
import com.oujiajie.utils.TransactionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public Boolean addProduct(Product product) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        int update = queryRunner.update("insert into product values (?,?,?,?,?,?,?,?);", product.getPid(), product.getPname(),
                product.getEstoreprice(), product.getMarkprice(), product.getPnum(), product.getCid(), product.getImgurl(),
                product.getDesc());
        return update == 1;
    }

    @Override
    public Boolean deleteProduct(String pid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        int update = queryRunner.update("delete from product where pid = ?;", pid);
        return update == 1;
    }


    @Override
    public Product findProduct(String pid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        Product product = queryRunner.query("select * from product where pid = ?;", new BeanHandler<>(Product.class),pid);
        return product;
    }


    @Override
    public PageHelper<Product> findAllProduct(String num, String pid, String pname, String cid, String minprice, String maxprice) throws SQLException {

        int currentPageNum = Integer.parseInt(num);
        PageHelper<Product> pageHelper = new PageHelper<>();
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        String sql = "select * from product where 1=1";
        ArrayList<Object> list = new ArrayList<>();
        if(pid != null && !pid.isEmpty()) {
            sql += " and pid=?";
            list.add(pid);
        }
        if(pname != null && !pname.isEmpty()) {
            sql += " and pname like ?";
            list.add("%"+pname+"%");
        }
        if(cid != null && !cid.isEmpty() && !"-1".equals(cid)) {
            sql += " and cid=?";
            list.add(cid);
        }
        if(minprice != null && !minprice.isEmpty()) {
            sql += " and estoreprice >= ?";
            list.add(minprice);
        }
        if(maxprice != null && !maxprice.isEmpty()) {
            sql += " and estoreprice <= ?";
            list.add(maxprice);
        }
        System.out.println(sql);
        Object[] objects = list.toArray();
        List<Product> allquery = queryRunner.query(sql+";", new BeanListHandler<>(Product.class),objects);
        int limitNumber = 6;
        int size = allquery.size();
        int pageNum = (size/limitNumber) + (size % limitNumber == 0 ? 0 : 1);
        if (currentPageNum > pageNum) currentPageNum = pageNum;
        if (currentPageNum < 1) currentPageNum = 1;
        int offset = (currentPageNum - 1) * limitNumber;
        pageHelper.setTotalPageNum(pageNum);
        pageHelper.setTotalRecordsNum(size);
        pageHelper.setPrevPageNum(currentPageNum-1);
        pageHelper.setNextPageNum(currentPageNum+1);
        list.add(offset);
        list.add(limitNumber);
        Object[] objects1 = list.toArray();
        List<Product> query = queryRunner.query(sql + " limit ?,?;", new BeanListHandler<>(Product.class),objects1);
        for (Product product : query) {
            Category category = queryRunner.query("select * from category where cid = ?;", new BeanHandler<>(Category.class), product.getCid());
            product.setCategory(category);
        }
        pageHelper.setCategoryList(query);
        pageHelper.setCurrentPageNum(currentPageNum);
        return pageHelper;
    }


    @Override
    public List<Category> getCategories() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        List<Category> category = queryRunner.query("select * from category;", new BeanListHandler<>(Category.class));
        return category;
    }

    @Override
    public boolean isExist(String pid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        Product product = queryRunner.query("select * from product where pid = ?;", new BeanHandler<>(Product.class), pid);
        return product != null;
    }

    @Override
    public boolean deleteProductNum(String pid, int buynum) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        int update = queryRunner.update(TransactionUtils.getConnection(),"update product set pnum = pnum - ? where pid = ?;", buynum,pid);
        return update == 1;
    }

    @Override
    public boolean rebackNum(int pid, int buynum) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        int update = queryRunner.update(TransactionUtils.getConnection(),"update product set pnum = pnum + ? where pid = ?;",buynum,pid );
        return update == 1;
    }
}
