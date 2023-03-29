package com.app.sushi.tei.adapter.SetMenuAdapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.sushi.tei.Model.CompassAllProduct.ProductDetailsItemItem;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.adapter.Bento.BentoProductRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.app.sushi.tei.activity.SubCategoryActivity.totalAmt;
import static com.app.sushi.tei.dialog.CartDialog.productCast;
import static com.app.sushi.tei.dialog.CartDialog.productPriceText;
import static com.app.sushi.tei.dialog.CartDialog.txtQuantitys;


public class SetMenuChildRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<List<ProductDetailsItemItem>> setMenuModifierList;

    private ChildHolderApply0 childHolderApply0;

    boolean checked = false;
    private ArrayList<String> nameList=new ArrayList<>();
    private ArrayList<String> priceList=new ArrayList<>();

    public static Double modifierPrice;

    public SetMenuChildRecyclerAdapter(Context mContext, List<ProductDetailsItemItem> productDetailsList, List<List<ProductDetailsItemItem>> productDetailsItemItemList, ArrayList<String> priceList, ArrayList<String> nameList) {
        this.mContext = mContext;
        this.setMenuModifierList=productDetailsItemItemList;
        this.nameList=nameList;
        this.priceList=priceList;
    }

    /*@Override
    public int getItemViewType(int position) {

        return minmaxSelect;
    }*/

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_setmenu_apply_0_child_item, parent, false);

            childHolderApply0 = new ChildHolderApply0(view);

            return childHolderApply0;


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {



        childHolderApply0 = (ChildHolderApply0) holder;

        childHolderApply0.txtModifierName.setText(setMenuModifierList.get(position).get(0).getProductAlias());



  if (setMenuModifierList.get(position).get(0).isChecked()) {

            childHolderApply0.imgChecked.setImageResource(R.drawable.asset53);
            childHolderApply0.modifiersRecyclerView.setVisibility(View.VISIBLE);

        } else {

            childHolderApply0.imgChecked.setImageResource(R.drawable.asset54);
            childHolderApply0.modifiersRecyclerView.setVisibility(View.GONE);
        }

        childHolderApply0.txtModifierName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String price =setMenuModifierList.get(position).get(0).getProductCost();
                double p = 0;


                for (int i = 0; i < setMenuModifierList.size(); i++) {

                    if (i != position) {

                        if (!setMenuModifierList.get(position).get(0).getHasModifier()) {

                            if (setMenuModifierList.get(i).get(0).isChecked()) {
                                try {
                                    String value=setMenuModifierList.get(i).get(0).getProductCost();
                                    p = Double.valueOf(value);
                                } catch (Exception e) {
                                    p = 0;
                                    e.printStackTrace();
                                }
                                BentoProductRecyclerAdapter.mSetMenuPrice = BentoProductRecyclerAdapter.mSetMenuPrice - p;
                            }
                        }
                        setMenuModifierList.get(i).get(0).setChecked(false);
                    }

                }

                if (setMenuModifierList.get(position).get(0).isChecked())
                {

                    setMenuModifierList.get(position).get(0).setChecked(false);

                    if (!setMenuModifierList.get(position).get(0).getHasModifier())
                    {

                        BentoProductRecyclerAdapter.mSetMenuPrice = BentoProductRecyclerAdapter.mSetMenuPrice - Double.valueOf(price);
                        BentoProductRecyclerAdapter.quantityCost = BentoProductRecyclerAdapter.mSetMenuPrice * BentoProductRecyclerAdapter.mSetMenuQuantity;
//                            quantityCost = mSetMenuPrice;


                        txtQuantitys.setText(Utility.setPriceSpan(mContext, BentoProductRecyclerAdapter.quantityCost));



                    }

                } else
                {

                    setMenuModifierList.get(position).get(0).setChecked(true);

                    if (setMenuModifierList.get(position).get(0).getHasModifier()) {
                        childHolderApply0.modifiersRecyclerView.setVisibility(View.VISIBLE);

                    } else {

                        childHolderApply0.modifiersRecyclerView.setVisibility(View.GONE);

                        BentoProductRecyclerAdapter.mSetMenuPrice = BentoProductRecyclerAdapter.mSetMenuPrice + Double.valueOf(price);
                        BentoProductRecyclerAdapter.quantityCost = BentoProductRecyclerAdapter.mSetMenuPrice * BentoProductRecyclerAdapter.mSetMenuQuantity;
//                            quantityCost = mSetMenuPrice;
                        BentoProductRecyclerAdapter.txtModifierPrice.setText(Utility.setPriceSpan(mContext, BentoProductRecyclerAdapter.quantityCost));

                    }

                }
                Double modifireCost= Double.valueOf(setMenuModifierList.get(position).get(0).getProductPrice());
                modifierPrice=modifireCost;
                productPriceText.setText((String.valueOf (productCast+modifireCost)));
                totalAmt.setText(String.valueOf (productCast+modifireCost));
                notifyDataSetChanged();

            }
        });




    }


    @Override
    public int getItemCount() {
        return setMenuModifierList.size();
    }

    public void updateExpandableView() {


    }

    public class ChildHolderApply0 extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtModifierName;
        private ImageView imgChecked;
        private RecyclerView modifiersRecyclerView;


        public ChildHolderApply0(View itemView) {
            super(itemView);

            txtModifierName = (TextView) itemView.findViewById(R.id.txtModifierName);
            imgChecked = (ImageView) itemView.findViewById(R.id.imgChecked);
            modifiersRecyclerView = (RecyclerView) itemView.findViewById(R.id.modifiersRecyclerView);
            imgChecked.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }


}
