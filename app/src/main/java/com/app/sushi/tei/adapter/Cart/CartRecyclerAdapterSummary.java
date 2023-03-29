package com.app.sushi.tei.adapter.Cart;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.Interface.ICartItemClick;
import com.app.sushi.tei.Model.Cart.Cart;
import com.app.sushi.tei.Model.Cart.CartModifier;
import com.app.sushi.tei.Model.ProductList.ModifierHeading;
import com.app.sushi.tei.Model.ProductList.ModifiersValue;
import com.app.sushi.tei.Model.ProductList.SetMenuModifier;
import com.app.sushi.tei.Model.ProductList.SetMenuTitle;
import com.app.sushi.tei.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.app.sushi.tei.activity.CartActivity.foodVoucher;


public class CartRecyclerAdapterSummary extends RecyclerView.Adapter<CartRecyclerAdapterSummary.OrderDetailHolder> {

    private Context mContext;
    public List<Cart> cartList;
    private ICartItemClick iCartItemClick;
    private int mQuantity = 1;
    private String mCustomerId = "", mReferenceId;
    private Double Product_SetComp_QTY = 0.0, Product_SetComp_PRC = 0.0;
    private Boolean iEnableproc = false;

    public CartRecyclerAdapterSummary(Context mContext, List<Cart> cartList) {
        this.mContext = mContext;
        this.cartList = cartList;
    }

