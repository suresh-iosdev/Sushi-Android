package com.app.sushi.tei.adapter.Products;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Interface.IModifierClick;
import com.app.sushi.tei.Model.ProductList.ModifierHeading;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.activity.HomeActivity;
import com.app.sushi.tei.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;

import static com.app.sushi.tei.adapter.Products.ProductListRecyclerAdapter.isCorrectCombination;
import static com.app.sushi.tei.adapter.Products.ProductListRecyclerAdapter.mAliasProductPrimaryId;


public class ModifierHeadingRecyclerAdapter extends RecyclerView.Adapter<ModifierHeadingRecyclerAdapter.ModifierHeadingHolder> {

    private Context mContext;
    private List<ModifierHeading> modifierHeadingList;
    private RecyclerView.LayoutManager layoutManager;
    private String mProductId = "";

    public ModifierHeadingRecyclerAdapter(Context mContext, List<ModifierHeading> modifierHeadingList, String productId) {
        this.mContext = mContext;
        this.modifierHeadingList = modifierHeadingList;
        mProductId = productId;
    }

    @Override
    public ModifierHeadingRecyclerAdapter.ModifierHeadingHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_modifier_heading_item, parent, false);
        ModifierHeadingHolder orderHolder = new ModifierHeadingHolder(view);
        return orderHolder;

    }

    @Override
    public void onBindViewHolder(ModifierHeadingRecyclerAdapter.ModifierHeadingHolder holder, int position) {


        if (modifierHeadingList.get(position).getModifiersList().size() > 0)
        {

            holder.txtHeading.setVisibility(View.VISIBLE);
            holder.modifierchildRecyclerview.setVisibility(View.VISIBLE);

            holder.txtHeading.setText(modifierHeadingList.get(position).getmModifierHeading());

            layoutManager = new LinearLayoutManager(mContext);
            holder.modifierchildRecyclerview.setLayoutManager(layoutManager);

            final ModifierChildRecyclerAdapter modifierChildRecyclerAdapter = new
                    ModifierChildRecyclerAdapter(mContext, modifierHeadingList, modifierHeadingList.get(position).getModifiersList(), position);


            holder.modifierchildRecyclerview.setAdapter(modifierChildRecyclerAdapter);
            holder.modifierchildRecyclerview.setItemAnimator(new DefaultItemAnimator());
            holder.modifierchildRecyclerview.setNestedScrollingEnabled(false);


            modifierChildRecyclerAdapter.setOnItemClick(new IModifierClick() {
                @Override
                public void onModifierClick(View v, int position, int parentPosition) {

                    int maxCount = modifierHeadingList.get(parentPosition).getmModifierMaxSelect();

                    int maxSelected = modifierHeadingList.get(parentPosition).getmMaxSelected();



                    for (int j = 0; j < modifierHeadingList.get(parentPosition).getModifiersList().size(); j++) {

                        if (j == position) {

//                            if (maxSelected < maxCount) {

                            if (modifierHeadingList.get(parentPosition).getModifiersList().get(j).getChekced()) {

                                modifierHeadingList.get(parentPosition).getModifiersList().get(j).setChekced(false);
                                maxSelected = maxSelected - 1;

                                modifierHeadingList.get(parentPosition).setmMaxSelected(maxSelected);


                            } else {

                                modifierHeadingList.get(parentPosition).getModifiersList().get(j).setChekced(true);
                                maxSelected = maxSelected + 1;

                                modifierHeadingList.get(parentPosition).setmMaxSelected(maxSelected);

                            }
                        } else {
                             if (modifierHeadingList.get(parentPosition).getModifiersList().get(j).getChekced()) {

                                modifierHeadingList.get(parentPosition).getModifiersList().get(j).setChekced(false);
                                maxSelected = maxSelected - 1;
                                 modifierHeadingList.get(parentPosition).setmMaxSelected(maxSelected);

                               /* }else{

                                    Toast.makeText(mContext,"You have selected maximum options",Toast.LENGTH_SHORT).show();
                                }*/
                            }
                        } /*else {
                            //modifierHeadingList.get(parentPosition).getModifiersList().get(j).setChekced(false);
                        }*/

                    }

                    notifyDataSetChanged();
                    checkAllModifiersSelected();

                }
            });


        } else {
            holder.txtHeading.setVisibility(View.GONE);
            holder.modifierchildRecyclerview.setVisibility(View.GONE);
        }

    }

    public void checkAllModifiersSelected() {

        boolean isChildSelected;
        int count = 0, pPosition = 0;
        StringBuilder stringBuilder = new StringBuilder();

        if (modifierHeadingList != null) {
            if (modifierHeadingList.size() > 0) {

             /*   for (int i = 0; i < modifierHeadingList.size(); i++) {

                    isChildSelected = false;

                    if (modifierHeadingList.get(i).getModifiersList() != null
                            && modifierHeadingList.get(i).getModifiersList().size() > 0) {

                        if (modifierHeadingList.get(i).getmMaxSelected() ==
                                modifierHeadingList.get(i).getmModifierMaxSelect()) {

                            for (int j = 0; j < modifierHeadingList.get(i).getModifiersList().size(); j++) {

                                if (modifierHeadingList.get(i).getModifiersList().get(j).getChekced()) {

                                    stringBuilder.append(modifierHeadingList.get(i).getModifiersList().get(j).getmModifierId() + ";");
                                }

                            }
                            count++;
                            isChildSelected = true;
//                            break;

                        } else {
                            isChildSelected = false;
                        }
                    }

                    if (!isChildSelected) {
                        break;
                    }

                }*/

                for (int i = 0; i < modifierHeadingList.size(); i++) {

                    isChildSelected = false;

                    if (modifierHeadingList.get(i).getModifiersList() != null
                            && modifierHeadingList.get(i).getModifiersList().size() > 0) {

                        for (int j = 0; j < modifierHeadingList.get(i).getModifiersList().size(); j++) {
                            if (modifierHeadingList.get(i).getModifiersList().get(j).getChekced()) {
                                count++;
                                stringBuilder.append(modifierHeadingList.get(i).getModifiersList().get(j).getmModifierId() + ";");
                                isChildSelected = true;
                                break;
                            } else {
                                isChildSelected = false;
                            }
                        }

                        if (!isChildSelected) {

//                            mValidationMessage = modifierHeadingList.get(i).getmModifierHeading();

                            break;
                        }
                    }
                }
            }


            if(stringBuilder!=null&& stringBuilder.toString().length()>0) {

                validateModifier(stringBuilder);
            }
/*
            if (stringBuilder.toString().length() > 0) {


                validateModifier(stringBuilder);


            } else {

                if (count == modifierHeadingList.size()) {

                    for (int i = 0; i < modifierHeadingList.size(); i++) {

                        if (modifierHeadingList.get(i).getModifiersList() != null
                                && modifierHeadingList.get(i).getModifiersList().size() > 0) {

                            for (int j = 0; j < modifierHeadingList.get(i).getModifiersList().size(); j++) {
                                if (modifierHeadingList.get(i).getModifiersList().get(j).getChekced()) {
                                    stringBuilder.append(modifierHeadingList.get(i).getModifiersList().get(j).getmModifierId() + ";");
                                }
                            }
                        }
                    }

                    validateModifier(stringBuilder);

                }
            }*/
        }
    }

    private void validateModifier(StringBuilder stringBuilder) {

        String modifierWithOutLastColan = "";
        String tempString = stringBuilder.toString();

        modifierWithOutLastColan = tempString.substring(0, tempString.length() - 1);


        String url = GlobalUrl.MODIFIER_VALIDATION_URL + "?app_id=" + GlobalValues.APP_ID +
                "&product_id=" + mProductId + "&modifier_value_id=" + modifierWithOutLastColan;

        new ModifierValidateTask().execute(url);
    }

    @Override
    public int getItemCount() {
        return modifierHeadingList.size();
    }


    class ModifierHeadingHolder extends RecyclerView.ViewHolder {

        private TextView txtHeading;
        private RecyclerView modifierchildRecyclerview;


        public ModifierHeadingHolder(View itemView) {
            super(itemView);

            txtHeading = (TextView) itemView.findViewById(R.id.txtModifierHeading);
            modifierchildRecyclerview = (RecyclerView) itemView.findViewById(R.id.mdifierChildRecyclerview);
        }
    }

    private class ModifierValidateTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {


            String response = WebserviceAssessor.getData(params[0]);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {


                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    JSONArray jsonResult = jsonObject.getJSONArray("result_set");

                    for (int i = 0; i < jsonResult.length(); i++) {

                        JSONObject json = jsonResult.getJSONObject(i);
                        mAliasProductPrimaryId = json.optString("alias_product_primary_id");

                        HomeActivity.mModifierPrice = Double.parseDouble(json.getString("product_price"));

                        HomeActivity.quantityCost=(double) HomeActivity.mModifierQuantity* HomeActivity.mModifierPrice;


                        final String price= String.format("%.2f",new BigDecimal(HomeActivity.quantityCost+""));

                        SpannableString cs = new SpannableString("$" + price);
                        cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                        HomeActivity.txtModifierPrice.setText(cs);
                        isCorrectCombination = true;
                    }

                } else {

                    isCorrectCombination = false;

                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            } finally {
                progressDialog.dismiss();
            }
        }
    }
}
