package com.app.sushi.tei.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.CompassAllProduct.ProductsItem;
import com.app.sushi.tei.Model.CompassAllProduct.SetMenuComponentItem;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.activity.SubCategoryActivity;
import com.app.sushi.tei.adapter.SetMenuAdapter.SetMenuTitleRecyclerAdapterNew;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.app.sushi.tei.adapter.SetMenuAdapter.SetMenuChildRecyclerAdapter.modifierPrice;

public class CartDialog {

//





    private Context mContext;
    yesORno onSelected;

    private String mCustomerId;
    private String mReferenceId;
    private ArrayList<ProductsItem> productsList;
    private int position;
    public static TextView txtQuantitys;
    private Dialog dialog;
    private SetMenuTitleRecyclerAdapterNew setMenuTitleRecyclerAdapternew;
    private ArrayList<SetMenuComponentItem> setMenuProductList;
    public static TextView productPriceText;
    public static Double productCast;

    public CartDialog(Context context, yesORno onSelected) {

        this.onSelected=onSelected;

    }

    public CartDialog(Context context) {
        this.mContext = context;
        showDialog();
    }

    public CartDialog(Context mContext, ArrayList<ProductsItem> subCategoryArrayList, int adapterPosition) {
        this.mContext = mContext;
        this.productsList=subCategoryArrayList;
        this.position=adapterPosition;

        showDialog();
    }

    public interface yesORno {
        void selectedAction(String action);

    }

    public void showDialog() {

        dialog = new Dialog(mContext, R.style.AppTheme);
        dialog.setContentView(R.layout.dialog_add_product);
        dialog.setCanceledOnTouchOutside(true);
        if (!dialog.isShowing())
            dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();

        if (window != null) {
            lp.copyFrom(window.getAttributes());

            lp.gravity= Gravity.CENTER;
            lp.width = android.view.ViewGroup.LayoutParams.FILL_PARENT;
            lp.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }

        Toolbar toolbar = dialog.findViewById(R.id.toolBar);
        LinearLayout imgBack = toolbar.findViewById(R.id.toolbarBack);
        ImageView imgDecreement = (ImageView) dialog.findViewById(R.id.imageDegreement);
        ImageView imgIncreement = (ImageView) dialog.findViewById(R.id.imageIngreement);
        ImageView cancel_Image=dialog.findViewById(R.id.cancel_Image);
        TextView AddtoCartBtn=dialog.findViewById(R.id.AddtoCart);
        TextView productName=dialog.findViewById(R.id.productName);
        TextView productDescription=dialog.findViewById(R.id.productDescription);
        productPriceText=dialog.findViewById(R.id.productPriceText);
        txtQuantitys=dialog.findViewById(R.id.totalcount);
        RecyclerView setmenuRecyclerView = (RecyclerView) dialog.findViewById(R.id.modifierRecyclerView);
        RecyclerView.LayoutManager modifierlayoutManager = new LinearLayoutManager(mContext);
        setmenuRecyclerView.setLayoutManager(modifierlayoutManager);
        setmenuRecyclerView.setNestedScrollingEnabled(false);


        setMenuProductList= (ArrayList<SetMenuComponentItem>) productsList.get(position).getSetMenuComponent();



        setMenuTitleRecyclerAdapternew = new SetMenuTitleRecyclerAdapterNew(mContext, setMenuProductList, new SetMenuTitleRecyclerAdapterNew.PasstheValue() {
            @Override
            public void addtoSubtotla(String productPrice) {
                          /*  double valu=Double.valueOf(productPrice)*Integer.valueOf(mProductQuantity);

                            Double finalVal = Double.valueOf(txtModi.getText().toString().replace("$", "")) + valu;

                            SpannableString cs = new SpannableString("$" + String.format("%.2f", finalVal));

                            cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                            txtModi.setText(cs);*/
            }

            @Override
            public void subtoSubtotla(String productPrice)

            {
                            /*double valu=Double.valueOf(productPrice)*Integer.valueOf(mProductQuantity);
                            Double finalVal = Double.valueOf(txtModi.getText().toString().replace("$", "")) - valu;

                            SpannableString cs = new SpannableString("$" + String.format("%.2f", finalVal));

                            cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                            txtModi.setText(cs);*/

            }
        });

        setmenuRecyclerView.setAdapter(setMenuTitleRecyclerAdapternew);
        setmenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
        setmenuRecyclerView.setNestedScrollingEnabled(false);
        setmenuRecyclerView.setHasFixedSize(true);


        productName.setText(productsList.get(position).getProductName());
        productDescription.setText(productsList.get(position).getProductShortDescription());
        productPriceText.setText(productsList.get(position).getProductPrice());
        productCast= Double.parseDouble(productsList.get(position).getProductPrice());
        cancel_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        });

        imgIncreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(txtQuantitys.getText().toString());
                Double productValue = Double.parseDouble(productPriceText.getText().toString());
                count++;

            /*    mModifierQuantity = count;

                quantityCost += mModifierPrice;

             */
            if (modifierPrice==null){

                modifierPrice=1.0;

            }
                Double totalVavlue=productCast*count*modifierPrice;
                txtQuantitys.setText(String.valueOf(count));
                productPriceText.setText(String.valueOf(totalVavlue));
                SubCategoryActivity.totalAmt.setText(String.valueOf(totalVavlue));

