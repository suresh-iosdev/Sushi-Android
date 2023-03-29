package com.app.sushi.tei.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.app.sushi.tei.R;

public class ChooseOutletDialog {

    private Context mContext;



    public ChooseOutletDialog(Context mContext) {
        this.mContext=mContext;
        showDialog();
    }


    public void showDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_choose_outlet_layout);
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

        RecyclerView recycle_outletList=dialog.findViewById(R.id.recycle_outletList);
   /*     LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
        recycle_outletList.setLayoutManager(linearLayoutManager);
        OutletListAdapter  outletListAdapter=new OutletListAdapter(mContext);
        recycle_outletList.setAdapter(outletListAdapter);

        outletListAdapter.setOnClickListeners(new OutletListAdapter.OnOutletClickListeners() {
            @Override
            public void OnClick(View v, int position) {
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        });*/

    }
}