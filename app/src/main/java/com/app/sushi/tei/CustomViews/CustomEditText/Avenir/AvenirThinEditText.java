package com.app.sushi.tei.CustomViews.CustomEditText.Avenir;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by root on 15/3/18.
 */

public class AvenirThinEditText extends EditText {

    public  static Typeface AvenirThinText;


    public AvenirThinEditText(Context context) {
        super(context);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public AvenirThinEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public AvenirThinEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }


    private void setTypeface()
    {
        if(AvenirThinText==null)
        {
            AvenirThinText = Typeface.createFromAsset(getContext().getAssets(), "avenir/AvenirThin.ttf");
        }

        setTypeface(AvenirThinText);
    }


}
