package com.avrioenergy.avriodemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Paint.Style;

public class BubbleChart extends View {
    private int bubbleSize;
    private int bubbleColor, bubbleTextColor;
    private String bubbleText;
    Paint bubblePaint;

    public BubbleChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.BubbleChart,
                0, 0);
        try {
            //Connecting our view to its properties in attrs.xml file

            bubbleSize = a.getInteger(R.styleable.BubbleChart_bubbleSize,0);
            bubbleColor = a.getInteger(R.styleable.BubbleChart_bubbleColor,0);
            bubbleTextColor = a.getInteger(R.styleable.BubbleChart_bubbleColor,0);
            bubbleText = a.getString(R.styleable.BubbleChart_bubbleText);
        } finally {
            a.recycle();
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Height and width of the view
        int viewWidthHalf = this.getMeasuredWidth()/2;
        int viewHeightHalf = this.getMeasuredHeight()/2;

        //Styling the paint object which will get painted on the canvas
        bubblePaint= new Paint(Paint.ANTI_ALIAS_FLAG); // Initializing the Paint object else it will give an error of null point exception
        bubblePaint.setStyle(Style.FILL);
        bubblePaint.setAntiAlias(true);
        bubblePaint.setColor(bubbleColor);

        //painting on the canvas
        canvas.drawCircle(viewWidthHalf, viewHeightHalf, bubbleSize , bubblePaint);

        //setting up the text properties
        bubblePaint.setColor(bubbleTextColor);
        bubblePaint.setTextAlign(Paint.Align.CENTER);
        bubblePaint.setTextSize(50);

        //drawing the text on the canvas
        canvas.drawText(bubbleText,viewWidthHalf,viewHeightHalf,bubblePaint);
    }

    public int getBubbleSize() {
        return bubbleSize;
    }

    public int getBubbleColor() {
        return bubbleColor;
    }

    public int getBubbleTextColor() {
        return bubbleTextColor;
    }

    public String getBubbleText() {
        return bubbleText;
    }

    public void setBubbleSize(int bubbleSize) {
        this.bubbleSize = bubbleSize;
        invalidate();
        requestLayout();
    }

    public void setBubbleColor(int bubbleColor) {
        this.bubbleColor = bubbleColor;
        invalidate();
        requestLayout();
    }

    public void setBubbleTextColor(int bubbleTextColor) {
        this.bubbleTextColor = bubbleTextColor;
        invalidate();
        requestLayout();
    }

    public void setBubbleText(String bubbleText) {
        this.bubbleText = bubbleText;
        invalidate();
        requestLayout();
    }

}
