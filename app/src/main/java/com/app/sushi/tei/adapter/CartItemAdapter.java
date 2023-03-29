package com.app.sushi.tei.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.sushi.tei.Model.Cart.Cart;
import com.app.sushi.tei.R;

import java.util.ArrayList;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Cart> cartlist;
    private OnItemClickListeners onItemClickListeners;

    public CartItemAdapter(Context mContext, ArrayList<Cart> cartlist) {
        this.mContext = mContext;
        this.cartlist = cartlist;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_cart_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Cart cart = cartlist.get(i);
       // viewHolder.specialmeesage_textview.setVisibility(View.GONE);

        viewHolder.productname.setText(cart.getmProductName());
        viewHolder.product_qty.setText(cart.getmProductQty());
        viewHolder.productprice.setText(cart.getmProductTotalPrice());
    }

    @Override
    public int getItemCount() {

        return cartlist != null ? cartlist.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView productname, productprice, product_qty;
  /*      private ImageView removefromcart, cartincrement, cartdecrement;
        private TextView specialmeesage_textview;*/

        public ViewHolder(View itemView) {
            super(itemView);

            productname = itemView.findViewById(R.id.cart_product_name);
            productprice = itemView.findViewById(R.id.cart_product_price);
          /*  product_qty = itemView.findViewById(R.id.cartqtydisplay);
            removefromcart = itemView.findViewById(R.id.cart_product_remove_item);
            cartincrement = itemView.findViewById(R.id.cart_product_quantity_increment);
            cartdecrement = itemView.findViewById(R.id.cart_product_quantity_decrement);
            specialmeesage_textview = itemView.findViewById(R.id.specialmesagetextview);*/
            itemView.setOnClickListener(this);
          //  removefromcart.setOnClickListener(this);
            product_qty.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
           // onItemClickListeners.OnClick(v, getAdapterPosition(), removefromcart, cartincrement, cartdecrement, product_qty);
        }

    }

    public void setOnClickListeners(OnItemClickListeners onMClickListener) {

        this.onItemClickListeners = onMClickListener;
    }

    public interface OnItemClickListeners {

        void OnClick(View v, int position, ImageView remove, ImageView increment, ImageView decrement, TextView qty);

    }

}
