package com.app.sushi.tei.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.sushi.tei.Interface.IOnItemClick;
import com.app.sushi.tei.Model.location.PredictionsItem;
import com.app.sushi.tei.R;

import java.util.List;


public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.DeliverLocationHolder> {
    private List<PredictionsItem> locationResponseList;
    private Context context;
    private IOnItemClick onItemClick;



    public PlacesAdapter(Context context, List<PredictionsItem> locationResponseList) {
        this.context = context;
        this.locationResponseList = locationResponseList;
    }


    @Override
    public DeliverLocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_places_filter_1, parent, false);
        return new DeliverLocationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliverLocationHolder holder, int position) {
        if (locationResponseList.get(position).getDescription().length()>30){
            holder.txtAddress.setText(locationResponseList.get(position).getDescription().substring(0,30)+"...");
        }else {
            holder.txtAddress.setText(locationResponseList.get(position).getDescription());
        }

    }

    @Override
    public int getItemCount() {
        return locationResponseList != null ? locationResponseList.size() : 0;
    }

    public class DeliverLocationHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtCityName, txtAddress;

        public DeliverLocationHolder(@NonNull View itemView) {
            super(itemView);
            txtCityName = itemView.findViewById(R.id.address);
            txtAddress = itemView.findViewById(R.id.subaddress);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            onItemClick.onItemClick(v, getAdapterPosition());
        }
    }

    public void setOnItemClick(IOnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
}





