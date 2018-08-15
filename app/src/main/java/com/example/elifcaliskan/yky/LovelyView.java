package com.example.elifcaliskan.yky;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class LovelyView extends View{
    //circle and text colors
    private int containerColor, labelColor;
    //label text
    private String containerText;
    //paint for drawing custom view
    private Paint containerPaint;

    public String getContainerText() {
        return containerText;
    }

    public void setContainerText(String containerText) {
        this.containerText = containerText;
    }

    public Paint getContainerPaint() {
        return containerPaint;
    }

    public void setContainerPaint(Paint containerPaint) {
        this.containerPaint = containerPaint;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    private int r;
    public LovelyView(Context context, AttributeSet attrs){
        super(context, attrs);
        containerPaint = new Paint();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.LovelyView, 0, 0);
        try {
            //get the text and colors specified using the names in attrs.xml
            containerText = a.getString(R.styleable.LovelyView_containerLabel);
            containerColor = a.getInteger(R.styleable.LovelyView_containerColor, 0);//0 is default
            labelColor = a.getInteger(R.styleable.LovelyView_labelColor, 0);
        } finally {
            a.recycle();
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        int viewWidth = this.getMeasuredWidth();
        int viewHeight = this.getMeasuredHeight();
        containerPaint.setStyle(Paint.Style.FILL);
        containerPaint.setAntiAlias(true);
        containerPaint.setColor(containerColor);
        canvas.drawRoundRect(0,0,viewWidth,viewHeight,r,r,containerPaint);
        //set text properties
        containerPaint.setTextAlign(Paint.Align.LEFT);
        containerPaint.setTextSize(50);
        containerPaint.setColor(labelColor);
        //draw the text using the string attribute and chosen properties
        canvas.drawText(containerText, viewWidth/20, viewHeight/1.7f, containerPaint);

    }
    public int getContainerColor(){
        return containerColor;
    }

    public int getLabelColor(){
        return labelColor;
    }

    public String getLabelText(){
        return containerText;
    }

    public void setContainerColor(int newColor){
        //update the instance variable
        containerColor=newColor;
        //redraw the view
        invalidate();
        requestLayout();
    }
    public void setLabelColor(int newColor){
        //update the instance variable
        labelColor=newColor;
        //redraw the view
        invalidate();
        requestLayout();
    }
    public void setLabelText(String newLabel){
        //update the instance variable
        containerText=newLabel;
        //redraw the view
        invalidate();
        requestLayout();
    }

}
