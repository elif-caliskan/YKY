package com.example.elifcaliskan.yky;

public class Book {
    String bookName;
    String imageUrl;
    String author;
    String bookUrl;
    int categoryColor;


    public int getCategoryColor() {
        return categoryColor;
    }

    public void setCategoryColor(int categoryColor) {
        this.categoryColor = categoryColor;
    }


    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public Book(String bookName,String imageUrl,String author,String bookUrl, int categoryColor){
        this.bookName=bookName;
        this.imageUrl=imageUrl;
        this.author=author;
        this.bookUrl=bookUrl;
        this.categoryColor=categoryColor;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getImageURL() {
        return imageUrl;
    }

    public void setImageURL(String bookURL) {
        this.imageUrl = imageUrl;
    }
}
