package com.oujiajie.model;

public class Admin {

    private int aid;
    private String username;
    private String password;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Admin() {
    }

    public Admin(int aid, String username, String password) {
        this.aid = aid;
        this.username = username;
        this.password = password;
    }
}
