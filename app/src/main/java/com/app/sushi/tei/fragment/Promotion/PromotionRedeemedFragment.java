package com.app.sushi.tei.fragment.Promotion;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.Promotion.PromotionRedeemed;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.Promotion.PromotionRedeemRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PromotionRedeemedFragment extends Fragment {

    private Activity mContext;
    RecyclerView recyclerviewPromotionRedeem;

    TextView txtEmpty;

    ArrayList<PromotionRedeemed> promotionRedeemedmodelArrayList;
    private PromotionRedeemed promotionRedeemedmodel;
    private PromotionRedeemRecyclerAdapter redeemRecyclerAdapter;

    public PromotionRedeemedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_promotion_redeemed, container, false);


        recyclerviewPromotionRedeem = (RecyclerView) view.findViewById(R.id.recyclerviewPromotionRedeem);
        txtEmpty = (TextView) view.findViewById(R.id.txtEmpty);


        return view;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {

        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (networkCheck()) {

            new PromotionRedeemedTask().execute(GlobalUrl.PROMOTION_REDEEM_URL + "?status=A&app_id=" + GlobalValues.APP_ID
                    + "&customer_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));

        } else {

            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();

        }
    }



    public boolean networkCheck() {

        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;

    }

    private class PromotionRedeemedTask extends AsyncTask<String, String, String> {

        String response, status, message, commonImageurl = "";
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                response = WebserviceAssessor.getData(params[0]);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();

            if (response != null) {

                JSONObject responseJSONObject = null;
                try {

                    responseJSONObject = new JSONObject(response);


                    status = responseJSONObject.getString("status");


                    if (status.equals("ok")) {
                        //Success

                        JSONObject resultjsonobject = responseJSONObject.getJSONObject("result_set");

                        JSONObject resultcommon = responseJSONObject.getJSONObject("common");


                        String CommonImageUrl = resultcommon.getString("promo_image_source");

                        JSONArray jsonArraymycat = resultjsonobject.getJSONArray("promo_history");

                        promotionRedeemedmodelArrayList = new ArrayList<>();

                        if (jsonArraymycat.length() > 0) {


                            for (int j = 0; j < jsonArraymycat.length(); j++) {

                                JSONObject jsonObjectcat = jsonArraymycat.getJSONObject(j);

                                promotionRedeemedmodel = new PromotionRedeemed();


                                promotionRedeemedmodel.setPromotionCode(jsonObjectcat.getString("promo_code"));

                                promotionRedeemedmodel.setPromotionId(jsonObjectcat.getString("promotion_id"));
                                promotionRedeemedmodel.setPromotionImage(CommonImageUrl + "/" + jsonObjectcat.getString("promotion_image"));
                                promotionRedeemedmodel.setPromotionTitle(jsonObjectcat.getString("promotion_title"));
                                promotionRedeemedmodel.setPromotionPercentage(jsonObjectcat.getString("promotion_percentage"));
                                promotionRedeemedmodel.setPromotDiscription(jsonObjectcat.getString("promo_desc"));
                                promotionRedeemedmodel.setPromoDaysleft(jsonObjectcat.optString("promo_days_left"));
                                promotionRedeemedmodel.setPromoMaxAmt(jsonObjectcat.getString("promotion_max_amt"));
                                promotionRedeemedmodel.setPromoType(jsonObjectcat.optString("promotion_type"));
                                promotionRedeemedmodelArrayList.add(promotionRedeemedmodel);


                            }
                        }


                        if (promotionRedeemedmodelArrayList.size() > 0) {

                            redeemRecyclerAdapter = new PromotionRedeemRecyclerAdapter(getActivity(), promotionRedeemedmodelArrayList);
                            //GridLayoutManager promotionGridlay = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                            recyclerviewPromotionRedeem.setLayoutManager(layoutManager);
                            recyclerviewPromotionRedeem.setHasFixedSize(true);
                            recyclerviewPromotionRedeem.setItemAnimator(new DefaultItemAnimator());
                            recyclerviewPromotionRedeem.setAdapter(redeemRecyclerAdapter);
                            recyclerviewPromotionRedeem.setNestedScrollingEnabled(false);

                            recyclerviewPromotionRedeem.setVisibility(View.VISIBLE);
                            txtEmpty.setVisibility(View.GONE);

                        } else {
                            recyclerviewPromotionRedeem.setVisibility(View.GONE);
                            txtEmpty.setVisibility(View.VISIBLE);

                        }


                    } else {

                        message = responseJSONObject.getString("message");
                        //  Toast.makeText(getActivity(), Html.fromHtml(message), Toast.LENGTH_LONG).show();
                        //No need to run cartDetailTask here //hence "else" will enter when there is no item in cart
/*
                        if(promotionmodels.size()>0)
                        {

                            textView_promotion.setVisibility(View.VISIBLE);

                            promotionlist.setVisibility(View.GONE);

                        }
                        else
                        {

                            promotionlist.setVisibility(View.GONE);
                            textView_promotion.setVisibility(View.VISIBLE);

                        }*/

                        recyclerviewPromotionRedeem.setVisibility(View.GONE);
                        txtEmpty.setVisibility(View.VISIBLE);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                   /* if(promotionmodels.size()>0)
                    {

                        textView_promotion.setVisibility(View.VISIBLE);

                        promotionlist.setVisibility(View.GONE);

                    }
                    else
                    {

                        promotionlist.setVisibility(View.GONE);
                        textView_promotion.setVisibility(View.VISIBLE);

                    }*/

                }


            } else {

                /*//    Toast.makeText(getActivity(), "Please check your internet connection.", Toast.LENGTH_LONG).show();
                if(promotionmodels.size()>0)
                {

                    textView_promotion.setVisibility(View.VISIBLE);

                    promotionlist.setVisibility(View.GONE);

                }
                else
                {

                    promotionlist.setVisibility(View.GONE);
                    textView_promotion.setVisibility(View.VISIBLE);

                }*/


            }


        }
    }


}
