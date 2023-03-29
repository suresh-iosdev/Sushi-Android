package com.app.sushi.tei.activity;

public class referenceClass {}
/*
    private RecyclerView recyclerview_CartItem;
    private CartItemAdapter cartItemAdapter;
    private Context context;
    private Toolbar toolbar;
    private LinearLayout toolbarBack;
    private TextView continues;
    private ArrayList<Cart> cartlist;
    private TextView textView_continue;
    private int overallpositioin;
    private int count = 0;
    private TextView clearcart;
    String url;
    private RelativeLayout navigationView;
    private View content;
    public static int cutOffTime;
    private int mTatTime = 0;
    private int mLastCardIndex = 1;
    private boolean is_break = false;
    private RecyclerView outletRecyclerView;
    private LinearLayoutManager layoutManager;
    private String cartCount = "";
    /// Secondary Address
    private TextView subtotaltextview;
    private LinearLayout cartdatelayout, carttimelayout;
    private Double overallprice = 0.0;
    private boolean isSelectTime = false, is_time_shown = false, is_date_shown = false;
    public static TextView txtDeliveryDate, txtDeliveryTime;
    private TextView txtDate, txtTime, txtChangeAddress, txtChangeAddress_pickup, txtDeliveryCharge,
            txtOutletName, txtAddress, txtFreeDelivery, txtEmpty, txtTakeawayName, txtDiscountLabel;
    private SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm");
    public static RelativeLayout layoutTime;
    public static RelativeLayout layoutCalendar;
    TextView previousButton, nextButton;
    private TextView currentDateText;
    private GridView calendarGridView;
    private SimpleDateFormat twelvetimeformat = new SimpleDateFormat("hh:mm");
    private static final int MAX_CALENDAR_COLUMN = 42;
    private int month, year;
    //private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private RecyclerView timeRecyclerView;
    private Intent intent;
    private RelativeLayout layoutCustomTime;
    List<Date> dayValueInCells;
    public static Date currentday, maxDate;
    public int mMinDate, mMaxDate;
    public List<Holidays> holidaysList;
    public List<String> slotDaysList;
    private List<String> timeList;
    private static String todayString = "";
    private boolean isHoliday = false, isSlotDay = false;
    public int mselectedDay;
    Calendar min;
    private boolean isChangeAddress = false;
    private int mFrom;
    private boolean mOutletTAT = false, mZoneTAT = false;
    public static boolean isDateDisplay = false;
    private ArrayList<SlotTime> slotTimeArrayList;
    private ArrayList<SlotTime> sundayArrayList;
    private ArrayList<SlotTime> mondayArrayList;
    private ArrayList<SlotTime> tuesdayArrayList;
    private ArrayList<SlotTime> wednesdayArrayList;
    private ArrayList<SlotTime> thursdayArrayList;
    private ArrayList<SlotTime> fridayArrayList;
    private ArrayList<SlotTime> satdayArrayList;
    private Date currentDate, selecteddate;
    int intervaltime;
    private List<Outlet> outletList;
    private RelativeLayout hidecartemptyvalueslayout;
    private LinearLayout hidecartlayoutifempty;
    private TextView totaltextview;
    private RelativeLayout layoutCart;
    private TextView txtCartCount;
    private EditText kitchennote;
    private String kicthen_notes = "";

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) *//*{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        context = this;
        subtotaltextview = findViewById(R.id.subtotaltextview);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        toolbarBack = findViewById(R.id.toolbarBack);
        continues = findViewById(R.id.continues);
        recyclerview_CartItem = findViewById(R.id.recyclerview_CartItem);
        // textView_continue = findViewById(R.id.continues);
        cartlist = new ArrayList<>();
        clearcart = findViewById(R.id.clearallcartitems);
        cartdatelayout = findViewById(R.id.cardatelayout);
        carttimelayout = findViewById(R.id.carttimelayout);
        outletList = new ArrayList<>();
        hidecartemptyvalueslayout = findViewById(R.id.cartvaluesemptylayout);
        hidecartlayoutifempty = findViewById(R.id.overallemptycartlayout);
        totaltextview = findViewById(R.id.totaltextview);
        kitchennote = findViewById(R.id.kitchennote11);


        if (cartlist != null && cartlist.size() == 0) {

            hidecartlayoutifempty.setVisibility(View.GONE);
            hidecartemptyvalueslayout.setVisibility(View.GONE);
        } else {

            hidecartlayoutifempty.setVisibility(View.VISIBLE);
            hidecartemptyvalueslayout.setVisibility(View.VISIBLE);

        }


//        cartdatelayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Utility.networkCheck(context)) {
//                    getTAT();
//
//
//                    // Create custom dialog object
//
//
//                } else {
//                    Toast.makeText(context, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });

//
//        carttimelayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cartdatelayout.performClick();
//            }
//        });


        carttimelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cartdatelayout.performClick();
            }
        });


        continues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                kicthen_notes = kitchennote.getText().toString();
                if (kicthen_notes != null && !kicthen_notes.isEmpty() && !kicthen_notes.equals("null")) {

                    kicthen_notes = kicthen_notes;
                } else {

                    kicthen_notes = "Note to kicthen";
                }




                Intent i = new Intent(context, OrderSummaryActivity.class);
                i.putExtra("notes", kicthen_notes);
                i.putExtra("sub", subtotaltextview.getText().toString());
                i.putExtra("total", totaltextview.getText().toString());
                startActivity(i);
            }
        });

        clearcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url1 = GlobalUrl.DESTROY_CART_URL;
                Map<String, String> params = new HashMap<>();
                params.put("app_id", GlobalValues.APP_ID);
                params.put("customer_id", Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID));
                params.put("reference_id", Utility.getReferenceId(context));

                if (Utility.networkCheck(context)) {

                    new DestroyCartTask(params).execute(url1);
                }
            }
        });

//        textView_continue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


        url = GlobalUrl.CART_LIST_URL + "?app_id=" + GlobalValues.APP_ID + "&availability="
                + Utility.readFromSharedPreference(context, GlobalValues.AVALABILITY_ID) + "&reference_id=" + Utility.getReferenceId(context) + "&customer_id=" + Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID);
        new CartListTask().execute(url);
       *//* if (!GlobalValues.DISCOUNT_APPLIED) {

            new CartListTask().execute(url);



        } else {
            new CartListTask().execute(url);


        }*//*


        //String url="https://ccpl.ninjaos.com/api/cart/contents?app_id=F60DC85C-6801-4536-8102-65D9A8666940&customer_id=&reference_id=351540061429619&availability_id=634E6FA8-8DAF-4046-A494-FFC1FCF8BD11";
        // String url = GlobalUrl.CART_LIST_URL;
        Map<String, String> params = new HashMap<>();

        params.put("app_id", GlobalValues.APP_ID);
        params.put("reference_id", Utility.getReferenceId(context));

        params.put("customer_id", Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID));
        //params.put("availability_name", productArrayList.get(position).getMproduct_alias());
        params.put("availability", Utility.readFromSharedPreference(context, GlobalValues.AVALABILITY_ID));


        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        continues.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, OrderSummaryActivity.class);
//                startActivity(intent);
//
//            }
//        });
    }*/





   /* private class CartTATtask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please wait...");
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
                    if (jsonObject.getString("tattime").equalsIgnoreCase("null") || jsonObject.getString("tattime") == null) {
                        mTatTime = 0;
                    } else {
                        mTatTime = Integer.parseInt(jsonObject.getString("tattime"));
                    }

                } else {
                    mTatTime = 0;
                }

            } catch (Exception e) {
                e.printStackTrace();
                mTatTime = 0;
            } finally {

                progressDialog.dismiss();
                String url = GlobalUrl.DAYAVAILABLE_URL + "?app_id=" + "F60DC85C-6801-4536-8102-65D9A8666940" + "&availability=" +
                        Utility.readFromSharedPreference(context, GlobalValues.AVALABILITY_ID) +
                        "&outlet_id=" + Utility.readFromSharedPreference(context, GlobalValues.OUTLET_ID);

                GlobalValues.CURRENT_TAT_TIME = String.valueOf(mTatTime);

                new CheckDayAvailability().execute(url);

            }
        }
    }*/


   /* private void getTAT() {

        if (Utility.networkCheck(context)) {

            String url = GlobalUrl.CART_TAT_URL +
                    "?app_id=" + "F60DC85C-6801-4536-8102-65D9A8666940" +
                    "&outletId=" + Utility.readFromSharedPreference(context, GlobalValues.OUTLET_ID) +
                    "&availability=" + GlobalValues.AVALABILITY_ID;

            new CartTATtask().execute(url);

        } else {
            Toast.makeText(context, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }

    }

*/
   /* private class CheckDayAvailability extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;


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

                    JSONArray jsonResult = jsonObject.getJSONArray("result_set");

                    if (jsonResult.getJSONObject(0) != null) {
                        JSONObject jsonResultset = jsonResult.getJSONObject(0);

                        if (jsonResultset != null) {

                            if (jsonResultset.getString("interval_time").length() > 0) {
//get json interval data
                                intervaltime = Integer.parseInt(jsonResultset.getString("interval_time"));
//                                intervaltime = 15;
                                GlobalValues.INERVAL_TIME = Integer.parseInt(jsonResultset.getString("interval_time"));
                            } else {

                            }

                            if (jsonResultset.getString("cut_off").length() > 0) {

                                int cutoff = Integer.parseInt(jsonResultset.getString("cut_off"));
                            }

                            mMinDate = Integer.parseInt(jsonResultset.getString("minimum_date"));
                            mMaxDate = Integer.parseInt(jsonResultset.getString("maximum_date"));

                            JSONObject jsonSlot = jsonResultset.getJSONObject("slot");


                            Iterator<String> iterator = jsonSlot.keys();

                            slotDaysList = new ArrayList<>();

                            while (iterator.hasNext()) {

                                String key = iterator.next();

                                slotDaysList.add(key);

                                if (jsonSlot.getJSONArray(key) instanceof JSONArray) {

                                    if (key.equalsIgnoreCase("sun")) {

                                        JSONArray sunArray = jsonSlot.getJSONArray("sun");

                                        if (sunArray.length() > 0) {

                                            sundayArrayList = new ArrayList<>();

                                            for (int m = 0; m < sunArray.length(); m++) {

                                                JSONObject sunJson = sunArray.getJSONObject(m);

                                                SlotTime slotTime = new SlotTime();
                                                slotTime.setmStartTime(sunJson.getString("slot_time1"));
                                                slotTime.setmEndTime(Utility.checkBefore11(sunJson.getString("slot_time2")));

                                                sundayArrayList.add(slotTime);
                                            }
                                        }
                                    }

                                    if (key.equalsIgnoreCase("mon")) {

                                        JSONArray monArray = jsonSlot.getJSONArray("mon");

                                        if (monArray.length() > 0) {

                                            mondayArrayList = new ArrayList<>();

                                            for (int m = 0; m < monArray.length(); m++) {

                                                JSONObject monJson = monArray.getJSONObject(m);

                                                SlotTime slotTime = new SlotTime();
                                                slotTime.setmStartTime(monJson.getString("slot_time1"));
                                                slotTime.setmEndTime(Utility.checkBefore11(monJson.getString("slot_time2")));

                                                mondayArrayList.add(slotTime);
                                            }
                                        }
                                    }

                                    if (key.equalsIgnoreCase("tue")) {

                                        JSONArray tueArray = jsonSlot.getJSONArray("tue");

                                        if (tueArray.length() > 0) {

                                            tuesdayArrayList = new ArrayList<>();

                                            for (int m = 0; m < tueArray.length(); m++) {
                                                JSONObject tueJson = tueArray.getJSONObject(m);

                                                SlotTime slotTime = new SlotTime();
                                                slotTime.setmStartTime(tueJson.getString("slot_time1"));
                                                slotTime.setmEndTime(Utility.checkBefore11(tueJson.getString("slot_time2")));

                                                tuesdayArrayList.add(slotTime);
                                            }
                                        }
                                    }

                                    if (key.equalsIgnoreCase("wed")) {

                                        JSONArray wedArray = jsonSlot.getJSONArray("wed");

                                        if (wedArray.length() > 0) {
                                            wednesdayArrayList = new ArrayList<>();

                                            for (int m = 0; m < wedArray.length(); m++) {

                                                JSONObject wedJson = wedArray.getJSONObject(m);

                                                SlotTime slotTime = new SlotTime();
                                                slotTime.setmStartTime(wedJson.getString("slot_time1"));
                                                slotTime.setmEndTime(Utility.checkBefore11(wedJson.getString("slot_time2")));

                                                wednesdayArrayList.add(slotTime);
                                            }
                                        }
                                    }

                                    if (key.equalsIgnoreCase("thu")) {

                                        JSONArray thuArray = jsonSlot.getJSONArray("thu");

                                        if (thuArray.length() > 0) {
                                            thursdayArrayList = new ArrayList<>();

                                            for (int m = 0; m < thuArray.length(); m++) {
                                                JSONObject thuJson = thuArray.getJSONObject(m);

                                                SlotTime slotTime = new SlotTime();
                                                slotTime.setmStartTime(thuJson.getString("slot_time1"));
                                                slotTime.setmEndTime(Utility.checkBefore11(thuJson.getString("slot_time2")));

                                                thursdayArrayList.add(slotTime);
                                            }
                                        }
                                    }

                                    if (key.equalsIgnoreCase("fri")) {

                                        JSONArray friArray = jsonSlot.getJSONArray("fri");

                                        if (friArray.length() > 0) {
                                            fridayArrayList = new ArrayList<>();

                                            for (int m = 0; m < friArray.length(); m++) {
                                                JSONObject friJson = friArray.getJSONObject(m);

                                                SlotTime slotTime = new SlotTime();
                                                slotTime.setmStartTime(friJson.getString("slot_time1"));
                                                slotTime.setmEndTime(Utility.checkBefore11(friJson.getString("slot_time2")));

                                                fridayArrayList.add(slotTime);
                                            }
                                        }
                                    }

                                    if (key.equalsIgnoreCase("sat")) {

                                        JSONArray satArray = jsonSlot.getJSONArray("sat");

                                        if (satArray.length() > 0) {

                                            satdayArrayList = new ArrayList<>();

                                            for (int m = 0; m < satArray.length(); m++) {
                                                JSONObject satJson = satArray.getJSONObject(m);

                                                SlotTime slotTime = new SlotTime();
                                                slotTime.setmStartTime(satJson.getString("slot_time1"));
                                                slotTime.setmEndTime(Utility.checkBefore11(satJson.getString("slot_time2")));

                                                satdayArrayList.add(slotTime);
                                            }
                                        }
                                    }


                                }
                            }

                        }
                    }


                    JSONArray holidayArray = jsonObject.getJSONArray("holidayresult");
                    holidaysList = new ArrayList<>();
                    if (holidayArray.length() > 0) {

                        for (int h = 0; h < holidayArray.length(); h++) {
                            JSONObject holidayJson = holidayArray.getJSONObject(h);

                            Holidays holidays = new Holidays();

                            holidays.setmId(holidayJson.getString("holiday_id"));
                            holidays.setmTitle(holidayJson.getString("holiday_title"));
                            holidays.setmDescription(holidayJson.getString("holiday_description"));
                            holidays.setmDate(holidayJson.getString("holiday_date"));

                            holidaysList.add(holidays);
                        }

                    } else {
                        holidaysList = new ArrayList<>();
                    }


                } else {

                    Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
                openDeliveryDateDialog(context);

            } catch (Exception e) {
                mMinDate = 0;
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
            }
        }
    }

*/

