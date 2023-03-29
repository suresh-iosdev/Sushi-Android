package com.app.sushi.tei.adapter.MyAccount;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.sushi.tei.Interface.IOnItemClick;
import com.app.sushi.tei.Model.Account.SecondaryAddress;
import com.app.sushi.tei.R;
import com.app.sushi.tei.activity.ChooseOutletActivity;

import java.util.ArrayList;

/**
 * Created by root on 19/4/18.
 */

public class OtherAddressRecyclerAdapter extends RecyclerView.Adapter<OtherAddressRecyclerAdapter.OtherHolder> {

    private Context mContext;
    private ArrayList<SecondaryAddress> addressArrayList;
    public IOnItemClick iOnItemClick;


    public OtherAddressRecyclerAdapter(Context mContext, ArrayList<SecondaryAddress> addressArrayList) {
        this.mContext = mContext;
        this.addressArrayList = addressArrayList;
    }

    public OtherAddressRecyclerAdapter(Context context) {
        this.mContext = mContext;
    }

    @Override
    public OtherHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_saved_address, parent, false);
        OtherHolder otherHolder = new OtherHolder(view);
        return otherHolder;
    }

    @Override
    public void onBindViewHolder(OtherHolder holder, int position) {
        SecondaryAddress secondaryAddress = addressArrayList.get(position);
        SpannableString content = new SpannableString(secondaryAddress.getAddress() + ", Singapore " + secondaryAddress.getPostal_code());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        holder.txt_addressDetails.setText(content.toString());

        if (position == ChooseOutletActivity.pos) {
            holder.img_selection.setImageResource(R.drawable.selected);
        } else {
            holder.img_selection.setImageResource(R.drawable.asset54);
        }
    }

    @Override
    public int getItemCount() {
        return addressArrayList != null ? Math.min(addressArrayList.size(), 10) : 0;
    }

    public void notifyAdapter() {
        notifyDataSetChanged();
    }

    public class OtherHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txt_addressTitle, txt_addressDetails;
        private ImageView img_selection;

        public OtherHolder(View itemView) {
            super(itemView);
            txt_addressTitle = itemView.findViewById(R.id.txt_addressTitle);
            txt_addressDetails = itemView.findViewById(R.id.txt_addressDetails);
            img_selection = itemView.findViewById(R.id.img_selection);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (iOnItemClick != null) {
                ChooseOutletActivity.pos = getPosition();
                iOnItemClick.onItemClick(v, getPosition());
            }
        }
    }

    public void setOnItemClick(IOnItemClick click) {
        iOnItemClick = click;
    }
}
