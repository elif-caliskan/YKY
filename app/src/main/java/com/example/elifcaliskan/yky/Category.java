package com.example.elifcaliskan.yky;

import java.util.ArrayList;

public class Category {
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public Category(String categoryName,int categoryColor,String categoryUrl){
        this.categoryName=categoryName;
        this.categoryColor=categoryColor;
        this.categoryUrl=categoryUrl;
        booklist=new ArrayList<Book>();
    }

    String categoryName;

    public ArrayList<Book> getBooklist() {
        return booklist;
    }

    public void setBooklist(ArrayList<Book> booklist) {
        this.booklist = booklist;
    }

    ArrayList<Book> booklist;

    public String getCategoryUrl() {
        return categoryUrl;
    }

    public void setCategoryUrl(String categoryUrl) {
        this.categoryUrl = categoryUrl;
    }

    String categoryUrl;
    public int getCategoryColor() {
        return categoryColor;
    }

    public void setCategoryColor(int categoryColor) {
        this.categoryColor = categoryColor;
    }

    int categoryColor;

}
