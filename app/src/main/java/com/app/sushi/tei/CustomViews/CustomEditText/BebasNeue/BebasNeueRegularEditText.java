package com.app.sushi.tei.CustomViews.CustomEditText.BebasNeue;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by root on 15/3/18.
 */

public class BebasNeueRegularEditText extends EditText {

    public  static Typeface BebasNeueRegularText;


    public BebasNeueRegularEditText(Context context) {
        super(context);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public BebasNeueRegularEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public BebasNeueRegularEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }


    private void setTypeface()
    {
        if(BebasNeueRegularText==null)
        {
            BebasNeueRegularText = Typeface.createFromAsset(getContext().getAssets(), "fjallaone/FjallaOneRegular.ttf");
        }

        setTypeface(BebasNeueRegularText);
    }


}
