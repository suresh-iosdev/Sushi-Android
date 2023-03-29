package com.app.sushi.tei.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.favourite.Favouriteitems;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.favouriteAdapter.FavouriteAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class FavouriteFragment extends Fragment {



    public FavouriteFragment()
    {
        // Required empty public constructor
    }


    private String mCustomerId = "", mReferenceId = "", mProductId = "", mProductQuantity = "1";

    private Context mContext;


    ArrayList<Favouriteitems> favouriteitemsArrayList;

    public static RecyclerView recyclerviewfavourite;

    public static RecyclerView.LayoutManager layoutManager;

    public static TextView txtEmpty;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_favourite, container, false);



        initview(view);



        return view;
    }

    private void initview(View view)
    {
        mContext = getActivity();


        recyclerviewfavourite=(RecyclerView)view.findViewById(R.id.recyclerviewfavourite);

        txtEmpty=(TextView)view.findViewById(R.id.txtEmpty);





        CallMethods();


    }

    public void CallMethods()
    {


        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

            mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
            mReferenceId = "";

        }
        else
        {
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

        if (Utility.networkCheck(mContext))
        {


            String app_id="?app_id="+ GlobalValues.APP_ID +"&customer_id="+mCustomerId;

            String availability_id="&availability_id="+ GlobalValues.CURRENT_AVAILABLITY_ID;

            GlobalValues.CURRENT_OUTLET_ID = Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID);


            String outlet_id="&outlet_id="+ GlobalValues.CURRENT_OUTLET_ID;


            String Url = GlobalUrl.FavouriteListURL+app_id+availability_id+outlet_id;


            new FavouriteListTask().execute(Url);

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

                String basePath = jsonObject.getJSONObject("common").optString("subcategory_image_url");



                 favouriteitemsArrayList = new ArrayList<>();


                  if(jsonObject.optString("status").equalsIgnoreCase("ok"))
                  {

                      JSONArray jsonArray = jsonObject.getJSONArray("result_set");


                      for(int i=0;i<jsonArray.length();i++)
                      {

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

                          favouriteitemsArrayList.add(favouriteitems);



                      }



                      if(favouriteitemsArrayList.size()>0)
                      {
                          recyclerviewfavourite.setVisibility(View.VISIBLE);
                          txtEmpty.setVisibility(View.GONE);

                          FavouriteAdapter favouriteAdapter = new FavouriteAdapter(mContext, favouriteitemsArrayList);
                          layoutManager = new LinearLayoutManager(mContext);
                          recyclerviewfavourite.setLayoutManager(layoutManager);
                          recyclerviewfavourite.setAdapter(favouriteAdapter);
                          recyclerviewfavourite.setItemAnimator(new DefaultItemAnimator());





                      }
                      else
                      {



                          recyclerviewfavourite.setVisibility(View.GONE);
                          txtEmpty.setVisibility(View.VISIBLE);
                      }




                  }
                  else
                  {

                      recyclerviewfavourite.setVisibility(View.GONE);
                      txtEmpty.setVisibility(View.VISIBLE);


                  }

                progressDialog.dismiss();






            } catch (Exception e) {
                e.printStackTrace();

                recyclerviewfavourite.setVisibility(View.GONE);
                txtEmpty.setVisibility(View.VISIBLE);
                progressDialog.dismiss();

            }

        }
    }


    public  void RefreshRemoved()
    {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();

    }

}
