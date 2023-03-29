package com.app.sushi.tei.adapter.SetMenuAdapter;

import android.content.Context;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sushi.tei.Model.CompassAllProduct.ProductDetailsItemItem;
import com.app.sushi.tei.Model.CompassAllProduct.SetMenuComponentItem;
import com.app.sushi.tei.Model.ProductList.SetMenuTitle;
import com.app.sushi.tei.R;
import com.app.sushi.tei.adapter.SetMenuChildRecyclerAdapterMulti;
import com.github.aakira.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gowtham on 5/2/19.
 */

public class SetMenuTitleRecyclerAdapterNew extends RecyclerView.Adapter<SetMenuTitleRecyclerAdapterNew.MyViewHolder> {

    List<List<ProductDetailsItemItem>> productDetailsItemItemList;
    List<ProductDetailsItemItem> productDetailsList;
    private SetMenuChildRecyclerAdapter setMenuChildRecyclerAdapter;
    private PasstheValue passtheValue;
    private String menuSetComponentName;
    private ProductDetailsItemItem productDetail;
    private List<SetMenuComponentItem> setMenuProductList;
    private ArrayList<String> nameList=new ArrayList<>();
    private ArrayList<String> priceList=new ArrayList<>();
    private Context mContext;
    private List<SetMenuTitle> setMenuTitleList;
    private int minmaxSelect = 0;
    private SetMenuChildRecyclerAdapterMulti setMenuChildRecyclerAdapterMulti;
    private int editType;

    public SetMenuTitleRecyclerAdapterNew(Context mContext, List<SetMenuTitle> setMenuTitleList, int minmaxSelect, int editType, PasstheValue passtheValue) {
        this.mContext = mContext;
        this.setMenuTitleList = setMenuTitleList;
        this.minmaxSelect = minmaxSelect;
        this.editType = editType;
        this.passtheValue =passtheValue;
    }

    public SetMenuTitleRecyclerAdapterNew(Context mContext, List<SetMenuTitle> setMenuTitleList, int minmaxSelect, int editType) {
        this.mContext = mContext;
        this.setMenuTitleList = setMenuTitleList;
        this.minmaxSelect = minmaxSelect;
        this.editType = editType;
    }

    public interface  PasstheValue
    {
        void  addtoSubtotla (String AddValue);
        void  subtoSubtotla(String SubValue);
    }

    public SetMenuTitleRecyclerAdapterNew(Context mContext, ArrayList<SetMenuComponentItem> setMenuProductList, PasstheValue passtheValue) {
        this.mContext = mContext;
        this.setMenuProductList=setMenuProductList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_setmenu_parent_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

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
            if (setMenuTitleList.get(position).getSetMenuModifierList().size() > 0) {

                holder.modifierValuesRecyclerView.setVisibility(View.VISIBLE);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);

                holder.modifierValuesRecyclerView.setLayoutManager(layoutManager);

//                if (minmaxSelect==1) {



                if(editType == 2)
                {

                    setMenuChildRecyclerAdapterMulti = new SetMenuChildRecyclerAdapterMulti(mContext,
                            setMenuTitleList.get(position).getSetMenuModifierList(), position, setMenuTitleList, "1", editType, new SetMenuChildRecyclerAdapterMulti.Incrementbutton() {
                        @Override
                        public void addtoSubtotla(String AddValue)
                        {

                            passtheValue.addtoSubtotla(AddValue);

                        }

                        @Override
                        public void subtoSubtotla(String SubValue)
                        {

                            passtheValue.subtoSubtotla(SubValue);


                        }
                    });


                }
                else
                {

                    setMenuChildRecyclerAdapterMulti = new SetMenuChildRecyclerAdapterMulti(mContext,
                            setMenuTitleList.get(position).getSetMenuModifierList(), position, setMenuTitleList, "1", editType);
//setMenuTitleList.get(position).getMenuComponentApplyPrice()

                }

                holder.modifierValuesRecyclerView.setAdapter(setMenuChildRecyclerAdapterMulti);

                holder.modifierValuesRecyclerView.setNestedScrollingEnabled(false);
                holder.modifierValuesRecyclerView.setItemAnimator(new DefaultItemAnimator());

//                }
//


            } else {
                holder.modifierValuesRecyclerView.setVisibility(View.GONE);
            }
        } else {
            holder.modifierValuesRecyclerView.setVisibility(View.GONE);
        }

        holder.layoutHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                holder.expandableParentLayout.toggle();
//
//                setMenuChildRecyclerAdapter.updateExpandableView();

            }
        });
    }

    @Override
    public int getItemCount() {
        return setMenuTitleList != null ? setMenuTitleList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtModifierTitle;
        private RelativeLayout layoutHeader;
        private RecyclerView modifierValuesRecyclerView;
        private ExpandableLayout expandableParentLayout;
        private TextView txtModifierHeading;
        private LinearLayout layoutParent;

        public MyViewHolder(View itemView) {
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


       /* productDetailsItemItemList=setMenuProductList.get(0).getProductDetails();
        productDetailsList=productDetailsItemItemList.get(position);



        holder.txtModifierTitle.setText(setMenuProductList.get(position).getMenuComponentName());

        for(int i=0;i<productDetailsItemItemList.size();i++){
            productDetailsList=productDetailsItemItemList.get(i);
            nameList.add(productDetailsList.get(0).getProductAlias());
            priceList.add(productDetailsList.get(0).getProductPrice());

        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        holder.modifierValuesRecyclerView.setLayoutManager(layoutManager);
        setMenuChildRecyclerAdapter = new SetMenuChildRecyclerAdapter(mContext, productDetailsList,productDetailsItemItemList,priceList,nameList);
        holder.modifierValuesRecyclerView.setAdapter(setMenuChildRecyclerAdapter);
        holder.modifierValuesRecyclerView.setNestedScrollingEnabled(false);
        holder.modifierValuesRecyclerView.setItemAnimator(new DefaultItemAnimator());


        holder.layoutHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                holder.expandableParentLayout.toggle();
//
//                setMenuChildRecyclerAdapter.updateExpandableView();

            }
        });
    }

    @Override
    public int getItemCount() {

        return setMenuProductList != null ? setMenuProductList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtModifierTitle;
        private RelativeLayout layoutHeader;
        private RecyclerView modifierValuesRecyclerView;
        private ExpandableLayout expandableParentLayout;
        private TextView txtModifierHeading;
        private LinearLayout layoutParent;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtModifierTitle = (TextView) itemView.findViewById(R.id.txtModifierTitle);
            layoutHeader = (RelativeLayout) itemView.findViewById(R.id.layoutHeader);
            layoutParent = (LinearLayout) itemView.findViewById(R.id.layoutParent);
            modifierValuesRecyclerView = (RecyclerView) itemView.findViewById(R.id.modifierValuesRecyclerView);
            txtModifierHeading = (TextView) itemView.findViewById(R.id.txtModifierHeading);
            expandableParentLayout = (ExpandableLayout) itemView.findViewById(R.id.expandableParentLayout);
        }
    }
}*/
