package com.example.elifcaliskan.yky;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter<Category>{
    int textColor;
    public CategoryAdapter(Activity context, ArrayList<Category> categories, int textColor){
        super(context, 0, categories);
        this.textColor=textColor;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_category, parent, false);
        }

        Category currentWordAdapter = getItem(position);


        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.textView);

        defaultTextView.setText(currentWordAdapter.getCategoryName());
        defaultTextView.setTextColor(ContextCompat.getColor(getContext(),textColor));

        //Set the theme color for the list item
        View textContainer = listItemView.findViewById(R.id.textView);
        textContainer.setBackgroundColor(ContextCompat.getColor(getContext(), currentWordAdapter.categoryColor));


        // View playbutton = listItemView.findViewById(R.id.playicon);
        //Set Background color to the color the resource Id maps to

        return listItemView;
    }
}
