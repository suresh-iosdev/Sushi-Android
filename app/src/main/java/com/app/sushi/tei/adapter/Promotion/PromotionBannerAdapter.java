package com.app.sushi.tei.adapter.Promotion;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Interface.IOnItemClick;
import com.app.sushi.tei.Model.Promotion.Promotion;
import com.app.sushi.tei.R;

import java.util.ArrayList;


public class PromotionBannerAdapter extends RecyclerView.Adapter<PromotionBannerAdapter.ViewHolder> {
    public IOnItemClick iOnItemClick;
    private Context mContext;
    private ArrayList<Promotion> promotionArrayList;

    public PromotionBannerAdapter(Context mContext, ArrayList<Promotion> promotionArrayList) {
        this.mContext = mContext;
        this.promotionArrayList = promotionArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_promotions_banner, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.txt_title.setText(promotionArrayList.get(i).getmPromotionTitle());
        /*holder.txt_description.setText(promotionArrayList.get(i).getmPromodeshowText().replace("<p>", "").replace("</p>", "")
                .replace("\n", "").replace("\r", "").replace("\t", ""));*/
        if (GlobalValues.promoID != null) {
            if (promotionArrayList.get(i).getmPromotionId().equalsIgnoreCase(GlobalValues.promoID)) {
                holder.img_promo_status.setBackgroundResource(R.drawable.badge_promotions_banner_select);
            } else {
                holder.img_promo_status.setBackgroundResource(R.drawable.badge_promotions_banner);
            }
        } else {
            holder.img_promo_status.setBackgroundResource(R.drawable.badge_promotions_banner);
        }

        holder.txt_description.setText("Promocode: " + promotionArrayList.get(i).getmPromoCode());
    }

    @Override
    public int getItemCount() {
        return promotionArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RelativeLayout rly_parent;
        private TextView txt_title, txt_description;
        private ImageView img_promo_status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rly_parent = itemView.findViewById(R.id.rly_parent);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_description = itemView.findViewById(R.id.txt_description);
            img_promo_status = itemView.findViewById(R.id.img_promo_status);
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
