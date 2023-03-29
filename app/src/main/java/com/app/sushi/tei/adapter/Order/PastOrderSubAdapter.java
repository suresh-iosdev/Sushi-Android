package com.app.sushi.tei.adapter.Order;

import android.app.Activity;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sushi.tei.Interface.IOnItemClick;
import com.app.sushi.tei.activity.ViewOrderDetailActivity;
import com.app.sushi.tei.Model.Order.OrderDetail;
import com.app.sushi.tei.R;
import com.app.sushi.tei.fragment.Order.OrderSubItemAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class PastOrderSubAdapter extends RecyclerView.Adapter<PastOrderSubAdapter.OrderViewHolder> {

    Activity context;
    List<OrderDetail> ordersArrayList;
    private IOnItemClick iOnItemClick;

    public PastOrderSubAdapter(Activity context, List<OrderDetail> ordersArrayList) {
        this.context = context;
        this.ordersArrayList = ordersArrayList;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_order_item, parent, false);
        OrderViewHolder viewholder = new OrderViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, final int position) {

       /* ViewOrderListAdapter viewOrderListAdapter = new ViewOrderListAdapter(context, 5);
        holder.cartListRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.cartListRecyclerView.setAdapter(viewOrderListAdapter);*/


        OrderDetail orderDetail = ordersArrayList.get(position);



        if (orderDetail.getOrder_availability_name().equalsIgnoreCase("Pickup")) {
            holder.txtOrderStatus.setText("Pickup");
        } else {
            holder.txtOrderStatus.setText("Delivery");
        }

        if((orderDetail.getOrder_qNumber() != null) && (!(orderDetail.getOrder_qNumber().equalsIgnoreCase("")))){
            holder.layout_qNumber.setVisibility(View.VISIBLE);
            holder.txt_qNumber.setText("Q No - " + orderDetail.getOrder_qNumber());
        }else {
            holder.layout_qNumber.setVisibility(View.GONE);
        }

        if((orderDetail.getOrder_customer_address_line1() != null) && (!(orderDetail.getOrder_customer_address_line1().equalsIgnoreCase("")))) {
            holder.txtaddress.setText(orderDetail.getOrder_customer_address_line1() + " " + orderDetail.getOrder_customer_address_line2());
        }else{
            holder.txtaddress.setVisibility(View.GONE);
        }

      /*  if (orderDetail.getOrder_availability_id().equals(GlobalValues.DINEIN)) {

            holder.availabilityDetailText.setText(orderDetail.getOutlet_name());
            holder.availabilityTitleText.setText("Dine In");
            holder.availabilityText.setText("Dine In At");

            if (orderDetail.getOrder_table_number() != null && !orderDetail.getOrder_table_number().equals("null")) {
                holder.availabilityDetailText.setText(orderDetail.getOutlet_name() + " Table Number: " + orderDetail.getOrder_table_number());
            } else {
                holder.availabilityDetailText.setText(orderDetail.getOutlet_name());
            }


        } else*/
//        if (orderDetail.getOrder_availability_id().equals(GlobalValues.TAKEAWAY_ID)) {
//
//            holder.availabilityDetailText.setText(orderDetail.getOutlet_name() +"" + orderDetail.getOutlet_address() + " \n Singapore - " + orderDetail.getOutlet_pincode());
//            holder.availabilityTitleText.setText("Takeaway");
//            holder.availabilityText.setText("Pickup Outlet-");
//
//        } else if (orderDetail.getOrder_availability_id().equals(GlobalValues.DELIVERY_ID))
//        {
//
//
//            if(orderDetail.getOrder_customer_unit_no1().toString().isEmpty())
//            {
//
//                holder.availabilityDetailText.setText(  " "+Utility.getValidString(Utility.getValidString(orderDetail.getOrder_customer_address_line1()) +
//                        " Singapore " + Utility.getValidString(orderDetail.getOrder_customer_postal_code())));
//
//
//
//            }
//            else
//            {
//
//                holder.availabilityDetailText.setText( " "+Utility.getValidString("#"+ orderDetail.getOrder_customer_unit_no1())+"-"+orderDetail.getOrder_customer_unit_no2() +" "+  Utility.getValidString(orderDetail.getOrder_customer_address_line1()) +
//                        " Singapore " + Utility.getValidString(orderDetail.getOrder_customer_postal_code()));
//
//            }
//
//            holder.availabilityTitleText.setText("Delivery");
//            holder.availabilityText.setText("Delivery Address-");
//
//        } else if(orderDetail.getOrder_availability_id().equals(GlobalValues.BENTO_ID)) {
//
//
//            holder.availabilityTitleText.setText("BENTO");
//
//            holder.availabilityText.setText("Delivery Address-");
//
//
//
//            if(orderDetail.getOrder_customer_unit_no1().toString().isEmpty())
//            {
//
//                holder.availabilityDetailText.setText(  " "+Utility.getValidString(  Utility.getValidString(orderDetail.getOrder_customer_address_line1()) + ", "  +
//                        " Singapore " + Utility.getValidString(orderDetail.getOrder_customer_postal_code())));
//
//
//
//            }
//            else
//            {
//
//                holder.availabilityDetailText.setText(" "+Utility.getValidString("#"+ orderDetail.getOrder_customer_unit_no1())+"-"+orderDetail.getOrder_customer_unit_no2() +" "+  Utility.getValidString(orderDetail.getOrder_customer_address_line1()) +
//                        " Singapore " + Utility.getValidString(orderDetail.getOrder_customer_postal_code()));
//
//            }
//
//        }
//
//
//        try {
//
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date date = sdf.parse(orderDetail.getOrder_date());
//
//            SimpleDateFormat formatedSDF = new SimpleDateFormat("dd/MM/yyyy, hh:mm a");
//
//            String time = formatedSDF.format(date);
//
//            String datetime[] = time.split(",");
//
//            holder.dateTimeTextView.setText(datetime[0]);
//            holder.txtTime.setText(datetime[1]);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//            holder.dateTimeTextView.setText(orderDetail.getOrder_date());
//
//        }


        if (orderDetail != null) {

            holder.ordernumberTxt.setText("Order #"+orderDetail.getOrder_local_no());

            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context);
            holder.recyclerViewSubItemList.setLayoutManager(layoutManager);

            OrderSubItemAdapter orderSubItemAdapter=new OrderSubItemAdapter(context,orderDetail.getItems());

            holder.recyclerViewSubItemList.setAdapter(orderSubItemAdapter);

            holder.recyclerViewSubItemList.setNestedScrollingEnabled(false);


            SpannableStringBuilder cs = new SpannableStringBuilder("$" + orderDetail.getOrder_total_amount());
            cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            cs.setSpan(new RelativeSizeSpan(.6f), 0, 1, 0);

            holder.totalTextView.setText(cs);

            holder.statusTextView.setText(orderDetail.getStatus_name());
            holder.txtOrderPlace.setText(orderDetail.getOutlet_name());

            String time = orderDetail.getOrder_date();
            try {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = sdf.parse(time);

                SimpleDateFormat formatedSDF = new SimpleDateFormat("dd/MM/yyyy, hh:mm a");

                String mdate = formatedSDF.format(date);

                String datetime[] = mdate.split(",");

                holder.txtDate.setText(datetime[0]);
                holder.txtTime.setText(datetime[1]);

            } catch (ParseException e) {
                e.printStackTrace();
            }

//            ViewOrderListAdapter viewOrderListAdapter = new ViewOrderListAdapter(context, orderDetail.getItems_json_array_string());
//            holder.cartListRecyclerView.setLayoutManager(new LinearLayoutManager(context));
//            holder.cartListRecyclerView.setAdapter(viewOrderListAdapter);


            holder.layViewReceipt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, ViewOrderDetailActivity.class);
                    intent.putExtra("ORDER_DETAIL", ordersArrayList.get(position));
                    context.startActivity(intent);
                }
            });

            holder.txt_orderAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iOnItemClick!=null)
                    {
                        iOnItemClick.onItemClick(v,position);
                    }
                }
            });
