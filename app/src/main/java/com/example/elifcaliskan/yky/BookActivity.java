package com.example.elifcaliskan.yky;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookActivity extends AppCompatActivity{
    String bookName;
    String imageUrl;
    String about;
    String author;
    String bookUrl;

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

    public class DownloadTask extends AsyncTask<String,Void,String> {

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
        imageUrl = intent.getStringExtra("imageUrl");
        author = intent.getStringExtra("authorName");
        bookName = intent.getStringExtra("bookName");
        bookUrl = intent.getStringExtra("bookUrl");

        BookActivity.DownloadTask task = new BookActivity.DownloadTask();
        String result;
        try {
            result = task.execute(bookUrl).get();
            Log.i("Contents of URL", result);
            String[] splitResult1 = result.split("<div id=\"tab1\" class=\"tab-content clearfix selected\">");
            String[] splitResult = splitResult1[1].split("</div>");

            Pattern p = Pattern.compile("<p>(.*?)</p>");
            Matcher m = p.matcher(splitResult[0]);

            about=m.group(1);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

}
