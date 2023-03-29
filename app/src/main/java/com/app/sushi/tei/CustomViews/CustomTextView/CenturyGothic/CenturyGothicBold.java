package com.app.sushi.tei.CustomViews.CustomTextView.CenturyGothic;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CenturyGothicBold extends TextView {

    public  static Typeface BebasNeueBoldText;


    public CenturyGothicBold(Context context) {
        super(context);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public CenturyGothicBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public CenturyGothicBold(Context context, AttributeSet attrs, int defStyleAttr) {
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
            BebasNeueBoldText = Typeface.createFromAsset(getContext().getAssets(), "centurygothic/gothicb.ttf");
        }

        setTypeface(BebasNeueBoldText);
    }


}
