package com.app.sushi.tei.CustomViews.CustomTextView.Avenir;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by root on 15/3/18.
 */

public class AvenirBoldTextView extends TextView {

    public  static Typeface AvenirBoldText;


    public AvenirBoldTextView(Context context) {
        super(context);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public AvenirBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public AvenirBoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }


    private void setTypeface()
    {
        if(AvenirBoldText==null)
        {
            AvenirBoldText = Typeface.createFromAsset(getContext().getAssets(), "avenir/Avenirbold.ttf");
        }

        setTypeface(AvenirBoldText);
    }


}
