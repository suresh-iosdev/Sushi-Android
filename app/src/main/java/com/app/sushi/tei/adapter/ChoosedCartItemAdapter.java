package com.app.sushi.tei.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.sushi.tei.Model.Cart.Cart;
import com.app.sushi.tei.R;

import java.util.ArrayList;

public class ChoosedCartItemAdapter extends RecyclerView.Adapter<ChoosedCartItemAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Cart> cartlist;

    public ChoosedCartItemAdapter(Context mContext) {
        this.mContext = mContext;

    }


    public ChoosedCartItemAdapter(Context mContext, ArrayList<Cart> cartArrayList) {
        this.mContext = mContext;
        this.cartlist = cartArrayList;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_choosed_cart_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Cart cart = cartlist.get(i);
        viewHolder.productname.setText(cart.getmProductName());
        viewHolder.productprice.setText(cart.getmProductTotalPrice());

        if (viewHolder.specialmeesagetextview.getVisibility()== View.VISIBLE){
            viewHolder.specialmeesagetextview.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {
        return cartlist != null ? cartlist.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView productname, productprice,specialmeesagetextview;

        public ViewHolder(View itemView) {
            super(itemView);
            productname = itemView.findViewById(R.id.cart_product_name);
            productprice = itemView.findViewById(R.id.cart_product_price);
            specialmeesagetextview=itemView.findViewById(R.id.specialmessagechoosedadpter);
        }


        @Override
        public void onClick(View v) {

        }

    }
  /*  public void  setOnClickListeners(OnItemClickListeners onMClickListener){

        this.mClickListener=onMClickListener;}

    public interface OnItemClickListeners {

        void OnClick(View v, int position);

    }*/

}
