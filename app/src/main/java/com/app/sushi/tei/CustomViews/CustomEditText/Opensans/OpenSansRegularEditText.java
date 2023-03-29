package com.app.sushi.tei.CustomViews.CustomEditText.Opensans;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by root on 15/3/18.
 */

public class OpenSansRegularEditText extends EditText {

    public  static Typeface OpenSansRegularText;


    public OpenSansRegularEditText(Context context) {
        super(context);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public OpenSansRegularEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public OpenSansRegularEditText(Context context, AttributeSet attrs, int defStyleAttr) {
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
