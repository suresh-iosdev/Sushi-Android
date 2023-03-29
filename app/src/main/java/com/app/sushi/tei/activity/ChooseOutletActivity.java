package com.app.sushi.tei.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.app.sushi.tei.Database.DatabaseHandler;
import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Interface.IOnItemClick;
import com.app.sushi.tei.Model.Account.SecondaryAddress;
import com.app.sushi.tei.Model.favourite.Favouriteitems;
import com.app.sushi.tei.Model.location.PredictionsItem;
import com.app.sushi.tei.Model.outlet.takeaway.OutletTimingItem;
import com.app.sushi.tei.Model.outlet.takeaway.ResultSetItem;
import com.app.sushi.tei.Model.outlet.takeaway.SlotItem;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.MyAccount.OtherAddressRecyclerAdapter;
import com.app.sushi.tei.adapter.OutletDeliveryAdapter;
import com.app.sushi.tei.adapter.PlacesAdapter;
import com.app.sushi.tei.adapter.OuletAdapter;
import com.app.sushi.tei.shadow.ShadowCenterLinearLayout;
import com.app.sushi.tei.shadow.ShadowLinearLayout;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseOutletActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    int selectedPosition = 0;
    boolean outletSelected = false;
    Button btn_continue;
    private Toolbar toolBar;

    private LinearLayout layout_orderStatus, layout_delivery_enable, layout_takeaway_enable;
    private ShadowCenterLinearLayout layout_outletLocation, layout_deliveryOutlet;
    private TextView txt_delivery, txt_takeaway_disable, txt_delivery_disable, txt_takeaway, txt_chooseOutletLabel, label;
    private EditText edt_pincode;
    private AutoCompleteTextView txt_selectoutlet;
    private RecyclerView recyclerView_outletList, recyclerView_deliveryOutletList;
    private TextView continues;
    private LinearLayoutCompat lnrlayout_register_new;
    private LinearLayout imgBack;
    private OuletAdapter ouletAdapter;
    private OutletDeliveryAdapter outletDeliveryAdapter;
    private Context mContext;
    private List<ResultSetItem> outletList;
    private DatabaseHandler databaseHandler;
    private String mCustomerId;
    private Toolbar toolbar;
    protected FusedLocationProviderClient mFusedLocationProviderClient;
    private double latitude = 0, longtitude = 0;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationManager locationManager;
    private LocationRequest mLocationRequest;
    private ImageView img_editOutlet;
    private RecyclerView recyclerViewAutoPlaces;
    private boolean isSearch = false;
    private TextView txt_deliveryTo, txt_deliveryAddress, txt_awesome;
    private ImageView img_success;
    private String availability = "takeaway";
    private ArrayList<Integer> outletImageList;
    private ArrayList<SecondaryAddress> secondaryAddressList;
    private OtherAddressRecyclerAdapter otherAddressRecyclerAdapter;
    private RecyclerView recyclerviewOtherAddress;
    private ShadowLinearLayout shadow_layout;
    private TextView txt_savedAddress;
    private String outletId, outletName, currentOutletAddress, outletPincode, outletUnitNo1, outletUnitNo2, availabilityId;
    public static int pos = -1;
    private String baseUrl = "";

    private ImageView user_imageview;
    private boolean fromLogin = false;
    private boolean secondBackPress = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_choose_outlet_new);

        if (getIntent().getExtras() != null) {
            availability = getIntent().getStringExtra("availability");
            if (availability == null) {
                availability = "takeaway";
            }
         }
        if (getIntent().getExtras() != null) {
            try {
                fromLogin = getIntent().getBooleanExtra("fromLogin", false);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }


        intiView();

//        user_imageview=toolbar.findViewById(R.id.user_imageview);
//        user_imageview.setVisibility(View.VISIBLE);
//        user_imageview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                if (!isCustomerLoggedIn()) {
//                    Intent intent = new Intent(ChooseOutletActivity.this, MemberBenefitActivity.class);
//                    startActivity(intent);
////                } else {
////                    drawerLayout.closeDrawers();
////                    openFiveMenuActivity(0);
////                }
//            }
//        });


        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        if (mGoogleApiClient == null) {
            buildGoogleApiClient();
        }


        if (Utility.networkCheck(mContext)) {
            String url = GlobalUrl.COMPASSOUTLET_URL + "?app_id=" + GlobalValues.APP_ID + "&availability_id=" + GlobalValues.TAKEAWAY_ID + "&latitude=" + latitude + "&longitude=" + longtitude;
            new OutletTask().execute(url);
            getSecondaryAddress();
        } else {
            Toast.makeText(mContext, "Please check your interner connection.", Toast.LENGTH_SHORT).show();
        }

        if (availability.equalsIgnoreCase("takeaway")) {
            txt_takeaway_disable.performClick();
        } else {
            layout_delivery_enable.setVisibility(View.VISIBLE);
            layout_takeaway_enable.setVisibility(View.GONE);
            txt_chooseOutletLabel.setText("Choose your delivery location");
            label.setText("Enter your postal code below");
            layout_outletLocation.setVisibility(View.GONE);
            layout_deliveryOutlet.setVisibility(View.VISIBLE);
            continues.setVisibility(View.GONE);
            recyclerView_outletList.setVisibility(View.GONE);
            /*shadow_layout.setVisibility(View.VISIBLE);
            txt_savedAddress.setVisibility(View.VISIBLE);*/
        }

        otherAddressRecyclerAdapter = new OtherAddressRecyclerAdapter(mContext);
        recyclerviewOtherAddress.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerviewOtherAddress.setAdapter(otherAddressRecyclerAdapter);

        txt_delivery_disable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                hide below for not implementing delivery
                Toast.makeText(ChooseOutletActivity.this, "This feature is not available", Toast.LENGTH_SHORT).show();

//                {
//                    layout_delivery_enable.setVisibility(View.VISIBLE);
//                    layout_takeaway_enable.setVisibility(View.GONE);
//                    txt_chooseOutletLabel.setText("Choose your delivery location");
//                    label.setText("Enter your postal code below");
//                    layout_outletLocation.setVisibility(View.GONE);
//                    layout_deliveryOutlet.setVisibility(View.VISIBLE);
//                    continues.setVisibility(View.GONE);
//                    lnrlayout_register_new.setVisibility(View.GONE);
//                    recyclerView_outletList.setVisibility(View.GONE);
//                    if (isCustomerLoggedIn()) {
//                        if (secondaryAddressList != null && secondaryAddressList.size() > 0) {
//                            shadow_layout.setVisibility(View.VISIBLE);
//                            txt_savedAddress.setVisibility(View.VISIBLE);
//                        } else {
//                            shadow_layout.setVisibility(View.GONE);
//                            txt_savedAddress.setVisibility(View.GONE);
//                        }
//                    } else {
//                        shadow_layout.setVisibility(View.GONE);
//                        txt_savedAddress.setVisibility(View.GONE);
//                    }
//                    availability = "delivery";
//                }
            }
        });

        txt_takeaway_disable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_delivery_enable.setVisibility(View.GONE);
                layout_takeaway_enable.setVisibility(View.VISIBLE);
                txt_chooseOutletLabel.setText("Choose your outlet");
                label.setText("You can select outlet based on distance");
                layout_outletLocation.setVisibility(View.VISIBLE);
                layout_deliveryOutlet.setVisibility(View.GONE);
                continues.setVisibility(View.GONE);
                lnrlayout_register_new.setVisibility(View.VISIBLE);
                recyclerView_outletList.setVisibility(View.VISIBLE);
                recyclerView_deliveryOutletList.setVisibility(View.GONE);
                shadow_layout.setVisibility(View.GONE);
                txt_savedAddress.setVisibility(View.GONE);
                availability = "takeaway";
            }
        });

        continues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utility.writeToSharedPreference(mContext, GlobalValues.OUTLET_ID, outletId);
                Utility.writeToSharedPreference(mContext, GlobalValues.OUTLET_NAME, outletName);
                Utility.writeToSharedPreference(mContext, GlobalValues.CURRENT_OUTLET_ADDRESS, currentOutletAddress);
                Utility.writeToSharedPreference(mContext, GlobalValues.OUTLET_PINCODE, outletPincode);
                Utility.writeToSharedPreference(mContext, GlobalValues.OUTLET_UNITNO1, outletUnitNo1);
                Utility.writeToSharedPreference(mContext, GlobalValues.OUTLET_UNITNO2, outletUnitNo2);
                Utility.writeToSharedPreference(mContext, GlobalValues.AVALABILITY_ID, GlobalValues.DELIVERY_ID);
                GlobalValues.CURRENT_OUTLET_ID = Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID);

