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
import com.app.sushi.tei.Model.Home.MainCategory;
import com.app.sushi.tei.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CategoryNewAdapter extends RecyclerView.Adapter<CategoryNewAdapter.CategoryViewHolder> {
    private List<MainCategory> mainCategoryList;
    private Context context;
    private IOnItemClick onItemClick;
    private String TAG="CategoryNewAdapter";


    public CategoryNewAdapter(Context context, List<MainCategory> mainCategoryList) {
        this.context = context;
        this.mainCategoryList = mainCategoryList;
    }


    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_category_new, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        try {
            holder.txtCategory_name.setText(mainCategoryList.get(position).getmCategoryName());

            Log.e(TAG,"Category_image_test::"+mainCategoryList.get(position).getmActiveImage());

            Picasso.with(context).load(mainCategoryList.get(position).getmActiveImage())
                    .error(R.drawable.place_holder_sushi_tei)
                    .placeholder(R.drawable.place_holder_sushi_tei)
                    .into(holder.img_category);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mainCategoryList != null ? mainCategoryList.size() : 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtCategory_name;
        private ImageView img_category;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategory_name = itemView.findViewById(R.id.txtCategory_name);
            img_category = itemView.findViewById(R.id.img_category);
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





