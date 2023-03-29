package com.app.sushi.tei.shadow;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.app.sushi.tei.R;


public class ShadowLinearLayoutOutlet extends LinearLayout {
    public ShadowLinearLayoutOutlet(Context context) {
        super(context);
        initBackground();
    }

    public ShadowLinearLayoutOutlet(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initBackground();
    }

    public ShadowLinearLayoutOutlet(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBackground();
    }

    private void initBackground() {
        setBackground(ViewUtils.generateBackgroundWithShadow(this, R.color.colorWhite,
                R.dimen._3sdp,R.color.sliverTransperent,R.dimen._4ssp, Gravity.BOTTOM));
    }
}