    @Override
    public CartRecyclerAdapterSummary.OrderDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_cart_item_summary, parent, false);
        OrderDetailHolder orderHolder = new OrderDetailHolder(view);
        return orderHolder;
    }

    @Override
    public void onBindViewHolder(final CartRecyclerAdapterSummary.OrderDetailHolder holder, final int position) {
        if (cartList.get(position).getmVoucherOrderItemId().equalsIgnoreCase("0") && cartList.get(position).getmProductType().equalsIgnoreCase("Simple")) {
            foodVoucher = true;
        } else {
            foodVoucher = false;
        }

        if(cartList.get(position).getmProductType().equalsIgnoreCase("Simple")){
           holder.edit.setVisibility(View.GONE);
        }else{
            holder.edit.setVisibility(View.VISIBLE);
        }

        if(cartList.get(position).getCart_item_promotion_discount() != null && !cartList.get(position).getCart_item_promotion_discount().equalsIgnoreCase("null")
                && !cartList.get(position).getCart_item_promotion_discount().equalsIgnoreCase("") && Double.parseDouble(cartList.get(position).getCart_item_promotion_discount()) > 0){
            holder.membership_discount.setVisibility(View.VISIBLE);
            holder.membership_discount.setText("($" + cartList.get(position).getCart_item_promotion_discount() + " " + cartList.get(position).getCart_item_promotion_type() + " discount Applied)");
        }else{
            holder.membership_discount.setVisibility(View.GONE);
        }

        if (!(cartList.get(position).getmProductDiscountVoucherName().equalsIgnoreCase("") || cartList.get(position).getmProductDiscountVoucherName().equalsIgnoreCase("0")
                || cartList.get(position).getmProductDiscountVoucherName().equalsIgnoreCase("null") || cartList.get(position).getmProductDiscountVoucherName().equalsIgnoreCase("0.00"))) {
            holder.txt_discountApplied.setVisibility(View.VISIBLE);
        } else {
            holder.txt_discountApplied.setVisibility(View.GONE);
        }

        if (!(cartList.get(position).getmCashVoucherOrderItemId().equalsIgnoreCase("") || cartList.get(position).getmCashVoucherOrderItemId().equalsIgnoreCase("0")
                || cartList.get(position).getmCashVoucherOrderItemId().equalsIgnoreCase("null") || cartList.get(position).getmCashVoucherOrderItemId().equalsIgnoreCase("0.00"))) {
            if (cartList.get(position).getmCartItemVoucherProductFree().equalsIgnoreCase("1")) {
                holder.txt_voucherApplied.setVisibility(View.GONE);
                holder.lly_amount.setVisibility(View.VISIBLE);
                holder.lly_plusminus.setVisibility(View.GONE);
                holder.txt_FreeApplied.setVisibility(View.VISIBLE);
            } else {
                holder.lly_amount.setVisibility(View.GONE);
                holder.txt_voucherApplied.setVisibility(View.VISIBLE);
                holder.lly_plusminus.setVisibility(View.VISIBLE);
                holder.txt_FreeApplied.setVisibility(View.GONE);
            }
        } else {
            holder.lly_amount.setVisibility(View.VISIBLE);
            holder.txt_voucherApplied.setVisibility(View.GONE);
            holder.lly_plusminus.setVisibility(View.VISIBLE);
            holder.txt_FreeApplied.setVisibility(View.GONE);
        }


        if (cartList.get(position).getmSpecialNotes() != null && cartList.get(position).getmSpecialNotes().length() > 0) {
            holder.txtComments.setText("Notes: " + cartList.get(position).getmSpecialNotes());

        } else {
            holder.txtComments.setVisibility(View.GONE);
        }


        if (cartList.get(position).getmProductImage().length() > 0) {

            if (cartList.get(position).getmProductImage().contains(".jpg") || cartList.get(position).getmProductImage().contains(".jpeg")
                    || cartList.get(position).getmProductImage().contains(".png") || cartList.get(position).getmProductImage().contains(".gif")) {
                Picasso.with(mContext).load(cartList.get(position).getmProductImage()).error(R.drawable.place_holder_sushi_tei).
                        into(holder.imgProduct);
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
           /* Picasso.with(mContext).load(cartList.get(position).getmProductImage()).error(R.drawable.default_image).
                    into(holder.imgProduct);*/

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


        mQuantity = Integer.parseInt(cartList.get(position).getmProductQty());



        //holder.txtProductName.setText(cartList.get(position).getmProductQty() + " x " + cartList.get(position).getmProductName().replace("\\", ""));
        holder.txtProductName.setText(cartList.get(position).getmProductName().replace("\\", ""));
        holder.txtAmount.setText(String.format("%.2f", Double.parseDouble(cartList.get(position).getmProductTotalPrice())));
        holder.txtQuantity.setText(cartList.get(position).getmProductQty());


        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iCartItemClick != null) {
                    iCartItemClick.deleteCartItem(v, position);
                }
            }
        });

        holder.imgIncreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (cartList.get(position).getmVoucherOrderItemId().equalsIgnoreCase("0") && cartList.get(position).getmProductType().equalsIgnoreCase("Simple")) {
                    int count;

                    int voucherIncreaseQty;
                    if (cartList.get(position).getmProductVoucherIncreaseQty().equalsIgnoreCase("") || cartList.get(position).getmProductVoucherIncreaseQty().equalsIgnoreCase("0")
                            || cartList.get(position).getmProductVoucherIncreaseQty().equalsIgnoreCase("0.00") || cartList.get(position).getmProductVoucherIncreaseQty().equalsIgnoreCase("null")) {
                        voucherIncreaseQty = 1;
                    } else {
                        voucherIncreaseQty = Integer.valueOf(cartList.get(position).getmProductVoucherIncreaseQty());
                    }

                    /*if (Integer.valueOf(holder.txtQuantity.getText().toString()) == 1) {
                        count = 0;
                    } else {
                        count = Integer.parseInt(holder.txtQuantity.getText().toString());
                    }*/
                    //count = count + voucherIncreaseQty;
                    count = Integer.parseInt(holder.txtQuantity.getText().toString());
                    count++;
                    if (iCartItemClick != null) {

                        iCartItemClick.updateCartItem(v, position, count);
                    }
                } else if (!(cartList.get(position).getmOrderItemVoucherBalanceQty().equalsIgnoreCase("null") || cartList.get(position).getmOrderItemVoucherBalanceQty().equalsIgnoreCase("0.00")
                        || cartList.get(position).getmOrderItemVoucherBalanceQty().equalsIgnoreCase("0") || cartList.get(position).getmOrderItemVoucherBalanceQty().equalsIgnoreCase(""))
                        && cartList.get(position).getmProductType().equalsIgnoreCase("Simple")) {
                    int count = Integer.parseInt(holder.txtQuantity.getText().toString());
                    if (count < Integer.valueOf(cartList.get(position).getmOrderItemVoucherBalanceQty())) {
                        count++;
                    } else {
                        Toast.makeText(mContext, "You have reached the maximum selection", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (iCartItemClick != null) {
                        iCartItemClick.updateCartItem(v, position, count);
                    }
                } else {

                    int count = Integer.parseInt(holder.txtQuantity.getText().toString());
                    count++;
                     if (iCartItemClick != null) {
                        iCartItemClick.updateCartItem(v, position, count);
                    }

                    /*if(cartList.get(position).getmProductType().equalsIgnoreCase("Simple")){
                        int count = Integer.parseInt(holder.txtQuantity.getText().toString());
                        count++;

                        if (iCartItemClick != null) {
                            iCartItemClick.updateCartItem(v, position, count);
                        }
                    }else{//for modifier products
                        if(iCartItemClick != null){
                            iCartItemClick.updateOverallCartItems(v, position, cartList.get(position));
                        }
                    }*/
                }
//                updateCart(position, mQuantity);
            }
        });

        holder.imgDecreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cartList.get(position).getmVoucherOrderItemId().equalsIgnoreCase("0") && cartList.get(position).getmProductType().equalsIgnoreCase("Simple")) {
                    int count;
                    int productMinQty;
                    int voucherIncreaseQty;


                    if (cartList.get(position).getmProductMinQty().equalsIgnoreCase("")
                            || cartList.get(position).getmProductMinQty().equalsIgnoreCase("0") || cartList.get(position).getmProductMinQty().equalsIgnoreCase("null")) {
                        productMinQty = 1;
                    } else {
                        productMinQty = Integer.valueOf(cartList.get(position).getmProductMinQty());
                    }

                    if (cartList.get(position).getmProductVoucherIncreaseQty().equalsIgnoreCase("")
                            || cartList.get(position).getmProductVoucherIncreaseQty().equalsIgnoreCase("0") || cartList.get(position).getmProductVoucherIncreaseQty().equalsIgnoreCase("null")) {
                        voucherIncreaseQty = 1;
                    } else {
                        voucherIncreaseQty = Integer.valueOf(cartList.get(position).getmProductVoucherIncreaseQty());
                    }

                   /* if (Integer.valueOf(holder.txtQuantity.getText().toString()) == 1) {
                        count = productMinQty;
                    } else {
                        count = Integer.parseInt(holder.txtQuantity.getText().toString());
                    }*/
                    count = Integer.parseInt(holder.txtQuantity.getText().toString());
                    if (count > productMinQty) {

                        //count = count - voucherIncreaseQty;
                        count--;
                        if (iCartItemClick != null) {
                            iCartItemClick.updateCartItem(v, position, count);
                        }
                    }else{
                        Toast.makeText(mContext, "You have reached the minimum selection", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    int count = Integer.parseInt(holder.txtQuantity.getText().toString());
                    if (count > 1) {

                        count--;
                         if (iCartItemClick != null) {
                            iCartItemClick.updateCartItem(v, position, count);
                        }

                        /*if(cartList.get(position).getmProductType().equalsIgnoreCase("Simple")){
                            count--;

                            if (iCartItemClick != null) {
                                iCartItemClick.updateCartItem(v, position, count);
                            }
                        }else{
                            if(iCartItemClick != null){
                                iCartItemClick.updateOverallCartItems(v, position, cartList.get(position));
                            }
                        }*/
                    }
                }
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iCartItemClick != null){
                    iCartItemClick.updateOverallCartItems(v, position, cartList.get(position));
                }
            }
        });


        holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cartList.get(position).getmProductType().equalsIgnoreCase("Simple")){
                   return;
                }else{
                    holder.edit.performClick();
                }

                /*if (cartList.get(position).getpType().equalsIgnoreCase("1")) {
                    iCartItemClick.makeapicall(cartList.get(position).getmProductId(), cartList.get(position), "1",position);
                    return;

                }

                if (cartList.get(position).getpType().equalsIgnoreCase("")) {
                    iCartItemClick.makeapicall(cartList.get(position).getmProductId(), cartList.get(position), "1",position);
                    return;

                }

                if (iCartItemClick != null) {
                    iCartItemClick.updateOverallCartItems(v, position, cartList.get(position));
                }*/
            }
        });
        holder.productNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cartList.get(position).getmProductType().equalsIgnoreCase("Simple")){
                    return;
                }else{
                    holder.edit.performClick();
                }

            }
        });


        /*holder.txtProductName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartList.get(position).getpType().equalsIgnoreCase("1")) {
                    iCartItemClick.makeapicall(cartList.get(position).getmProductId(), cartList.get(position), "1",position);
                    return;

                }

                if (cartList.get(position).getpType().equalsIgnoreCase("")) {
                    iCartItemClick.makeapicall(cartList.get(position).getmProductId(), cartList.get(position), "1",position);
                    return;

                }

                if (iCartItemClick != null) {
                    iCartItemClick.updateOverallCartItems(v, position, cartList.get(position));
                }


            }
        });
        holder.txtProductType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartList.get(position).getpType().equalsIgnoreCase("1")) {
                    iCartItemClick.makeapicall(cartList.get(position).getmProductId(), cartList.get(position), "1",position);
                    return;

                }

                if (cartList.get(position).getpType().equalsIgnoreCase("")) {
                    iCartItemClick.makeapicall(cartList.get(position).getmProductId(), cartList.get(position), "1",position);
                    return;

                }

                if (iCartItemClick != null) {
                    iCartItemClick.updateOverallCartItems(v, position, cartList.get(position));
                }


            }
        });*/


        if (cartList.get(position).getCartModifierList() != null && cartList.get(position).getCartModifierList().size() > 0) {
            String modifierName = "";

            List<CartModifier> cartModifierList = cartList.get(position).getCartModifierList();
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


        if (cartList.get(position).getSetMenuTitleList() != null && cartList.get(position).getSetMenuTitleList().size() > 0) {

            String name = "";

            List<SetMenuTitle> setMenuTitleList = cartList.get(position).getSetMenuTitleList();

            for (int t = 0; t < setMenuTitleList.size(); t++) {
                SetMenuTitle setMenuTitle = setMenuTitleList.get(t);

                name += setMenuTitle.getmTitleMenuName() + " : ";

                List<SetMenuModifier> setMenuModifierList = setMenuTitle.getSetMenuModifierList();

                if (setMenuModifierList != null && setMenuModifierList.size() > 0) {

                    for (int sm = 0; sm < setMenuTitle.getSetMenuModifierList().size(); sm++) {
                        SetMenuModifier setMenuModifier = setMenuTitle.getSetMenuModifierList().get(sm);

                        if (setMenuModifier.getmQuantity() > 0) {
                            name += "" + setMenuModifier.getmQuantity() + " x ";

                            iEnableproc = true;

                        } else {
                            name += "";

                        }

                        name += setMenuModifier.getmModifierName() + "\n";

                        List<ModifierHeading> modifierHeadingList = setMenuModifier.getModifierHeadingList();

                        if (modifierHeadingList != null && modifierHeadingList.size() > 0) {
                            for (int h = 0; h < modifierHeadingList.size(); h++) {
                                ModifierHeading modifierHeading = modifierHeadingList.get(h);


                                name += modifierHeading.getmModifierHeading() + " : ";

                                List<ModifiersValue> modifiersValueList = modifierHeading.getModifiersList();

                                if (modifiersValueList.size() > 0) {

                                    for (int v = 0; v < modifiersValueList.size(); v++) {
                                        ModifiersValue modifiersValue = modifiersValueList.get(v);
                                        if (modifiersValue.getmSubModifierTotal() != 0) {
                                            name += modifiersValue.getmSubModifierTotal() + " x " + modifiersValue.getmModifierName() + ", ";
                                        }
                                    }
                                    name += "\n";
                                }
                            }
                        }
                    }
                }
            }

            if (!iEnableproc) {
                holder.txtProductType.setText("");

            } else {
                holder.txtProductType.setText(name);

            }
        }

    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }

    class OrderDetailHolder extends RecyclerView.ViewHolder {
        private ImageView imgProduct, imgDelete, imgIncreement, imgDecreement;
        private TextView txtProductName, txtAmount, txtProductType, txtComments, txt_discountApplied, txt_voucherApplied, txt_FreeApplied, membership_discount, txtQuantity;
        private LinearLayout layoutModifier;
        private RecyclerView modifierRecyclerView;
        private LinearLayout productNameLayout, amountlayout, parentLayout, lly_amount, lly_plusminus;
        private TextView edit;

        public OrderDetailHolder(View itemView) {
            super(itemView);

            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            txtProductName = (TextView) itemView.findViewById(R.id.txtProductName);
            txtProductType = (TextView) itemView.findViewById(R.id.txtProductType);
            txtAmount = (TextView) itemView.findViewById(R.id.txtAmount);
            txtComments = (TextView) itemView.findViewById(R.id.txtComments);
            txt_discountApplied = itemView.findViewById(R.id.txt_discountApplied);
            productNameLayout = itemView.findViewById(R.id.productNameLayout);
            amountlayout = itemView.findViewById(R.id.amountlayout);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            txt_voucherApplied = itemView.findViewById(R.id.txt_voucherApplied);
            txt_FreeApplied = itemView.findViewById(R.id.txt_FreeApplied);
            lly_amount = itemView.findViewById(R.id.lly_amount);
            membership_discount = itemView.findViewById(R.id.membership_discount);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            imgIncreement = (ImageView) itemView.findViewById(R.id.imgIncreement);
            imgDecreement = (ImageView) itemView.findViewById(R.id.imgDecreement);
            lly_plusminus = itemView.findViewById(R.id.lly_plusminus);
            edit = itemView.findViewById(R.id.edit);
        }
    }

    public void setOnDeleteClickListener(ICartItemClick cartItemClick) {
        iCartItemClick = cartItemClick;
    }


}
