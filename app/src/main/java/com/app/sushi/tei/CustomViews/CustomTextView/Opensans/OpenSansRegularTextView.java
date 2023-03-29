package com.app.sushi.tei.CustomViews.CustomTextView.Opensans;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by root on 15/3/18.
 */

public class OpenSansRegularTextView extends TextView {

    public  static Typeface OpenSansRegularText;


    public OpenSansRegularTextView(Context context) {
        super(context);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public OpenSansRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public OpenSansRegularTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }


    private void setTypeface()
    {
        if(OpenSansRegularText==null)
        {
            OpenSansRegularText = Typeface.createFromAsset(getContext().getAssets(), "openSans/OpenSansRegular.ttf");
        }

        setTypeface(OpenSansRegularText);
    }


}
