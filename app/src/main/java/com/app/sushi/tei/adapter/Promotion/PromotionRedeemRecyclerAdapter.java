package com.app.sushi.tei.adapter.Promotion;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sushi.tei.Model.Promotion.PromotionRedeemed;
import com.app.sushi.tei.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;


public class PromotionRedeemRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    static Activity activity;
    static OnItemClickListener mItemClickListener;
    ArrayList<PromotionRedeemed> promotionRedeemedArrayList;


    public PromotionRedeemRecyclerAdapter(Activity activity, ArrayList<PromotionRedeemed> promotionRedeemedArrayList) {

        this.promotionRedeemedArrayList = promotionRedeemedArrayList;
        this.activity = activity;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_promotion_item_new, parent, false);
        VHItem dataObjectHolder = new VHItem(view);
        return dataObjectHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        final VHItem vhItemHolder = (VHItem) holder;
        vhItemHolder.txt_promotion_label.setText(promotionRedeemedArrayList.get(position).getPromotionTitle());
        //vhItemHolder.txt_promotion.setText(promotionRedeemedArrayList.get(position).getPromotionPercentage() + " OFF");
        vhItemHolder.txtPromoCode.setText("" + promotionRedeemedArrayList.get(position).getPromotionCode());
        //vhItemHolder.txtDateLeft.setText(promotionRedeemedArrayList.get(position).getPromoDaysleft());

        if(promotionRedeemedArrayList.get(position).getPromoType().equalsIgnoreCase("fixed")){
            vhItemHolder.clientCurrency.setVisibility(View.VISIBLE);
            vhItemHolder.txt_promotion.setText(promotionRedeemedArrayList.get(position).getPromoMaxAmt() + " OFF");
        }else{
            vhItemHolder.clientCurrency.setVisibility(View.GONE);
            vhItemHolder.txt_promotion.setText(promotionRedeemedArrayList.get(position).getPromotionPercentage() + "% OFF");
        }



        try {
            //vhItemHolder.txtDescription.setText(Html.fromHtml(promotionRedeemedArrayList.get(position).getPromotDiscription()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        vhItemHolder.layoutView.setVisibility(View.GONE);
        vhItemHolder.txt_redeem.setText("Redeemed");
        //vhItemHolder.layoutPromoCode.setVisibility(View.VISIBLE);
        if (promotionRedeemedArrayList.get(position).getPromotionImage() != null && !promotionRedeemedArrayList.get(position).getPromotionImage().isEmpty()) {


           /* Picasso.with(activity)
                    .load(productdatas.get(position).getPromotionImage())
                    .placeholder(R.drawable.product_no_image)
                    .into(vhItemHolder.image_imagebg);*/

            try {
                RequestCreator requestCreator = Picasso.with(activity).load(promotionRedeemedArrayList.get(position).getPromotionImage());
                //  requestCreator.placeholder(R.drawable.product_detail_no_image_org);
                //  requestCreator.error(R.drawable.product_detail_no_image_org);

                requestCreator.into(vhItemHolder.imgBackground, new Callback() {
                    @Override
                    public void onSuccess() {
                        //vhItemHolder.layoutPromoCode.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        vhItemHolder.imgBackground.setImageResource(R.drawable.place_holder_sushi_tei);

                       // vhItemHolder.layoutPromoCode.setVisibility(View.VISIBLE);
                        //vhItemHolder.imagebg_image.setImageResource(R.drawable.product_detail_no_image_org);
                    }
                });

            } catch (Exception e) {
                vhItemHolder.imgBackground.setImageResource(R.drawable.place_holder_sushi_tei);
                // vhItemHolder.layoutPromoCode.setVisibility(View.VISIBLE);
                //vhItemHolder.imagebg_image.setImageResource(R.drawable.product_detail_no_image_org);
                e.printStackTrace();

            }
            //TODO
/*

            Picasso.with(activity).load(promotionRedeemedArrayList.get(position).getPromotionImage()).
                    error(R.drawable.img_placeholder).into(vhItemHolder.imgBackground);
*/

        } else {
            //vhItemHolder.layoutPromoCode.setVisibility(View.VISIBLE);
            //   vhItemHolder.image_imagebg.setImageResource(R.drawable.product_no_image);

        }
    }

    @Override
    public int getItemCount() {

        return promotionRedeemedArrayList.size();
    }

    public static class VHItem extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_promotion, txtPromoCode, txtDateLeft, txtDescription, txt_promotion_label, txt_redeem, clientCurrency;
        RelativeLayout layoutRedeem, layoutView;
        ImageView imgBackground;


        LinearLayout layoutPromoCode;

        public VHItem(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            txtPromoCode = (TextView) itemView.findViewById(R.id.txtPromoCode);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            txtDateLeft = (TextView) itemView.findViewById(R.id.txtDateLeft);
            layoutRedeem = (RelativeLayout) itemView.findViewById(R.id.layoutRedeem);
            txt_redeem = itemView.findViewById(R.id.txt_redeem);
            layoutView = (RelativeLayout) itemView.findViewById(R.id.layoutView);
            imgBackground = (ImageView) itemView.findViewById(R.id.imgBackground);
            txt_promotion = itemView.findViewById(R.id.txt_promotion);
            txt_promotion_label = itemView.findViewById(R.id.txt_promotion_label);
            clientCurrency = itemView.findViewById(R.id.clientCurrency);

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