/*
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {


        try {
            MenuItem cartWBadge = menu.findItem(R.id.menu_cart);
            MenuItem menuSearch = menu.findItem(R.id.menu_search);

            menuSearch.setVisible(false);

            View viewBadge = menu.findItem(R.id.menu_cart).getActionView();

            layoutCart = (RelativeLayout) viewBadge.findViewById(R.id.layoutCart);
            txtCartCount = (TextView) viewBadge.findViewById(R.id.txtCartCount);

            cartCount = Utility.readFromSharedPreference(context, GlobalValues.CART_COUNT);

            if (!cartCount.equalsIgnoreCase("") && !cartCount.isEmpty()) {


                // updatecartvalues();
                txtCartCount.setVisibility(View.VISIBLE);
                txtCartCount.setText(cartCount);


            */
/*    if (isInvalidated) {
                    Tooltip.make((context),
                            new Tooltip.Builder(101)
                                    .anchor(layoutCart, Tooltip.Gravity.BOTTOM)
                                    .closePolicy(new Tooltip.ClosePolicy()
                                            .insidePolicy(true, false)
                                            .outsidePolicy(true, false), 300000)
                                    .activateDelay(100)
                                    .showDelay(200)
                                    .text("great! product added to the cart")
                                    .withArrow(true)
                                    .withOverlay(false)
                                    .typeface(BebasNeueBoldTextView.BebasNeueBoldText)
                                    .build()
                    ).show();
                }*//*


            } else {

                txtCartCount.setVisibility(View.GONE);
            }


            cartWBadge.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.onPrepareOptionsMenu(menu);
    }
*/


  /*  private class CartListTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        Map<String, String> params;


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

                    JSONObject jsonResult = jsonObject.getJSONObject("result_set");

                    JSONObject jsoncartDetails = jsonResult.getJSONObject("cart_details");
                    JSONArray jsonArray = jsonResult.optJSONArray("cart_items");


                    for (int y = 0; y < jsonArray.length(); y++) {

                        JSONObject cartobject = jsonArray.getJSONObject(y);
                        Cart products = new Cart();
                        products.setmCartItemId(cartobject.getString("cart_item_id"));
                        products.setmProductName(cartobject.getString("cart_item_product_name"));
                        products.setmProductQty(cartobject.getString("cart_item_qty"));
                        products.setmProductTotalPrice(cartobject.getString("cart_item_total_price"));
                        cartlist.add(products);

                    }


                    if (cartlist != null) {

                        if (cartlist.size() != 0) {
                            for (int size = 0; size < cartlist.size(); size++) {

                                overallprice += (Double.valueOf(cartlist.get(size).getmProductTotalPrice()));


                            }


                            String price = String.format(String.valueOf(overallprice), "%0.2d");
                            subtotaltextview.setText(String.valueOf(price));
                            totaltextview.setText(String.valueOf(price));


                            //Utility.writeToSharedPreference(context, GlobalValues.TOTAL_CART_PRICE, String.valueOf(price));
                            hidecartemptyvalueslayout.setVisibility(View.VISIBLE);
                            hidecartlayoutifempty.setVisibility(View.VISIBLE);

                        } else {


                            //Utility.writeToSharedPreference(context, GlobalValues.TOTAL_CART_PRICE, String.valueOf("0"));
                            //subtotaltextview.setText("126.00");

                            subtotaltextview.setVisibility(View.GONE);
                            hidecartemptyvalueslayout.setVisibility(View.GONE);
                            hidecartlayoutifempty.setVisibility(View.GONE);

                        }


                    } else {

                        //subtotaltextview.setText("126.00");


                        //Utility.writeToSharedPreference(context, GlobalValues.TOTAL_CART_PRICE, String.valueOf("0"));
                        subtotaltextview.setVisibility(View.GONE);
                        hidecartemptyvalueslayout.setVisibility(View.GONE);
                        hidecartlayoutifempty.setVisibility(View.GONE);
                    }


                } else {


                }
//                    }else {
//
//                        Cart products=new Cart();
//                        products.setmCartItemId(cartobject.getString("cart_item_id"));
//                        products.setmProductName(cartobject.getString("cart_item_product_name"));
//                        products.setmProductQty(cartobject.getString("cart_item_qty"));
//                        products.setmProductTotalPrice(cartobject.getString("cart_item_total_price"));
////                        cartlist.add(products);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                recyclerview_CartItem.setLayoutManager(linearLayoutManager);
                cartItemAdapter = new CartItemAdapter(context, cartlist);
                recyclerview_CartItem.setAdapter(cartItemAdapter);
                recyclerview_CartItem.setNestedScrollingEnabled(false);
                cartItemAdapter.setOnClickListeners(new CartItemAdapter.OnItemClickListeners() {
                    @Override
                    public void OnClick(View v, final int position, ImageView remove, ImageView asset56, ImageView asset56, final TextView qty) {
                        remove.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String url = GlobalUrl.DELETE_SINGLE_CART_URL;
                                overallpositioin = position;


                                Map<String, String> params = new HashMap<String, String>();

                                params.put("app_id", GlobalValues.APP_ID);
                                params.put("cart_item_id", cartlist.get(position).getmCartItemId());
                                params.put("customer_id", Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID));
                                params.put("reference_id", Utility.getReferenceId(context));
                                new DeleteCartItemTask(params, cartlist.get(position).getmProductId()).execute(url);

                            }
                        });
                        asset56.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (count < 1) {

                                    count = 1;
                                }
                                count = count + 1;
                                qty.setText(String.valueOf(count));
                            }
                        });
                        asset56.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count = count - 1;
                                if (count <= 1) {

                                    qty.setText("1");
                                } else {

                                    qty.setText(String.valueOf(count));
                                }
                            }
                        });

                    }
                });

*/
                // Utility.writeToSharedPreference(context, GlobalValues.CART_COUNT, jsoncartDetails.optString("cart_total_items"));


