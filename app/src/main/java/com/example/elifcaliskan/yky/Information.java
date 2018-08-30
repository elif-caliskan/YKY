package com.example.elifcaliskan.yky;

public class Information {
    String ISBN;
    String size;
    String translator;
    String ykyFirst;

    public Information() {
        ISBN="";
        size="";
        translator="";
        ykyFirst="";
        pageNumber="";
        tekrarBaskı="";
        category="";
        originalName="";
    }

    String pageNumber;
    String tekrarBaskı;
    String originalName;
    String category;

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getYkyFirst() {
        return ykyFirst;
    }

    public void setYkyFirst(String ykyFirst) {
        this.ykyFirst = ykyFirst;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getTekrarBaskı() {
        return tekrarBaskı;
    }

    public void setTekrarBaskı(String tekrarBaskı) {
        this.tekrarBaskı = tekrarBaskı;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
