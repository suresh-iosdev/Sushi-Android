package com.app.sushi.tei.adapter.Vouchers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.Voucher.VoucherUsedList;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.activity.LoginActivity;
import com.app.sushi.tei.dialog.MessageDialog;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class VoucherUsedAdapter extends RecyclerView.Adapter<VoucherUsedAdapter.VoucherUsedViewHolder> {
    private Context context;
    static OnItemClickListener mItemClickListener;
    private ArrayList<VoucherUsedList> productdatas;
    private Dialog dialog;
    private String imageUrl;

    public VoucherUsedAdapter(Activity mContext, ArrayList<VoucherUsedList> productdatas, String imageUrl) {
        this.productdatas = productdatas;
        this.context = mContext;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public VoucherUsedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_e_vouchers, viewGroup, false);
        return new VoucherUsedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherUsedViewHolder holder, final int i) {
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
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /*String date = "<b>" + productdatas.get(i).getExpiryDate() + "</b>";
        holder.txt_expAva.setText(Html.fromHtml("Expiry " + date));*/

        holder.layoutView.setVisibility(View.GONE);
        if (!(productdatas.get(i).getVoucherUsedCount().equalsIgnoreCase("null") || productdatas.get(i).getVoucherUsedCount().equalsIgnoreCase("0"))) {
            holder.txt_redeem.setText(productdatas.get(i).getVoucherUsedCount() + " Redeemed");
        } else {
            holder.txt_redeem.setText("Redeemed");
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

            }
        });
    }

    private void dialogVouchers(final int i) {
        dialog = new Dialog(context, R.style.custom_dialog_theme1);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //TODO:Identification
        dialog.setContentView(R.layout.layout_voucher_dialog_viewnow);
        dialog.show();

        ImageView imageViews = dialog.findViewById(R.id.imageViews);
        ImageView imgDecreement = dialog.findViewById(R.id.imageDegreement);
        ImageView imgIncreement = dialog.findViewById(R.id.imageIngreement);
        final ImageView cancel_Image = dialog.findViewById(R.id.cancel_Image);
        final TextView txtQuantity = dialog.findViewById(R.id.totalcount);
        final TextView AddtoCartBtn = dialog.findViewById(R.id.AddtoCart);
        LinearLayout lly_addToCart = dialog.findViewById(R.id.lly_addToCart);
        TextView productName = dialog.findViewById(R.id.productName);
        TextView productDescription = dialog.findViewById(R.id.productDescription);
        TextView txt_avaCoupons = dialog.findViewById(R.id.txt_avaCoupons);

        if (productdatas.get(i).getProductThumbnail() != null && productdatas.get(i).getProductThumbnail().length() > 0) {
            Picasso.with(context).load(imageUrl + productdatas.get(i).getProductThumbnail()).
                    error(R.drawable.place_holder_sushi_tei).into(imageViews);
        } else {
            Picasso.with(context).load(R.drawable.place_holder_sushi_tei).into(imageViews);
        }

        productName.setText(productdatas.get(i).getItemName());
        productDescription.setText(productdatas.get(i).getProductShortDescription());
        txt_avaCoupons.setText(productdatas.get(i).getOrderItemVoucherBalanceQty());

        imgIncreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(productdatas.get(i).getItemQty()) > Integer.valueOf(txtQuantity.getText().toString())) {
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
                }
            }
        });

        cancel_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productdatas.size();
    }

    public class VoucherUsedViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout layoutView, layoutRedeem;
        private TextView txt_avaCoupons, txt_drinksQty, txt_expAva, txt_description, txt_voucherPrice, txt_redeem;
        private ImageView img_bg;

        public VoucherUsedViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_avaCoupons = itemView.findViewById(R.id.txt_avaCoupons);
            txt_drinksQty = itemView.findViewById(R.id.txt_drinksQty);
            txt_expAva = itemView.findViewById(R.id.txt_expAva);
            txt_description = itemView.findViewById(R.id.txt_description);
            txt_voucherPrice = itemView.findViewById(R.id.txt_voucherPrice);
            txt_redeem = itemView.findViewById(R.id.txt_redeem);
            layoutView = itemView.findViewById(R.id.layoutView);
            layoutRedeem = itemView.findViewById(R.id.layoutRedeem);
            img_bg = itemView.findViewById(R.id.img_bg);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
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
