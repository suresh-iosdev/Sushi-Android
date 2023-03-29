package com.app.sushi.tei.adapter.Order;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sushi.tei.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewOrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity activity;
    JSONArray productsJSONArray;

    public ViewOrderListAdapter(Activity activity, JSONArray productsJSONArray) {

        this.activity = activity;
        this.productsJSONArray = productsJSONArray;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.base_view_oreder_list_item, parent, false);

        VHItem dataObjectHolder = new VHItem(view);
        return dataObjectHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof VHItem) {

            VHItem vhItemHolder = (VHItem) holder;

            try {

                JSONObject jsonObject = productsJSONArray.getJSONObject(position);

                String quantity = jsonObject.getString("item_qty");
                String productName = jsonObject.getString("item_name");
                String price = jsonObject.getString("item_total_amount");


                vhItemHolder.qtyTextView.setText(quantity);
                vhItemHolder.itemTextView.setText(productName.replace("\\",""));

                vhItemHolder.priceTextView.setText( String.format("%.2f", Double.parseDouble(price))+"");

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    @Override
    public int getItemCount() {
        return productsJSONArray.length();
    }

    public class VHItem extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView qtyTextView, itemTextView, modifierTextView, priceTextView;

        RelativeLayout babyCardImageLayout,modifierLayout;

        ImageView babyPackImageView;

        public VHItem(View itemView) {
            super(itemView);

            qtyTextView = (TextView) itemView.findViewById(R.id.qtyTextView);
            itemTextView = (TextView) itemView.findViewById(R.id.itemTextView);
            modifierTextView = (TextView) itemView.findViewById(R.id.modifierTextView);
            priceTextView = (TextView) itemView.findViewById(R.id.priceTextView);
            modifierLayout = (RelativeLayout) itemView.findViewById(R.id.modifierLayout);

            babyCardImageLayout = (RelativeLayout) itemView.findViewById(R.id.babyCardImageLayout);
            babyPackImageView = (ImageView) itemView.findViewById(R.id.babyPackImageView);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

        }

    }

}
