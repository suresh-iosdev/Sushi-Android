package com.app.sushi.tei.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.app.sushi.tei.Model.Cart.Cart;

import static com.app.sushi.tei.Database.SqliteQuerryManager.TABLE_CART;


@SuppressWarnings("unused")

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SPIZE";
    private static DatabaseHandler singleton;
    private SQLiteDatabase simpleDB;


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public static DatabaseHandler getInstance(final Context context) {
        if (singleton == null) {
            singleton = new DatabaseHandler(context.getApplicationContext());
        }
        return singleton;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SqliteQuerryManager.CREATE_CART_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i < DATABASE_VERSION) {
            sqLiteDatabase.execSQL(SqliteQuerryManager.CREATE_CART_TABLE);

        }

    }

    @Override
    public synchronized void close() {

        if (getActiveDBObj() != null && getActiveDBObj().isOpen())
            getActiveDBObj().close();

        super.close();
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

    private SQLiteDatabase getActiveDBObj() {
        return simpleDB;
    }


    public boolean insertProductData(String db_product_id, String db_product_qty) throws Exception {
        simpleDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SqliteQuerryManager.COL_PRODUCT_ID, db_product_id);
        contentValues.put(SqliteQuerryManager.COL_PRODUCT_QTY, db_product_qty);

        long result = simpleDB.insert(TABLE_CART, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }

    public int getQuantity(String id) {
        int qty = 0;
        try {

            simpleDB = this.getReadableDatabase();

            Cursor cursor = simpleDB.rawQuery("SELECT * FROM " + TABLE_CART + " WHERE "
                    + SqliteQuerryManager.COL_PRODUCT_ID + "=?", new String[]{id});

            if (cursor != null && cursor.getCount() > 0) {


                cursor.moveToFirst();
               do{

                    qty = Integer.parseInt(cursor.getString(cursor.getColumnIndex(SqliteQuerryManager.COL_PRODUCT_QTY)));


                } while (cursor.moveToNext());
            } else {
                qty = 0;

            }
        } catch (Exception e) {
            qty = 0;

            e.printStackTrace();
        }

        return qty;
    }

    public void updateQty(String id, String qty) {
        simpleDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SqliteQuerryManager.COL_PRODUCT_QTY, qty);
        simpleDB.update(TABLE_CART, contentValues, SqliteQuerryManager.COL_PRODUCT_ID + " =?", new String[]{id});
    }

    //    public ModeldatabaseProducts getproductdata(String id) throws Exception {
