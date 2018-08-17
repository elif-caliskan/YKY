package com.example.elifcaliskan.yky;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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

public class SubCategoryActivity extends AppCompatActivity{
    int color;
    int[] colors={R.color.red,R.color.lightRed,
            R.color.pink, R.color.lightPink,
            R.color.purple,R.color.lightPurple,
            R.color.deepPurple,R.color.lightDeepPurple,
            R.color.indigo,R.color.lightIndigo,
            R.color.bluee,R.color.lightBluee,
            R.color.darkBlue,R.color.blue,
            R.color.lighhtBlue,R.color.lightLighhtBlue,
            R.color.teal,R.color.lightTeal,
            R.color.green,R.color.lightGreen,
            R.color.lighttGreen,R.color.lightLightGreen,
            R.color.lime,R.color.lightLime,
            R.color.yellow,R.color.lightYellow,
            R.color.orange,R.color.lightOrange,
            R.color.brown,R.color.lightBrown,
            R.color.gray,R.color.lightGray};


    ArrayList<Category> categories=new ArrayList<Category>();
    ArrayList<String> categoryUrls=new ArrayList<String>();
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
            letterMap.put("&#243;","ó");
            letterMap.put("&#200;","È");
            letterMap.put("&#201;","É");
            letterMap.put("&#202;","Ê");
            letterMap.put("&#232;","è");
            letterMap.put("&#233;","é");
            letterMap.put("&#234;","ê");
            letterMap.put("&#64;","@");
            letterMap.put("&#38;","&");

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
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.books);
        Intent intent = getIntent();
        color = intent.getIntExtra("color", R.color.blue);
        final ArrayList<Book> books = new ArrayList<Book>();

        SubCategoryActivity.DownloadTask task = new SubCategoryActivity.DownloadTask();
        String result;
        try {
            result = task.execute("http://kitap.ykykultur.com.tr/kitaplar").get();
            if (result != null)
                Log.i("Contents of URL", result);
            String[] splitResult1 = result.split("<div class=\"acc-content\">");
            String[] splitResult = splitResult1[1].split("</ul>");

            Pattern p = Pattern.compile("<li><a href=\"(.*?)\" title=");
            Matcher m = p.matcher(splitResult[0]);

            while (m.find()) {

                categoryUrls.add("http://kitap.ykykultur.com.tr/"+m.group(1));
            }

            p = Pattern.compile("title=\"(.*?)\">");
            m = p.matcher(splitResult[0]);
            int i=0;
            while (m.find()) {
                String name=m.group(1);
                if(name.contains("class")){
                    name=" -> "+name.substring(0,name.indexOf("\""));
                    name=converter(name);
                    categories.add(new Category(name,colors[i-1]));
                }
                else{
                    name=converter(name);
                    categories.add(new Category(name,colors[i]));
                    i+=2;
                }

            }

            /*for (int i = 0; i < bookNames.size(); i++) {
                books.add(new Book(bookNames.get(i), imageUrls.get(i), authors.get(i), bookUrls.get(i)));
            } renkler için düşünüleilir*/

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        CategoryAdapter adapter = new CategoryAdapter(this, categories,android.R.color.white);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SubCategoryActivity.this, ListActivity.class);
                intent.putExtra("color",categories.get(position).getCategoryColor());
                intent.putExtra("url", categoryUrls.get(position));
                startActivity(intent);
            }
        });
    }

}
