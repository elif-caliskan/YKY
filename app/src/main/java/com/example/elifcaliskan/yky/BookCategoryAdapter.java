package com.example.elifcaliskan.yky;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;

public class BookCategoryAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private String imageUrl;
    private String bookUrl;
    private String bookName;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    private String author;

    public BookCategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            AboutFragment fragment = new AboutFragment();
            fragment.bookName=bookName;
            fragment.bookUrl=bookUrl;
            fragment.author=author;
            fragment.imageUrl=imageUrl;
            return fragment;
        } else if (position == 1) {
            AboutFragment fragment = new AboutFragment();
            fragment.bookName=bookName;
            fragment.bookUrl=bookUrl;
            fragment.author=author;
            fragment.imageUrl=imageUrl;
            return fragment;
        } else if (position == 2) {
            TadımlıkFragment fragment = new TadımlıkFragment();
            fragment.bookName=bookName;
            fragment.bookUrl=bookUrl;
            fragment.author=author;
            fragment.imageUrl=imageUrl;
            return fragment;
        } else if (position == 3) {
            AboutFragment fragment = new AboutFragment();
            fragment.bookName=bookName;
            fragment.bookUrl=bookUrl;
            fragment.author=author;
            fragment.imageUrl=imageUrl;
            return fragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.about_book);
        } else if (position == 1) {
            return mContext.getString(R.string.book_info);
        } else if (position == 2) {
            return mContext.getString(R.string.tadımlık);
        } else {
            return mContext.getString(R.string.comment);
        }
    }
}
