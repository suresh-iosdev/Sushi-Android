package com.app.sushi.tei.CustomViews.CustomEditText.BebasNeue;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by root on 15/3/18.
 */

public class BebasNeueLightEditText extends EditText {

    public  static Typeface BebasNeueLightText;


    public BebasNeueLightEditText(Context context) {
        super(context);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public BebasNeueLightEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public BebasNeueLightEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }


    private void setTypeface()
    {
        if(BebasNeueLightText==null)
        {
            BebasNeueLightText = Typeface.createFromAsset(getContext().getAssets(), "fjallaone/FjallaOneRegular.ttf");
        }

        setTypeface(BebasNeueLightText);
    }


}
