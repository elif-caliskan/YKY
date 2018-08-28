package com.example.elifcaliskan.yky;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



@SuppressLint("ValidFragment")
public class ListActivity extends AppCompatActivity {

    BookAdapter adapter;
    String url;
    int color;
    ArrayList<String> imageUrls = new ArrayList<String>();
    ArrayList<String> bookUrls = new ArrayList<String>();
    ArrayList<String> bookNames=new ArrayList<String>();
    ArrayList<String> authors=new ArrayList<String>();
    Map<String,String> letterMap=new HashMap<String, String>();
    public String converter(String word){
        while(word!=null&&word.contains("&#")){
            int index=word.indexOf("&#");
            String code=word.substring(index,index+5);
            if(code.contains(";")){
                word=word.replace(code,letterMap.get(code));
            }
            else{
                word=word.replace(code+";",letterMap.get(code+";"));
            }

        }
        return word;
    }

    public class DownloadTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            letterMap.put("&#305;","ı");
            letterMap.put("&#231;","ç");
            letterMap.put("&#351;","ş");
            letterMap.put("&#246;","ö");
            letterMap.put("&#252;","ü");
            letterMap.put("&#287;","ğ");
            letterMap.put("&#304;","I");
            letterMap.put("&#199;","Ç");
            letterMap.put("&#350;","Ş");
            letterMap.put("&#214;","Ö");
            letterMap.put("&#220;","Ü");
            letterMap.put("&#286;","Ğ");
            letterMap.put("&#226;","â");
            letterMap.put("&#39;","'");
            letterMap.put("&#243;","ó");
            letterMap.put("&#200;","È");
            letterMap.put("&#201;","É");
            letterMap.put("&#202;","Ê");
            letterMap.put("&#232;","è");
            letterMap.put("&#233;","é");
            letterMap.put("&#234;","ê");
            letterMap.put("&#64;","@");
            letterMap.put("&#38;","&");
            letterMap.put("&#225;","á");
            letterMap.put("&#193;","Á");
            letterMap.put("&#203;","Ë");
            letterMap.put("&#235;","ë");
            letterMap.put("&#198;","Æ");
            letterMap.put("&#230;","æ");
            letterMap.put("&#239;","ï");


            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {

                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection)url.openConnection();

                InputStream in =  urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while(data !=-1){

                    char current = (char)data;

                    result += current;

                    data = reader.read();
                }
                return result;
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.books_list);
        Intent intent = getIntent();
        color=intent.getIntExtra("color",R.color.blue);
        url = intent.getStringExtra("url");
        final ArrayList<Book> books = new ArrayList<Book>();

        DownloadTask task = new DownloadTask();
        String result;
        try {
            result = task.execute(url).get();
            if(result!=null)
                Log.i("Contents of URL", result);
            String[] splitResult1 = result.split("-list clearfix\">");
            String[] splitResult = splitResult1[1].split("<div class=\"footer-container\">");

            Pattern p = Pattern.compile("src=\"(.*?)\"");
            Matcher m = p.matcher(splitResult[0]);

            while (m.find()) {
                imageUrls.add(m.group(1));
            }

            p = Pattern.compile("<a href=\"(.*?)\" title=");
            m = p.matcher(splitResult[0]);

            while (m.find()) {
                bookUrls.add("http://kitap.ykykultur.com.tr"+m.group(1));
            }

            p = Pattern.compile("<h2>(.*?)</h2>");
            m = p.matcher(splitResult[0]);

            while (m.find()) {
                String name =converter(m.group(1));
                bookNames.add(name);

            }
            p = Pattern.compile("<h3>(.*?)</h3>");
            m = p.matcher(splitResult[0]);

            while (m.find()) {
                String name =converter(m.group(1));
                authors.add(name);

            }
            for (int i = 0; i < bookNames.size(); i++) {
                books.add(new Book(bookNames.get(i), imageUrls.get(i),authors.get(i),bookUrls.get(i)));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        adapter = new BookAdapter(this, books, color,android.R.color.white);
        ListView listView = (ListView) findViewById(R.id.book_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListActivity.this, BookActivity.class);
                intent.putExtra("bookName",bookNames.get(position));
                intent.putExtra("imageUrl", imageUrls.get(position));
                intent.putExtra("authorName",authors.get(position));
                intent.putExtra("bookUrl",bookUrls.get(position));


                startActivity(intent);
            }
        });
    }

}