package com.example.elifcaliskan.yky;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class BookFragment extends Fragment {
    BookAdapter adapter;
    String url;
    ArrayList<String> bookUrls= new ArrayList<String>();
    ArrayList<String> bookNames=new ArrayList<String>();

    public static BookFragment newInstance(String url){

        BookFragment fragment = new BookFragment();
        fragment.url=url;
        if(url!=null)
            Log.i("buraya",url);
        return fragment;

    }

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

    public BookFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*View rootView = inflater.inflate(R.layout.books_list, container, false);

        final ArrayList<Book> books = new ArrayList<Book>();

        DownloadTask task = new DownloadTask();
        String result;
        Log.i("nerede","3");
        try {
            result = task.execute(url).get();
            Log.i("Contents of URL",result);
            String[] splitResult1 = result.split("YKY Kitapları\">100 Temel Eserde YKY Kitapları</a></li>");
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

        adapter = new BookAdapter(getActivity(), books, R.color.darkBlue);
       // ListView listView = (ListView) rootView.findViewById(R.id.book_list);

        //listView.setAdapter(adapter);


        Log.i("nerede","1");*/
        return inflater.inflate(R.layout.books_list, container, false);
    }
    /*@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*ListView lv = (ListView) view.findViewById(R.id.book_list);
        Log.i("nerede","2");
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book word;
            }
        });
    }*/
}
