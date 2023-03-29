package com.app.sushi.tei.CustomViews.CustomEditText.Avenir;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by root on 15/3/18.
 */

public class AvenirBoldEditText extends EditText {

    public  static Typeface AvenirBoldText;


    public AvenirBoldEditText(Context context) {
        super(context);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public AvenirBoldEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(isInEditMode())
        {
            return;
        }
        setTypeface();
    }

    public AvenirBoldEditText(Context context, AttributeSet attrs, int defStyleAttr) {
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
