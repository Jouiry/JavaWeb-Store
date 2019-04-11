package com.oujiajie.utils;

import java.util.List;

public class PageHelper<T> {
    List<T> categoryList;
    private int totalRecordsNum;
    private int currentPageNum;
    private int totalPageNum;
    private int prevPageNum;
    private int nextPageNum;

    public int getTotalRecordsNum() {
        return totalRecordsNum;
    }

    public void setTotalRecordsNum(int totalRecordsNum) {
        this.totalRecordsNum = totalRecordsNum;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public int getPrevPageNum() {
        return prevPageNum;
    }

    public void setPrevPageNum(int prevPageNum) {
        this.prevPageNum = prevPageNum;
    }

    public int getNextPageNum() {
        return nextPageNum;
    }

    public void setNextPageNum(int nextPageNum) {
        this.nextPageNum = nextPageNum;
    }

    public PageHelper() {
    }

    public List<T> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<T> categoryList) {
        this.categoryList = categoryList;
    }

    public PageHelper(List<T> categoryList, int totalRecordsNum, int currentPageNum, int totalPageNum, int prevPageNum, int nextPageNum) {
        this.categoryList = categoryList;
        this.totalRecordsNum = totalRecordsNum;
        this.currentPageNum = currentPageNum;
        this.totalPageNum = totalPageNum;
        this.prevPageNum = prevPageNum;
        this.nextPageNum = nextPageNum;
    }
}
