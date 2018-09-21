package com.example.elifcaliskan.yky;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Information extends RelativeLayout {
    private TextView propertyName;
    private TextView realProperty;


    public Information(Context context) {
        super(context);
        init();
    }

    public Information(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Information(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.info_list, this);
        this.propertyName = (TextView)findViewById(R.id.property_name);
        this.realProperty = (TextView)findViewById(R.id.real_property);

    }
}
