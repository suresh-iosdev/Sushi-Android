package com.app.sushi.tei.adapter.Order;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.sushi.tei.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by vishnu on 8/3/18.
 */

public class ViewrewardsAdaptersnew extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Activity activity;
    JSONArray productsJSONArray;


    public ViewrewardsAdaptersnew(Activity activity
            , JSONArray productsJSONArray) {

        this.activity = activity;
        this.productsJSONArray = productsJSONArray;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.base_rewards_list_new, parent, false);

        VHItem dataObjectHolder = new VHItem(view);


        return dataObjectHolder;


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof VHItem) {

            VHItem vhItemHolder = (VHItem) holder;

            try {


                JSONObject jsonObject = productsJSONArray.getJSONObject(position);

                String quantity = jsonObject.getString("item_qty");

                String item_image = jsonObject.getString("item_image");


                try {
                    if (jsonObject.getString("item_image").toString() != null &&
                            !jsonObject.getString("item_image").toString().equals("")) {
//TODO with Dynamic Image

                        Picasso.with(activity).load(jsonObject.getString("item_image").toString())
                                .error(R.drawable.default_image)
                                .into(vhItemHolder.img_products);
                        vhItemHolder.img_products.setVisibility(View.VISIBLE);
                    } else {
                        vhItemHolder.img_products.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
//TODO
                    vhItemHolder.img_products.setVisibility(View.GONE);

                }

                if (jsonObject.getString("item_voucher_name") != null &&
                        !jsonObject.getString("item_voucher_name").toString().equals("")) {
                    vhItemHolder.txt_discountApplied.setVisibility(View.VISIBLE);
                } else {
                    vhItemHolder.txt_discountApplied.setVisibility(View.GONE);
                }

                if (!(jsonObject.getString("item_voucher_order_id").equalsIgnoreCase("null")) &&
                        !jsonObject.getString("item_voucher_order_id").equals("")) {
                    if (jsonObject.getString("item_voucher_free_product").equalsIgnoreCase("1")) {
                        vhItemHolder.txt_voucherApplied.setVisibility(View.GONE);
                        vhItemHolder.priceLayout.setVisibility(View.VISIBLE);
                        vhItemHolder.txt_FreeApplied.setVisibility(View.VISIBLE);
                    } else {
                        vhItemHolder.priceLayout.setVisibility(View.INVISIBLE);
                        vhItemHolder.txt_voucherApplied.setVisibility(View.VISIBLE);
                        vhItemHolder.txt_FreeApplied.setVisibility(View.GONE);
                    }
                } else {
                    vhItemHolder.priceLayout.setVisibility(View.VISIBLE);
                    vhItemHolder.txt_voucherApplied.setVisibility(View.GONE);
                    vhItemHolder.txt_FreeApplied.setVisibility(View.GONE);
                }

/*
                Picasso.with(activity).load(item_image)
                        .error(R.drawable.product_detail_no_image_org)
                        .into(vhItemHolder.img_products);
*/


                String productName = jsonObject.getString("item_name");
                String price = jsonObject.getString("item_total_amount");

                String specialInstruction = "";
                if (!(jsonObject.getString("item_specification").equalsIgnoreCase(""))) {
                    vhItemHolder.specialInstructionTextView.setVisibility(View.VISIBLE);

                    specialInstruction = jsonObject.getString("item_specification");
                    vhItemHolder.specialInstructionTextView.setText("Notes: " + specialInstruction);
                } else {
                    vhItemHolder.specialInstructionTextView.setVisibility(View.GONE);
                }


                vhItemHolder.itemTextView.setText(jsonObject.getString("item_qty") + " x " + jsonObject.getString("item_name"));


//           item_special_amount":null     vhItemHolder.priceTextView.setText("$" + String.format("%.2f", Double.parseDouble(price)));
                vhItemHolder.priceTextView.setText(String.format("%.2f", Double.parseDouble(price)) + "");


                String cart_item_special_amount = jsonObject.getString("item_special_amount").toString();


                if (cart_item_special_amount.equals("0.00")) {
                    vhItemHolder.kakiDiscountTypeTextView.setVisibility(View.GONE);


                } else if (!cart_item_special_amount.equals("null") && cart_item_special_amount.length() > 0) {
                    vhItemHolder.kakiDiscountTypeTextView.setVisibility(View.VISIBLE);

                    vhItemHolder.kakiDiscountTypeTextView.setText("* " + cart_item_special_amount + " 20% Kakis Discount Applied");



                } else {
                    vhItemHolder.kakiDiscountTypeTextView.setVisibility(View.GONE);

                }


                //Modifiers
                JSONArray modifiersArray = jsonObject.getJSONArray("modifiers");
                if (modifiersArray != null && modifiersArray.length() > 0) {

                    StringBuilder modifierSB = new StringBuilder();


                    for (int i = 0; i < modifiersArray.length(); i++) {
                        JSONObject jsonObject1 = modifiersArray.getJSONObject(i);

                        String modifierTypeName = jsonObject1.getString("order_modifier_name").replace("\\", "");

                        modifierSB.append(modifierTypeName);

                        JSONArray modifierValJSONArray = jsonObject1.getJSONArray("modifiers_values");

                        boolean hasAtleastOneModifierVal = false;

                        for (int j = 0; j < modifierValJSONArray.length(); j++) {

                            JSONObject modValJSONObj = modifierValJSONArray.getJSONObject(j);

                            String modValueName = modValJSONObj.getString("order_modifier_name").replace("\\", "");

                            if (j == 0) {
                                hasAtleastOneModifierVal = true;
                                modifierSB.append(" (");
                                modifierSB.append(modValueName + "");
                            } else {
                                modifierSB.append(", " + modValueName);
                            }


                        }

                        if (hasAtleastOneModifierVal) {
                            modifierSB.append(")");
                        }


                        //   if((i+1)==modifiersArray.length()){
                        modifierSB.append("\n");
                        //     }


                    }


                    vhItemHolder.modifierTextView.setText(modifierSB.toString());

                }


                //Menu Component
                JSONArray setMenuCompsArray = jsonObject.getJSONArray("set_menu_component");
                if (setMenuCompsArray.length() > 0) {



                    StringBuilder setMenuSB = new StringBuilder();

                    for (int i = 0; i < setMenuCompsArray.length(); i++) {


                        JSONObject singleMenuComponent = setMenuCompsArray.getJSONObject(i);

                        String menuName = singleMenuComponent.getString("menu_component_name");

                        setMenuSB.append(menuName + ": ");


                        JSONArray prodJSONArray = singleMenuComponent.getJSONArray("product_details");
                        //products list
                        for (int j = 0; j < prodJSONArray.length(); j++) {

                            JSONObject productJSONObject = prodJSONArray.getJSONObject(j);

                            String producName = productJSONObject.getString("menu_product_name");
                            String product_qty = productJSONObject.getString("menu_product_qty");
                            String product_price = productJSONObject.getString("menu_product_price");


                            //   setMenuSB.append(producName + "\n");

//                             String producName = productJSONObject.getString("cart_menu_component_product_name");


                            //setMenuSB.append(product_qty + " x " + producName + " (" + product_price + ")\n");


                            if (product_qty == null || product_qty.equals("0") || product_qty.equals("null")) {
                                //do nothing
                            } else {

                                /*if(productJSONObject.has("menu_menu_component_min_max_appy")
                                        && productJSONObject.getString("menu_menu_component_min_max_appy").equals("1")) {*/
                                //quantity based set menu component

                                if (j == 0) {
                                    String producNameVal = productJSONObject.getString("menu_product_name");
                                    setMenuSB.append(product_qty + " x " + producNameVal + "\n"); //+ " (" + product_price + ")\n");
                                } else {
                                    String producNameVal = productJSONObject.getString("menu_product_name");
                                    setMenuSB.append(product_qty + " x " + producNameVal + "\n"); //" (" + product_price + ")\n");
                                }
/*
                                }else{
                                    //normal set menu component

                                    if (j == 0) {
                                        String producNameVal = productJSONObject.getString("menu_product_name");
                                        setMenuSB.append( producNameVal + "\n");
                                    } else {
                                        String producNameVal = productJSONObject.getString("menu_product_name");
                                        setMenuSB.append( producNameVal + "\n");
                                    }

                                }*/
                            }

                            //Modifiers
                            JSONArray modifierJSONArray = productJSONObject.getJSONArray("modifiers");
                            if (modifierJSONArray.length() > 0) {


                                for (int k = 0; k < modifierJSONArray.length(); k++) {
                                    JSONObject jsonObject1 = modifierJSONArray.getJSONObject(k);

                                    String modifierTypeName = jsonObject1.getString("order_modifier_name");

                                    setMenuSB.append(modifierTypeName + ": ");

                                    JSONArray modifierValJSONArray = jsonObject1.getJSONArray("modifiers_values");

                                    boolean hasAtleastOneModifierVal = false;

                                    for (int l = 0; l < modifierValJSONArray.length(); l++) {

                                        JSONObject modValJSONObj = modifierValJSONArray.getJSONObject(l);

                                        String modValueName = modValJSONObj.getString("order_modifier_name");
                                        String modValueQty = modValJSONObj.getString("order_modifier_qty");

                                        /*if (l == 0) {
                                            hasAtleastOneModifierVal = true;
                                            setMenuSB.append(" (");
                                            setMenuSB.append(modValueQty+ " x "+modValueName);
                                        } else {

                                            setMenuSB.append(", " + modValueName);

                                        }*/

                                        if (!(modValueQty.equalsIgnoreCase("0"))) {
                                            setMenuSB.append(modValueQty + " x " + modValueName + ", ");
                                        }
                                    }
                                    setMenuSB.append("\n");
                                    /*if (hasAtleastOneModifierVal) {
                                        setMenuSB.append(")");
                                    }*/


                                    //        if ((k + 1) == modifiersArray.length()) {

                                    //        }


                                }
                            }


                        }


                    }


                    vhItemHolder.modifierTextView.setText(setMenuSB.toString());

                }


               /* //Bagels
                JSONArray bagelsJSONArray = jsonObject.getJSONArray("bagel_modifiers");
                if (bagelsJSONArray.length() > 0) {

                    StringBuilder bagelModifierSB = new StringBuilder();

                    for (int i = 0; i < bagelsJSONArray.length(); i++) {

                        JSONObject bagelsJSONObject = bagelsJSONArray.getJSONObject(i);

                        if (i == 0) {
                            bagelModifierSB.append(bagelsJSONObject.getString("modifier_value_name") + " (" + bagelsJSONObject.getString("modifier_value_qty") + ")");
                        } else {
                            bagelModifierSB.append("\n" + bagelsJSONObject.getString("modifier_value_name") + " (" + bagelsJSONObject.getString("modifier_value_qty") + ")");
                        }

                    }
                    vhItemHolder.modifierTextView.setText(bagelModifierSB.toString());

                }
*/

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    @Override
    public int getItemCount() {

        return productsJSONArray.length();


    }

    public class VHItem extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView qtyTextView, itemTextView, modifierTextView, priceTextView,
                kakiDiscountTypeTextView, specialInstructionTextView, txt_discountApplied, txt_voucherApplied, txt_FreeApplied;


        ImageView babyPackImageView, img_products;
        private LinearLayout priceLayout;

        public VHItem(View itemView) {
            super(itemView);

            itemTextView = (TextView) itemView.findViewById(R.id.productNameTextView);
            modifierTextView = (TextView) itemView.findViewById(R.id.modifiersOrSubMenuTextView);
            priceTextView = (TextView) itemView.findViewById(R.id.priceTextView);

            img_products = (ImageView) itemView.findViewById(R.id.cartImageView);

            kakiDiscountTypeTextView = (TextView) itemView.findViewById(R.id.kakiDiscountTypeTextView);
            specialInstructionTextView = itemView.findViewById(R.id.specialInstructionTextView);
            txt_discountApplied = itemView.findViewById(R.id.txt_discountApplied);
            txt_voucherApplied = itemView.findViewById(R.id.txt_voucherApplied);
            txt_FreeApplied = itemView.findViewById(R.id.txt_FreeApplied);
            priceLayout = itemView.findViewById(R.id.priceLayout);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {


        }

    }

}

