package com.app.sushi.tei.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.app.sushi.tei.R;

public class LoginDialog {

    private Context mContext;

    private OnSlectedString selectedString;
    private Bundle bundle;

    public LoginDialog(Context mContext, OnSlectedString onSlectedString) {
        this.mContext = mContext;
        this.selectedString = onSlectedString;
        bundle = new Bundle();
        showDialog();
    }


    public interface OnSlectedString {
        void selectedAction(String action, Bundle bundle);

    }

    public void showDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.activity_login_layout);
        dialog.setCanceledOnTouchOutside(false);
        if (!dialog.isShowing())
            dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();

        if (window != null) {
            lp.copyFrom(window.getAttributes());

            lp.gravity = Gravity.CENTER;
            lp.width = android.view.ViewGroup.LayoutParams.FILL_PARENT;
            lp.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
            ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
            InsetDrawable inset = new InsetDrawable(back, 20);
            window.setBackgroundDrawable(inset);
            window.setAttributes(lp);
        }

        TextView createAccounts = dialog.findViewById(R.id.createAccounts);
        TextView forgotPassword = dialog.findViewById(R.id.forgotPassword);
        TextView Login = dialog.findViewById(R.id.Login);
        final EditText edtEamilAddress = dialog.findViewById(R.id.EmailId);
        final EditText edtPassword = dialog.findViewById(R.id.password);

        createAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedString.selectedAction("Register", bundle);
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedString.selectedAction("Forgot", bundle);
                if (dialog.isShowing())
                    dialog.dismiss();

            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mEmailAddress = edtEamilAddress.getText().toString();
                String mPassword = edtPassword.getText().toString();
                bundle.putString("name", mEmailAddress);
                bundle.putString("pass", mEmailAddress);
                selectedString.selectedAction("Login", bundle);
                if (dialog.isShowing())
                    dialog.dismiss();

            }
        });


    }
}