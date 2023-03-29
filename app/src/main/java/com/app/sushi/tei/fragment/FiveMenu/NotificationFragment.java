package com.app.sushi.tei.fragment.FiveMenu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.app.sushi.tei.Interface.IOnItemClick;
import com.app.sushi.tei.Model.Notification.SpizeNotification;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.activity.FiveMenuActivityNew;
import com.app.sushi.tei.activity.LandingActivity;
import com.app.sushi.tei.activity.NotificationActivity;
import com.app.sushi.tei.adapter.Notification.NotificationRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class NotificationFragment extends Fragment {


    private Context mContext;
    private View mView;
    private RecyclerView recyclerviewNotification;
    private NotificationRecyclerAdapter notificationRecyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<SpizeNotification> spizeNotificationList;
    private TextView txtEmpty, readall;
    NotificationActivity fiveMenuActivity;
    private int index = 0;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        fiveMenuActivity = (NotificationActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_notification, container, false);


        recyclerviewNotification = (RecyclerView) mView.findViewById(R.id.recyclerviewNotification);
        txtEmpty = (TextView) mView.findViewById(R.id.txtEmpty);
        readall = (TextView) mView.findViewById(R.id.readall);


        layoutManager = new LinearLayoutManager(mContext);
        recyclerviewNotification.setLayoutManager(layoutManager);

        readall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.networkCheck(getActivity())) {

                    new ReadallTask().execute();
                } else {
                    Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            if (getActivity() != null) {
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getNotificationListService();
    }

    private void getNotificationListService() {


        if (Utility.networkCheck(mContext)) {

            String url = GlobalUrl.NOTIFICATION_LIST_URL + "?app_id=" + GlobalValues.APP_ID
                    + "&customer_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);

            new NotificationTask().execute(url);


        } else {
            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }
    }

    class NotificationTask extends AsyncTask<String, Void, String> {

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


                    JSONObject jsonCommon = jsonObject.getJSONObject("common");

                    String path = jsonCommon.optString("activities_image_source");

                    JSONObject jsonCount = jsonObject.getJSONObject("count");


                    GlobalValues.ORDERCOUNT = jsonCount.getString("order");
                    GlobalValues.NOTIFYCOUNT = jsonCount.getString("notify");
//TODO
               /*     if (GlobalValues.ORDERCOUNT != null && !GlobalValues.ORDERCOUNT.equals("0") &&
                            !GlobalValues.ORDERCOUNT.equalsIgnoreCase("")) {
                        txtOrderCount.setVisibility(View.VISIBLE);
                        txtOrderCount.setText(GlobalValues.ORDERCOUNT);
                    } else {
                        txtOrderCount.setVisibility(View.GONE);
                    }

                    if (GlobalValues.PROMOTIONCOUNT != null && !GlobalValues.PROMOTIONCOUNT.equals("0")
                            && !GlobalValues.PROMOTIONCOUNT.equalsIgnoreCase("")) {

                        txtPromotionCount.setVisibility(View.VISIBLE);
                        txtPromotionCount.setText(GlobalValues.PROMOTIONCOUNT);

                    } else {
                        txtPromotionCount.setVisibility(View.GONE);
                    }

                    if (GlobalValues.NOTIFYCOUNT != null && !GlobalValues.NOTIFYCOUNT.equals("0")
                            && !GlobalValues.NOTIFYCOUNT.equalsIgnoreCase("")) {

                        txtNotificationCount.setVisibility(View.VISIBLE);
                        txtNotificationCount.setText(GlobalValues.NOTIFYCOUNT);
                    } else {
                        txtNotificationCount.setVisibility(View.GONE);
                    }
*/
                    JSONArray jsonResult = jsonObject.getJSONArray("result_set");

                    spizeNotificationList = new ArrayList<>();

                    if (jsonResult.length() > 0) {
                        for (int i = 0; i < jsonResult.length(); i++) {
                            JSONObject object = jsonResult.getJSONObject(i);

                            SpizeNotification spizeNotification = new SpizeNotification();

                            spizeNotification.setmBasePath(path);
                            spizeNotification.setmId(object.getString("act_id"));
                            spizeNotification.setmCustomerId(object.getString("act_customer_id"));
                            spizeNotification.setmShortContent(object.getString("act_short_content"));
                            spizeNotification.setmTitle(object.getString("act_title"));
                            spizeNotification.setmContent(object.getString("act_content"));
                            spizeNotification.setmCreatedOn(object.getString("act_created_on"));
                            spizeNotification.setmImage(object.getString("act_image"));
                            spizeNotification.setmReadStatus(Integer.parseInt(object.optString("act_read_status")));
                            spizeNotification.setmRedirect(object.optString("act_redirect"));

                            spizeNotificationList.add(spizeNotification);

                        }

                        txtEmpty.setVisibility(View.GONE);
                        recyclerviewNotification.setVisibility(View.VISIBLE);

                        notificationRecyclerAdapter = new NotificationRecyclerAdapter(mContext, spizeNotificationList);

                        recyclerviewNotification.setAdapter(notificationRecyclerAdapter);
                        recyclerviewNotification.setItemAnimator(new DefaultItemAnimator());
//                        recyclerviewNotification.setNestedScrollingEnabled(false);

                        notificationRecyclerAdapter.setOnClick(new IOnItemClick() {
                            @Override
                            public void onItemClick(View v, int position) {

                                if (spizeNotificationList.get(position).getmReadStatus() == 1) {

                                    if (spizeNotificationList.get(position).getmRedirect().equalsIgnoreCase("Order")) {
                                        index = 1;
                                        openFiveMenuActivity(index);
                                    } else if (spizeNotificationList.get(position).getmRedirect().equalsIgnoreCase("Reward")) {
                                        index = 2;
                                        openFiveMenuActivity(index);
                                    } else if (spizeNotificationList.get(position).getmRedirect().equalsIgnoreCase("Vouchers")) {
                                        index = 3;
                                        openFiveMenuActivity(index);
                                    } else if (spizeNotificationList.get(position).getmRedirect().equalsIgnoreCase("Promotion")) {
                                        index = 4;
                                        openFiveMenuActivity(index);
                                    } else if (spizeNotificationList.get(position).getmRedirect().equalsIgnoreCase("Home")) {
                                        Intent intent = new Intent(mContext, LandingActivity.class);
                                        startActivity(intent);
                                    }

                                } else {
                                    if (spizeNotificationList.get(position).getmRedirect().equalsIgnoreCase("Order")) {
                                        index = 1;
                                        openFiveMenuActivity(index);
                                    } else if (spizeNotificationList.get(position).getmRedirect().equalsIgnoreCase("Reward")) {
                                        index = 2;
                                        openFiveMenuActivity(index);
                                    } else if (spizeNotificationList.get(position).getmRedirect().equalsIgnoreCase("Vouchers")) {
                                        index = 3;
                                        openFiveMenuActivity(index);
                                    } else if (spizeNotificationList.get(position).getmRedirect().equalsIgnoreCase("Promotion")) {
                                        index = 4;
                                        openFiveMenuActivity(index);
                                    } else if (spizeNotificationList.get(position).getmRedirect().equalsIgnoreCase("Home")) {
                                        Intent intent = new Intent(mContext, LandingActivity.class);
                                        startActivity(intent);
                                    }
                                    if (Utility.networkCheck(mContext)) {
                                        String url = GlobalUrl.NOTIFICATION_READ_URL + "?app_id=" + GlobalValues.APP_ID
                                                + "&customer_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID) +
                                                "&act_id=" + spizeNotificationList.get(position).getmId();
                                        if (spizeNotificationList.get(position).getmReadStatus() == 0) {
                                            new NotificationReadTask().execute(url);
                                        }
                                    }
                                }
                            }
                        });

                    } else {
                        txtEmpty.setVisibility(View.VISIBLE);
                        recyclerviewNotification.setVisibility(View.GONE);
                        readall.setVisibility(View.GONE);
                    }

                } else {
                    txtEmpty.setVisibility(View.VISIBLE);
                    recyclerviewNotification.setVisibility(View.GONE);
                    readall.setVisibility(View.GONE);
                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
            }

        }
    }

    private class NotificationReadTask extends AsyncTask<String, Void, String> {

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
                    getNotificationListService();
                    progressDialog.dismiss();

                    /*Intent intent = new Intent(mContext, SubCategoryActivity.class);
                    startActivity(intent);*/

                    //openFiveMenuActivity(index);

                } else {
                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }

        }
    }

    private void openFiveMenuActivity(int position) {
        Intent intent = new Intent(mContext, FiveMenuActivityNew.class);
        intent.putExtra("position", position);
        intent.putExtra(GlobalValues.FROM_KEY, GlobalValues.NOT_FROM_CHECKOUT);
        intent.putExtra("stackBackup", true);
        startActivity(intent);
    }


    private class ReadallTask extends AsyncTask<String, String, String> {

        String response, status, message;

        ProgressDialog prograssdialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            prograssdialog = new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_LIGHT);
            prograssdialog.setMessage(getResources().getString(R.string.loading_progress));
            prograssdialog.setCancelable(false);
            prograssdialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            try {


                String app_id = "?app_id=" + GlobalValues.APP_ID;
                String Customerid = "&customer_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);


                String Readall = GlobalUrl.ReadAllNotificationUrl + app_id + Customerid;

                response = ActiveRun(Readall);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            prograssdialog.dismiss();


            if (response != null) {

                JSONObject responseJSONObject = null;


                try {
                    responseJSONObject = new JSONObject(response);
                    status = responseJSONObject.getString("status");
                    if (status.equals("ok")) {

                        Toast.makeText(getActivity(), "Great ! You have read all your notifications !", Toast.LENGTH_LONG).show();

                        try {
/*
                           if (networkCheck()) {
                                new MYActiveTask().execute();
                            } else {
                                Toast.makeText(NotificationActivity.this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
                            }*/
                            getNotificationListService();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getActivity(), "You don't have any unread notifications!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "You don't have any read notifications!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public String ActiveRun(String url) throws IOException {
        System.out.println("URL" + url);
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = MyActiveRun.newCall(request).execute();
        // System.out.println("Response"+response.body().string());
        return response.body().string();
    }

    OkHttpClient MyActiveRun = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .build();


}

