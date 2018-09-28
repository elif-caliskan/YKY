package com.example.elifcaliskan.yky;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
    String categoryName;
    BookAdapter adapter;
    String url;
    int color;
    int position;
    String result;
    DatabaseReference dbref;
    FirebaseStorage storage;
    StorageReference storageReference;
    ArrayList<Book> books;
    ArrayList<String> imageUrls = new ArrayList<String>();
    ArrayList<String> bookUrls = new ArrayList<String>();
    ArrayList<String> bookNames=new ArrayList<String>();
    ArrayList<String> authors=new ArrayList<String>();
    Map<String,String> letterMap=new HashMap<String, String>();
    Map<String,Book> bookMap=new HashMap<String, Book>();
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
        categoryName=intent.getStringExtra("categoryName");
        books = new ArrayList<Book>();

        dbref=FirebaseDatabase.getInstance().getReference();
        dbref.child(categoryName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                        bookMap.clear();
                        books.clear();
                        ArrayList<HashMap<String, String>> bookObject = (ArrayList<HashMap<String, String>>) snapshot.getValue();
                        for (int i = 0; i < bookObject.size(); i++) {
                            books.add(new Book(bookObject.get(i).get("bookName"), bookObject.get(i).get("imageUrl"), bookObject.get(i).get("author"), bookObject.get(i).get("bookUrl"), new Category(categoryName, color, ""), bookObject.get(i).get("about")));

                        }
                    } else {
                        DownloadTask task = new DownloadTask();
                        try {
                            result = task.execute(url).get();
                            if (result != null)
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
                                bookUrls.add("http://kitap.ykykultur.com.tr" + m.group(1));
                            }

                            p = Pattern.compile("<h2>(.*?)</h2>");
                            m = p.matcher(splitResult[0]);

                            while (m.find()) {
                                String name = converter(m.group(1));
                                bookNames.add(name);

                            }
                            p = Pattern.compile("<h3>(.*?)</h3>");
                            m = p.matcher(splitResult[0]);

                            while (m.find()) {
                                String name = converter(m.group(1));
                                authors.add(name);

                            }

                            for (int i = 0; i < bookNames.size(); i++) {
                                Book book = new Book(bookNames.get(i), imageUrls.get(i), authors.get(i), bookUrls.get(i), new Category(categoryName, color, ""), "");
                                book.setTadımlık("");
                                books.add(book);
                                String name = bookNames.get(i);
                                name = name.replace(".", "1"); //1i değiştir
                                name = name.replace("/", "2");
                                name = name.replace("#", "3");
                                name = name.replace("$", "4");
                                name = name.replace("[", "5");
                                name = name.replace("]", "6");
                                bookMap.put(name, book);


                            }
                            dbref.child(categoryName).setValue(books);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter = new BookAdapter(ListActivity.this, books, color, android.R.color.white);
                    ListView listView = (ListView) findViewById(R.id.book_list);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(ListActivity.this, BookActivity.class);
                            intent.putExtra("bookName", books.get(position).getBookName());
                            intent.putExtra("imageUrl", books.get(position).getImageUrl());
                            intent.putExtra("authorName", books.get(position).getAuthor());
                            intent.putExtra("bookUrl", books.get(position).getBookUrl());
                            intent.putExtra("categoryName", books.get(position).getCategory().getCategoryName());
                            intent.putExtra("position", position);
                            startActivity(intent);
                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


    }


