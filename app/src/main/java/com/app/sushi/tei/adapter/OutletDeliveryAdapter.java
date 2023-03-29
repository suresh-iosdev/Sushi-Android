package com.app.sushi.tei.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.sushi.tei.Model.outlet.takeaway.ResultSetItem;
import com.app.sushi.tei.R;

import java.util.List;

public class OutletDeliveryAdapter extends RecyclerView.Adapter<OutletDeliveryAdapter.ViewHolder> {
    private Context mContext;
    private OnOutletClick onClicked;
    private List<ResultSetItem> outletResultSetItemList;

    public OutletDeliveryAdapter(Context mContext, List<ResultSetItem> outletResultSetItemList) {
        this.mContext = mContext;
        this.outletResultSetItemList = outletResultSetItemList;
    }

    @NonNull
    @Override
    public OutletDeliveryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_outlet_list_new, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OutletDeliveryAdapter.ViewHolder viewHolder, int i) {

        viewHolder.outletName.setText(outletResultSetItemList.get(i).getOutletName());
        viewHolder.outletAddress.setText(outletResultSetItemList.get(i).getOutletAddressLine1() + ", Singapore " + outletResultSetItemList.get(i).getOutletPostalCode());

       /* if(outletResultSetItemList.get(i).getOutletMarkerLongitude().equalsIgnoreCase("")  &&  (outletResultSetItemList.get(i).getOutletMarkerLatitude().equalsIgnoreCase("")) ){
            viewHolder.txt_outletDistance.setVisibility(View.GONE);
            viewHolder.img_location.setVisibility(View.GONE);
        }else {
            viewHolder.txt_outletDistance.setVisibility(View.VISIBLE);
            viewHolder.img_location.setVisibility(View.VISIBLE);
          *//*  if (Double.parseDouble(outletResultSetItemList.get(i).getDistance()) <= 1.0) {
                viewHolder.txt_outletDistance.setText(String.format("%.0d", (outletResultSetItemList.get(i).getDistance())) + " km");
            } else {*//*
            viewHolder.txt_outletDistance.setText((int) (Double.parseDouble(outletResultSetItemList.get(i).getDistance())) + " km");

        }*/

        //viewHolder.txt_outletDistance.setText(String.format("%03d", (int)(Double.parseDouble(outletResultSetItemList.get(i).getDistance()))) + " km");
        //viewHolder.txt_waitingMins.setText("Waiting:" + outletResultSetItemList.get(i).getOutletPickupTat() + "mins");

        if (outletResultSetItemList.get(i).getOutletName().equalsIgnoreCase("Cafe Connect")) {

            viewHolder.shopImage.setImageResource(R.drawable.cafeconnect);
            viewHolder.shopImage.setBackgroundResource(R.drawable.cafeconnect);
        }


        if (outletResultSetItemList.get(i).getOutletName().equalsIgnoreCase("Microsoft")) {

            viewHolder.shopImage.setImageResource(R.drawable.microsoft);
            viewHolder.shopImage.setBackgroundResource(R.drawable.microsoft);
        }
    }

    @Override
    public int getItemCount() {
        return outletResultSetItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout selected_layout;
        private TextView outletName, outletAddress, txt_operationSlot, txt_outletDistance, txt_waitingMins;
        private ImageView shopImage, img_location;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            selected_layout = itemView.findViewById(R.id.selected_layout);
            outletName = itemView.findViewById(R.id.outletName);
            outletAddress = itemView.findViewById(R.id.outletAddress);
            shopImage = itemView.findViewById(R.id.shopImage);
            img_location = itemView.findViewById(R.id.img_location);
            txt_operationSlot = itemView.findViewById(R.id.txt_operationSlot);
            txt_outletDistance = itemView.findViewById(R.id.txt_outletDistance);
            txt_waitingMins = itemView.findViewById(R.id.txt_waitingMins);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClicked.OnClick(view, getAdapterPosition());
        }
    }

    public interface OnOutletClick {

        void OnClick(View v, int position);

    }

    public void setOnClickListeners(OnOutletClick onMClickListener) {

        this.onClicked = onMClickListener;
    }

}
