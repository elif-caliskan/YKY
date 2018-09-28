package com.example.elifcaliskan.yky;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ArrayList<Category> categories = new ArrayList<Category>();
    String[] pos = {"a-z","","tekrar-basimlar","yeni-cikanlar","cok-satanlar","100-temel-eserde-yky-kitaplari"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categories.add(new Category("A-Z",R.color.darkBlue,"http://kitap.ykykultur.com.tr/kitaplar/" + pos[0]));
        categories.add(new Category("KONU DİZİNİ",R.color.darkerBlue2,"http://kitap.ykykultur.com.tr/kitaplar/" + pos[1]));
        categories.add(new Category("TEKRAR BASIMLAR",R.color.darkerBlue,"http://kitap.ykykultur.com.tr/kitaplar/" + pos[2]));
        categories.add(new Category("YENİ ÇIKANLAR",R.color.blue,"http://kitap.ykykultur.com.tr/kitaplar/" + pos[3]));
        categories.add(new Category("ÇOK SATANLAR",R.color.lightBlue,"http://kitap.ykykultur.com.tr/kitaplar/" + pos[4]));
        categories.add(new Category("100 TEMEL ESERDE YKY KİTAPLARI",R.color.lightestBlue,"http://kitap.ykykultur.com.tr/kitaplar/" + pos[5]));

        ListView listView = (ListView) findViewById(R.id.list);
        ImageView imageView=findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.yk);
        CategoryAdapter arrayAdapter = new CategoryAdapter(this, categories,android.R.color.black);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position!=1) {
                    Intent intent = new Intent(MainActivity.this, ListActivity.class);
                    intent.putExtra("url", categories.get(position).getCategoryUrl());
                    intent.putExtra("categoryName",categories.get(position).getCategoryName());
                    intent.putExtra("color", categories.get(position).getCategoryColor());
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(MainActivity.this, SubCategoryActivity.class);
                    intent.putExtra("color", categories.get(position).getCategoryColor());
                    startActivity(intent);
                }
            }
        });
    }
}
