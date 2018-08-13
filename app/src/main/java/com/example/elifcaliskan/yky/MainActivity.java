package com.example.elifcaliskan.yky;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ArrayList<String> categories = new ArrayList<String>();
    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.book_list, fragment);
        fragmentTransaction.commit(); // save the changes
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categories.add("A-Z");
        categories.add("KONU DİZİNİ");
        categories.add("TEKRAR BASIMLAR");
        categories.add("YENİ ÇIKANLAR");
        categories.add("ÇOK SATANLAR");
        categories.add("100 TEMEL ESERDE YKY KİTAPLARI");


        // Find the view pager that will allow the user to swipe between fragments
        //ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
       // SimpleFragmentPageAdapter adapter = new SimpleFragmentPageAdapter(getSupportFragmentManager());

        // Set the adapter onto the view pager
       // viewPager.setAdapter(adapter);

        /*TabLayout tabLayout = (TabLayout) findViewById(R.id.slidingtabs);
        tabLayout.setupWithViewPager(viewPager);*/


        ListView listView=(ListView)findViewById(R.id.myListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,categories);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url="http://kitap.ykykultur.com.tr/kitaplar/";
                if(position==0){
                    url=url+"a-z";
                    loadFragment(BookFragment.newInstance(url));
                }
                else if(position==1){

                    loadFragment(BookFragment.newInstance(url));
                }
                else if(position==2){
                    url=url+"tekrar-basimlar";
                    loadFragment(BookFragment.newInstance(url));
                    Log.i("uyarı","2ye bastık");
                }
                else if(position==3){
                    url=url+"yeni-cikanlar";
                    loadFragment(BookFragment.newInstance(url));
                }
                else if(position==4){
                    url=url+"cok-satanlar";
                    loadFragment(BookFragment.newInstance(url));
                }
                else {
                    url=url+"100-temel-eserde-yky-kitaplari";
                    loadFragment(BookFragment.newInstance(url));

                }
                /*fragmentTransaction.add(R.id.bookFragment, f);
                fragmentTransaction.commit();*/
                setContentView(R.layout.books_list);

            }
        });



    }





}
