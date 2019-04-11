package com.oujiajie.model;


import java.util.List;

public class Shoppingcar {
    private int sid;
    private int uid;
    private List<Shoppingitem> shoppingItems;


    public List<Shoppingitem> getShoppingItems() {
        return shoppingItems;
    }

    public void setShoppingItems(List<Shoppingitem> shoppingItems) {
        this.shoppingItems = shoppingItems;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
