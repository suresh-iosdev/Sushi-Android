package com.app.sushi.tei.CustomViews.CustomTextView.BebasNeue;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by root on 15/3/18.
 */

public class BebasNeueBoldTextView extends TextView {

    public  static Typeface BebasNeueBoldText;


    public BebasNeueBoldTextView(Context context) {
        super(context);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public BebasNeueBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public BebasNeueBoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }


    private void setTypeface()
    {
        if(BebasNeueBoldText==null)
        {
            BebasNeueBoldText = Typeface.createFromAsset(getContext().getAssets(), "fjallaone/FjallaOneRegular.ttf");
        }

        setTypeface(BebasNeueBoldText);
    }


}
