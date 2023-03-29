package com.app.sushi.tei.adapter.Products;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.ProductList.ModifierHeading;
import com.app.sushi.tei.Model.ProductList.ModifiersValue;
import com.app.sushi.tei.R;

import java.math.BigDecimal;
import java.util.List;

import static com.app.sushi.tei.Model.ProductList.ModifierHeading.subModifierPrice;
import static com.app.sushi.tei.activity.SubCategoryActivity.mSetMenuQuantity;
import static com.app.sushi.tei.activity.SubCategoryActivity.plusminusPriceValue;
import static com.app.sushi.tei.activity.SubCategoryActivity.txtModifierPrice;
import static com.app.sushi.tei.activity.SubCategoryActivity.mquanititycost_src;
import static com.app.sushi.tei.activity.SubCategoryActivity.quantityCost;
import static com.app.sushi.tei.activity.SubCategoryActivity.mSetmenuoverallprices;
import static com.app.sushi.tei.activity.SubCategoryActivity.value;

public class SetMenuModifierValuePlusMinusAdapter extends RecyclerView.Adapter<SetMenuModifierValuePlusMinusAdapter.ModifierValueHolder> {

    private Context mContext;
    private List<ModifiersValue> modifiersValueList;
    private double price = 0;
    private int pos;
    private List<ModifierHeading> modifierHeadingList;
    private String applyPrice;
    private String state;
    private boolean modifierQty = false;
    private boolean hasCheckedProduct = false;


    public SetMenuModifierValuePlusMinusAdapter(Context mContext, List<ModifiersValue> modifiersValueList, List<ModifierHeading> modifierHeadingList, int pos, String applyPrice, String state) {
        this.mContext = mContext;
        this.modifiersValueList = modifiersValueList;
        this.modifierHeadingList = modifierHeadingList;
        this.pos = pos;
        this.applyPrice = applyPrice;
        this.state = state;
        plusminusPriceValue = 0.0;

        Log.e("quantCostplus", quantityCost + "--");
    }

