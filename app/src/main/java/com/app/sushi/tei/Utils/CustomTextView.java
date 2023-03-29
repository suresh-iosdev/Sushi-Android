package com.app.sushi.tei.Utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.app.sushi.tei.R;

public class CustomTextView extends androidx.appcompat.widget.AppCompatTextView {
    private static final String  DEFAULT_FONT="defaultFont.ttf";

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView, 0, 0);
        String fontName = arr.getString(R.styleable.CustomTextView_fontName);
        // Sets to a default font if none is specified in xml
        if (fontName == null) {
            fontName = DEFAULT_FONT;
        }
        setFont(fontName);
    }

    private void setFont(String fontName) {
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
        setTypeface(typeface);
    }
}
