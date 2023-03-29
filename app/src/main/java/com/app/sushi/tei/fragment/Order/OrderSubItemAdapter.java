package com.app.sushi.tei.fragment.Order;

import android.app.Activity;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.sushi.tei.Model.Order.ItemResponse;
import com.app.sushi.tei.R;

import java.util.List;


public class OrderSubItemAdapter extends RecyclerView.Adapter<OrderSubItemAdapter.OrderSubItemHolder> {

    private Context mContext;
    private List<ItemResponse> ordersItemList;



    public OrderSubItemAdapter(Activity context, List<ItemResponse> items) {
        this.mContext = context;
        this.ordersItemList =items;
    }

    @Override
    public OrderSubItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_order_subitem, parent, false);

        OrderSubItemHolder orderSubItemHolder = new OrderSubItemHolder(view);

        return orderSubItemHolder;
    }

    @Override
    public void onBindViewHolder(final OrderSubItemHolder holder, final int position) {

        holder.productName.setText(ordersItemList.get(position).getItemName());
        holder.productQuantity.setText(ordersItemList.get(position).getItemQty()+"×");
        holder.productCost.setText(ordersItemList.get(position).getItemTotalAmount());



    }

    @Override
    public int getItemCount() {
        return ordersItemList.size();
    }

    public class OrderSubItemHolder extends RecyclerView.ViewHolder {

        private TextView productName,productQuantity,productCost;


        public OrderSubItemHolder(View itemView) {
            super(itemView);

            productName = (TextView) itemView.findViewById(R.id.productName);
            productQuantity = (TextView) itemView.findViewById(R.id.productQuantity);
            productCost = (TextView) itemView.findViewById(R.id.productCost);


        }
    }
}
