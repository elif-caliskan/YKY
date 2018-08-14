package com.example.elifcaliskan.yky;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter<Category>{
    public CategoryAdapter(Activity context, ArrayList<Category> categories){
        super(context, 0, categories);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_category, parent, false);
        }

        Category currentWordAdapter = getItem(position);
        ImageView iconView = (ImageView) listItemView.findViewById(R.id.category_icon);

        String categoryName = currentWordAdapter.getCategoryName();

        iconView.setImageResource(R.drawable.rocket);
        iconView.setVisibility(View.VISIBLE);


        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.list_item_category);

        defaultTextView.setText(currentWordAdapter.getCategoryName());

       //Set the theme color for the list item
        View textContainer = listItemView.findViewById(R.id.textContainer);
        // View playbutton = listItemView.findViewById(R.id.playicon);
        //Set Background color to the color the resource Id maps to

        return listItemView;
    }
}
