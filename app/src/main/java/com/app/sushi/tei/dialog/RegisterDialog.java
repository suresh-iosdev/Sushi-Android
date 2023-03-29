package com.app.sushi.tei.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.app.sushi.tei.R;

public class RegisterDialog {

    private Context mContext;


    public RegisterDialog(Context context) {
        this.mContext = context;
        showDialog();
    }



    public void showDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_register_layout);
        dialog.setCanceledOnTouchOutside(false);
        if (!dialog.isShowing())
            dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();

        if (window != null) {
            lp.copyFrom(window.getAttributes());

        lp.gravity= Gravity.CENTER;
        lp.width = android.view.ViewGroup.LayoutParams.FILL_PARENT;
        lp.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
            ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
            InsetDrawable inset = new InsetDrawable(back, 20);
            window.setBackgroundDrawable(inset);
        window.setAttributes(lp);
        }



        LinearLayout chooseOutletLayout = dialog.findViewById(R.id.chooseOutletLayout);
        Button signup1 = dialog.findViewById(R.id.signup1);

        chooseOutletLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChooseOutletDialog(mContext);

            }
        });
        signup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(dialog.isShowing())
                    dialog.dismiss();

            }
        });
}
}