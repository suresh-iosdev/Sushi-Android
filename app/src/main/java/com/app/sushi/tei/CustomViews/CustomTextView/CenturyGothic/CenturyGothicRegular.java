package com.app.sushi.tei.CustomViews.CustomTextView.CenturyGothic;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CenturyGothicRegular extends TextView {

    public  static Typeface BebasNeueBoldText;


    public CenturyGothicRegular(Context context) {
        super(context);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public CenturyGothicRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public CenturyGothicRegular(Context context, AttributeSet attrs, int defStyleAttr) {
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
            BebasNeueBoldText = Typeface.createFromAsset(getContext().getAssets(), "centurygothic/gothic.ttf");
        }

        setTypeface(BebasNeueBoldText);
    }


}
