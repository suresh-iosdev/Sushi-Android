package com.app.sushi.tei.adapter.Search;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.sushi.tei.Interface.IOnItemClick;
import com.app.sushi.tei.Model.Search.SearchProduct;
import com.app.sushi.tei.R;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder>{
    private Context mContext;
    public IOnItemClick onItemClickListener;
    private List<SearchProduct> searchProductList;

    public SearchAdapter(Context context, List<SearchProduct> list) {
        this.mContext = context;
        searchProductList=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_search_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {


        holder.txtProductName.setText(searchProductList.get(position).getProduct_alias());

    }

    @Override
    public int getItemCount() {
         return searchProductList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private TextView txtProductName;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtProductName= (TextView) itemView.findViewById(R.id.txtProductName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, getPosition());
            }
        }
    }



    public void setOnItemClickListener(IOnItemClick onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }
}
