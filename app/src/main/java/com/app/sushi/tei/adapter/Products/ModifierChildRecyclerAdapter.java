package com.app.sushi.tei.adapter.Products;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.sushi.tei.Interface.IModifierClick;
import com.app.sushi.tei.Model.ProductList.ModifierHeading;
import com.app.sushi.tei.Model.ProductList.ModifiersValue;
import com.app.sushi.tei.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ModifierChildRecyclerAdapter extends RecyclerView.Adapter<ModifierChildRecyclerAdapter.ModifierHolder> {

    private Context mContext;
    private List<ModifiersValue> modifiersValueList;
    private List<ModifierHeading> modifierHeadingList;
    private IModifierClick iModifierClick;
    private int parentpos;

    public ModifierChildRecyclerAdapter(Context mContext, List<ModifierHeading> modifierHeadingList, List<ModifiersValue> modifiersValueList, int parentpos) {
        this.mContext = mContext;
        this.modifierHeadingList = modifierHeadingList;
        this.modifiersValueList = modifiersValueList;
        this.parentpos = parentpos;
    }

    @Override
    public ModifierChildRecyclerAdapter.ModifierHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_modifier_item, parent, false);
        ModifierHolder orderHolder = new ModifierHolder(view);
        return orderHolder;
    }

    @Override
    public void onBindViewHolder(final ModifierChildRecyclerAdapter.ModifierHolder holder, final int position) {

        holder.txtModifierName.setText(modifiersValueList.get(position).getmModifierName());



        if (modifiersValueList.get(position).getChekced()) {
            Picasso.with(mContext).load(R.drawable.selected).into(holder.imgChecked);
        } else {
            Picasso.with(mContext).load(R.drawable.asset54).into(holder.imgChecked);
        }

    }

    @Override
    public int getItemCount() {
        return modifiersValueList.size();
    }

    class ModifierHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtModifierName;
        private ImageView imgChecked;

        public ModifierHolder(View itemView) {
            super(itemView);

            txtModifierName = (TextView) itemView.findViewById(R.id.txtModifierName);
            imgChecked = (ImageView) itemView.findViewById(R.id.imgChecked);

            imgChecked.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (iModifierClick != null) {
                iModifierClick.onModifierClick(v, getPosition(), parentpos);
            }
        }
    }

    public void setOnItemClick(IModifierClick itemClick) {

        iModifierClick= itemClick;

    }

}
