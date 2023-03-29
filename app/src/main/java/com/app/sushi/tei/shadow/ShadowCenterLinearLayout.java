
package com.app.sushi.tei.shadow;

        import android.content.Context;
        import androidx.annotation.Nullable;
        import android.util.AttributeSet;
        import android.view.Gravity;
        import android.widget.LinearLayout;

        import com.app.sushi.tei.R;


public class ShadowCenterLinearLayout extends LinearLayout {
    public ShadowCenterLinearLayout(Context context) {
        super(context);
        initBackground();
    }

    public ShadowCenterLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initBackground();
    }

    public ShadowCenterLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBackground();
    }

    private void initBackground() {
        setBackground(ViewUtils.generateBackgroundWithShadow(this, R.color.colorWhite,
                R.dimen._3sdp,R.color.sliver,R.dimen._3ssp, Gravity.CENTER));
    }
}