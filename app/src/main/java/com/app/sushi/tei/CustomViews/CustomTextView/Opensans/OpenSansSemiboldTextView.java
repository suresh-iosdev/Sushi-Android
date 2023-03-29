package com.app.sushi.tei.CustomViews.CustomTextView.Opensans;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by root on 15/3/18.
 */

public class OpenSansSemiboldTextView extends TextView {

    public  static Typeface OpenSansSemiboldText;


    public OpenSansSemiboldTextView(Context context) {
        super(context);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public OpenSansSemiboldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public OpenSansSemiboldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }


    private void setTypeface()
    {
        if(OpenSansSemiboldText==null)
        {
            OpenSansSemiboldText = Typeface.createFromAsset(getContext().getAssets(), "openSans/OpenSansSemibold.ttf");
        }

        setTypeface(OpenSansSemiboldText);
    }


}
