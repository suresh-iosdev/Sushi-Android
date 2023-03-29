package com.app.sushi.tei.CustomViews.CustomEditText.Avenir;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by root on 15/3/18.
 */

public class AvenirRegularEditText extends EditText {

    public  static Typeface AvenirRegularText;


    public AvenirRegularEditText(Context context) {
        super(context);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public AvenirRegularEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public AvenirRegularEditText(Context context, AttributeSet attrs, int defStyleAttr) {
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
