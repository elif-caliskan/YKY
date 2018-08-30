package com.example.elifcaliskan.yky;

public class Book {
    String bookName;
    String imageUrl;
    String author;
    String bookUrl;
    Category category;

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    String about;
    int position;


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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


    public Book(String bookName,String imageUrl,String author,String bookUrl,Category category,String about){
        this.bookName=bookName;
        this.imageUrl=imageUrl;
        this.author=author;
        this.bookUrl=bookUrl;
        this.category=category;
        this.about=about;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String bookURL) {
        this.imageUrl = imageUrl;
    }
}