package com.app.sushi.tei.adapter.Vouchers;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.Cart.Cart;
import com.app.sushi.tei.Model.Voucher.VoucherListItem;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.activity.LoginActivity;
import com.app.sushi.tei.activity.OrderSummaryActivity;
import com.app.sushi.tei.dialog.MessageDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.app.sushi.tei.GlobalMembers.GlobalValues.CURRENT_AVAILABLITY_ID;
import static com.app.sushi.tei.activity.SubCategoryActivity.txtNotificationCount;
import static com.app.sushi.tei.activity.SubCategoryActivity.txtOrderCount;
import static com.app.sushi.tei.activity.SubCategoryActivity.txtPromotionCount;
import static com.app.sushi.tei.activity.SubCategoryActivity.txtVoucherCount;

public class VouchersAdapter extends RecyclerView.Adapter<VouchersAdapter.VoucherViewHolder> {
    private Activity context;
    static VouchersAdapter.VoucherItemClick mItemClickListener;
    private ArrayList<VoucherListItem> productdatas;
    private Dialog dialogVourcherView;
    private String imageUrl;
    private String mCustomerId = "", mReferenceId = "";
    private List<Cart> cartList;
    private OrderSummaryActivity orderSummaryActivity;
    private String isFrom;

    public VouchersAdapter(Activity mContext, ArrayList<VoucherListItem> productdatas, String imageUrl, String isFrom) {
        this.productdatas = productdatas;
        this.context = mContext;
        this.imageUrl = imageUrl;
        this.orderSummaryActivity = orderSummaryActivity;
        this.isFrom = isFrom;

    }

    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_e_vouchers, viewGroup, false);
        return new VoucherViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherViewHolder holder, final int i) {
        holder.txt_drinksQty.setText(productdatas.get(i).getItemName());
        holder.txt_avaCoupons.setText(productdatas.get(i).getOrderItemVoucherBalanceQty());
        holder.txt_description.setText(productdatas.get(i).getProductShortDescription());
        holder.txt_voucherPrice.setText(productdatas.get(i).getItemUnitPrice());

        /*if (productdatas.get(i).getProductThumbnail() != null && productdatas.get(i).getProductThumbnail().length() > 0) {
            Picasso.with(context).load(imageUrl + productdatas.get(i).getProductThumbnail()).
                    error(R.drawable.voucher_card_view).into(holder.img_bg);
        } else {
            Picasso.with(context).load(R.drawable.voucher_cardview).into(holder.img_bg);
        }*/

        SimpleDateFormat placedDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        SimpleDateFormat dateoutput = null;
        try {
            date = placedDateFormatter.parse(productdatas.get(i).getExpiryDate());
            dateoutput = new SimpleDateFormat("dd-MMM-yyyy");

            holder.txt_expAva.setText(dateoutput.format(date));
        }catch (ParseException e) {
            e.printStackTrace();
        }

        if(isFrom.equalsIgnoreCase("1")){
            holder.layoutView.setVisibility(View.VISIBLE);
        }else{
            holder.layoutView.setVisibility(View.GONE);
        }


        holder.layoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogVouchers(i);
            }
        });

        holder.layoutRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogVouchers(i);
                /*if(mItemClickListener != null)
                 mItemClickListener.onItemClick(v, "1", i);*/
                //VoucherTask(v, "1", i);
            }
        });
    }

    private void dialogVouchers(final int i) {
        dialogVourcherView = new Dialog(context, R.style.custom_dialog_theme1);
        dialogVourcherView.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //TODO:Identification
        dialogVourcherView.setContentView(R.layout.layout_voucher_dialog_viewnow);
        dialogVourcherView.show();

        ImageView imageViews = dialogVourcherView.findViewById(R.id.imageViews);
        ImageView imgDecreement = dialogVourcherView.findViewById(R.id.imageDegreement);
        ImageView imgIncreement = dialogVourcherView.findViewById(R.id.imageIngreement);
        final ImageView cancel_Image = dialogVourcherView.findViewById(R.id.cancel_Image);
        final TextView txtQuantity = dialogVourcherView.findViewById(R.id.totalcount);
        final TextView AddtoCartBtn = dialogVourcherView.findViewById(R.id.AddtoCart);
        LinearLayout lly_addToCart = dialogVourcherView.findViewById(R.id.lly_addToCart);
        TextView productName = dialogVourcherView.findViewById(R.id.productName);
        TextView productDescription = dialogVourcherView.findViewById(R.id.productDescription);
        TextView txt_avaCoupons = dialogVourcherView.findViewById(R.id.txt_avaCoupons);
        TextView txt_available = dialogVourcherView.findViewById(R.id.txt_available);

       /* if (productdatas.get(i).getProductThumbnail() != null && productdatas.get(i).getProductThumbnail().length() > 0) {
            Picasso.with(context).load(imageUrl + productdatas.get(i).getProductThumbnail()).
                    error(R.drawable.place_holder_sushi_tei).into(imageViews);
        } else {
            Picasso.with(context).load(R.drawable.place_holder_sushi_tei).into(imageViews);
        }*/
        //Picasso.with(context).load(R.drawable.place_holder_sushi_tei).into(imageViews);
        productName.setText(productdatas.get(i).getItemName());
        productDescription.setText(productdatas.get(i).getProductShortDescription());
        txt_avaCoupons.setText(productdatas.get(i).getOrderItemVoucherBalanceQty());

        String expiryDate = "";
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateForm = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            Date date = dateFormatter.parse(productdatas.get(i).getExpiryDate());
            expiryDate = dateForm.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        txt_available.setText(expiryDate);

        imgIncreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int voucherBalanceQty;
                if(productdatas.get(i).getOrderItemVoucherBalanceQty().equalsIgnoreCase("null")){
                    voucherBalanceQty = 1;
                }else{
                    voucherBalanceQty = Integer.valueOf(productdatas.get(i).getOrderItemVoucherBalanceQty());
                }
                if (voucherBalanceQty > Integer.valueOf(txtQuantity.getText().toString())) {
                    int count = Integer.parseInt(txtQuantity.getText().toString());
                    count++;
                    txtQuantity.setText(String.valueOf(count));
                    AddtoCartBtn.setText("Redeem (" + count + " )");
                } else {
                    Toast.makeText(context, "You have reached the maximum selection", Toast.LENGTH_SHORT).show();
                }
            }
        });


        imgDecreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(txtQuantity.getText().toString());
                if (count > 1) {
                    count--;
                    txtQuantity.setText(count + "");
                    AddtoCartBtn.setText("Redeem (" + count + " )");
                }
            }
        });

        lly_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddtoCartBtn.performClick();
            }
        });

        AddtoCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCustomerLoggedIn()) {

                    new MessageDialog(context, new MessageDialog.OnSlectedString() {
                        @Override
                        public void selectedAction(String action) {

                            if (action.equalsIgnoreCase("Ok")) {
                                Intent intent = new Intent(context, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent);
                                ((Activity) context).finish();
                            }
                        }
                    });
                } else {

                    if(mItemClickListener != null)
                    mItemClickListener.onItemClick(v, txtQuantity.getText().toString(), i);
                    if (dialogVourcherView.isShowing())
                        dialogVourcherView.dismiss();
                    //VoucherTask(v, txtQuantity.getText().toString(), i);
                }
            }
        });

        cancel_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogVourcherView.isShowing())
                    dialogVourcherView.dismiss();
            }
        });
    }

    private void VoucherTask(View v, String productqty, int i) {
         if (productdatas.get(i).getProductVoucher().equalsIgnoreCase("f")) {
            String url = GlobalUrl.ADD_CART_VOUCHER_URL;
            if (Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID).length() > 0) {

                mCustomerId = Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID);
                mReferenceId = Utility.getReferenceId(context);

            } else {
                try {
                    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO:
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    GlobalValues.DEVICE_ID = telephonyManager.getDeviceId();
                    mReferenceId = GlobalValues.DEVICE_ID;

                } catch (Exception e) {
                    GlobalValues.DEVICE_ID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                    mReferenceId = GlobalValues.DEVICE_ID;

                } finally {
                    mCustomerId = "";
                }
            }
            Map<String, String> params = new HashMap<String, String>();

            if (Utility.readFromSharedPreference(context, GlobalValues.AVALABILITY_ID).equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
                params.put("availability_name", "DELIVERY");
            } else {
                params.put("availability_name", "TAKEAWAY");
            }

            params.put("order_availability_id", productdatas.get(i).getOrderAvailabilityId());
            params.put("order_item_id", productdatas.get(i).getOrderItemId());
            params.put("order_outlet_id", productdatas.get(i).getOrderOutletId());
            params.put("app_id", GlobalValues.APP_ID);
            params.put("product_id", productdatas.get(i).getItemProductId());
            //params.put("product_type", productdatas.get(i).getProductType());
            params.put("product_name", productdatas.get(i).getItemName());
            params.put("product_sku", productdatas.get(i).getItemSku());
            params.put("product_image", productdatas.get(i).getProductThumbnail());
            params.put("availability_id", Utility.readFromSharedPreference(context, GlobalValues.AVALABILITY_ID));
            params.put("product_unit_price", "0");
            params.put("product_qty", productqty);
            params.put("product_total_price", "0");
            params.put("voucher_gift_name", "");
            params.put("voucher_gift_email", "");
            params.put("voucher_gift_message", "");
            params.put("product_voucher_points", productdatas.get(i).getProductVoucherPoints());
            params.put("reference_id", "");
            params.put("customer_id", mCustomerId);
            new VoucherListTask(params, i).execute(url);
        } else {
            String url = GlobalUrl.VOUCHER_REDEEM_URL;
            Map<String, String> params = new HashMap<String, String>();
            params.put("app_id", GlobalValues.APP_ID);
            params.put("product_qty", productqty);
            params.put("product_voucher_points", productdatas.get(i).getProductVoucherPoints());
            params.put("customer_id", mCustomerId);
            params.put("order_item_id", productdatas.get(i).getOrderItemId());
             new VoucherListTask(params, i).execute(url);
        }
    }

    public class VoucherListTask extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;
        private Map<String, String> voucherParams;
        private int i;

        public VoucherListTask(Map<String, String> voucherParams, int i) {
            this.voucherParams = voucherParams;
            this.i = i;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            System.out.print("Voucher redeem");

            String response = WebserviceAssessor.postRequest(context, params[0], voucherParams);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                JSONObject jsonObject = new JSONObject(s);
                Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                if (Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID).length() > 0) {
                    mCustomerId = Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID);
                    mReferenceId = "";
                } else {
                    mCustomerId = "";
                    mReferenceId = Utility.getReferenceId(context);
                }

                CURRENT_AVAILABLITY_ID = Utility.readFromSharedPreference(context, GlobalValues.AVALABILITY_ID);

                String url = GlobalUrl.CART_LIST_URL + "?app_id=" + GlobalValues.APP_ID +
                        "&customer_id=" + mCustomerId +
                        "&reference_id=" + mReferenceId +
                        "&availability_id=" + CURRENT_AVAILABLITY_ID;
                //new CartListTask().execute(url);
                getActiveCount();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
            }
        }
    }

    private void getActiveCount() {

        if (Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID).length() > 0) {
            //Promotion count and Reawrd points
            Map<String, String> mapData = new HashMap<>();
            mapData.put("app_id", GlobalValues.APP_ID);

            if (Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID).length() > 0) {
                mapData.put("customer_id", Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID));

            } else {
                mapData.put("customer_id", Utility.getReferenceId(context));
            }

            JSONArray jsonArray = new JSONArray();
            jsonArray.put("order");
            jsonArray.put("promotionwithoutuqc");
            jsonArray.put("reward_monthly");
            jsonArray.put("order_all");
            jsonArray.put("notify");
            jsonArray.put("reward");
            mapData.put("act_arr", jsonArray.toString());

            String url = GlobalUrl.ACTIVE_COUNT_URL + "?app_id=" + GlobalValues.APP_ID + "&customer_id=" + Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID) +
                    "&act_arr=" + jsonArray.toString();

            new ActivCountTask(mapData).execute(url);
        }
    }

    private class ActivCountTask extends AsyncTask<String, Void, String> {

        private Map<String, String> countParams;

        private ProgressDialog progressDialog;

        public ActivCountTask(Map<String, String> mapData) {
            countParams = mapData;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
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

                    JSONObject countJson = jsonObject.getJSONObject("result_set");

                    GlobalValues.ORDERCOUNT = countJson.getString("order");
                    GlobalValues.NOTIFYCOUNT = countJson.getString("notify");
                    GlobalValues.PROMOTIONCOUNT = countJson.optString("promotionwithoutuqc");
                    GlobalValues.VOUCHERCOUNT = countJson.optString("vouchers");

                    double x_reedempoints = countJson.optDouble("reward_ponits");

                    GlobalValues.CUSTOMER_REWARD_POINT = x_reedempoints;


                    if (!countJson.isNull("reward_ponits_monthly")) {

                        GlobalValues.CUSTOMER_MONTHLY_REWARD_POINT = Double.valueOf(countJson.optString("reward_ponits_monthly"));

                    } else {
                        GlobalValues.CUSTOMER_MONTHLY_REWARD_POINT = 0.00;
                    }

                    if (GlobalValues.ORDERCOUNT != null && !GlobalValues.ORDERCOUNT.equals("0") && !GlobalValues.ORDERCOUNT.equalsIgnoreCase("")) {
                        txtOrderCount.setVisibility(View.VISIBLE);
                        txtOrderCount.setText(GlobalValues.ORDERCOUNT);
                    } else {
                        txtOrderCount.setVisibility(View.GONE);
                    }

                    if (GlobalValues.PROMOTIONCOUNT != null && !GlobalValues.PROMOTIONCOUNT.equals("0") && !GlobalValues.PROMOTIONCOUNT.equalsIgnoreCase("")) {

                        txtPromotionCount.setVisibility(View.VISIBLE);
                        txtPromotionCount.setText(GlobalValues.PROMOTIONCOUNT);

                    } else {
                        txtPromotionCount.setVisibility(View.GONE);
                    }

                    if (GlobalValues.VOUCHERCOUNT != null && !GlobalValues.VOUCHERCOUNT.equals("0") && !GlobalValues.VOUCHERCOUNT.equalsIgnoreCase("")) {

                        txtVoucherCount.setVisibility(View.VISIBLE);
                        txtVoucherCount.setText(GlobalValues.VOUCHERCOUNT);

                    } else {
                        txtVoucherCount.setVisibility(View.GONE);
                    }

                    if (GlobalValues.NOTIFYCOUNT != null && !GlobalValues.NOTIFYCOUNT.equals("0") && !GlobalValues.NOTIFYCOUNT.equalsIgnoreCase("")) {

                        txtNotificationCount.setVisibility(View.VISIBLE);
                        txtNotificationCount.setText(GlobalValues.NOTIFYCOUNT);
                    } else {
                        txtNotificationCount.setVisibility(View.GONE);
                    }

                } else {

                }
                progressDialog.dismiss();

            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            } finally {
                progressDialog.dismiss();
            }
        }
    }


    @Override
    public int getItemCount() {
        return productdatas.size();
    }

    public class VoucherViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout layoutView, layoutRedeem, rly_voucherLayout;
        private TextView txt_avaCoupons, txt_drinksQty, txt_expAva, txt_description, txt_voucherPrice;
        private ImageView img_bg;

        public VoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_avaCoupons = itemView.findViewById(R.id.txt_avaCoupons);
            txt_drinksQty = itemView.findViewById(R.id.txt_drinksQty);
            txt_expAva = itemView.findViewById(R.id.txt_expAva);
            txt_description = itemView.findViewById(R.id.txt_description);
            txt_voucherPrice = itemView.findViewById(R.id.txt_voucherPrice);
            layoutView = itemView.findViewById(R.id.layoutView);
            layoutRedeem = itemView.findViewById(R.id.layoutRedeem);
            rly_voucherLayout = itemView.findViewById(R.id.rly_voucherLayout);
            img_bg = itemView.findViewById(R.id.img_bg);
        }
    }

    public interface VoucherItemClick {
        public void onItemClick(View view, String productQty,int position);
    }

    public void SetOnItemClickListener(VoucherItemClick mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private boolean isCustomerLoggedIn() {

        if (Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID).length() > 0) {

            return true;
        } else {
            Utility.writeToSharedPreference(context, GlobalValues.OPENLOGIN, "Close");
            return false;
        }
    }
}