//
//            holder.printLay.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        return ordersArrayList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        TextView availabilityText, availabilityDetailText,
                dateTimeTextView, ordernumberTxt, totalTextView, statusTextView,
                availabilityTitleText, txtTime, txtDate;
        RelativeLayout printLay;
        LinearLayout redeemLayout;
        private LinearLayout viewreceiptLay, receivedLay;
        View darkgreenView;
        private TextView txtOrderPlace;
        private RelativeLayout layViewReceipt;

        RecyclerView cartListRecyclerView;
        private RecyclerView recyclerViewSubItemList;
        private RelativeLayout layout_qNumber;
        private TextView txtOrderStatus, txt_qNumber, txtaddress, txt_orderAgain;

        public OrderViewHolder(View itemView)  {
            super(itemView);
            //availabilityText = (TextView) itemView.findViewById(R.id.deliveryTxt);
            //availabilityDetailText = (TextView) itemView.findViewById(R.id.availabilityDetailText);
            ordernumberTxt = (TextView) itemView.findViewById(R.id.txtOrderNumber);
            totalTextView = (TextView) itemView.findViewById(R.id.txtOrderTotalPrice);
            statusTextView = (TextView) itemView.findViewById(R.id.statusTextView);
            txtOrderPlace = itemView.findViewById(R.id.txtOrderPlace);
            layViewReceipt = itemView.findViewById(R.id.viewreceiptLay);
            recyclerViewSubItemList=itemView.findViewById(R.id.recyclerViewSubItemList);
            layout_qNumber = itemView.findViewById(R.id.layout_qNumber);
            txtOrderStatus = itemView.findViewById(R.id.txtOrderStatus);
            txtaddress = itemView.findViewById(R.id.txtaddress);
            txt_qNumber = itemView.findViewById(R.id.txt_qNumber);
            txt_orderAgain = itemView.findViewById(R.id.txt_orderAgain);
            //txtTime = (TextView) itemView.findViewById(R.id.txtTime);

            // availabilityTitleText = (TextView) itemView.findViewById(R.id.availabilityTitleText);
            // cartListRecyclerView = (RecyclerView) itemView.findViewById(R.id.cartListRecyclerView);

            //dateTimeTextView = (TextView) itemView.findViewById(R.id.dateTimeTextView);
            txtDate = itemView.findViewById(R.id.txtOrderDate);
            txtTime = itemView.findViewById(R.id.txtOrderTime);

            // redeemLayout = (LinearLayout) itemView.findViewById(R.id.redeemLayout);
            receivedLay = itemView.findViewById(R.id.receivedLay);
            // viewreceiptLay = itemView.findViewById(R.id.viewreceiptLay);
            // printLay = (RelativeLayout) itemView.findViewById(R.id.printLay);
            // darkgreenView = (View) itemView.findViewById(R.id.darkgreenView);

        }

        @Override
        public void onClick(View v) {



        }
    }

    public void setOnItemClick(IOnItemClick click)
    {
        iOnItemClick=click;
    }
}