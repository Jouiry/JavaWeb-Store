package com.oujiajie.model;

public class Category {

    private int cid;
    private String cname;

    public Category() {
    }

    @Override
    public String toString() {
        return "Category{" +
                "cid=" + cid +
                ", cname='" + cname + '\'' +
                '}';
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Category(int cid, String cname) {
        this.cid = cid;
        this.cname = cname;
    }
}
