package com.app.sushi.tei.adapter.Products;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sushi.tei.Model.ProductList.ModifiersValue;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.activity.HomeActivity;

import java.util.List;


public class SetMenuModifierValueRecyclerAdapter extends RecyclerView.Adapter<SetMenuModifierValueRecyclerAdapter.ModifierValueHolder> {

    private Context mContext;
    private List<ModifiersValue> modifiersValueList;
    private double price=0;


    public SetMenuModifierValueRecyclerAdapter(Context mContext, List<ModifiersValue> modifiersValueList) {
        this.mContext = mContext;
        this.modifiersValueList = modifiersValueList;
    }

    @Override
    public ModifierValueHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_setmenu_modifier_item, parent, false);

        ModifierValueHolder modifierValueHolder = new ModifierValueHolder(view);

        return modifierValueHolder;
    }

    @Override
    public void onBindViewHolder(final ModifierValueHolder holder, final int position) {

        String name=modifiersValueList.get(position).getmModifierName().toUpperCase();
        double price = Double.parseDouble(modifiersValueList.get(position).getmModifierValuePrice());


        String prices= String.valueOf(price);
        if(prices.equalsIgnoreCase("0.0")){
            name=name+"";
        }
        else{
            name=name+"(+"+modifiersValueList.get(position).getmModifierValuePrice()+")";
        }

        holder.txtModifierName.setText(name);

        try {
            price= Double.parseDouble(modifiersValueList.get(position).getmModifierValuePrice());

        }catch (NumberFormatException e) {
            price=0;
            e.printStackTrace();
        }

        if (modifiersValueList.get(position).getChekced()) {

            holder.imgChecked.setImageResource(R.drawable.asset53);

          /*  mSetMenuPrice = mSetMenuPrice + price;
            txtModifierPrice.setText(String.valueOf(mSetMenuPrice));*/

        } else {

            holder.imgChecked.setImageResource(R.drawable.asset54);

        }

        holder.layoutChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
      /*  if( GlobalValues.MAXIMUMSELECTED.equalsIgnoreCase("0")){*/

                double price = Double.parseDouble(modifiersValueList.get(position).getmModifierValuePrice());
                double p=0;

             for (int i = 0; i < modifiersValueList.size(); i++) {

                    if (i != position) {

                            if (modifiersValueList.get(i).getChekced()) {
                                try {
                                    p = Double.valueOf(modifiersValueList.get(i).getmModifierValuePrice());

                                } catch (Exception e) {
                                    p = 0;
                                    e.printStackTrace();
                                }
                                HomeActivity.mSetMenuPrice = HomeActivity.mSetMenuPrice - p;
                            }
                        modifiersValueList.get(i).setChekced(false);
                    }
                }

                if (modifiersValueList.get(position).getChekced()) {

                    modifiersValueList.get(position).setChekced(false);
                    holder.imgChecked.setImageResource(R.drawable.asset54);
                    HomeActivity.mSetMenuPrice = HomeActivity.mSetMenuPrice - price;
                    HomeActivity.quantityCost=(HomeActivity.mSetMenuPrice+ HomeActivity.doubletitle)* HomeActivity.mSetMenuQuantity;
                    HomeActivity.txtModifierPrice.setText(Utility.setPriceSpan(mContext, HomeActivity.quantityCost));
                } else {

                    modifiersValueList.get(position).setChekced(true);
                    holder.imgChecked.setImageResource(R.drawable.asset53);

                    HomeActivity.mSetMenuPrice = HomeActivity.mSetMenuPrice + price;

                    HomeActivity.quantityCost=(HomeActivity.mSetMenuPrice+ HomeActivity.doubletitle)* HomeActivity.mSetMenuQuantity;
                    HomeActivity.txtModifierPrice.setText(Utility.setPriceSpan(mContext, HomeActivity.quantityCost));

                }

                notifyDataSetChanged();
            }
        });
    }
    /*}else{

            double temporaryPrice=mSetMenuBasePrice;
            double price = Double.parseDouble(modifiersValueList.get(position).getmModifierValuePrice());




       if (modifiersValueList.get(position).getChekced()) {


           holder.imgChecked.setImageResource(R.drawable.asset54);
           modifiersValueList.get(position).setChekced(false);

           Double move=0.0;
           Double totalSubPrice=0.0;
           for (int i = 0; i < modifiersValueList.size(); i++) {


               if (modifiersValueList.get(i).getChekced()) {

                   totalSubPrice=totalSubPrice+Double.valueOf(modifiersValueList.get(i).getmModifierValuePrice());

               }else{
                   try {
                       move = Double.valueOf(modifiersValueList.get(i).getmModifierValuePrice());

                   } catch (Exception e) {

                       e.printStackTrace();
                   }

               }
           }

            price+=Double.parseDouble(modifiersValueList.get(position).getmModifierValuePrice());

           temporaryPrice=temporaryPrice+totalSubPrice;
           mSetMenuPrice = temporaryPrice;

            quantityCost=mSetMenuPrice*mSetMenuQuantity;
            txtModifierPrice.setText(String.valueOf(Utility.setPriceSpan(mContext, quantityCost)));



        } else {
           holder.imgChecked.setImageResource(R.drawable.asset53);
           modifiersValueList.get(position).setChekced(true);

           Double move=0.0;
           Double totalSubPrice=0.0;
           for (int i = 0; i < modifiersValueList.size(); i++) {


                   if (modifiersValueList.get(i).getChekced()) {

                       totalSubPrice=totalSubPrice+Double.valueOf(modifiersValueList.get(i).getmModifierValuePrice());

                   }else{
                       try {
                           move = Double.valueOf(modifiersValueList.get(i).getmModifierValuePrice());


                       } catch (Exception e) {

                           e.printStackTrace();
                       }

               }
           }


           temporaryPrice=temporaryPrice+totalSubPrice;
            mSetMenuPrice = temporaryPrice;

            quantityCost=mSetMenuPrice*mSetMenuQuantity;
            txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost));



        }



}*/


    @Override
    public int getItemCount() {
        return modifiersValueList.size();
    }

    public class ModifierValueHolder extends RecyclerView.ViewHolder {

        private TextView txtModifierName;
        private ImageView imgChecked;
        private RelativeLayout layoutChild;

        public ModifierValueHolder(View itemView) {
            super(itemView);

            txtModifierName = (TextView) itemView.findViewById(R.id.txtModifierName);

            imgChecked = (ImageView) itemView.findViewById(R.id.imgChecked);

            layoutChild = (RelativeLayout) itemView.findViewById(R.id.layoutChild);
        }
    }
}
