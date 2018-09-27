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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InfoFragment extends Fragment {
    DatabaseReference dbref;
    String result;
    HashMap<String,String> info;
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
        InfoFragment.ImageDownloader task = new InfoFragment.ImageDownloader();
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
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        info=new HashMap<String, String>();
        final View rootView = inflater.inflate(R.layout.info_book, container, false);
        dbref = FirebaseDatabase.getInstance().getReference();
        dbref.child(book.getCategory().getCategoryName()).child(String.valueOf(book.getPosition())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child("information").getValue()!=null) {
                    info=(HashMap<String,String>)snapshot.child("information").getValue();

                } else {
                    info=new HashMap<String, String>();
                    InfoFragment.DownloadTask task = new InfoFragment.DownloadTask();
                    try {
                        result = task.execute(book.getBookUrl()).get();
                        Log.i("Contents of URL", result);

                        Pattern p = Pattern.compile("<strong>((?s).*?)</li>");
                        Matcher m = p.matcher(result);

                        while (m.find()) {
                            String x = m.group(1);
                            String first = x.substring(0,x.indexOf("<"));
                            String k="";
                            first=converter(first);
                            if(x.contains("title=")){
                                x=x.substring(x.indexOf("title")+6,x.indexOf("</a>"));
                                k=x.substring(x.indexOf(">")+1);
                            }
                            else{
                                k=x.substring(x.indexOf("<br />")+6).trim();

                            }
                            k=converter(k);
                            info.put(first,k);
                        }

                        book.setInformation(info);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    dbref.child(book.getCategory().getCategoryName()).child(String.valueOf(book.getPosition())).child("information").setValue(book.getInformation());
                    }
                    List<String> list = new ArrayList<String>(info.keySet());
                    for(int i=0;i<list.size();i++){
                        Information information=(Information) rootView.findViewWithTag(Integer.toString(i+1));
                        TextView text = (TextView)information.findViewById(R.id.property_name);
                        text.setText(list.get(i));
                        text=(TextView)information.findViewById(R.id.real_property);
                        text.setText(info.get(list.get(i)));
                        information.setVisibility(View.VISIBLE);
                    }
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
        TextView text=(TextView)view.findViewById(R.id.book_name);
        text.setText(book.getBookName());

        List<String> list = new ArrayList<String>(info.keySet());
        for (int i = 0; i < list.size(); i++) {
                Information information = (Information) view.findViewWithTag(Integer.toString(i+1));
                TextView textView = (TextView) information.findViewById(R.id.property_name);
                textView.setText(list.get(i));
                textView = (TextView) information.findViewById(R.id.real_property);
                textView.setText(info.get(list.get(i)));

        }

    }

}
