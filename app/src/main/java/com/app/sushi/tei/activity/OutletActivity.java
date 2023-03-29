package com.app.sushi.tei.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.CompassOutlet.OutletResultSetItem;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.OutletListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OutletActivity extends AppCompatActivity {

    private Context context;
    private List<OutletResultSetItem> outletList;
    private RecyclerView recycle_outletList;
    private OutletListAdapter outletListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet);
        context = this;

        recycle_outletList = findViewById(R.id.recycle_outletList);


        getOutletService();

    }

    private void getOutletService() {


        if (Utility.networkCheck(context)) {
            String url = GlobalUrl.COMPASSOUTLET_URL + "?app_id=" + GlobalValues.APP_ID;

            new OutletTask().execute(url);

        } else {
            Toast.makeText(context, "Please check your interner connection.", Toast.LENGTH_SHORT).show();
        }


    }

    private class OutletTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        OutletTask() {

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

                    JSONArray arrayResult = jsonObject.getJSONArray("result_set");

                    if (arrayResult.length() > 0) {
                        outletList = new ArrayList<>();

                        for (int j = 0; j < arrayResult.length(); j++) {

                            JSONObject jsonOutlet = arrayResult.getJSONObject(j);

                            OutletResultSetItem outlet = new OutletResultSetItem();

                            outlet.setOaOutletId(jsonOutlet.getString("oa_outlet_id"));
                            outlet.setOaAvailabilityId(jsonOutlet.optString("oa_availability_id"));
                            outlet.setOutletName(jsonOutlet.optString("outlet_name"));
                            outlet.setOutletId(jsonOutlet.getString("outlet_id"));
                            outlet.setOutletPhone(jsonOutlet.getString("outlet_phone"));
                            outlet.setOutletDeliveryTiming(jsonOutlet.getString("outlet_delivery_timing"));
                            outlet.setOutletUnitNumber1(jsonOutlet.getString("outlet_unit_number1"));
                            outlet.setOutletUnitNumber2(jsonOutlet.getString("outlet_unit_number2"));
                            outlet.setOutletAddressLine1(jsonOutlet.getString("outlet_address_line1"));
                            outlet.setOutletAddressLine2(jsonOutlet.getString("outlet_address_line2"));
                            outlet.setOutletPostalCode(jsonOutlet.getString("outlet_postal_code"));
                            outlet.setOutletDefalutValue(jsonOutlet.getString("outlet_defalut_value"));
                            outlet.setOutletOpenDate(jsonOutlet.getString("outlet_open_date"));
                            outlet.setOutletCloseDate(jsonOutlet.getString("outlet_close_date"));
                            outlet.setOutletOpenTime(jsonOutlet.getString("outlet_open_time"));
                            outlet.setOutletCloseTime(jsonOutlet.getString("outlet_close_time"));
                            outlet.setOutletRouteId(jsonOutlet.getString("outlet_route_id"));
                            outlet.setOutletMarkerLatitude(jsonOutlet.getString("outlet_marker_latitude"));
                            outlet.setOutletMarkerLongitude(jsonOutlet.getString("outlet_marker_longitude"));
                            outlet.setOutletDineTat(jsonOutlet.getString("outlet_dine_tat"));
                            outlet.setOutletDeliveryTat(jsonOutlet.getString("outlet_delivery_tat"));
                            outlet.setOutletPickupTat(jsonOutlet.getString("outlet_pickup_tat"));
                            outlet.setOutletServiceCharge(jsonOutlet.getString("outlet_service_charge"));
                            outletList.add(outlet);


                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                            recycle_outletList.setLayoutManager(linearLayoutManager);
                            outletListAdapter = new OutletListAdapter(context, outletList);
                            recycle_outletList.setAdapter(outletListAdapter);

                        }


                        outletListAdapter.setOnClickListeners(new OutletListAdapter.OnOutletClick() {
                            @Override
                            public void OnClick(View v, int position) {
                                Utility.writeToSharedPreference(context, GlobalValues.OUTLET_ID, outletList.get(position).getOaOutletId());
                                Utility.writeToSharedPreference(context, GlobalValues.OUTLET_NAME, outletList.get(position).getOutletName());
                                Utility.writeToSharedPreference(context, GlobalValues.CURRENT_OUTLET_ADDRESS, outletList.get(position).getOutletAddressLine1());
                                finish();
                            }

                        });


                    } else {

                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utility.writeToSharedPreference(context, "IS_ACTIVE","1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Utility.writeToSharedPreference(context, "OREO_UPDATE","1");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utility.writeToSharedPreference(context, "IS_ACTIVE","0");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Utility.writeToSharedPreference(context, "OREO_UPDATE","1");
        }
    }
}
