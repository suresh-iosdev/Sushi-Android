package com.app.sushi.tei.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;

import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.dialog.CheckOutMessageDialog;

public class MemberBenefitActivity extends AppCompatActivity {


    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_member_benifit);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mContext = MemberBenefitActivity.this;
        TextView str_member_benefits = (TextView) findViewById(R.id.str_member_benefits);
        TextView str_rebates = (TextView) findViewById(R.id.str_rebates);
        TextView str_rewards = (TextView) findViewById(R.id.str_rewards);
        TextView str_bonus = (TextView) findViewById(R.id.str_bonus);
        LinearLayout toolbarBack = findViewById(R.id.toolbarBack);
        ImageView logo = findViewById(R.id.toolbartxtTitle);

        RelativeLayout layoutSearch = findViewById(R.id.layoutSearch);
        RelativeLayout layoutCart = findViewById(R.id.layoutCart);
        TextView txtCartCount = (TextView) findViewById(R.id.txtCartCount);
        layoutSearch.setVisibility(View.VISIBLE);
        layoutCart.setVisibility(View.VISIBLE);

        String cartCount = Utility.readFromSharedPreference(MemberBenefitActivity.this, GlobalValues.CART_COUNT);
        if (!cartCount.equalsIgnoreCase("") && !cartCount.isEmpty()) {

            txtCartCount.setVisibility(View.VISIBLE);
            txtCartCount.setText(cartCount);

        } else {
            txtCartCount.setVisibility(View.GONE);
        }


        layoutCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String MinQual = Utility.readFromSharedPreference(mContext, GlobalValues.CART_COUNT);
                    if (!MinQual.equals("")) {
                        if (Integer.parseInt(MinQual) >= 1) {
                            String message = "Do you want to add more items?";
                            new CheckOutMessageDialog(mContext, message, new CheckOutMessageDialog.OnSlectedMethod() {
                                @Override
                                public void selectedAction(String action) {
                                    if (action.equalsIgnoreCase("YES")) {
                                    } else {
//                                                            Intent intent = new Intent(mContext, CartActivity.class);
                                        Intent intent = new Intent(mContext, OrderSummaryActivity.class);
                                        mContext.startActivity(intent);
                                    }
                                }
                            });
                        }
                    } else {
                        Toast.makeText(mContext, "Cart is empty. Go to ‘Order Now’ to add products!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        layoutSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID).length() > 0) {
                    Intent intent = new Intent(mContext, SearchActivity.class);
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, "Please select your outlet!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        String memberText = "Sign up for S$68 and <font color='#015B26'>receive ST $80</font> for immediate use.";
        String rebatesText = "Enjoy a <font color='#015B26'>10% rebate</font> on your next dine-in bill, no minimum spending required. Redeemable on your next visit.";
        String rewardsText = "Simply quote your contact number to earn <font color='#015B26'>rebates</font>, no physical card needed.";

        String birthdayText = "Enjoy <font color='#015B26'>20% rebate</font> and unlimited visits on your birthday month.";
        String exclusivePrivilegesText = "Be the first to know of exclusive deals, and receive invites to special events.";
        String exclusiveMerchandiseText = "Redeem exclusive merchandise with Sushi Tei Dollars (ST$)";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            str_member_benefits.setText(Html.fromHtml(memberText, Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
            str_rebates.setText(Html.fromHtml(rebatesText, Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
            str_rewards.setText(Html.fromHtml(rewardsText, Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
            str_bonus.setText(Html.fromHtml(birthdayText, Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
        } else {
            str_member_benefits.setText(Html.fromHtml(memberText), TextView.BufferType.SPANNABLE);
            str_rebates.setText(Html.fromHtml(rebatesText), TextView.BufferType.SPANNABLE);
            str_rewards.setText(Html.fromHtml(rewardsText), TextView.BufferType.SPANNABLE);
            str_bonus.setText(Html.fromHtml(birthdayText), TextView.BufferType.SPANNABLE);
        }

     //   logo.setImageResource(R.drawable.sushi_good_deal_logo);
        logo.setImageResource(R.drawable.sushi_tei_header_white);
        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}
