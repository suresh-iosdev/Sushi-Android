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
import android.widget.TextView;

import com.app.sushi.tei.R;

public class ClearCartMessageDialog {

    private Context mContext;

    private OnSlectedMethod selectedString;
    private String message;
    private String btnName;




    public ClearCartMessageDialog(Context context, String message, OnSlectedMethod onSlectedMethod) {

        this.mContext = context;
        this.message=message;
        this.selectedString = onSlectedMethod;
        showDialog();
    }

    public ClearCartMessageDialog(Context mContext, String cancel, String message, OnSlectedMethod onSlectedMethod) {

        this.mContext = mContext;
        this.message=message;
        this.selectedString = onSlectedMethod;
        this.btnName=cancel;
        showDialog();
    }


    public interface OnSlectedMethod {
        void selectedAction(String action);

    }
    public void showDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_clearcart_layout);
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
        TextView messageText=dialog.findViewById(R.id.messageText);
        messageText.setText(message);

        if (btnName!=null){
            btnCancel.setText(btnName.toUpperCase());
        }


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
                selectedString.selectedAction("Yes");
                if (dialog.isShowing())
                    dialog.dismiss();

            }
        });




    }
}