package com.app.sushi.tei.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sushi.tei.Model.Home.MainCategory;
import com.app.sushi.tei.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<MainCategory> maiCategoryArrayList;
    private IOnItemClick iOnItemClick;
    private String mCategoryBasePath;


    public CategoryRecyclerAdapter(Context mContext, String mCategoryBasePath, ArrayList<MainCategory> mainCategoryList) {

        this.mContext = mContext;
        this.maiCategoryArrayList = mainCategoryList;
        this.mCategoryBasePath = mCategoryBasePath;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_home_category_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.txtCategoryItem.setText(maiCategoryArrayList.get(position).getmCategoryName());

             holder.overlaylayout.getBackground().setAlpha(60);
        if (maiCategoryArrayList.get(position).getPro_cat_description() != null) {

            if (!maiCategoryArrayList.get(position).getPro_cat_description().isEmpty() && !maiCategoryArrayList.get(position).getPro_cat_description().equalsIgnoreCase("")) {

                holder.txtcatdescription.setText(maiCategoryArrayList.get(position).getPro_cat_description());
            } else {

                holder.txtcatdescription.setText("");
            }

        } else {
            holder.txtcatdescription.setText("");

        }
        if (maiCategoryArrayList.get(position).getmActiveImage() != null &&
                maiCategoryArrayList.get(position).getmCategoryName().length() > 0) {

            Picasso.with(mContext).load(mCategoryBasePath + maiCategoryArrayList.get(position).getmActiveImage()).
                    error(R.drawable.default_home_image).into(holder.imgItem);

        } else {

            Picasso.with(mContext).load(R.drawable.default_home_image).into(holder.imgItem);
        }


    }

    @Override
    public int getItemCount() {
        return maiCategoryArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtCategoryItem;
        private TextView txtcatdescription;
        private ImageView imgItem;
        private RelativeLayout overlaylayout;

        public ViewHolder(View itemView) {
            super(itemView);

            txtCategoryItem = (TextView) itemView.findViewById(R.id.txtCategoryItem);
            imgItem = (ImageView) itemView.findViewById(R.id.imgItem);
            txtcatdescription = (TextView) itemView.findViewById(R.id.txtcatdescription);
            overlaylayout=itemView.findViewById(R.id.overlaylayout);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (iOnItemClick != null) {
                iOnItemClick.onItemClick(v, getPosition());
            }
        }
    }

    public void setonItemClick(IOnItemClick iOnItemClick) {
        this.iOnItemClick = iOnItemClick;
    }

    public interface IOnItemClick {

        public void onItemClick(View v, int position);

    }
}
