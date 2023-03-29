package com.app.sushi.tei.activity;

import static com.app.sushi.tei.GlobalMembers.GlobalValues.CURRENT_AVAILABLITY_ID;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.Landing.ResultSetItem;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.exoplayer2.SimpleExoPlayer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class LandingActivity extends AppCompatActivity {

    private LinearLayout llyViewMenu, toolbarBack;
    private Toolbar toolBar;
    private ImageView img_orderNow, img_myAcc, img_promotion, img_about;
    private Context mContext;
    private Intent intent;
    private String commonURL;
    private ArrayList<ResultSetItem> landinglist;
    private VideoView vdo_orderNow, vdo_myAcc, vdo_promotion, vdo_about;
    private TextView txt_OrderNow, txt_myAcc, txt_promotion, txt_about;
    private LinearLayout lly_OrderNow, lly_aboutUs;
    private RelativeLayout rlyAcc, rly_promotions;
    private SimpleExoPlayer player;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        toolBar = findViewById(R.id.toolBar);
        toolbarBack = toolBar.findViewById(R.id.toolbarBack);
        llyViewMenu = findViewById(R.id.llyViewMenu);
        img_orderNow = findViewById(R.id.img_orderNow);
        img_myAcc = findViewById(R.id.img_myAcc);
        img_promotion = findViewById(R.id.img_promotion);
        img_about = findViewById(R.id.img_about);
        vdo_orderNow = findViewById(R.id.vdo_orderNow);
        vdo_myAcc = findViewById(R.id.vdo_myAcc);
        vdo_promotion = findViewById(R.id.vdo_promotion);
        vdo_about = findViewById(R.id.vdo_about);
        txt_OrderNow = findViewById(R.id.txt_OrderNow);
        txt_myAcc = findViewById(R.id.txt_myAcc);
        txt_promotion = findViewById(R.id.txt_promotion);
        txt_about = findViewById(R.id.txt_about);
        lly_OrderNow = findViewById(R.id.lly_OrderNow);
        lly_aboutUs = findViewById(R.id.lly_aboutUs);
        rlyAcc = findViewById(R.id.rlyAcc);
        rly_promotions = findViewById(R.id.rly_promotions);

        mContext = LandingActivity.this;
        toolbarBack.setVisibility(View.GONE);

        if (Utility.networkCheck(mContext)) {
            String app_id = "?app_id=" + GlobalValues.APP_ID;
            String url = GlobalUrl.LANDING_URL + app_id;
            new LandingRequestTask().execute(url);
        } else {
            Toast.makeText(mContext, "You are offline please check your internet connection", Toast.LENGTH_SHORT).show();
        }

        llyViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID).equalsIgnoreCase("")
                        || Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID).equalsIgnoreCase("null")) {
                    intent = new Intent(mContext, ChooseOutletActivity.class);
                    if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
                        intent.putExtra("availability", "delivery");
                    } else {
                        intent.putExtra("availability", "takeaway");
                    }
                    startActivity(intent);
                } else {
                    intent = new Intent(mContext, SubCategoryActivity.class);
                    startActivity(intent);
                }
            }
        });

        lly_OrderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_OrderNow.performClick();
            }
        });

        rlyAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_myAcc.performClick();
            }
        });

        rly_promotions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_promotion.performClick();
            }
        });

        lly_aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_about.performClick();
            }
        });


        txt_OrderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoggedIn()) {
                    if (Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID).equalsIgnoreCase("")
                            || Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID).equalsIgnoreCase("null")) {
                        intent = new Intent(mContext, ChooseOutletActivity.class);
                        if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
                            intent.putExtra("availability", "delivery");
                        } else {
                            intent.putExtra("availability", "takeaway");
                        }
                        startActivity(intent);
                        return;
                    }
                    if (landinglist != null) {
                        if (landinglist.get(0).getLandingType().equalsIgnoreCase("1")) {
                            if (Patterns.WEB_URL.matcher(landinglist.get(0).getLandingSlug()).matches()) {
                                intent = new Intent(mContext, CmsActivity.class);
                                intent.putExtra("TITLE", landinglist.get(0).getLandingTitle());
                                intent.putExtra("SEARCH_KEY", landinglist.get(0).getLandingSlug());
                                startActivity(intent);
                            }
                        } else if (landinglist.get(0).getLandingType().equalsIgnoreCase("2")) {
                            final Dialog dialog = new Dialog(mContext, R.style.custom_dialog_theme);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.dialog_landing_description);
                            dialog.show();

                            TextView txt_title = dialog.findViewById(R.id.txt_title);
                            TextView txt_description = dialog.findViewById(R.id.txt_description);
                            ImageView img_exit = dialog.findViewById(R.id.img_exit);

                            txt_title.setText(landinglist.get(0).getLandingTitle());
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                txt_description.setText(Html.fromHtml(landinglist.get(0).getLandingDescription(), Html.FROM_HTML_MODE_COMPACT));
                            } else {
                                txt_description.setText(Html.fromHtml(landinglist.get(0).getLandingDescription()));
                            }

                            img_exit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                        } else if (landinglist.get(0).getLandingType().equalsIgnoreCase("3")) {
                            if (Patterns.WEB_URL.matcher(landinglist.get(0).getLandingSlug()).matches()) {
                                intent = new Intent(mContext, CmsActivity.class);
                                intent.putExtra("TITLE", landinglist.get(0).getLandingTitle());
                                intent.putExtra("SEARCH_KEY", landinglist.get(0).getLandingSlug());
                                startActivity(intent);
                            } else {
                                intent = new Intent(mContext, SubCategoryActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                } else {
                    intent = new Intent(mContext, LoginActivity.class);
                    //intent.putExtra("disableLoginLater", true);
                    startActivity(intent);
                }
            }
        });

        txt_myAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoggedIn()) {
                    if (Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID).equalsIgnoreCase("")
                            || Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID).equalsIgnoreCase("null")) {
                        intent = new Intent(mContext, ChooseOutletActivity.class);
                        if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
                            intent.putExtra("availability", "delivery");
                        } else {
                            intent.putExtra("availability", "takeaway");
                        }
                        startActivity(intent);
                        return;
                    }
                    if (landinglist.get(1).getLandingType().equalsIgnoreCase("1")) {

                    } else if (landinglist.get(1).getLandingType().equalsIgnoreCase("2")) {
                        final Dialog dialog = new Dialog(mContext, R.style.custom_dialog_theme);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_landing_description);
                        dialog.setCanceledOnTouchOutside(false);
                        if (!dialog.isShowing())
                            dialog.show();

                        TextView txt_title = dialog.findViewById(R.id.txt_title);
                        TextView txt_description = dialog.findViewById(R.id.txt_description);
                        ImageView img_exit = dialog.findViewById(R.id.img_exit);

                        txt_title.setText(landinglist.get(1).getLandingTitle());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            txt_description.setText(Html.fromHtml(landinglist.get(1).getLandingDescription(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            txt_description.setText(Html.fromHtml(landinglist.get(1).getLandingDescription()));
                        }
                        img_exit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    } else if (landinglist.get(1).getLandingType().equalsIgnoreCase("3")) {
                        if (landinglist.get(1).getLandingSlug().equalsIgnoreCase("notification")) {
                            intent = new Intent(mContext, NotificationActivity.class);
                            intent.putExtra("disableLoginLater", true);
                            startActivity(intent);
                        } else if (landinglist.get(1).getLandingSlug().equalsIgnoreCase("account")) {
                            intent = new Intent(mContext, FiveMenuActivityNew.class);
                            intent.putExtra("position", 0);
                            intent.putExtra(GlobalValues.FROM_KEY, GlobalValues.NOT_FROM_CHECKOUT);
                            startActivity(intent);
                        } else if (landinglist.get(1).getLandingSlug().equalsIgnoreCase("order")) {
                            intent = new Intent(mContext, FiveMenuActivityNew.class);
                            intent.putExtra("position", 1);
                            intent.putExtra(GlobalValues.FROM_KEY, GlobalValues.NOT_FROM_CHECKOUT);
                            startActivity(intent);
                        } else if (landinglist.get(1).getLandingSlug().equalsIgnoreCase("reward")) {
                            intent = new Intent(mContext, FiveMenuActivityNew.class);
                            intent.putExtra("position", 2);
                            intent.putExtra(GlobalValues.FROM_KEY, GlobalValues.NOT_FROM_CHECKOUT);
                            startActivity(intent);
                        } else if (landinglist.get(1).getLandingSlug().equalsIgnoreCase("vouchers")) {
                            intent = new Intent(mContext, FiveMenuActivityNew.class);
                            intent.putExtra("position", 3);
                            intent.putExtra(GlobalValues.FROM_KEY, GlobalValues.NOT_FROM_CHECKOUT);
                            startActivity(intent);
                        } else if (landinglist.get(1).getLandingSlug().equalsIgnoreCase("promotion")) {
                            intent = new Intent(mContext, FiveMenuActivityNew.class);
                            intent.putExtra("position", 4);
                            intent.putExtra(GlobalValues.FROM_KEY, GlobalValues.NOT_FROM_CHECKOUT);
                            startActivity(intent);
                        } else if (landinglist.get(1).getLandingSlug().equalsIgnoreCase("home")) {
                            intent = new Intent(mContext, SubCategoryActivity.class);
                            startActivity(intent);
                        }
                    }
                } else {
                    intent = new Intent(mContext, LoginActivity.class);
                    intent.putExtra("disableLoginLater", true);
                    startActivity(intent);
                }
            }
        });

        txt_promotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoggedIn()) {
                    if (Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID).equalsIgnoreCase("")
                            || Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID).equalsIgnoreCase("null")) {
                        intent = new Intent(mContext, ChooseOutletActivity.class);
                        if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
                            intent.putExtra("availability", "delivery");
                        } else {
                            intent.putExtra("availability", "takeaway");
                        }
                        startActivity(intent);
                        return;
                    }
                    if (landinglist.get(2).getLandingType().equalsIgnoreCase("1")) {

                    } else if (landinglist.get(2).getLandingType().equalsIgnoreCase("2")) {
                        final Dialog dialog = new Dialog(mContext, R.style.custom_dialog_theme);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_landing_description);
                        dialog.show();

                        TextView txt_title = dialog.findViewById(R.id.txt_title);
                        TextView txt_description = dialog.findViewById(R.id.txt_description);
                        ImageView img_exit = dialog.findViewById(R.id.img_exit);

                        txt_title.setText(landinglist.get(2).getLandingTitle());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            txt_description.setText(Html.fromHtml(landinglist.get(2).getLandingDescription(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            txt_description.setText(Html.fromHtml(landinglist.get(2).getLandingDescription()));
                        }

                        img_exit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    } else if (landinglist.get(2).getLandingType().equalsIgnoreCase("3")) {
                        if (landinglist.get(2).getLandingSlug().equalsIgnoreCase("notification")) {
                            intent = new Intent(mContext, NotificationActivity.class);
                            intent.putExtra("disableLoginLater", true);
                            startActivity(intent);
                        } else if (landinglist.get(2).getLandingSlug().equalsIgnoreCase("account")) {
                            intent = new Intent(mContext, FiveMenuActivityNew.class);
                            intent.putExtra("position", 0);
                            intent.putExtra(GlobalValues.FROM_KEY, GlobalValues.NOT_FROM_CHECKOUT);
                            startActivity(intent);
                        } else if (landinglist.get(2).getLandingSlug().equalsIgnoreCase("order")) {
                            intent = new Intent(mContext, FiveMenuActivityNew.class);
                            intent.putExtra("position", 1);
                            intent.putExtra(GlobalValues.FROM_KEY, GlobalValues.NOT_FROM_CHECKOUT);
                            startActivity(intent);
                        } else if (landinglist.get(2).getLandingSlug().equalsIgnoreCase("reward")) {
                            intent = new Intent(mContext, FiveMenuActivityNew.class);
                            intent.putExtra("position", 2);
                            intent.putExtra(GlobalValues.FROM_KEY, GlobalValues.NOT_FROM_CHECKOUT);
                            startActivity(intent);
                        } else if (landinglist.get(2).getLandingSlug().equalsIgnoreCase("vouchers")) {
                            intent = new Intent(mContext, FiveMenuActivityNew.class);
                            intent.putExtra("position", 3);
                            intent.putExtra(GlobalValues.FROM_KEY, GlobalValues.NOT_FROM_CHECKOUT);
                            startActivity(intent);
                        } else if (landinglist.get(2).getLandingSlug().equalsIgnoreCase("promotion")) {
                            intent = new Intent(mContext, FiveMenuActivityNew.class);
                            intent.putExtra("position", 4);
                            intent.putExtra(GlobalValues.FROM_KEY, GlobalValues.NOT_FROM_CHECKOUT);
                            startActivity(intent);
                        } else if (landinglist.get(2).getLandingSlug().equalsIgnoreCase("home")) {
                            intent = new Intent(mContext, SubCategoryActivity.class);
                            startActivity(intent);
                        }
                    }
                } else {
                    intent = new Intent(mContext, LoginActivity.class);
                    intent.putExtra("disableLoginLater", true);
                    startActivity(intent);
                }
            }
        });

        txt_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoggedIn()) {
                    if (Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID).equalsIgnoreCase("")
                            || Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID).equalsIgnoreCase("null")) {
                        intent = new Intent(mContext, ChooseOutletActivity.class);
                        if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
                            intent.putExtra("availability", "delivery");
                        } else {
                            intent.putExtra("availability", "takeaway");
                        }
                        startActivity(intent);
                        return;
                    }
                    if (landinglist.get(3).getLandingType().equalsIgnoreCase("1")) {
                        intent = new Intent(mContext, CmsActivity.class);
                        intent.putExtra("TITLE", landinglist.get(3).getLandingTitle());
                        intent.putExtra("SEARCH_KEY", landinglist.get(3).getLandingSlug());
                        startActivity(intent);
                    } else if (landinglist.get(3).getLandingType().equalsIgnoreCase("2")) {
                        final Dialog dialog = new Dialog(mContext, R.style.custom_dialog_theme);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_landing_description);
                        dialog.show();

                        TextView txt_title = dialog.findViewById(R.id.txt_title);
                        TextView txt_description = dialog.findViewById(R.id.txt_description);
                        ImageView img_exit = dialog.findViewById(R.id.img_exit);

                        txt_title.setText(landinglist.get(3).getLandingTitle());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            txt_description.setText(Html.fromHtml(landinglist.get(3).getLandingDescription(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            txt_description.setText(Html.fromHtml(landinglist.get(3).getLandingDescription()));
                        }

                        img_exit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    } else if (landinglist.get(3).getLandingType().equalsIgnoreCase("3")) {

                    }
                } else {
                    intent = new Intent(mContext, LoginActivity.class);
                    //intent.putExtra("loginLater", true);
                    startActivity(intent);
                }
            }
        });
    }

    class LandingRequestTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        public LandingRequestTask() {

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
                    JSONObject commonJSON = jsonObject.getJSONObject("common");
                    landinglist = new ArrayList<>();
                    commonURL = commonJSON.getString("image_source");
                    JSONArray resultSetJSONArray = jsonObject.getJSONArray("result_set");
                    /*txt_OrderNow.setVisibility(View.VISIBLE);
                    txt_myAcc.setVisibility(View.VISIBLE);
                    txt_promotion.setVisibility(View.VISIBLE);
                    txt_about.setVisibility(View.VISIBLE);*/
                    if (resultSetJSONArray.length() > 0) {
                        for (int i = 0; i < resultSetJSONArray.length(); i++) {
                            JSONObject landingJSON = resultSetJSONArray.getJSONObject(i);
                            ResultSetItem resultSetItem = new ResultSetItem();
                            resultSetItem.setLandingId(landingJSON.getString("landing_id"));
                            resultSetItem.setLandingTitle(landingJSON.getString("landing_title"));
                            resultSetItem.setLandingType(landingJSON.getString("landing_type"));
                            resultSetItem.setLandingSlug(landingJSON.getString("landing_slug"));
                            resultSetItem.setLandingImage(commonURL + landingJSON.getString("landing_image"));
                            resultSetItem.setLandingSortOrder(landingJSON.getString("landing_sort_order"));
                            resultSetItem.setLandingDescription(landingJSON.getString("landing_description"));
                            resultSetItem.setLandingShortDescription(landingJSON.getString("landing_short_description"));
                            resultSetItem.setType(landingJSON.getString("type"));

                            landinglist.add(resultSetItem);
                        }

                        for (int j = 0; j < resultSetJSONArray.length(); j++) {
                            if (landinglist.get(j).getType().equalsIgnoreCase("image")) {
                                int cornerRadius;
                                if (landinglist.get(j).getLandingImage().contains("gif")) {
                                    cornerRadius = 3;
                                } else {
                                    cornerRadius = 10;
                                }
                                if (j == 0) {
                                    if (landinglist.get(0).getType().equalsIgnoreCase("image")) {
                                        img_orderNow.setVisibility(View.VISIBLE);
                                        vdo_orderNow.setVisibility(View.GONE);
                                        Glide.with(mContext)
                                                .load(landinglist.get(0).getLandingImage())
                                                .apply(MyGlideExtension.roundedCorners(new RequestOptions(), mContext, cornerRadius))
                                                .into(img_orderNow);
                                    } else {
                                        img_orderNow.setVisibility(View.GONE);
                                        vdo_orderNow.setVisibility(View.VISIBLE);
                                        vdo_orderNow.setVideoPath(landinglist.get(0).getLandingImage());
                                        vdo_orderNow.requestFocus();
                                        vdo_orderNow.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                            @Override
                                            public void onPrepared(MediaPlayer mp) {
                                                mp.setLooping(true);
                                            }
                                        });
                                        vdo_orderNow.start();
                                    }
                                }

                                if (j == 1) {
                                    if (landinglist.get(1).getType().equalsIgnoreCase("image")) {
                                        img_myAcc.setVisibility(View.VISIBLE);
                                        vdo_myAcc.setVisibility(View.GONE);
                                        Glide.with(mContext)
                                                .load(landinglist.get(1).getLandingImage())
                                                .apply(MyGlideExtension.roundedCorners(new RequestOptions(), mContext, cornerRadius))
                                                .into(img_myAcc);
                                    } else {
                                        img_myAcc.setVisibility(View.GONE);
                                        vdo_myAcc.setVisibility(View.VISIBLE);
                                        vdo_myAcc.setVideoPath(landinglist.get(1).getLandingImage());
                                        vdo_myAcc.requestFocus();
                                        vdo_myAcc.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                            @Override
                                            public void onPrepared(MediaPlayer mp) {
                                                mp.setLooping(true);
                                            }
                                        });
                                        vdo_myAcc.start();
                                    }
                                }

                                if (j == 2) {
                                    if (landinglist.get(2).getType().equalsIgnoreCase("image")) {
                                        img_promotion.setVisibility(View.VISIBLE);
                                        vdo_promotion.setVisibility(View.GONE);
                                        Glide.with(mContext)
                                                .load(landinglist.get(2).getLandingImage())
                                                .apply(MyGlideExtension.roundedCorners(new RequestOptions(), mContext, cornerRadius))
                                                .into(img_promotion);
                                    } else {
                                        img_promotion.setVisibility(View.GONE);
                                        vdo_promotion.setVisibility(View.VISIBLE);
                                        vdo_promotion.setVideoPath(landinglist.get(2).getLandingImage());
                                        vdo_promotion.requestFocus();
                                        vdo_promotion.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                            @Override
                                            public void onPrepared(MediaPlayer mp) {
                                                mp.setLooping(true);
                                            }
                                        });
                                        vdo_promotion.start();
                                    }
                                }

                                if (j == 3) {
                                    if (landinglist.get(3).getType().equalsIgnoreCase("image")) {
                                        img_about.setVisibility(View.VISIBLE);
                                        vdo_about.setVisibility(View.GONE);
                                        Glide.with(mContext)
                                                .load(landinglist.get(3).getLandingImage())
                                                .apply(MyGlideExtension.roundedCorners(new RequestOptions(), mContext, cornerRadius))
                                                .into(img_about);
                                    } else {
                                        img_about.setVisibility(View.GONE);
                                        vdo_about.setVisibility(View.VISIBLE);
                                        vdo_about.setVideoPath(landinglist.get(3).getLandingImage());
                                        vdo_about.requestFocus();
                                        vdo_about.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                            @Override
                                            public void onPrepared(MediaPlayer mp) {
                                                mp.setLooping(true);
                                            }
                                        });
                                        vdo_about.start();
                                    }
                                }
                            }
                        }
                    } else {
                        Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            } finally {
                progressDialog.dismiss();
            }
        }
    }

    private boolean isLoggedIn() {
        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
            return true;
        } else {
            return false;
        }
    }


}