//
//        ModeldatabaseProducts modifiers = null;
//        simpleDB = this.getReadableDatabase();
//        Cursor cur;
//        cur = simpleDB.rawQuery("SELECT " + SqliteQuerryManager.COL_PRODUCT_ID + "," + SqliteQuerryManager.COL_PRODUCT_SLUG + "," + SqliteQuerryManager.COL_ADDRESS + "," + SqliteQuerryManager.COL_IMAGE_URL + "," + SqliteQuerryManager.COL_PRICE_PER_BOX + "," + SqliteQuerryManager.COL_PRODUCT_QTY + "," + SqliteQuerryManager.COL_PRODUCT_HEADER + "," + SqliteQuerryManager.COL_PRODUCT_ITEM + "," + SqliteQuerryManager.COL_PRODUCT_SHORT_DESCRIPTION + "," + SqliteQuerryManager.COL_PRODUCT_DESCRIPTION + "," + SqliteQuerryManager.COL_PRODUCT_SUBTOTAL + "," + SqliteQuerryManager.COL_PRODUCT_ADDONTOTAL + "," + SqliteQuerryManager.COL_PRODUCT_EQUIPTOTAL + "," + SqliteQuerryManager.COL_PRODUCT_TOTAL + " FROM " + SqliteQuerryManager.TABLE_CART + " WHERE "
//                + SqliteQuerryManager.COL_PRODUCT_ID + "=?", new String[]{id});
//
//        if (cur != null && cur.getCount() > 0 && cur.moveToFirst()) {
//            do {
//                modifiers = new ModeldatabaseProducts();
//                modifiers.setPRODUCTID(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_PRODUCT_ID)));
//                modifiers.setPRODUCTSLUG(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_PRODUCT_SLUG)));
//                modifiers.setADDRESS(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_ADDRESS)));
//                modifiers.setIMAGEURL(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_IMAGE_URL)));
//                modifiers.setPRICEPERBOX(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_PRICE_PER_BOX)));
//                modifiers.setPRODUCTQTY(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_PRODUCT_QTY)));
//                modifiers.setPRODUCTHEADER(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_PRODUCT_HEADER)));
//                modifiers.setPRODUCTITEM(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_PRODUCT_ITEM)));
//                modifiers.setPRODUCTSHORTDESCRIPTION(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_PRODUCT_SHORT_DESCRIPTION)));
//                modifiers.setPRODUCTDESCRIPTION(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_PRODUCT_DESCRIPTION)));
//                modifiers.setPRODUCTSUBTOTAL(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_PRODUCT_SUBTOTAL)));
//                modifiers.setPRODUCTADDONTOTAL(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_PRODUCT_ADDONTOTAL)));
//                modifiers.setPRODUCTEQUIPTOTAL(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_PRODUCT_EQUIPTOTAL)));
//                modifiers.setPRODUCTTOTAL(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_PRODUCT_TOTAL)));
//
//
//            } while (cur.moveToNext());
//        }
//        if (cur != null) {
//            cur.close();
//        }
//
//
//        return modifiers;
//
//    }
    public Cart getAllTotalData(String id) throws Exception {
        Cart modifiers = null;

        simpleDB = this.getReadableDatabase();
        Cursor cur;
        cur = simpleDB.rawQuery("SELECT * FROM " + TABLE_CART + " WHERE " + SqliteQuerryManager.COL_PRODUCT_ID + "=?", new String[]{id});

        if (cur != null && cur.getCount() > 0 && cur.moveToFirst()) {
            do {
                modifiers = new Cart();
                modifiers.setmProductQty(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_PRODUCT_QTY)));

            } while (cur.moveToNext());
        }
        if (cur != null) {
            cur.close();
        }

        return modifiers;
//
    }

    //
