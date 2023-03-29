package com.app.sushi.tei.CustomViews.CustomTextView.Opensans;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by root on 15/3/18.
 */

public class OpenSansLightItalicTextView extends TextView {

    public  static Typeface OpenSansLightItalicText;


    public OpenSansLightItalicTextView(Context context) {
        super(context);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public OpenSansLightItalicTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public OpenSansLightItalicTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }


    private void setTypeface()
    {
        if(OpenSansLightItalicText==null)
        {
            OpenSansLightItalicText = Typeface.createFromAsset(getContext().getAssets(), "openSans/OpenSansLightItalic.ttf");
        }

        setTypeface(OpenSansLightItalicText);
    }


}
