package com.app.sushi.tei.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.sushi.tei.Model.CompassOutlet.OutletResultSetItem;
import com.app.sushi.tei.R;

import java.util.List;

public class OutletListAdapter extends RecyclerView.Adapter<OutletListAdapter.ViewHolder> {
    private Context mContext;
    private OnOutletClick  onClicked;
    private List<OutletResultSetItem> outletList;



    public OutletListAdapter(Context context, List<OutletResultSetItem> outletList) {
        this.mContext = context;
        this.outletList = outletList;
    }

    @Override
    public OutletListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.adapter_outletlist_layout,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final OutletListAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.outletName.setText(outletList.get(i).getOutletName());
        viewHolder.outletAddress.setText(outletList.get(i).getOutletAddressLine1());

if(outletList.get(i).getOutletName().equalsIgnoreCase("Cafe Connect")){

    viewHolder.shopImage.setImageResource(R.drawable.cafeconnect);
        viewHolder.shopImage.setBackgroundResource(R.drawable.cafeconnect);}




    if(outletList.get(i).getOutletName().equalsIgnoreCase("Microsoft")){

        viewHolder.shopImage.setImageResource(R.drawable.microsoft);
        viewHolder.shopImage.setBackgroundResource(R.drawable.microsoft);}


}

    @Override
    public int getItemCount() {
        return outletList != null ? outletList.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        private LinearLayout selected_layout;
        private TextView outletName,outletAddress;
        private ImageView shopImage;

        public ViewHolder(View itemView){
            super(itemView);
            selected_layout=itemView.findViewById(R.id.selected_layout);
            outletName=itemView.findViewById(R.id.outletName);
            outletAddress=itemView.findViewById(R.id.outletAddress);
            shopImage=itemView.findViewById(R.id.shopImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClicked.OnClick(v,getAdapterPosition());
        }

    }


    public interface OnOutletClick {

        void OnClick(View v, int position);

    }

    public void  setOnClickListeners(OnOutletClick onMClickListener){

        this.onClicked=onMClickListener;
    }


}
