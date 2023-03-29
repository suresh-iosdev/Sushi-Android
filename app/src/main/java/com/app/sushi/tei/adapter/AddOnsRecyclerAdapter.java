package com.app.sushi.tei.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.sushi.tei.R;


public class AddOnsRecyclerAdapter extends RecyclerView.Adapter<AddOnsRecyclerAdapter.AddOnsHolder> {
    Context context;

    public AddOnsRecyclerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public AddOnsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_addons_item, parent, false);
        AddOnsHolder orderHolder = new AddOnsHolder(view);
        return orderHolder;
    }

    @Override
    public void onBindViewHolder(final AddOnsHolder holder, int position) {

        holder.imgIncreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = Integer.parseInt(holder.txtQuantity.getText().toString());
                count++;

                holder.txtQuantity.setText(String.valueOf(count));
            }
        });

        holder.imgDecreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(holder.txtQuantity.getText().toString());

                if(count>1) {
                    count--;

                    holder.txtQuantity.setText(count + "");
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return 2;
    }

    class AddOnsHolder extends RecyclerView.ViewHolder {

        private ImageView imgIncreement, imgDecreement;
        private TextView txtQuantity;

        public AddOnsHolder(View itemView) {
            super(itemView);

            imgIncreement= (ImageView) itemView.findViewById(R.id.imgIncreement);
            imgDecreement= (ImageView) itemView.findViewById(R.id.imgDecreement);
            txtQuantity= (TextView) itemView.findViewById(R.id.txtQuantity);
        }
    }
}
