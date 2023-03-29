package com.app.sushi.tei.adapter.Rewards;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sushi.tei.Model.Rewards.Rewards;
import com.app.sushi.tei.R;
import com.app.sushi.tei.activity.ViewOrderDetailForRewardsActivity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RewardsEarnedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    static Activity activity;
    static RewardsEarnedAdapter.OnItemClickListener mItemClickListener;
    private ArrayList<Rewards> productdatas;

    int rewardType;

    public RewardsEarnedAdapter(Activity activity, ArrayList<Rewards> Productdatas, int rewardType) {

        this.productdatas = Productdatas;
        this.activity = activity;
        this.rewardType = rewardType;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rewards_list_item, viewGroup, false);

        VHItem dataObjectHolder = new VHItem(view);

        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        final VHItem vhItemHolder = (VHItem) holder;


        String primaryId="Order #"+productdatas.get(position).getmOrderPrimaryId();
        SpannableString content = new SpannableString(primaryId);
        content.setSpan(new UnderlineSpan(), 0, primaryId.length(), 0);
        vhItemHolder.txtOrderId.setText(content);

        vhItemHolder.txtPoint.setText("Redeemed "+String.format("%.2f", new BigDecimal(productdatas.get(position).getMlhRedeemPoint())) + " points");


        vhItemHolder.txtTotal.setText(productdatas.get(position).getMlhRedeemAmount());


        String orderId= productdatas.get(position).getmOrderPrimaryId();

        String lhFrom=productdatas.get(position).getlh_from();

        if (orderId.equalsIgnoreCase("null")) {

            vhItemHolder.layoutViewReciept.setVisibility(View.GONE);
            vhItemHolder.txtOrderId.setVisibility(View.GONE);
            vhItemHolder.totalLayout.setVisibility(View.GONE);
            vhItemHolder.posText.setVisibility(View.VISIBLE);
            vhItemHolder.posText.setText(productdatas.get(position).getlh_reason());
            //String lhCreate=productdatas.get(position).getlh_created_on();


            Calendar cals = Calendar.getInstance();
            SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                //cals.setTime(sdfs.parse(lhCreate));// all done

                SimpleDateFormat simpleDateFormats = new SimpleDateFormat("dd/MM/yyyy");


                SimpleDateFormat simpleDatetimes = new SimpleDateFormat("hh:mm a");

                vhItemHolder.txtDate.setText(simpleDateFormats.format(cals.getTime()));
                vhItemHolder.txtTime.setText(simpleDatetimes.format(cals.getTime()));


            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            vhItemHolder.layoutViewReciept.setVisibility(View.VISIBLE);
            vhItemHolder.txtOrderId.setVisibility(View.VISIBLE);
            vhItemHolder.totalLayout.setVisibility(View.VISIBLE);
            vhItemHolder.posText.setVisibility(View.GONE);


            Calendar cals = Calendar.getInstance();
            SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                cals.setTime(sdfs.parse(productdatas.get(position).getmOrderDate()));// all done

                SimpleDateFormat simpleDateFormats = new SimpleDateFormat("dd/MM/yyyy");


                SimpleDateFormat simpleDatetimes = new SimpleDateFormat("hh:mm a");

                vhItemHolder.txtDate.setText(simpleDateFormats.format(cals.getTime()));
                vhItemHolder.txtTime.setText(simpleDatetimes.format(cals.getTime()));


            } catch (ParseException e) {
                e.printStackTrace();
            }

        }


        vhItemHolder.layoutViewReciept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//TODO
            /*    Intent intent = new Intent(activity, VieworderRewardsActivity.class);
                intent.putExtra("OrderId", productdatas.get(position).getmOrderId());
                activity.startActivity(intent);*/

            }
        });

        try {

            if (productdatas.get(position).getmOrderAvailabilityName().equalsIgnoreCase("pickup")) {

                // vhItemHolder.txtAvailabilityName.setText("Takeway");

            } else {
                //vhItemHolder.txtAvailabilityName.setText(productdatas.get(position).getmOrderAvailabilityName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }





        vhItemHolder.layoutViewReciept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(activity, VieworderRewardsActivity.class);
                Intent intent = new Intent(activity, ViewOrderDetailForRewardsActivity.class);
                intent.putExtra("OrderId", productdatas.get(position).getmOrderId());
                activity.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return productdatas.size();
    }

    public static class VHItem extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RelativeLayout layoutViewReciept;

        private TextView txtOrderId, txtDate, txtTime, txtTotal, txtPoint, txtEarnedLabel,
                txtAvailabilityName,posText;
        private LinearLayout totalLayout;

        public VHItem(View itemView) {
            super(itemView);

            txtOrderId = (TextView) itemView.findViewById(R.id.txtOrderId);
            txtDate = (TextView) itemView.findViewById(R.id.txtOrderDate);
            txtTime = (TextView) itemView.findViewById(R.id.txtOrderTime);
            txtTotal = (TextView) itemView.findViewById(R.id.txtOrderTotalPrice);
            txtPoint = (TextView) itemView.findViewById(R.id.txtEarnedPoints);
            totalLayout=itemView.findViewById(R.id.totalLayout);
            posText=itemView.findViewById(R.id.posText);
            layoutViewReciept = (RelativeLayout) itemView.findViewById(R.id.layoutViewReciept);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
