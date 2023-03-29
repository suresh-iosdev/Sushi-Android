package com.app.sushi.tei.CustomViews.CustomTextView.Avenir;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by root on 15/3/18.
 */

public class AvenirRegularTextView extends TextView {

    public  static Typeface AvenirRegularText;


    public AvenirRegularTextView(Context context) {
        super(context);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public AvenirRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public AvenirRegularTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }


    private void setTypeface()
    {
        if(AvenirRegularText==null)
        {
            AvenirRegularText = Typeface.createFromAsset(getContext().getAssets(), "avenir/AvenirRegular.ttf");
        }

        setTypeface(AvenirRegularText);
    }


}
