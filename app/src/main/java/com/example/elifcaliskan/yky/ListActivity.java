package com.example.elifcaliskan.yky;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressLint("ValidFragment")
public class ListActivity extends AppCompatActivity {
    BookAdapter adapter;
    String url;
    ArrayList<String> bookUrls= new ArrayList<String>();
    ArrayList<String> bookNames=new ArrayList<String>();
    ArrayList<String> authors=new ArrayList<String>();


    public class DownloadTask extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... urls) {
            Log.i("nerede","5");
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
                Log.i("nerede","4");
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
        url = intent.getStringExtra("url");
        Log.i("gelen url", url);
        final ArrayList<Book> books = new ArrayList<Book>();

        DownloadTask task = new DownloadTask();
        String result;
        Log.i("nerede", "3");
        try {
            result = task.execute(url).get();
            Log.i("Contents of URL", result);
            String[] splitResult1 = result.split("100 TEMEL ESERDE YKY KİTAPLARI</a></li>");
            String[] splitResult = splitResult1[1].split("<div class=\"footer-container\">");

            Pattern p = Pattern.compile("src=\"(.*?)\"");
            Matcher m = p.matcher(splitResult[0]);

            while (m.find()) {
                bookUrls.add(m.group(1));
            }
            p = Pattern.compile("<h2>(.*?)</h2>");
            m = p.matcher(splitResult[0]);

            while (m.find()) {
                bookNames.add(m.group(1));
            }
            p = Pattern.compile("<h3>(.*?)</h3>");
            m = p.matcher(splitResult[0]);

            while (m.find()) {
                authors.add(m.group(1));
            }
            for (int i = 0; i < bookNames.size(); i++) {
                books.add(new Book(bookNames.get(i), bookUrls.get(i),authors.get(i)));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        adapter = new BookAdapter(this, books, R.color.darkBlue);
        ListView listView = (ListView) findViewById(R.id.book_list);
        listView.setAdapter(adapter);
    }
}
