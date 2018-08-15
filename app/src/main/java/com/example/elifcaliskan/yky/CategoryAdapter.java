package com.example.elifcaliskan.yky;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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


        LovelyView lovelyView = (LovelyView)listItemView.findViewById(R.id.custView);

        lovelyView.setLabelText(currentWordAdapter.getCategoryName());

        lovelyView.setR(50);

       //Set the theme color for the list item

        lovelyView.setContainerColor(ContextCompat.getColor(getContext(), currentWordAdapter.categoryColor));

        // View playbutton = listItemView.findViewById(R.id.playicon);
        //Set Background color to the color the resource Id maps to

        return listItemView;
    }
}
