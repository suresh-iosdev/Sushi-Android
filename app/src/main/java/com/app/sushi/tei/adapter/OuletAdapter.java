package com.app.sushi.tei.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.LinearLayoutCompat;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OuletAdapter extends RecyclerView.Adapter<OuletAdapter.ViewHolder> {
    private Context mContext;
    private OnOutletClick onClicked;
    private List<ResultSetItem> outletResultSetItemList;
    private ArrayList<Integer> outletImageList;

    boolean outletSelected = false;
    int selectedPosition = 0;

    public OuletAdapter(Context mContext, List<ResultSetItem> outletResultSetItemList, ArrayList<Integer> outletImageList) {
        this.mContext = mContext;
        this.outletResultSetItemList = outletResultSetItemList;
        this.outletImageList = outletImageList;
    }

    @NonNull
    @Override
    public OuletAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_outlet_list_new, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OuletAdapter.ViewHolder viewHolder, int i) {

        viewHolder.outletName.setText(outletResultSetItemList.get(i).getOutletName());
        viewHolder.outletAddress.setText(outletResultSetItemList.get(i).getOutletAddressLine1() + " Singapore " + outletResultSetItemList.get(i).getOutletPostalCode());
        viewHolder.txt_operationSlot.setText("Earliest Collection Time " + outletResultSetItemList.get(i).getOperationHrs());

        //viewHolder.txt_operationSlot.setText("Operations: 10am - 6pm");
        if (outletResultSetItemList.get(i).getOutletMarkerLongitude().equalsIgnoreCase("") && (outletResultSetItemList.get(i).getOutletMarkerLatitude().equalsIgnoreCase(""))) {
            viewHolder.txt_outletDistance.setVisibility(View.GONE);
            viewHolder.img_location.setVisibility(View.GONE);
        } else {
            viewHolder.txt_outletDistance.setVisibility(View.VISIBLE);
            viewHolder.img_location.setVisibility(View.VISIBLE);
          /*  if (Double.parseDouble(outletResultSetItemList.get(i).getDistance()) <= 1.0) {
                viewHolder.txt_outletDistance.setText(String.format("%.0d", (outletResultSetItemList.get(i).getDistance())) + " km");
            } else {*/
            viewHolder.txt_outletDistance.setText((int) (Double.parseDouble(outletResultSetItemList.get(i).getDistance())) + " km");
        }

        //viewHolder.txt_outletDistance.setText(String.format("%03d", (int)(Double.parseDouble(outletResultSetItemList.get(i).getDistance()))) + " km");
        viewHolder.txt_waitingMins.setText("Waiting: " + outletResultSetItemList.get(i).getOutletPickupTat() + "mins");
        //viewHolder.shopImage.setImageResource(outletImageList.get(i));
        if (outletResultSetItemList.get(i).getOutlet_image().contains("jpg")||
                outletResultSetItemList.get(i).getOutlet_image().contains("jpeg")||
                outletResultSetItemList.get(i).getOutlet_image().contains("png")) {
            Picasso.with(mContext).load(outletResultSetItemList.get(i).getOutlet_image())
                    .error(R.drawable.place_holder_sushi_tei)
                    .into(viewHolder.shopImage);
        }else {
            Log.e("TAG","OutletImageTest::"+outletResultSetItemList.get(i).getOutlet_image()+"\n"+
                    outletResultSetItemList.get(i).getOutletName());
            viewHolder.shopImage.setImageResource(R.drawable.place_holder_sushi_tei);
        }


//        if (outletResultSetItemList.get(i).getOutletId().equalsIgnoreCase("16")) {
//            viewHolder.shopImage.setImageResource(R.drawable.change_alley_mall);
//        }
//
//        if (outletResultSetItemList.get(i).getOutletId().equalsIgnoreCase("14")) {
//            viewHolder.shopImage.setImageResource(R.drawable.outlet_313);
//        }
//
//        if (outletResultSetItemList.get(i).getOutletId().equalsIgnoreCase("10")) {
//            viewHolder.shopImage.setImageResource(R.drawable.outlet_funan);
//        }
//
//        if (outletResultSetItemList.get(i).getOutletId().equalsIgnoreCase("11")) {
//            viewHolder.shopImage.setImageResource(R.drawable.outlet_plq);
//        }
//
//        if (outletResultSetItemList.get(i).getOutletId().equalsIgnoreCase("15")) {
//            viewHolder.shopImage.setImageResource(R.drawable.changi_city_point);
//        }
//
//        if (outletResultSetItemList.get(i).getOutletId().equalsIgnoreCase("13")) {
//            viewHolder.shopImage.setImageResource(R.drawable.outlet_westgate);
//        }
//
//        if (outletResultSetItemList.get(i).getOutletId().equalsIgnoreCase("9")) {
//            viewHolder.shopImage.setImageResource(R.drawable.outlet_suntec);
//        }
//
//        if (outletResultSetItemList.get(i).getOutletId().equalsIgnoreCase("12")) {
//            viewHolder.shopImage.setImageResource(R.drawable.outlet_novena);
//        }
//
//        if (outletResultSetItemList.get(i).getOutletId().equalsIgnoreCase("18")) {
//            viewHolder.shopImage.setImageResource(R.drawable.outlet_guoco_tower);
//        }

        if (outletSelected) {
            if (selectedPosition == i) {
                viewHolder.lnr_child.setBackground(ContextCompat.getDrawable(mContext, R.drawable.login_register_bg_new2));
            } else {
                viewHolder.lnr_child.setBackgroundResource(0);
            }
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
        LinearLayoutCompat lnr_child;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            selected_layout = itemView.findViewById(R.id.selected_layout);
            lnr_child = itemView.findViewById(R.id.lnr_child);
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

    public void outletSelected(int pos) {
        selectedPosition = pos;
        outletSelected = true;
        notifyItemChanged(pos);
        for (int i = 0; i < outletResultSetItemList.size(); i++) {
            notifyItemChanged(i);
        }
    }
}