//                    //txtSubTotal.setText("$" + jsoncartDetails.getString("cart_sub_total"));
//                  //  r_sub_total = jsoncartDetails.getString("cart_sub_total");
//
//                    if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID) || GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID))
//                    {
//
//                        //setCustomProgress();
//
//                        double d_progress_limit = Double.valueOf(outletZoneJson.getString("zone_free_delivery")) - Double.valueOf(r_sub_total);
//
//                        if (d_progress_limit > 0)
//                        {
//
//                            GlobalValues.DELEIVERY_CHARGES = outletZoneJson.getString("zone_delivery_charge");
//
//
//                            /*Toast.makeText(mContext, ""+outletZoneJson.getString("zone_additional_delivery_charge"), Toast.LENGTH_SHORT).show();*/
//
//
//                            GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");
//
//                            if (GlobalValues.PRMOTION_DELIVERY_APPLIED)
//                            {
//
//                                GlobalValues.DELEIVERY_CHARGES = "0.00";
//                                GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");
//
//
//                                txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
//                                        Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));
//
//                                txtDeliveryCharge.setText("$0.00");
//
//                                mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
//                                        Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));
//
//                                if( GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
//                                    mGST = (mGrandTotal * 7) / 100;
//                                    GlobalValues.GST=mGST;
//                                    mGrandTotal+=mGST;
//
//
//                                }
//                                else
//                                {
//
//                                    int gst_values = Integer.valueOf( GlobalValues.GstChargers).intValue();
//                                    mGST = (mGrandTotal * gst_values) / 100;
//                                    GlobalValues.GST=mGST;
//                                    mGrandTotal+=mGST;
//
//
//                                }
//
//
//                            } else {
//
//
//
//                                GlobalValues.DELEIVERY_CHARGES = outletZoneJson.getString("zone_delivery_charge");
//                                GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");
//
//                                txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
//                                        Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));
//
//                                txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));
//
//                                // Toast.makeText(mContext, ""+outletZoneJson.getString("zone_additional_delivery_charge"), Toast.LENGTH_SHORT).show();
//
//
//
//                                mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
//                                        Double.parseDouble(outletZoneJson.getString("zone_delivery_charge")) +
//                                        Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));
//
//                                if( GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID))
//                                {
//
//                                    mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
//                                            Double.parseDouble(outletZoneJson.getString("zone_delivery_charge"));
//
//
//                                    mGST = (mGrandTotal * 7) / 100;
//                                    GlobalValues.GST=mGST;
//                                    mGrandTotal+=mGST;
//                                    txtGSTLabel.setText("GST ("+"7"+"%)" );
//                                    txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));
//
//
//                                }
//                                else
//                                {
//
//                                    int gst_values = Integer.valueOf( GlobalValues.GstChargers).intValue();
//
//                                    mGST = (mGrandTotal * gst_values) / 100;
//                                    GlobalValues.GST=mGST;
//                                    mGrandTotal+=mGST;
//                                    txtGSTLabel.setText("GST ("+"7"+"%)" );
//
//
//                                }
//
//                            }
//
//                            txtFreeDelivery.setText("$" + String.format("%.2f", d_progress_limit) + " more to FREE delivery!");
//
//                            //progressBar.setProgress(Double.valueOf(r_sub_total.trim()).intValue());
//
//
//                        } else {
//
//                            GlobalValues.DELEIVERY_CHARGES = "0.00";
//                            GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");
//
//                            txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
//                                    Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));
//                            txtDeliveryCharge.setText("$0.00");
//                            txtFreeDelivery.setText("FREE delivery!");
//
//                            // Toast.makeText(mContext, ""+outletZoneJson.getString("zone_additional_delivery_charge"), Toast.LENGTH_SHORT).show();
//
//
////                            progressBar.setProgress(1000);
//
//                            mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
//                                    Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));
//
//                            if( GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID))
//                            {
//                                mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
//                                        Double.parseDouble(outletZoneJson.getString("zone_delivery_charge"));
//
//
//                                mGST = (mGrandTotal * 7) / 100;
//                                GlobalValues.GST=mGST;
//                                mGrandTotal+=mGST;
//                                txtGSTLabel.setText("GST ("+"7"+"%)" );
//                                txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));
//
//
//
//
//                            }
//                            else
//                            {
//
//                                int gst_values = Integer.valueOf( GlobalValues.GstChargers).intValue();
//
//
//                                mGST = (mGrandTotal * gst_values) / 100;
//                                GlobalValues.GST=mGST;
//                                mGrandTotal+=mGST;
//
//
//                            }
//
//
//                        }
//
////                                Double.parseDouble(outletZoneJson.getString("zone_delivery_charge"));
//
//
//
//                    } else if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {
//                        mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));
//
//                        int gst_values = Integer.valueOf( GlobalValues.GstChargers).intValue();
//
//
//                        mGST = (mGrandTotal * gst_values) / 100;
//                        GlobalValues.GST=mGST;
//                        mGrandTotal+=mGST;
//
//                    }
//
//                    txtGST.setText("$" + String.format("%.2f", mGST));
//                    txtTotal.setText("$" + String.format("%.2f", mGrandTotal));
//
//                    InclusiveGst(mGrandTotal);
//
//
//                    JSONArray jsonCartItem = jsonResult.getJSONArray("cart_items");
//
//                    Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, jsonObject.getJSONObject("result_set").toString());
//
//
//
//                    setCartAdapter(jsonCartItem);
//
//
//                } else {
//
//                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//
//                }
       /*     } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
            }
        }
    }
*/
  /*  private class DeleteCartItemTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private Map<String, String> deleteParams;
        private String mProductId = "";

        public DeleteCartItemTask(Map<String, String> deleteParams, String id) {
            this.deleteParams = deleteParams;
            mProductId = id;
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

            String response = WebserviceAssessor.postRequest(context, params[0], deleteParams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {


                JSONObject jsonObject = new JSONObject(s);

                ///  Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {


                    //deleteCurrentQuantity(mProductId);
                    Double text = Double.valueOf(subtotaltextview.getText().toString());
                    Double price = text - Double.valueOf(cartlist.get(overallpositioin).getmProductTotalPrice());

                    cartlist.remove(overallpositioin);
                    cartItemAdapter.notifyItemRemoved(overallpositioin);
                    cartItemAdapter.notifyItemRangeChanged(overallpositioin, cartlist.size());

                    if (cartlist.size() == 0) {

                        finish();
                    } else {
                        totaltextview.setText(String.format(String.valueOf(price), "%0.2d"));
                        subtotaltextview.setText(String.format(String.valueOf(price), "%0.2d"));
                    }
                    //   cartItemAdapter.notifyDataSetChanged();

                    if (!jsonObject.isNull("contents")) {

                        JSONObject jsonResult = jsonObject.getJSONObject("contents");


                        JSONObject jsoncartDetails = jsonResult.getJSONObject("cart_details");


                        Utility.writeToSharedPreference(context, GlobalValues.CART_COUNT,
                                jsoncartDetails.getString("cart_total_items"));*/


