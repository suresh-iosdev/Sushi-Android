package com.app.sushi.tei.adapter.Rewards;

import android.app.Activity;
import android.content.Intent;
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
import java.util.Date;


public class RewardsAdapters extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int REWARD_TYPE_EARNED = 1;
    public static final int REWARD_TYPE_REDEEMED = 2;

    static Activity activity;
    static OnItemClickListener mItemClickListener;
    private ArrayList<Rewards> productdatas;

    int rewardType;

    public RewardsAdapters(Activity activity, ArrayList<Rewards> Productdatas, int rewardType) {

        this.productdatas = Productdatas;

        this.activity = activity;
        this.rewardType = rewardType;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rewards_list, parent, false);

        VHItem dataObjectHolder = new VHItem(view);

        return dataObjectHolder;


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        final VHItem vhItemHolder = (VHItem) holder;


        String primaryId = "Order #" + productdatas.get(position).getmOrderPrimaryId();
        SpannableString content = new SpannableString(primaryId);
        content.setSpan(new UnderlineSpan(), 0, primaryId.length(), 0);
        vhItemHolder.txtOrderId.setText(content);

        vhItemHolder.txtDate.setText(productdatas.get(position).getTransactDate());
        vhItemHolder.txtTime.setText(productdatas.get(position).getTransactTime());
        vhItemHolder.txtTotal.setText(productdatas.get(position).getOrderAmount());
        vhItemHolder.txtPoint.setText("Points "+productdatas.get(position).getPoints());
        vhItemHolder.txtOrderId.setText("Receipt No :"+productdatas.get(position).getReceiptNo());

       /* Hide 08_11_2022
        if (productdatas.get(position).getmOrderAvailabilityName().equalsIgnoreCase("Delivery")) {
            vhItemHolder.txtOrderStatus.setText(productdatas.get(position).getmOrderAvailabilityName());
        } else {
            vhItemHolder.txtOrderStatus.setText("Takeaway");
        }


        vhItemHolder.txtPoint.setText(productdatas.get(position).getmDiscountType() + " " + String.format("%.2f", new BigDecimal(productdatas.get(position).getMlhRedeemPoint())) + " points");

        if (productdatas.get(position).getmOrderId() != null) {

        }

        vhItemHolder.txtTotal.setText(productdatas.get(position).getMlhRedeemAmount());

        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date expiryDate = inputDateFormat.parse(productdatas.get(position).getLh_expiry_on());
            vhItemHolder.txtvalidTill.setText("Valid till " + outputDateFormat.format(expiryDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        String orderId = productdatas.get(position).getmOrderPrimaryId();

        String lhFrom = productdatas.get(position).getlh_from();


        if (orderId.equalsIgnoreCase("null")) {

            vhItemHolder.layoutViewReciept.setVisibility(View.VISIBLE);
            vhItemHolder.txtOrderId.setVisibility(View.GONE);
            vhItemHolder.txtOrderStatus.setVisibility(View.GONE);
            vhItemHolder.totalLayout.setVisibility(View.GONE);
            vhItemHolder.view1.setVisibility(View.GONE);
            vhItemHolder.view2.setVisibility(View.GONE);
            vhItemHolder.posText.setVisibility(View.VISIBLE);
            vhItemHolder.posText.setText(productdatas.get(position).getlh_reason() + " - Added by admin");


        } else {
            vhItemHolder.layoutViewReciept.setVisibility(View.VISIBLE);
            vhItemHolder.txtOrderId.setVisibility(View.VISIBLE);
            vhItemHolder.txtOrderStatus.setVisibility(View.VISIBLE);
            vhItemHolder.totalLayout.setVisibility(View.VISIBLE);
            vhItemHolder.view1.setVisibility(View.VISIBLE);
            vhItemHolder.view2.setVisibility(View.VISIBLE);
            vhItemHolder.posText.setVisibility(View.GONE);



        }


        try {

            if (productdatas.get(position).getmOrderAvailabilityName().equalsIgnoreCase("pickup")) {

                // vhItemHolder.txtAvailabilityName.setText("Takeway");

            } else {
                //vhItemHolder.txtAvailabilityName.setText(productdatas.get(position).getmOrderAvailabilityName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (rewardType == REWARD_TYPE_REDEEMED) {
            // vhItemHolder.txtPoint.setText(productdatas.get(position).getmDiscountType());
        } else if (rewardType == REWARD_TYPE_EARNED) {
            // vhItemHolder.txtPoint.setText(productdatas.get(position).getmDiscountType());
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

*/
    }

    @Override
    public int getItemCount() {
        return productdatas.size();
    }

    public static class VHItem extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RelativeLayout layoutViewReciept;

        private TextView txtOrderId, txtDate, txtTime, txtTotal, txtPoint, txtEarnedLabel,
                txtAvailabilityName, posText, txtOrderStatus, txtvalidTill;
        private LinearLayout totalLayout;
        private View view1, view2;

        public VHItem(View itemView) {
            super(itemView);
            txtOrderStatus = itemView.findViewById(R.id.txtOrderStatus);
            txtOrderId = (TextView) itemView.findViewById(R.id.txtOrderId);

            txtDate = (TextView) itemView.findViewById(R.id.txtOrderDate);
            txtTime = (TextView) itemView.findViewById(R.id.txtOrderTime);

            txtTotal = (TextView) itemView.findViewById(R.id.txtOrderTotalPrice);

            txtPoint = (TextView) itemView.findViewById(R.id.txtEarnedPoints);
            txtvalidTill = itemView.findViewById(R.id.txtvalidTill);

            totalLayout = itemView.findViewById(R.id.totalLayout);
            posText = itemView.findViewById(R.id.posText);
            view1 = itemView.findViewById(R.id.view1);
            view2 = itemView.findViewById(R.id.view);

            // txtAvailabilityName = (TextView) itemView.findViewById(R.id.txtAvailabilityName);

            layoutViewReciept = (RelativeLayout) itemView.findViewById(R.id.layoutViewReciept);


            // txtEarnedLabel = (TextView) itemView.findViewById(R.id.txtEarnedPoints);


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
