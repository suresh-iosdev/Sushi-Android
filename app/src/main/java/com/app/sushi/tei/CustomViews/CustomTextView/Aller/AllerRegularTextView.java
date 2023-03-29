package com.app.sushi.tei.CustomViews.CustomTextView.Aller;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by root on 15/3/18.
 */

public class AllerRegularTextView extends TextView {

    public  static Typeface AllerRegularText;


    public AllerRegularTextView(Context context) {
        super(context);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public AllerRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public AllerRegularTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }


    private void setTypeface()
    {
        if(AllerRegularText==null)
        {
            AllerRegularText = Typeface.createFromAsset(getContext().getAssets(), "aller/aller_regular.ttf");
        }

        setTypeface(AllerRegularText);
    }


}
