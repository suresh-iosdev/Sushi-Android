package com.app.sushi.tei.adapter.Cart;

import android.content.Context;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sushi.tei.Model.ProductList.SetMenuTitle;
import com.app.sushi.tei.R;
import com.github.aakira.expandablelayout.ExpandableLayout;

import java.util.List;


public class SetMenuTitleRecyclerCartAdapter extends RecyclerView.Adapter<SetMenuTitleRecyclerCartAdapter.TitleHolder> {

    private Context mContext;
    private List<SetMenuTitle> setMenuTitleList;
        private SetMenuChildRecyclerCartAdapter setMenuChildRecyclerAdapter;
    private int minmaxSelect = 0;
    private String Status="";


    public SetMenuTitleRecyclerCartAdapter(Context mContext, List<SetMenuTitle> setMenuTitleList, int minmaxSelect, String Status) {
        this.mContext = mContext;
        this.setMenuTitleList = setMenuTitleList;
        this.minmaxSelect = minmaxSelect;
        this.Status = Status;

    }

    @Override
    public TitleHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_setmenu_parent_item, parent, false);

        TitleHolder titleHolder = new TitleHolder(view);

        return titleHolder;
    }

    @Override
    public void onBindViewHolder(final TitleHolder holder, int position) {

        if (minmaxSelect == 0) {

            holder.layoutParent.setVisibility(View.VISIBLE);
            holder.txtModifierHeading.setVisibility(View.GONE);

        } else {

            holder.layoutParent.setVisibility(View.GONE);
            holder.txtModifierHeading.setVisibility(View.VISIBLE);
        }

        holder.txtModifierTitle.setText(setMenuTitleList.get(position).getmTitleMenuName());
        holder.txtModifierHeading.setText(setMenuTitleList.get(position).getmTitleMenuName() +
                "(Minimum " + setMenuTitleList.get(position).getmMinSelect() + ", Maximum " + setMenuTitleList.get(position).getmMaxSelect() + ")");


        if (setMenuTitleList.get(position).getSetMenuModifierList() != null) {
            if (setMenuTitleList.get(position).getSetMenuModifierList().size() > 0)
            {

                holder.modifierValuesRecyclerView.setVisibility(View.VISIBLE);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                holder.modifierValuesRecyclerView.setLayoutManager(layoutManager);



                if(Status.equalsIgnoreCase("update"))
                {


                }
                else
                {




                }

                setMenuChildRecyclerAdapter = new SetMenuChildRecyclerCartAdapter(mContext,
                        setMenuTitleList.get(position).getSetMenuModifierList(),
                        minmaxSelect, setMenuTitleList, position,Status);
                holder.modifierValuesRecyclerView.setAdapter(setMenuChildRecyclerAdapter);
                holder.modifierValuesRecyclerView.setNestedScrollingEnabled(false);
                holder.modifierValuesRecyclerView.setItemAnimator(new DefaultItemAnimator());






            } else {
                holder.modifierValuesRecyclerView.setVisibility(View.GONE);
            }
        } else {
            holder.modifierValuesRecyclerView.setVisibility(View.GONE);
        }

        holder.layoutHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.expandableParentLayout.toggle();

                setMenuChildRecyclerAdapter.updateExpandableView();

            }
        });


    }

    @Override
    public int getItemCount() {
        return setMenuTitleList.size();
    }

    public class TitleHolder extends RecyclerView.ViewHolder {


        private TextView txtModifierTitle;
        private RelativeLayout layoutHeader;
        private RecyclerView modifierValuesRecyclerView;
        private ExpandableLayout expandableParentLayout;
        private TextView txtModifierHeading;
        private LinearLayout layoutParent;

        public TitleHolder(View itemView) {
            super(itemView);

            txtModifierTitle = (TextView) itemView.findViewById(R.id.txtModifierTitle);
            layoutHeader = (RelativeLayout) itemView.findViewById(R.id.layoutHeader);
            layoutParent = (LinearLayout) itemView.findViewById(R.id.layoutParent);
            modifierValuesRecyclerView = (RecyclerView) itemView.findViewById(R.id.modifierValuesRecyclerView);
            txtModifierHeading = (TextView) itemView.findViewById(R.id.txtModifierHeading);
            expandableParentLayout = (ExpandableLayout) itemView.findViewById(R.id.expandableParentLayout);


        }
    }
}
