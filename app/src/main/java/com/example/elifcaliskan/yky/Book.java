package com.example.elifcaliskan.yky;

import java.util.HashMap;

public class Book {
    private String bookName;
    private String imageUrl;
    private String author;
    private String bookUrl;
    private Category category;


    public String getTadımlık() {
        return tadımlık;
    }

    public void setTadımlık(String tadımlık) {
        this.tadımlık = tadımlık;
    }

    private String tadımlık;
    public HashMap<String, String> getInformation() {
        return information;
    }

    public void setInformation(HashMap<String, String> information) {
        this.information = information;
    }

    private HashMap<String,String> information;



    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    private String about;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private int position;


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
    public Book(){
        this.bookName="";
        this.about="";
        this.position=0;
        this.category=new Category("",R.color.colorPrimary,"");
        this.bookUrl="";
        this.imageUrl="";
        this.information=new HashMap<String, String>();
        this.tadımlık="";
    }

    public Book(String bookName,String imageUrl,String author,String bookUrl,Category category,String about){
        this.bookName=bookName;
        this.imageUrl=imageUrl;
        this.author=author;
        this.bookUrl=bookUrl;
        this.category=category;
        this.about=about;
        this.information=new HashMap<String,String>();
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

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}