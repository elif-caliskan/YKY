package com.example.elifcaliskan.yky;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> bookUrls= new ArrayList<String>();
    ArrayList<String> bookNames=new ArrayList<String>();

    ArrayList<Book> books = new ArrayList<Book>();

    public class DownloadTask extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... urls) {

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
        setContentView(R.layout.activity_main);

        ListView myListview = findViewById(R.id.myListView);


        DownloadTask task = new DownloadTask();
        String result = null;
        try {
            result = task.execute("http://kitap.ykykultur.com.tr/kitaplar/100-temel-eserde-yky-kitaplari").get();
            Log.i("Contents of URL",result);
            String[] splitResult1 = result.split("ESERDE YKY KİTAPLARI</a></li>");
            String[] splitResult = splitResult1[1].split("<div class=\"footer-container\">");

            Pattern p= Pattern.compile("src=\"(.*?)\"");
            Matcher m=p.matcher(splitResult[0]);

            while(m.find()){
                bookUrls.add(m.group(1));
            }
            p= Pattern.compile("alt=\"(.*?)\"");
            m=p.matcher(splitResult[0]);

            while(m.find()){
                bookNames.add(m.group(1));
            }
            for(int i=0;i<bookNames.size();i++){
                books.add(new Book(bookNames.get(i),bookUrls.get(i)));
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        // Create an adapter that knows which fragment should be shown on each page
        BookAdapter bookAdapter = new BookAdapter(this,books,R.color.lightBlue);

        // Set the adapter onto the view pager
        //viewPager.setAdapter(adapter);
        myListview.setAdapter(bookAdapter);

        /*TabLayout tabLayout = (TabLayout) findViewById(R.id.slidingtabs);
        tabLayout.setupWithViewPager(viewPager);*/
    }




}
