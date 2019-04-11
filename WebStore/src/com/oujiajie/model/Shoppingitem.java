package com.oujiajie.model;

import java.util.Objects;

public class Shoppingitem {

    private int itemid;
    private int sid;
    private String pid;
    private Product product;
    private int snum;

    public int getSnum() {
        return snum;
    }

    public void setSnum(int snum) {
        this.snum = snum;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getPid() {
        return pid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shoppingitem that = (Shoppingitem) o;
        return itemid == that.itemid &&
                sid == that.sid &&
                snum == that.snum &&
                Objects.equals(pid, that.pid) &&
                Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemid, sid, pid, product, snum);
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
