package com.app.sushi.tei.CustomViews.CustomTextView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyriadproRegular extends TextView {

    public  static Typeface BebasNeueBoldText;


    public MyriadproRegular(Context context) {
        super(context);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public MyriadproRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public MyriadproRegular(Context context, AttributeSet attrs, int defStyleAttr) {
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
            BebasNeueBoldText = Typeface.createFromAsset(getContext().getAssets(), "Myriadpro_regular.otf");
        }

        setTypeface(BebasNeueBoldText);
    }


}
