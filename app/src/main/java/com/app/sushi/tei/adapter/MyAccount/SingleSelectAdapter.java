package com.app.sushi.tei.adapter.MyAccount;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.sushi.tei.Interface.IOnItemClick;
import com.app.sushi.tei.Model.Account.SecondaryAddress;
import com.app.sushi.tei.R;

import java.util.ArrayList;

public class SingleSelectAdapter extends RecyclerView.Adapter<SingleSelectAdapter.OtherHolder> {

    private Context mContext;
    private ArrayList<SecondaryAddress> addressArrayList;
    public IOnItemClick iOnItemClick;

    public SingleSelectAdapter(Context mContext, ArrayList<SecondaryAddress> addressArrayList) {
        this.mContext = mContext;
        this.addressArrayList = addressArrayList;
    }

    @Override
    public SingleSelectAdapter.OtherHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.items_secondary_address_layout, parent, false);

        OtherHolder otherHolder = new OtherHolder(view);

        return otherHolder;
    }

    @Override
    public void onBindViewHolder(OtherHolder holder, int position)
    {
        SecondaryAddress secondaryAddress = addressArrayList.get(position);

        holder.txtAdress.setText(secondaryAddress.getAddress() + "  - " + secondaryAddress.getPostal_code());

//TODO
      /*  if(HomeActivity.pos == position)
        {

            holder.imageChecked.setImageResource(R.drawable.modifier_checked);
        }
        else
        {

            holder.imageChecked.setImageResource(R.drawable.modifier_unchecked);

        }
*/






    }

    @Override
    public int getItemCount() {
        return addressArrayList != null ? addressArrayList.size() : 0;
    }



    public class OtherHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtAdress;

        private ImageView imageChecked;


        public OtherHolder(View itemView) {
            super(itemView);
            txtAdress = (TextView) itemView.findViewById(R.id.txtAdress);

            imageChecked = (ImageView)itemView.findViewById(R.id.imageChecked);


            imageChecked.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if(iOnItemClick!=null)
            {
                iOnItemClick.onItemClick(v,getPosition());
            }

        }
    }

    public void setOnItemClick(IOnItemClick click)
    {
        iOnItemClick=click;
    }

    public void updateCells(ArrayList<SecondaryAddress> mArpaid)
    {
        this.addressArrayList = mArpaid;
        notifyDataSetChanged();
    }
}
