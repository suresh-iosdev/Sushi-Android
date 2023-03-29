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

import com.app.sushi.tei.R;

public class MessageDialog {

    private Context mContext;

    private OnSlectedString selectedString;



    public MessageDialog(Context mContext, OnSlectedString onSlectedString) {
        this.mContext = mContext;
        this.selectedString = onSlectedString;
        showDialog();
    }


    public interface OnSlectedString {
        void selectedAction(String action);

    }
    public void showDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_show_message_layout);
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

        Button btnCancel=dialog.findViewById(R.id.btnCancel);
        Button btnOk=dialog.findViewById(R.id.btnOk);



        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedString.selectedAction("Cancel");
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedString.selectedAction("Ok");
                if (dialog.isShowing())
                    dialog.dismiss();

            }
        });
    }
}