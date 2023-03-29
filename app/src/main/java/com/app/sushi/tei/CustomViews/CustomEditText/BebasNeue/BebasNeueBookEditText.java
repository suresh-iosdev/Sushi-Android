package com.app.sushi.tei.CustomViews.CustomEditText.BebasNeue;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by root on 15/3/18.
 */

public class BebasNeueBookEditText extends EditText {

    public  static Typeface BebasNeueBookText;


    public BebasNeueBookEditText(Context context) {
        super(context);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public BebasNeueBookEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public BebasNeueBookEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }


    private void setTypeface()
    {
        if(BebasNeueBookText==null)
        {
            BebasNeueBookText = Typeface.createFromAsset(getContext().getAssets(), "fjallaone/FjallaOneRegular.ttf");
        }

        setTypeface(BebasNeueBookText);
    }


}
