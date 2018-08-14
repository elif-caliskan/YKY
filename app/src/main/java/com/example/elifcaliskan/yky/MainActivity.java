package com.example.elifcaliskan.yky;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ArrayList<String> categories = new ArrayList<String>();
    String[] pos = {"a-z","","tekrar-basimlar","yeni-cikanlar","cok-satanlar","100-temel-eserde-yky-kitaplari"};

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

        ListView listView = (ListView) findViewById(R.id.list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);
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