/*
                SpannableString cs = new SpannableString("$" + String.format("%.2f", new BigDecimal(quantityCost)));

                cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                txtModifierPrice.setText(cs);

                mProductQuantity = txtQuantity.getText().toString();

                */

            }
        });



        imgDecreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = Integer.parseInt(txtQuantitys.getText().toString());
                Double productValue = Double.parseDouble(productPriceText.getText().toString());
                if (count > 1)
                {
                    count--;

              /*      mModifierQuantity = count;
                    quantityCost -= mModifierPrice;*/

                    txtQuantitys.setText(count + "");

              /*

                    SpannableString cs = new SpannableString("$" + String.format("%.2f", new BigDecimal(quantityCost)));

                    cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                    txtModifierPrice.setText(cs);

                    mProductQuantity = txtQuantity.getText().toString();

                    */
                }
                if (modifierPrice==null){

                    modifierPrice=1.0;

                }
                Double totalVavlue=productCast*count*modifierPrice;
                productPriceText.setText(String.valueOf(totalVavlue));
                SubCategoryActivity.totalAmt.setText(String.valueOf(totalVavlue));
            }
        });

        AddtoCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (dialog.isShowing())
                    dialog.dismiss();*/


              /*  GradientDrawable bgShape = (GradientDrawable) holder.layoutQuantityParent.getBackground();
                bgShape.setColor(mContext.getResources().getColor(R.color.colorBottomShape));*/

                if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                    mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                    mReferenceId = "";

                } else {
                    try {
                        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                        GlobalValues.DEVICE_ID = telephonyManager.getDeviceId();
                        mReferenceId = GlobalValues.DEVICE_ID;

                    } catch (SecurityException e) {
                        GlobalValues.DEVICE_ID = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
                        mReferenceId = GlobalValues.DEVICE_ID;

                    } finally {
                        {
                            mCustomerId = "";
                        }
                    }
                }


                Double totalprice = Double.valueOf(txtQuantitys.getText().toString()) * Double.valueOf(productsList.get(position).getProductPrice());
                if (Utility.networkCheck(mContext)) {
                    String url = GlobalUrl.ADD_CART_SET_MENU_URL;
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("product_id", productsList.get(position).getProductId());
                    // params.put("product_type", productsList.get(position).getProductType());
                    params.put("app_id", GlobalValues.APP_ID);
                    params.put("reference_id", Utility.getReferenceId(mContext));
                    params.put("customer_id", mCustomerId);
//                    params.put("availability_name", GlobalValues.TAKEAWAY);
                    params.put("availability_id", Utility.readFromSharedPreference(mContext,GlobalValues.AVALABILITY_ID));
                    params.put("product_qty", txtQuantitys.getText().toString());
                    params.put("product_name", productsList.get(position).getProductName());
                    params.put("product_total_price", String.valueOf(totalprice));
                    params.put("product_unit_price", productsList.get(position).getProductPrice());
                    params.put("price_with_modifier", "0.00");
                    params.put("product_sku", productsList.get(position).getProductSku());
                    params.put("product_image", "");
                    params.put("allow_cart", "yes");
                    params.put("cart_source", "Mobile");




                    new AddCartTask(params, txtQuantitys.getText().toString()).execute(url);

                } else {
                    Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private class AddCartTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private Map<String, String> cartparams;
        private String mQuantity;

        public AddCartTask(Map<String, String> cartparams, String quantity) {
            this.cartparams = cartparams;

            this.mQuantity = quantity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            System.out.print("Add to cart modifier");

            String response = WebserviceAssessor.postRequest(mContext, params[0], cartparams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);

            try {

                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {



                /*    if (txtCurrentCartQuantity.getText().length() > 0)
                    {

                        int value = 0;
                        try {
                            value = Integer.valueOf(txtCurrentCartQuantity.getText().toString().replace("X", "")) + Integer.valueOf(mQuantity);

                            ;
                       databaseHandler.updateQty(productsList.get(position).getProductId(), String.valueOf(value));
                            ((ProductListActivity) mContext).onrefreshdtata();
                        } catch (NumberFormatException e)
                        {
                            e.printStackTrace();
                        }


                    } else {
                      databaseHandler.insertProductData(productsList.get(position).getProductId(),""+Integer.valueOf(mQuantity));
                        ((ProductListActivity) mContext).onrefreshdtata();

                    }*/


                    JSONObject jsonContent = jsonObject.getJSONObject("contents");

                    JSONObject jsonCartDetails = jsonContent.getJSONObject("cart_details");


                    Utility.writeToSharedPreference(mContext, GlobalValues.CART_COUNT,
                            jsonCartDetails.getString("cart_total_items"));

                    Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, jsonObject.getJSONObject("contents").toString());

                    Utility.writeToSharedPreference(mContext, GlobalValues.PRODUCT_LEAD_TIME, jsonContent.getString("product_lead_time"));
                    Utility.writeToSharedPreference(mContext, GlobalValues.TOTAL_CART_PRICE,jsonCartDetails.getString("cart_grand_total"));



                  /*  ProductListActivity.isInvalidated = true;
                    ((ProductListActivity) mContext).invalidateOptionsMenu();*/

                    if (dialog != null) {
                        dialog.dismiss();
                    }

                    ((SubCategoryActivity) mContext).updatecartvalues();
                   // new SubCategoryActivity().updatecartvalues(jsonCartDetails.getString("cart_total_items"));

                } else {

                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();

            } finally {


                progressDialog.dismiss();
            }

        }
    }
}