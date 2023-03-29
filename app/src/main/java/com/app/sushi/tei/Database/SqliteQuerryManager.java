package com.app.sushi.tei.Database;

/**
 * Created by gowtham on 08-03-2017.
 */
public class SqliteQuerryManager {

    static final String TABLE_CART = "CART_TABLE";


    //    static final String TABLE_REMINDER = "REMINDER_TABLE";


    //    product data
    public static final String COL_PRODUCT_ID = "PRODUCTID";
    public static final String COL_PRODUCT_QTY = "PRODUCTQTY";



    // product table
    static final String CREATE_CART_TABLE =
            "CREATE TABLE " + TABLE_CART + "("
                    + COL_PRODUCT_ID + " VARCHAR , "
                    + COL_PRODUCT_QTY + " VARCHAR  "
                    + ")";



}