//                        if (txtPromoApply.getText().toString().equalsIgnoreCase("REMOVE")) {
//                            removePromotion();
//                        }
//
//
//                        if (txtRedeemApply.getText().toString().equalsIgnoreCase("REMOVE")) {
//                            removeRewardPoints();
//                        }
//
//                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_COUNT, jsoncartDetails.getString("cart_total_items"));
//
//
//                        txtSubTotal.setText("$" + jsoncartDetails.getString("cart_sub_total"));
//
//                        r_sub_total = jsoncartDetails.getString("cart_sub_total");
//
//
//                        if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID) ||
//                                GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
//
//                            setCustomProgress();
//
//
//                            double d_progress_limit = Double.valueOf(outletZoneJson.getString("zone_free_delivery"))
//                                    - Double.valueOf(r_sub_total);
//
//                            if (d_progress_limit > 0)
//                            {
//
//                                GlobalValues.DELEIVERY_CHARGES = outletZoneJson.getString("zone_delivery_charge");
//                                GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");
//
//
//                                txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));
//                                txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
//                                        Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));
//
//                                txtFreeDelivery.setText("$" + String.format("%.2f", d_progress_limit) + " more to FREE delivery!");
//
//                                //progressBar.setProgress(Double.valueOf(r_sub_total.trim()).intValue());
//
//                                mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
//                                        Double.parseDouble(outletZoneJson.getString("zone_delivery_charge")) +
//                                        Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));
//
//                                if( GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
//                                    mGST = (mGrandTotal * 7) / 100;
//                                    GlobalValues.GST=mGST;
//                                    mGrandTotal+=mGST;
//                                }
//                                else
//                                {
//
//
//
//                                    GlobalValues.GstChargers = Utility.readFromSharedPreference(mContext,GlobalValues.GstCharger);
//
//                                    int gst_values = Integer.valueOf( GlobalValues.GstChargers).intValue();
//
//
//                                    mGST = (mGrandTotal * gst_values) / 100;
//                                    GlobalValues.GST=mGST;
//                                    mGrandTotal+=mGST;
//
//                                }
//
//
//                            } else {
//
//
//
//                                GlobalValues.DELEIVERY_CHARGES = "0.00";
//                                GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");
//                                txtDeliveryCharge.setText("$0.00");
//                                txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
//                                        Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));
//                                txtFreeDelivery.setText("FREE delivery!");
//
////                            progressBar.setProgress(1000);
//
//                                mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
//                                        Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"));
//
//                                if( GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID))
//                                {
//
//                                    mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
//                                            Double.valueOf(outletZoneJson.getString("zone_delivery_charge"));
//
//
//                                    mGST = (mGrandTotal * 7) / 100;
//                                    GlobalValues.GST=mGST;
//                                    mGrandTotal+=mGST;
//
//                                    txtGSTLabel.setText("GST ("+"7"+"%)" );
//                                    txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));
//
//
//
//                                }
//                                else
//                                {
//
//                                    int gst_values = Integer.valueOf( GlobalValues.GstChargers).intValue();
//
//
//                                    mGST = (mGrandTotal * gst_values) / 100;
//                                    GlobalValues.GST=mGST;
//                                    mGrandTotal+=mGST;
//
//
//                                }
//
//
//                            }
//
////                                Double.parseDouble(outletZoneJson.getString("zone_delivery_charge"));
//
//
//
//                        } else if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {
//
//                            GlobalValues.DELEIVERY_CHARGES = "0.00";
//                            GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = "0.00";
//
//                            mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));
//
//                        }


