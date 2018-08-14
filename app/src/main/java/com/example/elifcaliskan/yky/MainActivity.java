package com.example.elifcaliskan.yky;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ArrayList<Category> categories = new ArrayList<Category>();
    String[] pos = {"a-z","","tekrar-basimlar","yeni-cikanlar","cok-satanlar","100-temel-eserde-yky-kitaplari"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categories.add(new Category("A-Z"));
        categories.add(new Category("KONU DİZİNİ"));
        categories.add(new Category("TEKRAR BASIMLAR"));
        categories.add(new Category("YENİ ÇIKANLAR"));
        categories.add(new Category("ÇOK SATANLAR"));
        categories.add(new Category("100 TEMEL ESERDE YKY KİTAPLARI"));

        ListView listView = (ListView) findViewById(R.id.list);
        CategoryAdapter arrayAdapter = new CategoryAdapter(this, categories);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("url","http://kitap.ykykultur.com.tr/kitaplar/"+pos[position]);
                startActivity(intent);
            }
        });
    }
}
