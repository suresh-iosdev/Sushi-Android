package com.app.sushi.tei.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sushi.tei.Model.Cart.CartModifier;
import com.app.sushi.tei.Model.OrderHistory;
import com.app.sushi.tei.Model.ProductList.ModifierHeading;
import com.app.sushi.tei.Model.ProductList.ModifiersValue;
import com.app.sushi.tei.Model.ProductList.SetMenuModifier;
import com.app.sushi.tei.Model.ProductList.SetMenuTitle;
import com.app.sushi.tei.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ThanksRecyclerAdapter extends RecyclerView.Adapter<ThanksRecyclerAdapter.OrderDetailHolder> {

    private Context mContext;
    private List<OrderHistory> orderHistoryList;

    public ThanksRecyclerAdapter(Context mContext, List<OrderHistory> orderHistoryList) {
        this.mContext = mContext;
        this.orderHistoryList = orderHistoryList;
    }

    @Override
    public ThanksRecyclerAdapter.OrderDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_thanks_order_item, parent, false);
        OrderDetailHolder orderHolder = new OrderDetailHolder(view);
        return orderHolder;
    }

    @Override
    public void onBindViewHolder(OrderDetailHolder holder, int position) {


        if (orderHistoryList.get(position).getmProductImage().length() > 0) {

            Picasso.with(mContext).load(orderHistoryList.get(position).getmProductImage())
                    .error(R.drawable.default_image).into(holder.imgProduct);

        } else {
            holder.imgProduct.setVisibility(View.GONE);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0.6f
            );
            LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0.4f
            );
            holder.parentLayout.setWeightSum(1f);
            holder.productNameLayout.setLayoutParams(param);
            holder.amountlayout.setLayoutParams(param1);
        }

        holder.txtProductName.setText(orderHistoryList.get(position).getmProductQty() + " x " + orderHistoryList.get(position).getmProductName());
        holder.txtAmount.setText(orderHistoryList.get(position).getmProductTotalPrice());

        if (orderHistoryList.get(position).getmProductSpecification().length() > 0) {

            holder.txtComments.setVisibility(View.VISIBLE);

            holder.txtComments.setText("Notes: " + orderHistoryList.get(position).getmProductSpecification());

        } else {
            holder.txtComments.setVisibility(View.GONE);
        }

        if (orderHistoryList.get(position).getCartModifierList() != null && orderHistoryList.get(position).getCartModifierList().size() > 0) {
            String modifierName = "";

            List<CartModifier> cartModifierList = orderHistoryList.get(position).getCartModifierList();
            for (int x = 0; x < cartModifierList.size(); x++) {
                if (cartModifierList.get(x).getCartModifierValueList().size() > 0) {
                    for (int y = 0; y < cartModifierList.get(x).getCartModifierValueList().size(); y++) {

                        modifierName += cartModifierList.get(x).getmModifierName() + " : " +
                                cartModifierList.get(x).getCartModifierValueList().get(y).getmModifierValueName() + "\n";
                    }
                }
            }

            holder.txtProductType.setText(modifierName);
        }

        if (orderHistoryList.get(position).getmItemVoucherName() != null && orderHistoryList.get(position).getmItemVoucherName().length() > 0) {
            holder.txt_discountApplied.setVisibility(View.VISIBLE);
        } else {
            holder.txt_discountApplied.setVisibility(View.GONE);
        }

        if (!(orderHistoryList.get(position).getmItemVoucherOrderId().equalsIgnoreCase("null")) && orderHistoryList.get(position).getmItemVoucherOrderId().length() > 0) {
            if(orderHistoryList.get(position).getmItemVoucherFreeProduct().equalsIgnoreCase("1")){
                holder.txt_voucherApplied.setVisibility(View.GONE);
                holder.lly_price.setVisibility(View.VISIBLE);
                holder.txt_FreeApplied.setVisibility(View.VISIBLE);
            }else {
                holder.lly_price.setVisibility(View.INVISIBLE);
                holder.txt_voucherApplied.setVisibility(View.VISIBLE);
                holder.txt_FreeApplied.setVisibility(View.GONE);
            }
        } else {
            holder.lly_price.setVisibility(View.VISIBLE);
            holder.txt_voucherApplied.setVisibility(View.GONE);
            holder.txt_FreeApplied.setVisibility(View.GONE);
        }

        if (orderHistoryList.get(position).getSetMenuTitleList() != null && orderHistoryList.get(position).getSetMenuTitleList().size() > 0) {

            String name = "";

            List<SetMenuTitle> setMenuTitleList = orderHistoryList.get(position).getSetMenuTitleList();

            for (int t = 0; t < setMenuTitleList.size(); t++) {
                SetMenuTitle setMenuTitle = setMenuTitleList.get(t);

                name += setMenuTitle.getmTitleMenuName() + ": ";

                List<SetMenuModifier> setMenuModifierList = setMenuTitle.getSetMenuModifierList();

                if (setMenuModifierList != null && setMenuModifierList.size() > 0) {

                    for (int sm = 0; sm < setMenuTitle.getSetMenuModifierList().size(); sm++) {

                        SetMenuModifier setMenuModifier = setMenuTitle.getSetMenuModifierList().get(sm);

                        if (setMenuModifier.getmQuantity() > 0) {

                            name += "" + setMenuModifier.getmQuantity() + "x";
                        } else {
                            name += "";
                        }

                        name += setMenuModifier.getmModifierName() + "\n";

                        List<ModifierHeading> modifierHeadingList = setMenuModifier.getModifierHeadingList();

                        if (modifierHeadingList != null && modifierHeadingList.size() > 0) {
                            for (int h = 0; h < modifierHeadingList.size(); h++) {
                                ModifierHeading modifierHeading = modifierHeadingList.get(h);
                                name += modifierHeading.getmModifierHeading() + ": ";
                                List<ModifiersValue> modifiersValueList = modifierHeading.getModifiersList();

                                if (modifiersValueList.size() > 0) {

                                    for (int v = 0; v < modifiersValueList.size(); v++) {
                                        ModifiersValue modifiersValue = modifiersValueList.get(v);
                                        if (modifiersValue.getmSubModifierTotal() != 0) {
                                            name += modifiersValue.getmSubModifierTotal() + "x" + modifiersValue.getmModifierName() + ", ";
                                        }
                                    }
                                    name += "\n";
                                }
                            }
                        }
                    }
                }
            }

            holder.txtProductType.setText(name);
        }


    }

    @Override
    public int getItemCount() {
        return orderHistoryList.size();
    }

    class OrderDetailHolder extends RecyclerView.ViewHolder {

        View orderdetailView;
        private ImageView imgProduct;
        private TextView txtProductName, txtAmount, txtComments, txtProductType, txt_discountApplied, txt_voucherApplied, txt_FreeApplied;
        private LinearLayout parentLayout, lly_price;
        private RelativeLayout amountlayout, productNameLayout;

        public OrderDetailHolder(View itemView) {
            super(itemView);

            orderdetailView = (View) itemView.findViewById(R.id.orderdetailView);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            txtProductName = (TextView) itemView.findViewById(R.id.txtProductName);
            txtAmount = (TextView) itemView.findViewById(R.id.txtAmount);
            txtComments = (TextView) itemView.findViewById(R.id.txtComments);
            txtProductType = (TextView) itemView.findViewById(R.id.txtProductType);
            txt_discountApplied = itemView.findViewById(R.id.txt_discountApplied);
            productNameLayout = itemView.findViewById(R.id.productNameLayout);
            amountlayout = itemView.findViewById(R.id.amountlayout);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            txt_voucherApplied = itemView.findViewById(R.id.txt_voucherApplied);
            txt_FreeApplied = itemView.findViewById(R.id.txt_FreeApplied);
            lly_price = itemView.findViewById(R.id.lly_price);

        }
    }
}
