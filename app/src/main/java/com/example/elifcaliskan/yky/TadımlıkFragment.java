package com.example.elifcaliskan.yky;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TadımlıkFragment extends Fragment {
    DatabaseReference dbref;
    String result;
    public Book book;
    public class ImageDownloader extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url=new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public Bitmap downloadImage(View view, String url){
        TadımlıkFragment.ImageDownloader task = new TadımlıkFragment.ImageDownloader();
        Bitmap myImage;
        try {
            myImage = task.execute(url).get();
            return myImage;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;

    }

    public Map<String,String> letterMap=new HashMap<String, String>();
    public String converter(String word){
        String newWord="";
        while(!word.equals("")){
            if(word.charAt(0)=='&'){
                if(word.contains(";")) {
                    String code = word.substring(0, word.indexOf(";") + 1);
                    if(letterMap.containsKey(code)) {
                        word = word.replace(code, letterMap.get(code));
                    }
                }
            }
            newWord+=word.charAt(0);
            word=word.substring(1);
        }
        return newWord;
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
            letterMap.put("&Ccedil;","Ç");
            letterMap.put("&ccedil;","ç");
            letterMap.put("&Ouml;","Ö");
            letterMap.put("&ouml;","ö");
            letterMap.put("&Uuml;","Ü");
            letterMap.put("&uuml;","ü");
            letterMap.put("&Agrave;","À");
            letterMap.put("&agrave;","à");
            letterMap.put("&Acirc;","Â");
            letterMap.put("&acirc;","â");
            letterMap.put("&Egrave;","È");
            letterMap.put("&egrave;;","è");
            letterMap.put("&Eacute;","É");
            letterMap.put("&eacute;","é");
            letterMap.put("&Ecirc;","Ê");
            letterMap.put("&ecirc;","ê");
            letterMap.put("&ldquo;","“");
            letterMap.put("&rsquo;","'");
            letterMap.put("&rdquo;","”");

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.about_book, container, false);
        dbref = FirebaseDatabase.getInstance().getReference();
        dbref.child(book.getCategory().getCategoryName()).child(String.valueOf(book.getPosition())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.child("tadımlık").getValue().equals("")) {
                    book.setTadımlık((String)snapshot.child("tadımlık").getValue());
                } else {
                    TadımlıkFragment.DownloadTask task = new TadımlıkFragment.DownloadTask();
                    try {
                        result = task.execute(book.getBookUrl()).get();
                        Log.i("Contents of URL", result);
                        if (result.contains("<div id=\"tab3\" class=\"tab-content clearfix\">")) {
                            String[] splitResult1 = result.split("<div id=\"tab3\" class=\"tab-content clearfix\">");
                            String[] splitResult = splitResult1[1].split("<div id=\"tab9\" class=\"tab-content clearfix\">");

                            Pattern p = Pattern.compile("<p>(.*?)</p>");
                            Matcher m = p.matcher(splitResult[0]);
                            String tadımlık = "\t";

                            while (m.find()) {
                                tadımlık += m.group(1);
                                tadımlık += "\n\n\t";
                            }

                            book.setTadımlık(converter(tadımlık));
                        }
                        else{
                            book.setTadımlık("Kitabın tadımlık bölümü bulunmamaktadır.");
                        }
                        } catch(InterruptedException e){
                            e.printStackTrace();
                        } catch(ExecutionException e){
                            e.printStackTrace();
                        }
                        dbref.child(book.getCategory().getCategoryName()).child(String.valueOf(book.getPosition())).child("tadımlık").setValue(book.getTadımlık());


                }

                TextView textView=(TextView)rootView.findViewById(R.id.book_about);
                textView.setText(book.getTadımlık());
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return rootView;
    }
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        TextView textView = (TextView)view.findViewById(R.id.book_name);
        textView.setText(book.getBookName());
        textView=(TextView)view.findViewById(R.id.book_author);
        textView.setText(book.getAuthor());
        textView=(TextView)view.findViewById(R.id.book_about);
        textView.setText(book.getTadımlık());
        textView=view.findViewById(R.id.about);
        textView.setText("Tadımlık:");
        ImageView iconView=(ImageView) view.findViewById(R.id.book_cover);
        iconView.setImageBitmap(downloadImage(iconView,book.getImageUrl()));
        iconView.setVisibility(View.VISIBLE);
    }

}
