package com.example.elifcaliskan.yky;

import android.widget.ArrayAdapter;

public class Book {
    String bookName;
    String bookURL;
    public Book(String bookName,String bookURL){
        this.bookName=bookName;
        this.bookURL=bookURL;
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
