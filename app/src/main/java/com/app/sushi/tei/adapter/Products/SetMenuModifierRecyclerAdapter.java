package com.app.sushi.tei.adapter.Products;

import android.content.Context;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sushi.tei.Model.ProductList.ModifierHeading;
import com.app.sushi.tei.R;
import com.github.aakira.expandablelayout.ExpandableLayout;

import java.util.List;

import static com.app.sushi.tei.activity.SubCategoryActivity.quantityCost;


public class SetMenuModifierRecyclerAdapter extends RecyclerView.Adapter<SetMenuModifierRecyclerAdapter.SetMenuModifierHolder> {

    private Context mContext;
    private List<ModifierHeading> modifierHeadingList;
    private String applyPrice;
    private String status;

    public SetMenuModifierRecyclerAdapter(Context mContext, List<ModifierHeading> modifierHeadingList, String applyPrice, String status) {
        this.mContext = mContext;
        this.applyPrice = applyPrice;
        this.modifierHeadingList = modifierHeadingList;
        this.status = status;

        Log.e("quantMod", quantityCost + "--");
    }

    @Override
    public SetMenuModifierHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(mContext).inflate(R.layout.layout_setmenu_grandhchild_item,parent,false);

        SetMenuModifierHolder setMenuModifierHolder=new SetMenuModifierHolder(view);

        return setMenuModifierHolder;
    }

    @Override
    public void onBindViewHolder(final SetMenuModifierHolder holder, final int position) {

        holder.txtSubModifierName.setText(modifierHeadingList.get(position).getmModifierHeading()
                + "(MINIMUM " + modifierHeadingList.get(position).getmModifierMinSelect() + ", MAXIMUM " +  modifierHeadingList.get(position).getmModifierMaxSelect() + ")");

        modifierHeadingList.get(position).setModifierMinSelect(modifierHeadingList.get(position).getmModifierMinSelect());


        if (modifierHeadingList.get(position).getModifiersList()!=null)
        {
            if(modifierHeadingList.get(position).getModifiersList().size()>0)
            {
                holder.submodifiersRecyclerView.setVisibility(View.VISIBLE);

                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(mContext);
                holder.submodifiersRecyclerView.setLayoutManager(layoutManager);

               /* SetMenuModifierValueRecyclerAdapter setMenuModifierValueRecyclerAdapter=new
                        SetMenuModifierValueRecyclerAdapter(mContext,modifierHeadingList.get(position).getModifiersList());*/

                SetMenuModifierValuePlusMinusAdapter setMenuModifierValueRecyclerAdapter=new
                        SetMenuModifierValuePlusMinusAdapter(mContext,modifierHeadingList.get(position).getModifiersList(),modifierHeadingList, position, applyPrice, status);

                holder.submodifiersRecyclerView.setAdapter(setMenuModifierValueRecyclerAdapter);

                holder.submodifiersRecyclerView.setNestedScrollingEnabled(false);
                holder.submodifiersRecyclerView.setItemAnimator(new DefaultItemAnimator());


            }else{
                holder.submodifiersRecyclerView.setVisibility(View.GONE);
            }
        }else{
            holder.submodifiersRecyclerView.setVisibility(View.GONE);
        }

        holder.layoutSubHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.expandableChildLayout.toggle();
            }
        });

    }

    @Override
    public int getItemCount() {
        return modifierHeadingList.size();
    }

    public class SetMenuModifierHolder extends RecyclerView.ViewHolder {

        private TextView txtSubModifierName;
        private RelativeLayout layoutSubHeader;
        private RecyclerView submodifiersRecyclerView;
        private ExpandableLayout expandableChildLayout;


        public SetMenuModifierHolder(View itemView) {
            super(itemView);

            txtSubModifierName= (TextView) itemView.findViewById(R.id.txtSubModifierName);
            layoutSubHeader= (RelativeLayout) itemView.findViewById(R.id.layoutSubHeader);
            submodifiersRecyclerView= (RecyclerView) itemView.findViewById(R.id.submodifiersRecyclerView);
            expandableChildLayout= (ExpandableLayout) itemView.findViewById(R.id.expandableChildLayout);


        }
    }
}
