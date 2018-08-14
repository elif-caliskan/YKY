package com.example.elifcaliskan.yky;

public class Book {
    String bookName;
    String bookURL;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    String author;
    public Book(String bookName,String bookURL,String author){
        this.bookName=bookName;
        this.bookURL=bookURL;
        this.author=author;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookURL() {
        return bookURL;
    }

    public void setBookURL(String bookURL) {
        this.bookURL = bookURL;
    }
}
