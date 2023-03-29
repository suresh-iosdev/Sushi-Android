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

import com.app.sushi.tei.Interface.IOnItemClick;
import com.app.sushi.tei.Model.Search.SearchProduct;
import com.app.sushi.tei.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ProductHotMenuAdapter extends RecyclerView.Adapter<ProductHotMenuAdapter.CategoryViewHolder> {
    private List<SearchProduct> mainCategoryList;
    private Context context;
    private IOnItemClick onItemClick;


    public ProductHotMenuAdapter(Context context, List<SearchProduct> mainCategoryList) {
        this.context = context;
        this.mainCategoryList = mainCategoryList;
    }


    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_hot_menu_product, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        try {
            holder.txt_product_name.setText(mainCategoryList.get(position).getmProductName());
            Log.e("TAG","ImageUrlTest::"+mainCategoryList.get(position).getmProductGalleryImage());
            if (!mainCategoryList.get(position).getmProductGalleryImage().isEmpty()){
                Picasso.with(context).load(mainCategoryList.get(position).getmProductGalleryImage())
                        .error(R.drawable.place_holder_sushi_tei)
                        .placeholder(R.drawable.place_holder_sushi_tei)
                        .into(holder.img_product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mainCategoryList != null ? mainCategoryList.size() : 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txt_product_name;
        private ImageView img_product;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            img_product = itemView.findViewById(R.id.img_product);
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