    @Override
    public ModifierValueHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_setmenu_apply_1_child_item, parent, false);

        ModifierValueHolder modifierValueHolder = new ModifierValueHolder(view);
         if (state.equalsIgnoreCase("create")) {
            modifierHeadingList.get(pos).settQuantity(0);
            for (int i = 0; i < modifiersValueList.size(); i++) {
                modifiersValueList.get(i).setmSubModifierTotal(0);
            }
        }else{
             for (int i = 0; i < modifiersValueList.size(); i++) {

            }

            //modifierHeadingList.get(pos).settQuantity(0);
            for (int i = 0; i < modifiersValueList.size(); i++) {
                if(modifiersValueList.get(i).isChekced()){
                     hasCheckedProduct = true;
                }else{
                    modifiersValueList.get(i).setmSubModifierTotal(0);
                }
            }

            if(hasCheckedProduct){

            }else{
                modifierHeadingList.get(pos).settQuantity(0);
            }

            Log.e("update01", String.valueOf(modifierHeadingList.get(pos).gettQuantity()));
        }

        return modifierValueHolder;
    }

    @Override
    public void onBindViewHolder(final ModifierValueHolder holder, final int position) {
        holder.txtModifierName.setText(modifiersValueList.get(position).getmModifierName());
        String name = modifiersValueList.get(position).getmModifierName().toUpperCase();
        double price = Double.parseDouble(modifiersValueList.get(position).getmModifierValuePrice());

        final String prices = String.valueOf(price);
        if(applyPrice.equalsIgnoreCase("1")){
            if (prices.equalsIgnoreCase("0.0")) {
                name = name;
            } else {
                name = name + "(+" + modifiersValueList.get(position).getmModifierValuePrice() + ")";
            }
        }else{

        }

        holder.txtModifierName.setText(name);
        holder.txtQuantity.setText(String.valueOf(modifiersValueList.get(position).getmSubModifierTotal()));

        holder.imgIncreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = 0;
                //plusminusPriceValue = 0.0;
                count = modifiersValueList.get(position).getmSubModifierTotal();
                int totalcount = modifierHeadingList.get(pos).gettQuantity();
                int minselect = modifierHeadingList.get(pos).getmModifierMinSelect();
                int maxselect = modifierHeadingList.get(pos).getmModifierMaxSelect();

                Log.e("plus", count + "--" + totalcount + "--" + minselect + "--" + maxselect);

                count++;
                totalcount++;

                if (totalcount <= maxselect) {
                    modifiersValueList.get(position).setmSubModifierTotal(count);


                    GlobalValues.subModifierSelectCount = totalcount;

                    modifierHeadingList.get(pos).settQuantity(totalcount);
                    modifiersValueList.get(position).setChekced(true);

                    if (applyPrice.equalsIgnoreCase("1")) {
                        double price;
                         try {
                            price = Double.parseDouble(modifiersValueList.get(position).getmModifierValuePrice());
                            //price = 0;
                        } catch (Exception e) {
                            e.printStackTrace();
                            price = 0;
                        }

                        /*if (modifiersValueList.get(position).getmModifierValuePrice().equalsIgnoreCase("0")
                                || modifiersValueList.get(position).getmModifierValuePrice().equalsIgnoreCase("0.0")
                                || modifiersValueList.get(position).getmModifierValuePrice().equalsIgnoreCase("0.00")
                                || modifiersValueList.get(position).getmModifierValuePrice().equalsIgnoreCase("")
                                || modifiersValueList.get(position).getmModifierValuePrice().equalsIgnoreCase("null")) {*/
                        mSetmenuoverallprices = price * count;
                        /*for (int i = 0; i < modifiersValueList.size(); i++) {
                            if (modifiersValueList.get(i).getmSubModifierTotal() != 0) {
                                if (i != position) {
                                    modifierQty = true;
                                }
                            }
                        }

                        if (modifierQty) {
                            if (!(modifiersValueList.get(position).getmModifierValuePrice().equalsIgnoreCase("0")
                                    || modifiersValueList.get(position).getmModifierValuePrice().equalsIgnoreCase("0.0")
                                    || modifiersValueList.get(position).getmModifierValuePrice().equalsIgnoreCase("0.00")
                                    || modifiersValueList.get(position).getmModifierValuePrice().equalsIgnoreCase("")
                                    || modifiersValueList.get(position).getmModifierValuePrice().equalsIgnoreCase("null"))) {
                                plusminusPriceValue = plusminusPriceValue + (price * count);
                            }
                        } else {
                            if (!(modifiersValueList.get(position).getmModifierValuePrice().equalsIgnoreCase("0")
                                    || modifiersValueList.get(position).getmModifierValuePrice().equalsIgnoreCase("0.0")
                                    || modifiersValueList.get(position).getmModifierValuePrice().equalsIgnoreCase("0.00")
                                    || modifiersValueList.get(position).getmModifierValuePrice().equalsIgnoreCase("")
                                    || modifiersValueList.get(position).getmModifierValuePrice().equalsIgnoreCase("null"))) {
                                plusminusPriceValue = price * count;
                            }
                        }*/


                        Double quantityCost_Menu = Double.parseDouble(txtModifierPrice.getText().toString()) + (price* mSetMenuQuantity);
                        subModifierPrice += price ;
                        mquanititycost_src = quantityCost_Menu;
                        value = 0.00;
                        for (int i = 0; i< modifiersValueList.size(); i++){
                            if(modifiersValueList.get(i).isChekced()){
                                value += (Double.parseDouble(modifiersValueList.get(i).getmModifierValuePrice()) * modifiersValueList.get(i).getmSubModifierTotal());
                            }
                        }

                        txtModifierPrice.setText(String.format("%.2f", new BigDecimal(quantityCost_Menu)));
                        //SubCategoryActivity.mSetMenuPrice = mquanititycost_src;

                    } else {
                        if (totalcount > minselect) {
                            double price = 0.0;

                            try {
                                price = 0;
                                //price = Double.parseDouble(setMenuModifierList.get(position).getmModifierPrice());
                            } catch (Exception e) {
                                e.printStackTrace();
                                price = 0;
                            }

                            int Total = totalcount - minselect;
                            mSetmenuoverallprices = price * Total;
                            //plusminusPriceValue = plusminusPriceValue + (price * count);
                            Double quantityCost_Menu = mSetmenuoverallprices + quantityCost;
                            mquanititycost_src = quantityCost_Menu;
                            txtModifierPrice.setText(String.format("%.2f", new BigDecimal(quantityCost_Menu)));
                        }
                    }
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext, "You have reached the maximum selection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.imgDecreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = 0;
                //plusminusPriceValue = 0.0;
                count = modifiersValueList.get(position).getmSubModifierTotal();

                int totalcount = modifierHeadingList.get(pos).gettQuantity();
                int minselect = modifierHeadingList.get(pos).getmModifierMinSelect();
                int maxselect = modifierHeadingList.get(pos).getmModifierMaxSelect();

                if (count > 0) {
                    count--;
                    totalcount--;
                    modifiersValueList.get(position).setmSubModifierTotal(count);
                    modifierHeadingList.get(pos).settQuantity(totalcount);
                    modifiersValueList.get(position).setChekced(true);
                    GlobalValues.subModifierSelectCount = totalcount;

                    if (applyPrice.equalsIgnoreCase("1")) {

                        double price;

                        try {
                            price = Double.parseDouble(modifiersValueList.get(position).getmModifierValuePrice()) ;
                            //price = 0;
                        } catch (Exception e) {
                            e.printStackTrace();
                            price = 0;
                        }

                         mSetmenuoverallprices = price * count;
                        //plusminusPriceValue = plusminusPriceValue - (price * count);
                         Double quantityCost_Menu;
                        if (count > -1) {
                            quantityCost_Menu = Double.parseDouble(txtModifierPrice.getText().toString()) - (price* mSetMenuQuantity);
                            //plusminusPriceValue = plusminusPriceValue - price;
                            subModifierPrice -= price;

                        } else {
                            quantityCost_Menu = Double.parseDouble(txtModifierPrice.getText().toString()) - 0;
                            //plusminusPriceValue = plusminusPriceValue - 0;
                            subModifierPrice -= 0;
                        }

                         mquanititycost_src = quantityCost_Menu;
                        String qtyCost = String.format("%.2f", new BigDecimal(quantityCost_Menu)).replace("-", "");
                        value = 0.00;
                        for (int i = 0; i< modifiersValueList.size(); i++){
                            if(modifiersValueList.get(i).isChekced()){
                                value += (Double.parseDouble(modifiersValueList.get(i).getmModifierValuePrice()) * modifiersValueList.get(i).getmSubModifierTotal());
                            }
                        }
                        txtModifierPrice.setText(qtyCost);
                        //SubCategoryActivity.mSetMenuPrice = mquanititycost_src;
                    } else {
                        double price = 0.0;
                        if (totalcount >= minselect) {
                            try {
                                // price = Double.parseDouble(modifiersValueList.get(position).getmModifierPrice());
                                price = 0;
                            } catch (Exception e) {
                                e.printStackTrace();
                                price = 0;
                            }
                            int Total = totalcount - minselect;
                            mSetmenuoverallprices = price * Total;
                            //plusminusPriceValue = plusminusPriceValue - (price * count);
                            Double quantityCost_Menu = mSetmenuoverallprices + quantityCost;
                            mquanititycost_src = quantityCost_Menu;
                            txtModifierPrice.setText(String.format("%.2f", new BigDecimal(quantityCost_Menu)));

                        }
                    }
                    notifyDataSetChanged();
                } else {
                    modifiersValueList.get(position).setChekced(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return modifiersValueList.size();
    }

    public class ModifierValueHolder extends RecyclerView.ViewHolder {

        private TextView txtModifierName, txtQuantity;
        private ImageView imgDecreement, imgIncreement;

        public ModifierValueHolder(View itemView) {
            super(itemView);

            txtModifierName = (TextView) itemView.findViewById(R.id.txtModifierName);
            txtQuantity = (TextView) itemView.findViewById(R.id.txtQuantity);
            imgDecreement = (ImageView) itemView.findViewById(R.id.imgDecreement);
            imgIncreement = (ImageView) itemView.findViewById(R.id.imgIncreement);
        }
    }
}
