package com.example.elifcaliskan.yky;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class BookAdapter extends ArrayAdapter<Book> {

    ImageView downloadedImg;
    int mColorResourceId;
    public BookAdapter(Activity context, ArrayList<Book> books, int ColorResourceId){
            super(context, 0, books);
            mColorResourceId = ColorResourceId;
        }
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
        public Bitmap downloadImage(View view,String url){
            ImageDownloader task = new ImageDownloader();
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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item, parent, false);
            }

            Book currentWordAdapter = getItem(position);
            ImageView iconView = (ImageView) listItemView.findViewById(R.id.list_item_icon);

            String bookURL = currentWordAdapter.getBookURL();

            iconView.setImageBitmap(downloadImage(iconView,bookURL));
            iconView.setVisibility(View.VISIBLE);


            TextView defaultTextView = (TextView) listItemView.findViewById(R.id.list_item_name);

            defaultTextView.setText(currentWordAdapter.getBookName());

            TextView defaultTextView1 = (TextView) listItemView.findViewById(R.id.list_item_author);

            defaultTextView1.setText(currentWordAdapter.getAuthor());

            return listItemView;
        }
}

