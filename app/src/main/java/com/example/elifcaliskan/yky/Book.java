package com.example.elifcaliskan.yky;

public class Book {
    String bookName;
    String imageUrl;
    String author;
    String bookUrl;

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


    public Book(String bookName,String imageUrl,String author,String bookUrl){
        this.bookName=bookName;
        this.imageUrl=imageUrl;
        this.author=author;
        this.bookUrl=bookUrl;
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