//
//    public ArrayList<Modifiers> getModifiersData(String id) throws Exception {
//        ArrayList<Modifiers> arrModelAction = null;
//
//        simpleDB = this.getReadableDatabase();
//        Cursor cur;
//        cur = simpleDB.rawQuery("SELECT * FROM " + SqliteQuerryManager.TABLE_MODIFIERS + " WHERE "
//                + SqliteQuerryManager.COL_ID + "=?", new String[]{id});
//
//        if (cur != null && cur.getCount() > 0 && cur.moveToFirst()) {
//            arrModelAction = new ArrayList<>();
//            do {
//                Modifiers modifiers = new Modifiers();
//                modifiers.setProModifierId(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_MODIFIERS_ID)));
//                modifiers.setProModifierName(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_MODIFIERS_NAME)));
//                modifiers.setProModifierMinSelect(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_MODIFIERS_MAX_SELECT)));
//
//
//                arrModelAction.add(modifiers);
//            } while (cur.moveToNext());
//        }
//        if (cur != null) {
//            cur.close();
//        }
//
//        return arrModelAction;
//
//    }
//
//
//    public ArrayList<ModifiersValuesItem> getModifiersFilterData(String cartId, String id) throws Exception {
//        ArrayList<ModifiersValuesItem> arrModelAction = null;
//
//        simpleDB = this.getReadableDatabase();
//        Cursor cur;
//        cur = simpleDB.rawQuery("SELECT * FROM " + SqliteQuerryManager.TABLE_MODIFIERS_VALUES + " WHERE "
//                + SqliteQuerryManager.COL_FILTER_ID + " =? AND " + SqliteQuerryManager.COL_MODIFIERS_PARENT_ID + " =? ", new String[]{cartId, id});
//
//        if (cur != null && cur.getCount() > 0 && cur.moveToFirst()) {
//            arrModelAction = new ArrayList<>();
//            do {
//                ModifiersValuesItem modifiers = new ModifiersValuesItem();
//                modifiers.setProModifierValueId(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_MODIFIERS_FILTER_ID)));
//                modifiers.setProModifierValueName(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_MODIFIERS_FILTER_NAME)));
//                modifiers.setModifier_value_qty(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_MODIFIERS_FILTER_QTY)));
//                modifiers.setSelected(Boolean.parseBoolean(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_MODIFIERS_FILTER_ISSELECTED))));
//                modifiers.setProModifierValueModifierId(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_MODIFIERS_PARENT_ID)));
//
//
//                arrModelAction.add(modifiers);
//            } while (cur.moveToNext());
//        }
//        if (cur != null) {
//            cur.close();
//        }
//
//        return arrModelAction;
//
//    }
//
//
//    public ArrayList<ModelAddOns> getAddonsData(String id) throws Exception {
//        ArrayList<ModelAddOns> arrModelAction = null;
//
//        simpleDB = this.getReadableDatabase();
//        Cursor cur;
//        cur = simpleDB.rawQuery("SELECT * FROM " + SqliteQuerryManager.TABLE_ADDONS + " WHERE "
//                + SqliteQuerryManager.COL_P_ID + "=?", new String[]{id});
//
//        if (cur != null && cur.getCount() > 0 && cur.moveToFirst()) {
//            arrModelAction = new ArrayList<>();
//            do {
//                ModelAddOns modifiers = new ModelAddOns();
//                modifiers.setProductPrimaryId(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_PRODUCT_PRIMARY_ID)));
//                modifiers.setProductId(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_PRODUCT_P_ID)));
//                modifiers.setProductName(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_PRODUCT_NAME)));
//                modifiers.setProductPrice(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_PRODUCT_PRICE)));
//                modifiers.setProductCost(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_PRODUCT_COST)));
//                modifiers.setProductSlug(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_PRODUCT_ADDONS_SLUG)));
//                modifiers.setProductLongDescription(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_PRODUCT_LDESCRIBE)));
//                modifiers.setProductCateringLabel(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_PRODUCT_Label)));
//                modifiers.setProductCateringAddonsId(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_PRODUCT_ADDONS_ID)));
//                modifiers.setProductquantity(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_PRODUCT_ADDONS_QTY)));
//                modifiers.setProductTotalPrice(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_PRODUCT_ADDONS_TOTAL)));
//                arrModelAction.add(modifiers);
//            } while (cur.moveToNext());
//        }
//        if (cur != null) {
//            cur.close();
//        }
//
//        return arrModelAction;
//
//    }
//
//
//    public ArrayList<ModelEquip> getEquipData(String id) throws Exception {
//        ArrayList<ModelEquip> arrModelAction = null;
//
//        simpleDB = this.getReadableDatabase();
//        Cursor cur;
//        cur = simpleDB.rawQuery("SELECT * FROM " + SqliteQuerryManager.TABLE_EQUIPONS + " WHERE "
//                + SqliteQuerryManager.COL_E_ID + "=?", new String[]{id});
//
//        if (cur != null && cur.getCount() > 0 && cur.moveToFirst()) {
//            arrModelAction = new ArrayList<>();
//            do {
//                ModelEquip modifiers = new ModelEquip();
//                modifiers.setCateringEquipmentSetupId(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_ESETUP_ID)));
//                modifiers.setCateringEquipmentCompanyId(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_ECOMPANY_ID)));
//                modifiers.setCateringEquipmentAppId(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_EAPP_ID)));
//                modifiers.setCateringEquipmentTitle(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_E_TITLE)));
//                modifiers.setCateringEquipmentSlug(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_E_SLUG)));
//                modifiers.setCateringEquipmentDescription(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_E_DES)));
//                modifiers.setCateringEquipmentPrice(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_E_PRICE)));
//                modifiers.setProductCateringEquipmentId(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_E_PRODUCT_ID)));
//                modifiers.setProductquantity(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_PRODUCT_EQUIP_QTY)));
//                modifiers.setProductTotalPrice(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_PRODUCT_EQUIP_TOTAL)));
//
//                arrModelAction.add(modifiers);
//            } while (cur.moveToNext());
//        }
//        if (cur != null) {
//            cur.close();
//        }
//
//        return arrModelAction;
//
//    }
//
//    public ArrayList<ModelCateringHall> getCateringData(String id) throws Exception {
//        ArrayList<ModelCateringHall> arrModelAction = null;
//
//        simpleDB = this.getReadableDatabase();
//        Cursor cur;
//        cur = simpleDB.rawQuery("SELECT * FROM " + SqliteQuerryManager.TABLE_CATERING_HALL + " WHERE "
//                + SqliteQuerryManager.COL_C_ID + "=?", new String[]{id});
//
//        if (cur != null && cur.getCount() > 0 && cur.moveToFirst()) {
//            arrModelAction = new ArrayList<>();
//            do {
//                ModelCateringHall modifiers = new ModelCateringHall();
////                modifiers.set(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_CVENUE_TYPE)));
//                modifiers.setCateringHallAppId(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_C_HALL)));
//                modifiers.setCateringHallPrice(cur.getString(cur.getColumnIndex(SqliteQuerryManager.COL_C_HALL_CHARGES)));
//
//
//                arrModelAction.add(modifiers);
//            } while (cur.moveToNext());
//        }
//        if (cur != null) {
//            cur.close();
//        }
//
//        return arrModelAction;
//
//    }
//
//    public void deleteAllData(String id) throws Exception {
//        simpleDB = this.getWritableDatabase();
//        simpleDB.delete(SqliteQuerryManager.TABLE_CART, SqliteQuerryManager.COL_PRODUCT_ID + " =?", new String[]{id});
//        simpleDB.delete(SqliteQuerryManager.TABLE_MODIFIERS, SqliteQuerryManager.COL_ID + " =?", new String[]{id});
//        simpleDB.delete(SqliteQuerryManager.TABLE_MODIFIERS_VALUES, SqliteQuerryManager.COL_FILTER_ID + " =?", new String[]{id});
//        simpleDB.delete(SqliteQuerryManager.TABLE_ADDONS, SqliteQuerryManager.COL_P_ID + " =?", new String[]{id});
//        simpleDB.delete(SqliteQuerryManager.TABLE_EQUIPONS, SqliteQuerryManager.COL_E_ID + " =?", new String[]{id});
//        simpleDB.delete(SqliteQuerryManager.TABLE_CATERING_HALL, SqliteQuerryManager.COL_C_ID + " =?", new String[]{id});
//
//
//    }
//
    public void deleteCartQuantity(String id) throws Exception {
        simpleDB = this.getWritableDatabase();
        int count = simpleDB.delete(TABLE_CART, SqliteQuerryManager.COL_PRODUCT_ID + " =?", new String[]{id});

    }

    public void deleteAllCartQuantity() throws Exception {

        simpleDB = this.getWritableDatabase();

        simpleDB.execSQL("delete from " + TABLE_CART);

        long count = DatabaseUtils.queryNumEntries(simpleDB, TABLE_CART);


        simpleDB.close();
    }

//    public void deleteEquipData(String id) throws Exception {
//        simpleDB = this.getWritableDatabase();
//        simpleDB.delete(SqliteQuerryManager.TABLE_EQUIPONS, SqliteQuerryManager.COL_E_ID + " =?", new String[]{id});
//    }

    public boolean isMasterEmpty() throws Exception {

        boolean flag;

        String quString = "SELECT * FROM " + TABLE_CART;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(quString, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);

        if (count == 1) {
            flag = false;
        } else {
            flag = true;
        }
        cursor.close();
        db.close();

        return flag;
    }

}
