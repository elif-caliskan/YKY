package com.example.elifcaliskan.yky;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;

public class BookCategoryAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    private Book book;

    public BookCategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        book=new Book();
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            AboutFragment fragment = new AboutFragment();
            fragment.book=book;
            return fragment;
        } else if (position == 1) {
            InfoFragment fragment = new InfoFragment();
            fragment.book=book;
            return fragment;
        } else if (position == 2) {
            AboutFragment fragment = new AboutFragment();
            fragment.book=book;
            //category eklenmedi
            return fragment;
        } else  {
            AboutFragment fragment = new AboutFragment();
            fragment.book=book;
            return fragment;
        }

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
