package com.example.elifcaliskan.yky;

public class Category {
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public Category(String categoryName,int categoryColor){
        this.categoryName=categoryName;
        this.categoryColor=categoryColor;
    }

    String categoryName;

    public int getCategoryColor() {
        return categoryColor;
    }

    public void setCategoryColor(int categoryColor) {
        this.categoryColor = categoryColor;
    }

    int categoryColor;

}
