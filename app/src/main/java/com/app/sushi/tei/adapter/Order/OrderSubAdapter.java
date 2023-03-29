package com.app.sushi.tei.adapter.Order;

import android.annotation.SuppressLint;
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

import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Interface.IOnItemClick;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.activity.ViewOrderDetailActivity;
import com.app.sushi.tei.Model.Order.OrderDetail;
import com.app.sushi.tei.R;
import com.app.sushi.tei.fragment.Order.OrderSubItemAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class OrderSubAdapter extends RecyclerView.Adapter<OrderSubAdapter.OrderViewHolder> {

    Activity context;
    List<OrderDetail> ordersArrayList;
    private IOnItemClick iOnItemClick;

    public OrderSubAdapter(Activity context, List<OrderDetail> ordersArrayList) {
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
    public void onBindViewHolder(OrderViewHolder holder, @SuppressLint("RecyclerView") final int position) {

       /* ViewOrderListAdapter viewOrderListAdapter = new ViewOrderListAdapter(context, 5);
        holder.cartListRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.cartListRecyclerView.setAdapter(viewOrderListAdapter);*/


        final OrderDetail orderDetail = ordersArrayList.get(position);
        Log.e("TAG","Data_test::"+ orderDetail.getOrder_customer_address_line1() );
        Log.e("orderDetai_getOrder::", orderDetail.getOrder_availability_id()+"\n"+
                orderDetail.getOrder_packaging_charge());

        if ((orderDetail.getOrder_qNumber() != null) && (!(orderDetail.getOrder_qNumber().equalsIgnoreCase("")))) {
            if (orderDetail.getOrder_availability_name().equalsIgnoreCase("Pickup")) {
                holder.layout_qNumber.setVisibility(View.VISIBLE);
                holder.txt_qNumber.setText("Q No - " + orderDetail.getOrder_qNumber());
            }else{
                holder.layout_qNumber.setVisibility(View.GONE);
            }
        } else {
            holder.layout_qNumber.setVisibility(View.GONE);
        }
        if (orderDetail.getOperational_hr() != null && orderDetail.getOperational_hr().length()>0){
            holder.txt_outletOpertationHrs.setVisibility(View.VISIBLE);
            holder.txt_outletOpertationHrs.setText("Operations: " + orderDetail.getOperational_hr());
        }

        if (orderDetail.getOrder_availability_name().equalsIgnoreCase("Pickup")) {
    //        holder.txtOrderStatus.setText("Pickup");
            holder.txtOrderStatus.setText("Takeaway");
        } else {
            holder.txtOrderStatus.setText("Delivery");
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

            holder.ordernumberTxt.setText("Order #" + orderDetail.getOrder_local_no());

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            holder.recyclerViewSubItemList.setLayoutManager(layoutManager);

            OrderSubItemAdapter orderSubItemAdapter = new OrderSubItemAdapter(context, orderDetail.getItems());

            holder.recyclerViewSubItemList.setAdapter(orderSubItemAdapter);

            holder.recyclerViewSubItemList.setNestedScrollingEnabled(false);


            SpannableStringBuilder cs = new SpannableStringBuilder("$" + orderDetail.getOrder_total_amount());
            cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            cs.setSpan(new RelativeSizeSpan(.6f), 0, 1, 0);

            holder.totalTextView.setText(cs);

            if (orderDetail.getStatus_name().equalsIgnoreCase("completed")) {
                holder.statusTextView1.setVisibility(View.VISIBLE);
                holder.statusTextView.setVisibility(View.GONE);
                holder.statusTextView1.setText(orderDetail.getStatus_name());
            } else {
                holder.statusTextView1.setVisibility(View.GONE);
                holder.statusTextView.setVisibility(View.VISIBLE);
                holder.statusTextView.setText(orderDetail.getStatus_name());
            }
            holder.txtOrderPlace.setText(orderDetail.getOutlet_name());
            if ((orderDetail.getOutlet_address() != null)) {
                holder.txtaddress.setText(orderDetail.getOutlet_address() + ", Singapore " + orderDetail.getOutlet_pincode());
            } else {
                holder.txtaddress.setVisibility(View.GONE);
            }
            String time = orderDetail.getOrder_date();
            try {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = sdf.parse(time);

                SimpleDateFormat formatedSDF = new SimpleDateFormat("dd/MM/yyyy, hh:mm a");

                String mdate = formatedSDF.format(date);

                String datetime[] = mdate.split(",");

//                holder.txtDate.setText(datetime[0]);
//                holder.txtTime.setText("ETA " + datetime[1]);

                String order_is_timeslot = orderDetail.getOrder_is_timeslot();

                String order_pickup_time_slot_from = orderDetail.getOrder_pickup_time_slot_from();
                String order_pickup_time_slot_to = orderDetail.getOrder_pickup_time_slot_to();

                String order_delivery_time_slot_from = orderDetail.getOrder_delivery_time_slot_from();
                String order_delivery_time_slot_to = orderDetail.getOrder_delivery_time_slot_to();
                String order_availability_name = orderDetail.getOrder_availability_name();

                if (order_is_timeslot.equalsIgnoreCase("Yes")) {
                    if (order_availability_name.equalsIgnoreCase("Pickup")) {
                        holder.txtTime.setText("ETA " +Utility.convertTime(order_pickup_time_slot_from) + " - " +
                                Utility.convertTime(order_pickup_time_slot_to));
                    } else {
                        holder.txtTime.setText("ETA " +Utility.convertTime(order_delivery_time_slot_from) + " - " +
                                Utility.convertTime(order_delivery_time_slot_to));
                    }
                    if (datetime != null) {
                        holder.txtDate.setText(datetime[0]);
                    }
                } else {
                    if (datetime != null) {
                        holder.txtDate.setText(datetime[0]);
                        holder.txtTime.setText("ETA " + datetime[1]);
                    }
                }
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
              //      intent.putExtra("ORDER_DETAIL", ordersArrayList.get(position));
                    Log.e("TAG","OrderPackChargeTest_3::"+ordersArrayList.get(position).getOrder_packaging_charge());
                    GlobalValues.PACKING_CHARGE_SHOW= ordersArrayList.get(position).getOrder_packaging_charge();
                    intent.putExtra("ORDER_DETAIL", ordersArrayList.get(position));
//                    Gson gson = new Gson();
//                    intent.putExtra("ORDER_DETAIL", gson.toJson(ordersArrayList.get(position)));
                    context.startActivity(intent);
                }
            });

            holder.txt_orderAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (iOnItemClick != null) {
                        iOnItemClick.onItemClick(v, position);
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

    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView availabilityText, availabilityDetailText,
                dateTimeTextView, ordernumberTxt, totalTextView, statusTextView, statusTextView1,
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
        private TextView txtOrderStatus, txt_qNumber, txtaddress, txt_orderAgain, txt_outletOpertationHrs;

        public OrderViewHolder(View itemView) {
            super(itemView);
            //availabilityText = (TextView) itemView.findViewById(R.id.deliveryTxt);
            //availabilityDetailText = (TextView) itemView.findViewById(R.id.availabilityDetailText);
            ordernumberTxt = (TextView) itemView.findViewById(R.id.txtOrderNumber);
            totalTextView = (TextView) itemView.findViewById(R.id.txtOrderTotalPrice);
            statusTextView = (TextView) itemView.findViewById(R.id.statusTextView);
            statusTextView1 = (TextView) itemView.findViewById(R.id.statusTextView1);
            txtOrderPlace = itemView.findViewById(R.id.txtOrderPlace);
            layViewReceipt = itemView.findViewById(R.id.viewreceiptLay);
            recyclerViewSubItemList = itemView.findViewById(R.id.recyclerViewSubItemList);
            layout_qNumber = itemView.findViewById(R.id.layout_qNumber);
            txtOrderStatus = itemView.findViewById(R.id.txtOrderStatus);
            txtaddress = itemView.findViewById(R.id.txtaddress);
            txt_qNumber = itemView.findViewById(R.id.txt_qNumber);
            txt_orderAgain = itemView.findViewById(R.id.txt_orderAgain);
            txt_outletOpertationHrs = itemView.findViewById(R.id.txt_outletOpertationHrs);
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

    public void setOnItemClick(IOnItemClick click) {
        iOnItemClick = click;
    }
}