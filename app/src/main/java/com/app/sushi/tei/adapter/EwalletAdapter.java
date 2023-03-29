package com.app.sushi.tei.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Interface.IOnItemClick;
import com.app.sushi.tei.R;
import com.app.sushi.tei.activity.OrderSummaryActivity;
import com.app.sushi.tei.fragment.FiveMenu.FragmentReward;

import java.util.ArrayList;

import static com.app.sushi.tei.fragment.FiveMenu.FragmentReward.pos;

public class EwalletAdapter extends RecyclerView.Adapter<EwalletAdapter.EWalletViewHolder> {

    private Context context;
    private ArrayList<String> arr;
    public IOnItemClick iOnItemClick;


    public EwalletAdapter(Context context, ArrayList<String> arr) {
        this.context = context;
        this.arr = arr;
    }

    @NonNull
    @Override
    public EWalletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ewallet, parent, false);

        return new EWalletViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EWalletViewHolder holder, final int position) {

        holder.txt_ewalletAmount.setText("$" + arr.get(position));

        if (pos > 5) {
            holder.txt_ewalletAmount.setBackgroundResource(R.drawable.loginbutton);
            holder.txt_ewalletAmount.setTextColor(context.getResources().getColor(R.color.greendark));
        } else {
            if (pos == position) {
                holder.txt_ewalletAmount.setBackgroundResource(R.drawable.checkout_paynow_background);
                holder.txt_ewalletAmount.setTextColor(context.getResources().getColor(R.color.colorWhite));
                GlobalValues.eWalletAmount = holder.txt_ewalletAmount.getText().toString();

            } else {
                holder.txt_ewalletAmount.setBackgroundResource(R.drawable.loginbutton);
                holder.txt_ewalletAmount.setTextColor(context.getResources().getColor(R.color.greendark));
            }
        }

        holder.txt_ewalletAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = position;

                try {
                    FragmentReward.edt_enterAmount.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        OrderSummaryActivity.edt_enterAmount.setText("");
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class EWalletViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txt_ewalletAmount;

        public EWalletViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_ewalletAmount = itemView.findViewById(R.id.txt_ewalletAmount);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (iOnItemClick != null) {
                iOnItemClick.onItemClick(v, getPosition());
            }
        }
    }

    public void setOnItemClick(IOnItemClick click) {
        iOnItemClick = click;
    }
}
