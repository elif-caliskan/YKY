package com.example.elifcaliskan.yky;

public class Category {
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public Category(String categoryName){
        this.categoryName=categoryName;
    }

    String categoryName;

}