//                        subtotal_value = jsoncartDetails.getString("cart_sub_total");

                     /*   if (Double.parseDouble(outletZoneJson.optString("zone_free_delivery")) > 0) {

                            progressBar.setProgress(Double.valueOf(r_sub_total.trim()).intValue());
                            progressBar.setSecondaryProgress((int) Double.parseDouble(
                                    outletZoneJson.optString("zone_free_delivery")));
                        } else {

                            progressBar.setProgress(1000);
                            progressBar.setSecondaryProgress(1000);
                        }
*/

                      /*  if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {

                            mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                    Double.parseDouble(outletZoneJson.getString("zone_delivery_charge")) +
                                    Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));

//                                    Double.parseDouble(outletZoneJson.getString("zone_delivery_charge"));

                        } else if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {

                            mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));
//                        }*/
//                        txtGST.setText("$" + String.format("%.2f", mGST));
//                        txtTotal.setText("$" + String.format("%.2f", mGrandTotal));
//
//                        InclusiveGst(mGrandTotal);
//
//                        JSONArray jsonCartItem = jsonResult.getJSONArray("cart_items");
//
//                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, jsonObject.getJSONObject("contents").toString());
//
//
//
////                        Remove applied coupon or reward points
//
//
//                        setCartAdapter(jsonCartItem);
//                    } else {
//
//                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, "");
//
//                        Utility.writeToSharedPreference(mContext, GlobalValues.BENTO_CART_COUNT, "");
//
//                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_COUNT, "");
//
//                        invalidateOptionsMenu();
//                        finish();
//
//                        try {
//                            databaseHandler.deleteAllCartQuantity();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                        Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//
//
//                    }


     /*               } else {


                        Utility.writeToSharedPreference(context, GlobalValues.CART_COUNT,
                                "0");
                    }
                } else {
                    Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                }


                invalidateOptionsMenu();
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            progressDialog.dismiss();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_home, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private class DestroyCartTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        Map<String, String> params1;

        public DestroyCartTask(Map<String, String> params) {
            this.params1 = params;
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

            String response = WebserviceAssessor.postRequest(context, params[0], params1);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {


                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {


                    Utility.writeToSharedPreference(context, GlobalValues.CART_COUNT,
                            "0");
                    // Toast.makeText(context, "Cart Emptied", Toast.LENGTH_SHORT).show();
                    cartlist.clear();
                    cartItemAdapter.notifyDataSetChanged();
                    finish();


                    Intent intent = new Intent(CartActivity.this, HomeActivity.class);
                    startActivity(intent);
//                subtotaltextview.setText("");
//                hidecartemptyvalueslayout.setVisibility(View.GONE);
//                hidecartlayoutifempty.setVisibility(View.GONE);

                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }

            progressDialog.dismiss();
        }
    }
}


*/