//                Intent intent = new Intent(mContext, SubCategoryActivity.class);
                Intent intent = new Intent(mContext, MenuCategoryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        txt_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        txt_takeaway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

      /*  img_editOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_selectoutlet.setText("");
                txt_selectoutlet.setEnabled(true);
                txt_selectoutlet.setFocusable(true);
                isSearch = true;
                recyclerViewAutoPlaces.setVisibility(View.GONE);
            }
        });*/

        edt_pincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count < 6) {
                    pos = -1;
                    otherAddressRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                String data = edt_pincode.getText().toString();
                int length = data.length();

                if (length == 6) {

                    new DeliveryOutlet().execute(data);

                }
            }
        });

        txt_selectoutlet.setThreshold(3);
        txt_selectoutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSearch = true;
            }
        });

       /* txt_selectoutlet.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace

                    isSearch = true;
                }
                return false;
            }
        });*/

       /* txt_selectoutlet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                recyclerViewAutoPlaces.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Map<String, String> mapPrams = new HashMap<String, String>();
                mapPrams.put("input", s.toString());
                //mapPrams.put("key", "AIzaSyDLZ5YQwFzCH6m0nP6_yayyiR8JqAEFvw8");
                mapPrams.put("key", "AIzaSyB6ACp90IJp4SbAnOWYlXnM9qkVMH8ii1M");
//                mapPrams.put("key", "AIzaSyDf3xZF2g6qV_udmlgkOzZR4ydD_CrtoRc");//test
                mapPrams.put("components", "country:SG");
                if (isSearch && checkPermissionLocation(mContext)) {
                    if (txt_selectoutlet.getText().toString().length() > 2) {
                        new GetLocation(mapPrams).execute("https://maps.googleapis.com/maps/api/place/autocomplete/json" + "?input=" + s.toString()
                                + "&key=" + "AIzaSyB6ACp90IJp4SbAnOWYlXnM9qkVMH8ii1M" + "&components=country:SG");
//                                + "&key=" + "AIzaSyDf3xZF2g6qV_udmlgkOzZR4ydD_CrtoRc" + "&components=country:SG");//test
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                recyclerViewAutoPlaces.setVisibility(View.GONE);
            }
        });*/


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (outletSelected) {


                    clickContinue(selectedPosition);

                   /* AlertDialog.Builder successDialogBuilder = new AlertDialog.Builder(ChooseOutletActivity.this, R.style.AlertDialogTheme);

                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialog_outlet_success, null);
                    successDialogBuilder.setView(dialogView);

                    final AlertDialog alertDialog = successDialogBuilder.create();
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    final TextView continueTextView = (TextView) dialogView.findViewById(R.id.successContinueTextView);
//                    continueTextView.setBackgroundResource((Config.getString(Config.SELECTED_SHOP_FLOW).equals(Config.FLOW_ANDERSON)) ? R.drawable.gradient_red : R.drawable.gradient_orange);
                    final TextView deliveryAddressTextView = (TextView) dialogView.findViewById(R.id.deliveryAddressTextView);
//                    LinearLayout layout_dialog_bg = dialogView.findViewById(R.id.layout_dialog_bg);
//                    layout_dialog_bg.setBackgroundResource((Config.getString(Config.SELECTED_SHOP_FLOW).equals(Config.FLOW_ANDERSON)) ? R.drawable.border_red_bg : R.drawable.border_orange_bg);
                    RelativeLayout closeLayout = (RelativeLayout) dialogView.findViewById(R.id.closeLayout);


                    final TextView titlePickupLabel = (TextView) dialogView.findViewById(R.id.titlePickupLabel);
                    final TextView subTitlePickupLabel = (TextView) dialogView.findViewById(R.id.subTitlePickupLabel);

                    try {
                        String formattedAddressText = outletList.get(selectedPosition).getOutletAddressLine1() + " " + outletList.get(selectedPosition).getOutletAddressLine2() +
                                ", Singapore " +
                                outletList.get(selectedPosition).getOutletPostalCode();

                        deliveryAddressTextView.setText(outletList.get(selectedPosition).getOutletName() + "\n" + formattedAddressText);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    closeLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });

                    continueTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            clickContinue(selectedPosition);

                        }
                    });

                    alertDialog.show();*/


                }
            }
        });
    }

    private void intiView() {
        mContext = this;
        btn_continue = findViewById(R.id.btn_continue);
        toolBar = findViewById(R.id.toolBar);

        ImageView toolbartxtTitleChooseOutlet;
        ImageView toolbartxtTitle;
        toolbartxtTitleChooseOutlet = findViewById(R.id.toolbartxtTitleChooseOutlet);
        toolbartxtTitle = findViewById(R.id.toolbartxtTitle);
        toolbartxtTitleChooseOutlet.setVisibility(View.VISIBLE);
        toolbartxtTitle.setVisibility(View.GONE);

        layout_orderStatus = findViewById(R.id.layout_orderStatus);
        layout_delivery_enable = findViewById(R.id.layout_delivery_enable);
        layout_takeaway_enable = findViewById(R.id.layout_takeaway_enable);
        layout_outletLocation = findViewById(R.id.layout_outletLocation);
        layout_deliveryOutlet = findViewById(R.id.layout_deliveryOutlet);
        txt_delivery = findViewById(R.id.txt_delivery);
        txt_takeaway_disable = findViewById(R.id.txt_takeaway_disable);
        txt_delivery_disable = findViewById(R.id.txt_delivery_disable);
        txt_takeaway = findViewById(R.id.txt_takeaway);
        txt_chooseOutletLabel = findViewById(R.id.txt_chooseOutletLabel);
        label = findViewById(R.id.label);
        txt_selectoutlet = findViewById(R.id.txt_selectoutlet);
        edt_pincode = findViewById(R.id.edt_pincode);
        recyclerView_outletList = findViewById(R.id.recyclerView_outletList);
        recyclerView_deliveryOutletList = findViewById(R.id.recyclerView_deliveryOutletList);
        continues = findViewById(R.id.continues);
        lnrlayout_register_new = findViewById(R.id.lnrlayout_register_new);
        imgBack = toolBar.findViewById(R.id.toolbarBack);
        String StrAvailability = null;
        if (getIntent().getExtras() != null) {
            StrAvailability = getIntent().getStringExtra("availability");
        }

        imgBack.setVisibility(StrAvailability == null || fromLogin ? View.INVISIBLE : View.VISIBLE);

        img_editOutlet = findViewById(R.id.img_editOutlet);
        txt_deliveryTo = findViewById(R.id.txt_deliveryTo);
        txt_deliveryAddress = findViewById(R.id.txt_deliveryAddress);
        txt_awesome = findViewById(R.id.txt_awesome);
        img_success = findViewById(R.id.img_success);
        databaseHandler = DatabaseHandler.getInstance(mContext);
        recyclerViewAutoPlaces = findViewById(R.id.recyclerViewAutoPlaces);
        shadow_layout = findViewById(R.id.shadow_layout);
        recyclerviewOtherAddress = findViewById(R.id.recyclerView_address);
        txt_savedAddress = findViewById(R.id.txt_savedAddress);

        outletImageList = new ArrayList<>();

        outletImageList.add(R.drawable.changi_city_point);
        outletImageList.add(R.drawable.outlet_313);
        outletImageList.add(R.drawable.outlet_funan);
        outletImageList.add(R.drawable.outlet_plq);
        outletImageList.add(R.drawable.outlet_wood_square);
        outletImageList.add(R.drawable.outlet_westgate);
        outletImageList.add(R.drawable.outlet_suntec);
        outletImageList.add(R.drawable.outlet_novena);

    }

    private class OutletTask extends AsyncTask<String, Void, String> {

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

            Log.e("Outlet_service:", params[0]);

            String response = WebserviceAssessor.getData(params[0]);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                Log.e("outlet_services_param:", s);
                GlobalValues.outletsJson = s;
                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    try {
                        JSONObject commonJson = jsonObject.getJSONObject("common");
                        baseUrl = commonJson.getString("image_source") + "/";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    JSONArray arrayResult = jsonObject.getJSONArray("result_set");


                    if (arrayResult.length() > 0) {
                        outletList = new ArrayList<>();

                        for (int j = 0; j < arrayResult.length(); j++) {

                            JSONObject jsonOutlet = arrayResult.getJSONObject(j);

                            ResultSetItem outlet = new ResultSetItem();

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
                            outlet.setOperationHrs(jsonOutlet.getString("operational_hr"));
                            outlet.setDistance(jsonOutlet.getString("distance"));
                            outlet.setOutlet_image(baseUrl + jsonOutlet.optString("outlet_image"));

                            JSONArray arrayOutletTiming = jsonOutlet.getJSONArray("outlet_timing");
                            ArrayList<OutletTimingItem> outletTimingItemArrayList = new ArrayList<>();

                            for (int k = 0; k < arrayOutletTiming.length(); k++) {
                                JSONObject jsonOutletTiming = arrayOutletTiming.getJSONObject(k);
                                OutletTimingItem outletTimingItem = new OutletTimingItem();
                                outletTimingItem.setTitle(jsonOutletTiming.getString("title"));

                                JSONArray arraySlot = jsonOutletTiming.getJSONArray("slot");
                                ArrayList<SlotItem> slotItemArrayList = new ArrayList<>();
                                if (arraySlot.length() > 0) {
                                    for (int q = 0; q < arraySlot.length(); q++) {
                                        JSONObject jsonSlot = arraySlot.getJSONObject(q);
                                        SlotItem slotItem = new SlotItem();

                                        slotItem.setDays(jsonSlot.getString("days"));
                                        slotItem.setSlottext(jsonSlot.getString("slottext"));

                                        slotItemArrayList.add(slotItem);
                                    }
                                    outletTimingItem.setSlot(slotItemArrayList);
                                }
                                outletTimingItemArrayList.add(outletTimingItem);
                            }

                            outlet.setOutletTiming(outletTimingItemArrayList);

                            outletList.add(outlet);

                            if ((availability.equalsIgnoreCase("takeaway"))) {
                                recyclerView_outletList.setVisibility(View.VISIBLE);
                            } else {
                                recyclerView_outletList.setVisibility(View.GONE);
                            }

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                            recyclerView_outletList.setLayoutManager(linearLayoutManager);
                            ouletAdapter = new OuletAdapter(mContext, outletList, outletImageList);
                            recyclerView_outletList.setAdapter(ouletAdapter);
                        }

                        ouletAdapter.setOnClickListeners(new OuletAdapter.OnOutletClick() {
                            @Override
                            public void OnClick(View v, final int position) {

//                                Utility.writeToSharedPreference(mContext, GlobalValues.OUTLET_ID, outletList.get(position).getOaOutletId());
//                                Utility.writeToSharedPreference(mContext, GlobalValues.OUTLET_NAME, outletList.get(position).getOutletName());
//                                Utility.writeToSharedPreference(mContext, GlobalValues.CURRENT_OUTLET_ADDRESS, outletList.get(position).getOutletAddressLine1());
//                                Utility.writeToSharedPreference(mContext, GlobalValues.OUTLET_PINCODE, outletList.get(position).getOutletPostalCode());
//                                Utility.writeToSharedPreference(mContext, GlobalValues.AVALABILITY_ID, GlobalValues.TAKEAWAY_ID);
//
//                                Utility.writeToSharedPreference(mContext, GlobalValues.OUTLET_DELIVERY_TAT, outletList.get(position).getOutletDeliveryTat());
//                                Utility.writeToSharedPreference(mContext, GlobalValues.OUTLET_PICKUP_TAT, outletList.get(position).getOutletPickupTat());
//                                Utility.writeToSharedPreference(mContext, GlobalValues.OUTLET_DELIVERY_TIMING, outletList.get(position).getOutletDeliveryTiming());
//
//                                GlobalValues.CURRENT_OUTLET_ID = Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID);
//
//                                clearCart();
//                                CheckFacourites();
//                                invalidateOptionsMenu();
//                                getCartCount();
//                                getActiveCount();
//                                Intent intent = new Intent(mContext, SubCategoryActivity.class);
//                                startActivity(intent);
//                                finish();

//                                clickContinue(position);
                                outletSelected = true;
                                selectedPosition = position;
                                ouletAdapter.outletSelected(position);
                            }
                        });
                    } else {
                        Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
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

    private class DeliveryOutlet extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        String response;

        DeliveryOutlet() {
        }

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
             try {

                // ?app_id=9962A11D-3F0D-4AB3-BB3A-4BFFF7E33E2A&availability_id=634E6FA8-8DAF-4046-A494-FFC1FCF8BD11&postal_code=307683&postalcode_basedoutlet=yes"

                String app_id = "?app_id=" + GlobalValues.APP_ID;
                String availability_id = "&availability_id=" + GlobalValues.DELIVERY_ID;
                String zipCode = "&postal_code=" + params[0];

                String url = GlobalUrl.DELIVERY_OUTLET_URL + app_id + availability_id + zipCode + "&postalcode_basedoutlet=yes";

                response = WebserviceAssessor.GetRequest(url);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject jsonObject = new JSONObject(s);
//307683
                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    Utility.writeToSharedPreference(mContext, GlobalValues.OUTLETZONE, jsonObject.getJSONObject("result_set").toString());
                    JSONObject resultSet = jsonObject.getJSONObject("result_set");

                    outletId = resultSet.optString("outlet_id");
                    outletName = resultSet.optString("outlet_name");
                    currentOutletAddress = resultSet.optString("outlet_address_line1");
                    outletPincode = resultSet.optString("outlet_postal_code");
                    outletUnitNo1 = resultSet.optString("outlet_unit_number1");
                    outletUnitNo2 = resultSet.optString("outlet_unit_number2");
                    availabilityId = GlobalValues.DELIVERY_ID;

                    clearCart();
                    CheckFacourites();
                    invalidateOptionsMenu();
                    getCartCount();
                    getActiveCount();

                    img_success.setVisibility(View.VISIBLE);
                    txt_awesome.setVisibility(View.VISIBLE);
                    txt_deliveryTo.setVisibility(View.VISIBLE);
                    txt_deliveryAddress.setVisibility(View.VISIBLE);
                    continues.setVisibility(View.VISIBLE);
                    txt_deliveryAddress.setText(resultSet.optString("outlet_address_line1") + " Singapore " + resultSet.optString("outlet_postal_code"));
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

    private void getSecondaryAddress() {

        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID) != null) {

            mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
        }

        String url = GlobalUrl.OTHER_ADDRESS_URL + "?app_id=" + GlobalValues.APP_ID + "&status=A" + "&refrence=" + mCustomerId;

        new OtherAddressTask().execute(url);
    }

    private class OtherAddressTask extends AsyncTask<String, Void, String> {

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
                    // layoutOtherAddress.setVisibility(View.VISIBLE);

                    secondaryAddressList = new ArrayList<>();
                    // layoutOtherAddress.setVisibility(View.VISIBLE);
                    JSONArray jsonArray = jsonObject.getJSONArray("result_set");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject resultObj = jsonArray.getJSONObject(i);
                        SecondaryAddress secondaryAddress = new SecondaryAddress();

                        secondaryAddress.setSecondary_address_id(resultObj.getString("secondary_address_id"));
                        secondaryAddress.setAddress(resultObj.getString("address"));
                        secondaryAddress.setPostal_code(resultObj.getString("postal_code"));
                        secondaryAddressList.add(secondaryAddress);

                     }

                    if (!(availability.equalsIgnoreCase("takeaway"))) {
                        if (isCustomerLoggedIn()) {
                            if (secondaryAddressList.size() > 0) {
                                shadow_layout.setVisibility(View.VISIBLE);
                                txt_savedAddress.setVisibility(View.VISIBLE);
                            } else {
                                shadow_layout.setVisibility(View.GONE);
                                txt_savedAddress.setVisibility(View.GONE);
                            }
                        }
                    }

                    otherAddressRecyclerAdapter = new OtherAddressRecyclerAdapter(mContext, secondaryAddressList);
                    recyclerviewOtherAddress.setAdapter(otherAddressRecyclerAdapter);
                    recyclerviewOtherAddress.setNestedScrollingEnabled(false);
                    otherAddressRecyclerAdapter.notifyAdapter();

                    otherAddressRecyclerAdapter.setOnItemClick(new IOnItemClick() {
                        @Override
                        public void onItemClick(View v, int position) {
                            if (Utility.networkCheck(mContext)) {
                                edt_pincode.setText(secondaryAddressList.get(position).getPostal_code());
                                new DeliveryOutlet().execute(secondaryAddressList.get(position).getPostal_code());
                                otherAddressRecyclerAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                } else {

                    //layoutOtherAddress.setVisibility(View.GONE);

                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
            }

        }
    }

    private void clearCart() {
        String url1 = GlobalUrl.DESTROY_CART_URL;
        Map<String, String> params = new HashMap<>();
        params.put("app_id", GlobalValues.APP_ID);
        params.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
        params.put("reference_id", "");

        if (Utility.networkCheck(mContext)) {

            new DestroyCartTask(params).execute(url1);
        }
    }

    private class DestroyCartTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private Map<String, String> destroyParams;

        public DestroyCartTask(Map<String, String> destroyParams) {
            this.destroyParams = destroyParams;
        }

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


            String response = WebserviceAssessor.postRequest(mContext, params[0], destroyParams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {


                JSONObject destroyJson = new JSONObject(s);

                if (destroyJson.getString("status").equalsIgnoreCase("ok")) {
                    Utility.removeFromSharedPreference(mContext, GlobalValues.CART_COUNT);
                    Utility.removeFromSharedPreference(mContext, GlobalValues.CART_RESPONSE);
                    Utility.removeFromSharedPreference(mContext, GlobalValues.BENTO_CART_COUNT);


                    try {
                        databaseHandler.deleteAllCartQuantity();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                 }

            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                progressDialog.dismiss();


            }
        }
    }

    private void CheckFacourites() {

        if (Utility.networkCheck(mContext)) {

            mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);

            String app_id = "?app_id=" + GlobalValues.APP_ID + "&customer_id=" + mCustomerId;

            String availability_id = "&availability_id=" + GlobalValues.CURRENT_AVAILABLITY_ID;

            GlobalValues.CURRENT_OUTLET_ID = Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID);

            String outlet_id = "&outlet_id=" + GlobalValues.CURRENT_OUTLET_ID;


            String Url = GlobalUrl.FavouriteListURL + app_id + availability_id + outlet_id;


            try {
                new FavouriteListTask().execute(Url);

            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }
    }

    private class FavouriteListTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Loading");
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

                // String basePath = jsonObject.getJSONObject("common").optString("subcategory_image_url");


                if (jsonObject.optString("status").equalsIgnoreCase("ok")) {

                    JSONArray jsonArray = jsonObject.getJSONArray("result_set");


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObjectfav = jsonArray.getJSONObject(i);

                        Favouriteitems favouriteitems = new Favouriteitems();


                        favouriteitems.setProduct_primary_id(jsonObjectfav.optString("product_primary_id"));
                        favouriteitems.setProduct_id(jsonObjectfav.optString("product_id"));
                        favouriteitems.setProduct_name(jsonObjectfav.optString("product_name"));
                        favouriteitems.setProduct_alias(jsonObjectfav.optString("product_alias"));
                        favouriteitems.setProduct_sku(jsonObjectfav.optString("product_sku"));
                        favouriteitems.setProduct_sequence(jsonObjectfav.optString("product_sequence"));
                        favouriteitems.setProduct_thumbnail(jsonObjectfav.optString("product_thumbnail"));
                        favouriteitems.setProduct_short_description(jsonObjectfav.optString("product_short_description"));
                        favouriteitems.setProduct_long_description(jsonObjectfav.optString("product_long_description"));
                        favouriteitems.setProduct_status(jsonObjectfav.optString("product_status"));
                        favouriteitems.setProduct_slug(jsonObjectfav.optString("product_slug"));
                        favouriteitems.setProduct_price(jsonObjectfav.optString("product_price"));
                        favouriteitems.setProduct_type(jsonObjectfav.optString("product_type"));
                        favouriteitems.setProduct_stock(jsonObjectfav.optString("product_stock"));
                        favouriteitems.setProduct_minimum_quantity(jsonObjectfav.optString("product_minimum_quantity"));
                        favouriteitems.setFav_id(jsonObjectfav.optString("fav_id"));
                        favouriteitems.setFav_product_primary_id(jsonObjectfav.optString("fav_product_primary_id"));
                        favouriteitems.setFav_customer_id(jsonObjectfav.optString("fav_customer_id"));
                        favouriteitems.setFav_app_id(jsonObjectfav.optString("fav_app_id"));
                        favouriteitems.setFav_created_on(jsonObjectfav.optString("fav_created_on"));

                        //favouriteitemsArrayList.add(favouriteitems);


                    }


                 /*   if (favouriteitemsArrayList.size() > 0) {

                        heartblink_imageview.setImageResource(R.drawable.ic_heart_blink1);

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(70, 70);
                        heartblink_imageview.setLayoutParams(layoutParams);


                    } else {

                        heartblink_imageview.setImageResource(R.drawable.asset34);


                    }*/


                } else {

                    //heartblink_imageview.setImageResource(R.drawable.asset34);


                }

                progressDialog.dismiss();


            } catch (Exception e) {
                e.printStackTrace();
                //heartblink_imageview.setImageResource(R.drawable.asset34);


                progressDialog.dismiss();

            }

        }
    }

    private void getCartCount() {

        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

            mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
            //mReferenceId = "";
        } else {
            mCustomerId = "";
            //mReferenceId = Utility.getReferenceId(mContext);
        }

        if (Utility.networkCheck(mContext)) {

            String url = GlobalUrl.CART_LIST_URL + "?app_id=" + GlobalValues.APP_ID +
                    "&customer_id=" + mCustomerId +
                    "&reference_id=" + "" +
                    "&availability_id=" + GlobalValues.CURRENT_AVAILABLITY_ID;

            if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
                new CartListTask().execute(url);
            }

        } else {
            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }
    }

    private void getActiveCount() {

        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
            //Promotion count and Reawrd points
            Map<String, String> mapData = new HashMap<>();
            mapData.put("app_id", GlobalValues.APP_ID);

            if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
                mapData.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));

            } else {
                mapData.put("customer_id", Utility.getReferenceId(mContext));
            }

            JSONArray jsonArray = new JSONArray();
            jsonArray.put("order");
            jsonArray.put("promotionwithoutuqc");
            jsonArray.put("reward_monthly");
            jsonArray.put("order_all");
            jsonArray.put("notify");
            jsonArray.put("reward");

            mapData.put("act_arr", jsonArray.toString());

            String url = GlobalUrl.ACTIVE_COUNT_URL + "?app_id=" + GlobalValues.APP_ID + "&customer_id=" +
                    Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID) +
                    "&act_arr=" + jsonArray.toString();

            new ActivCountTask(mapData).execute(url);

        }
    }

    private class CartListTask extends AsyncTask<String, Void, String> {

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

                    if (jsonObject.has("result_set")) {
                        JSONObject jsonContent = jsonObject.getJSONObject("result_set");

                        JSONObject jsonCartDetails = jsonContent.getJSONObject("cart_details");


                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_COUNT,
                                jsonCartDetails.optString("cart_total_items"));

                        if (Integer.valueOf(jsonCartDetails.optString("cart_total_items")) > 0) {
                            Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, jsonContent.toString());

                        } else {
                            Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, "");
                        }

                        /*cart_availability_id = jsonCartDetails.optString("cart_availability_id");

                        cart_availability_name = jsonCartDetails.optString("cart_availability_name");
*/

                        //ProductListActivity.isInvalidated = false;

                        invalidateOptionsMenu();

                        JSONArray jsonCartItem = jsonContent.getJSONArray("cart_items");

                        if (jsonCartItem.length() > 0) {

                            for (int c = 0; c < jsonCartItem.length(); c++) {

                                JSONObject jsonItem = null;
                                jsonItem = jsonCartItem.getJSONObject(c);

                                databaseHandler.updateQty(jsonItem.getString("cart_item_product_id"), jsonItem.getString("cart_item_qty"));

                            }
                        }

                    } else {

                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_COUNT, "");
                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, "");
                        Utility.writeToSharedPreference(mContext, GlobalValues.BENTO_CART_COUNT, "");


                        //ProductListActivity.isInvalidated = false;
                        invalidateOptionsMenu();

                        // Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                }

                // slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();


                String id = Utility.getCartAvailabilityId(mContext);


             /*   if (id.length() > 0) {

                    swipedIndex = Utility.getAvailabilityIndex(id);

                    Picasso.with(mContext).load(cardIconsList[swipedIndex]).into(imgCardImage);
                    txtCardName.setText(cardNamesList.get(swipedIndex));

                } else {

                    Picasso.with(mContext).load(cardIconsList[0]).into(imgCardImage);
                    txtCardName.setText(cardNamesList.get(0));
                }*/

              /*
                mCardStack.setVisibleCardNum(swipedIndex);

                int count = 1;

                count = swipedIndex;



                for (int k = 0; k < HomeActivity.cardList.size(); k++) {


                    if (k == 0) {
                        HomeActivity.imgCard1.setImageBitmap(
                                Utility.decodeSampledBitmapFromResource(getResources(),
                                        HomeActivity.cardList.get(count), 110, 170));
                    } else if (k == 1) {
                        HomeActivity.imgCard2.setImageBitmap(
                                Utility.decodeSampledBitmapFromResource(getResources(),
                                        HomeActivity.cardList.get(count), 110, 170));
                    } else if (k == 2) {
                        HomeActivity.imgCard3.setImageBitmap(
                                Utility.decodeSampledBitmapFromResource(getResources(),
                                        HomeActivity.cardList.get(count), 110, 170));
                    }
                    count++;

                    if (count > 3) {
                        count = 0;
                    }
                }
*/

            }
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

                    JSONObject countJson = jsonObject.getJSONObject("result_set");

                    GlobalValues.ORDERCOUNT = countJson.getString("order");
                    GlobalValues.NOTIFYCOUNT = countJson.getString("notify");
                    GlobalValues.PROMOTIONCOUNT = countJson.getString("promotionwithoutuqc");
                    GlobalValues.VOUCHERCOUNT = countJson.optString("vouchers");

                   /* if (GlobalValues.ORDERCOUNT != null && !GlobalValues.ORDERCOUNT.equals("0") && !GlobalValues.ORDERCOUNT.equalsIgnoreCase("")) {
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

                    if (GlobalValues.NOTIFYCOUNT != null && !GlobalValues.NOTIFYCOUNT.equals("0") && !GlobalValues.NOTIFYCOUNT.equalsIgnoreCase("")) {

                        txtNotificationCount.setVisibility(View.VISIBLE);
                        txtNotificationCount.setText(GlobalValues.NOTIFYCOUNT);
                    } else {
                        txtNotificationCount.setVisibility(View.GONE);
                    }*/

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
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(30000);
        mLocationRequest.setFastestInterval(30000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {


            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);

            if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {


                getDeviceLocation();

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                    enableLocation();
                }
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);


        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        configureCameraIdle(latitude, longtitude, "");
        if (mMap != null) {
            mMap.setOnCameraIdleListener(onCameraIdleListener);
        }
    }

    private void enableLocation() {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);
        PendingResult result =
                LocationServices.SettingsApi
                        .checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback() {

            @Override
            public void onResult(@NonNull Result result) {
                final Status status = result.getStatus();
                 switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied.
                        // You can initialize location requests here.
                        int permissionLocation = ContextCompat
                                .checkSelfPermission(mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION);
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied.
                        // But could be fixed by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            // Ask to turn on GPS automatically
                            if (mContext != null) {
                                status.startResolutionForResult(ChooseOutletActivity.this,
                                        102);
                            }
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        //finish();
                        break;
                }
            }
        });
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102) {
            if (resultCode == RESULT_OK) {


                   /* LocationServices.FusedLocationApi.requestLocationUpdates(
                            mGoogleApiClient, mLocationRequest, new LocationListener() {
                                @Override
                                public void onLocationChanged(Location location) {
                                    if (location != null) {
                                        latitude = location.getLatitude();
                                        longitude = location.getLongitude();

//                                startService(new Intent(HomeActivity.this, DriverLocationUpdate.class));

                                    } else {


                                    }
                                }
                            });*/
                configureCameraIdle(latitude, longtitude, "");
            }
        }
    }

    private void configureCameraIdle(final double latitude, final double longitude, String searchedLocation) {

        if (Utility.networkCheck(mContext)) {
            String url = GlobalUrl.COMPASSOUTLET_URL + "?app_id=" + GlobalValues.APP_ID + "&availability_id=" + GlobalValues.TAKEAWAY_ID + "&latitude=" + latitude + "&longitude=" + longitude;

            new OutletTask().execute(url);

        } else {
            Toast.makeText(mContext, "Please check your interner connection.", Toast.LENGTH_SHORT).show();
        }

        Geocoder geocoder = new Geocoder(mContext);
        List<Address> addressList = null;
        try {
            if (!(searchedLocation.equalsIgnoreCase("searchedLocation"))) {


                addressList = geocoder.getFromLocation(latitude, longitude, 1);
                if (null != addressList && addressList.size() > 0) {
                    if (addressList.get(0).getLocality() != null) {
                        txt_selectoutlet.setText(addressList.get(0).getLocality());
                    }
                    if (addressList.get(0).getThoroughfare() != null) {
                        txt_selectoutlet.setText(addressList.get(0).getThoroughfare());
                    }
                    if (addressList.get(0).getCountryName() != null) {
                        txt_selectoutlet.setText(txt_selectoutlet.getText().toString() + ", " + addressList.get(0).getCountryName());
                    }

                    if (addressList.get(0).getAddressLine(0) != null) {
                        txt_selectoutlet.setText(addressList.get(0).getAddressLine(0));
                    }


                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkLocationPermission() {

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                requestPermissions(

                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
             } else {
                requestPermissions(
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        101);
             }
        } else {
         }
    }

    private void isLocationEnabled() {
         if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext, R.style.AlertDialogCustom);
            alertDialog.setTitle("Enable Location");
            alertDialog.setMessage("Your locations setting is not enabled. Please enabled it in settings menu.");
            alertDialog.setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, 100);
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = alertDialog.create();
            alert.show();
        } else {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (mGoogleApiClient == null) {
                        buildGoogleApiClient();

                    }
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        enableLocation();
                    } else {
                        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                                    mLocationRequest, this);
                            enableLocation();
                        }

                    }


                    //enableLocation();
                    if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    Activity#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for Activity#requestPermissions for more details.
                        return;
                    }
                    if (mMap != null) {
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                }
            }
        }
    }

    private void getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext);
        try {
            Task locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(ChooseOutletActivity.this, new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Location mLastKnownLocation = (Location) task.getResult();
                        try {
                            latitude = mLastKnownLocation.getLatitude();
                            longtitude = mLastKnownLocation.getLongitude();
                        } catch (Exception e) {
                            Toast.makeText(mContext, "Current location not found", Toast.LENGTH_SHORT).show();
                            latitude = 0;
                            longtitude = 0;
                        }
                         configureCameraIdle(latitude, longtitude, "");

                        //LatLng latLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                    }
                }
            });

        } catch (SecurityException e) {
         }
    }

    private class GetLocation extends AsyncTask<String, Void, String> {
        Map<String, String> mapParams;
        private ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        public GetLocation(Map<String, String> mapPrams) {
            this.mapParams = mapPrams;
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
                    JSONArray jsonArray = jsonObject.getJSONArray("predictions");
                    final List<PredictionsItem> predictionsItemArrayList = new ArrayList<>();

                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonPlaces = jsonArray.getJSONObject(i);
                            PredictionsItem predictionsItem = new PredictionsItem();
                            predictionsItem.setDescription(jsonPlaces.getString("description"));
                            predictionsItemArrayList.add(predictionsItem);
                        }

                        PlacesAdapter placesAdapter = new PlacesAdapter(mContext, predictionsItemArrayList);
                        recyclerViewAutoPlaces.setLayoutManager(new LinearLayoutManager(mContext));

                        recyclerViewAutoPlaces.setVisibility(View.VISIBLE);
                        recyclerViewAutoPlaces.setAdapter(placesAdapter);

                        placesAdapter.setOnItemClick(new IOnItemClick() {
                            @Override
                            public void onItemClick(View v, int position) {
                                isSearch = false;
                                txt_selectoutlet.setEnabled(false);
                                recyclerViewAutoPlaces.setVisibility(View.GONE);
                                txt_selectoutlet.setText(predictionsItemArrayList.get(position).getDescription());
                                new GetlatlngTask().execute("https://maps.google.com/maps/api/geocode/json?address=" + predictionsItemArrayList.get(position).getDescription() + "&sensor=false" +
                                        "&key=" + "AIzaSyB6ACp90IJp4SbAnOWYlXnM9qkVMH8ii1M");
//                                        "&key=" + "AIzaSyDf3xZF2g6qV_udmlgkOzZR4ydD_CrtoRc");//test
                            }
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }
    }

    private class GetlatlngTask extends AsyncTask<String, Void, String> {
        Map<String, String> mapParams;
        private ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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

                double lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location")
                        .getDouble("lng");

                double lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location")
                        .getDouble("lat");



                if (jsonObject.getString("status").equals("OK")) {
                    configureCameraIdle(lat, lng, "searchedLocation");
                } else {
                    Toast.makeText(mContext, jsonObject.getString("status"), Toast.LENGTH_SHORT).show();
                    configureCameraIdle(0, 0, "searchedLocation");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
            }
        }
    }

    public static boolean checkPermissionLocation(Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    showDialog("Location", context,
                            Manifest.permission.ACCESS_FINE_LOCATION);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    100);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    private boolean isLocationEnable() {
         if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext, R.style.AlertDialogCustom);
            alertDialog.setTitle("Enable Location");
            alertDialog.setMessage("Your locations setting is not enabled. Please enabled it in settings menu.");
            alertDialog.setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, 100);
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = alertDialog.create();
            alert.show();
            return false;
        } else {
            return true;
        }
    }

    public static void showDialog(final String msg, final Context context,
                                  final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{permission},
                                100);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    private boolean isCustomerLoggedIn() {

        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utility.writeToSharedPreference(mContext, "IS_ACTIVE", "0");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE", "1");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utility.writeToSharedPreference(mContext, "IS_ACTIVE", "1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE", "1");
        }

    }

    public void clickContinue(int position) {
        Utility.writeToSharedPreference(mContext, GlobalValues.OUTLET_ID, outletList.get(position).getOaOutletId());
        Utility.writeToSharedPreference(mContext, GlobalValues.OUTLET_NAME, outletList.get(position).getOutletName());
        Utility.writeToSharedPreference(mContext, GlobalValues.CURRENT_OUTLET_ADDRESS, outletList.get(position).getOutletAddressLine1());
        Utility.writeToSharedPreference(mContext, GlobalValues.OUTLET_PINCODE, outletList.get(position).getOutletPostalCode());
        Utility.writeToSharedPreference(mContext, GlobalValues.AVALABILITY_ID, GlobalValues.TAKEAWAY_ID);

        Utility.writeToSharedPreference(mContext, GlobalValues.OUTLET_DELIVERY_TAT, outletList.get(position).getOutletDeliveryTat());
        Utility.writeToSharedPreference(mContext, GlobalValues.OUTLET_PICKUP_TAT, outletList.get(position).getOutletPickupTat());
        Utility.writeToSharedPreference(mContext, GlobalValues.OUTLET_DELIVERY_TIMING, outletList.get(position).getOutletDeliveryTiming());

        GlobalValues.CURRENT_OUTLET_ID = Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID);

        clearCart();
        CheckFacourites();
        invalidateOptionsMenu();
        getCartCount();
        getActiveCount();
//        Intent intent = new Intent(mContext, SubCategoryActivity.class);
        Intent intent = new Intent(mContext, MenuCategoryActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {

        if (isCustomerLoggedIn()) {
            if (fromLogin) {
//                Intent intent = new Intent(mContext, LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                finish();
                if (!secondBackPress) {
                    secondBackPress = true;
                    Toast.makeText(ChooseOutletActivity.this, "Press back again to close app", Toast.LENGTH_SHORT).show();
                } else {
                    finishAffinity();
                }
            } else {
                String strAvailability = null;
                if (getIntent().getExtras() != null) {
                    strAvailability = getIntent().getStringExtra("availability");
                }
                if (strAvailability == null && !secondBackPress) {
                    secondBackPress = true;
                    Toast.makeText(ChooseOutletActivity.this, "Press back again to close app", Toast.LENGTH_SHORT).show();
                } else if (secondBackPress) {
                    finishAffinity();
                } else {
                    super.onBackPressed();
//                    finishAffinity();
                }
            }
        } else {

            Intent intent = new Intent(mContext, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }


    }
}
