package com.app.sushi.tei.CustomViews.CustomTextView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by root on 15/3/18.
 */

public class ParallogramTextView extends TextView {

    Paint mBoarderPaint;
    Paint mInnerPaint;
    Typeface OpenSansBoldText;


    public ParallogramTextView(Context context) {
        super(context);
        init();
    }

    public ParallogramTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ParallogramTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mBoarderPaint = new Paint();
        mBoarderPaint.setAntiAlias(true);
        mBoarderPaint.setColor(Color.parseColor("#893324"));
        mBoarderPaint.setStyle(Paint.Style.STROKE);
        mBoarderPaint.setStrokeWidth(6);
        mBoarderPaint.setStrokeJoin(Paint.Join.ROUND);

        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(Color.parseColor("#893324"));
        mInnerPaint.setStyle(Paint.Style.FILL);
        mInnerPaint.setStrokeJoin(Paint.Join.ROUND);


        if(OpenSansBoldText==null)
        {
            OpenSansBoldText = Typeface.createFromAsset(getContext().getAssets(), "openSans/OpenSansaBold.ttf");
        }

//        setTypeface(OpenSansBoldText);
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Path path = new Path();
        path.moveTo(getWidth(),0);
        path.lineTo(getWidth()/2, 0);
        path.lineTo(0, getHeight());
        path.lineTo(getWidth()/2,getHeight());
        path.lineTo(getWidth(), 0);
        canvas.drawPath(path, mInnerPaint);
        canvas.drawPath(path, mBoarderPaint);
    }


}
