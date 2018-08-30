package com.example.elifcaliskan.yky;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

public class BookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.book_pager);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        BookCategoryAdapter adapter = new BookCategoryAdapter(this, getSupportFragmentManager());
        Intent intent = getIntent();
        adapter.getBook().setAuthor(intent.getStringExtra("authorName"));
        adapter.getBook().setBookName(intent.getStringExtra("bookName"));
        adapter.getBook().setBookUrl(intent.getStringExtra("bookUrl"));
        adapter.getBook().setAbout("");
        adapter.getBook().setCategory(new Category(intent.getStringExtra("categoryName"),R.color.bluee));
        adapter.getBook().setImageUrl(intent.getStringExtra("imageUrl"));
        adapter.getBook().setPosition(intent.getIntExtra("position",0));

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Find the tab layout that shows the tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);


    }
}