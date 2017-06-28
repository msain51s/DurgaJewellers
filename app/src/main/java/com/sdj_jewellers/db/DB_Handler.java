package com.sdj_jewellers.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.sdj_jewellers.model.CategoryInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 4/18/2017.
 */

public class DB_Handler extends SQLiteOpenHelper {
    String mUserName;
    public DB_Handler(Context context) {
        super(context, DB_Data.DB_NAME, null, DB_Data.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_Data.createProductTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DB_Data.getDropTableProduct());
        onCreate(db);
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

/*GET CART DATA*/

    public ArrayList<CategoryInfo> getCartData() {

        ArrayList<CategoryInfo> list = new ArrayList<CategoryInfo>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(DB_Data.getCartData_query(), null);

        CategoryInfo obj = new CategoryInfo();

        if (cursor.moveToFirst()) {
            do {

                obj = new CategoryInfo();
                obj.setCatID(cursor.getInt(0));
                obj.setPostTitle(cursor.getString(1));
                obj.setPostDate(cursor.getString(2));
                obj.setPostContent(cursor.getString(3));
                obj.setPostExcerpt(cursor.getString(4));
                obj.setPostStatus(cursor.getString(5));
                obj.setPostName(cursor.getString(6));
                obj.setPostModifiedDate(cursor.getString(7));
                obj.setPostType(cursor.getString(8));
                obj.setImage(cursor.getString(9));
                obj.setImagePath(cursor.getString(10));
                obj.setParent(cursor.getString(11));
                obj.setTermTaxonomyID(cursor.getString(12));
                obj.setProduct_quantity(cursor.getInt(13));
                list.add(obj);
            } while (cursor.moveToNext());

            for (int i = list.size(); i > cursor.getCount(); i--) {
                list.remove(i);
            }
            cursor.close();
        }
        db.close();

        return list;

    }

    /*ADD PRODUCT TO DB*/

    public void addDataToCart(CategoryInfo categoryInfo) {

        SQLiteDatabase db=null;
        try {
            ContentValues values = new ContentValues();
            values.put(DB_Data.COLUMN_POST_TITLE, categoryInfo.getPostTitle());
            values.put(DB_Data.COLUMN_POST_DATE, categoryInfo.getPostDate());
            values.put(DB_Data.COLUMN_POST_CONTENT, categoryInfo.getPostContent());
            values.put(DB_Data.COLUMN_POST_EXCERPT, categoryInfo.getPostExcerpt());
            values.put(DB_Data.COLUMN_POST_STATUS, categoryInfo.getPostStatus());
            values.put(DB_Data.COLUMN_POST_NAME, categoryInfo.getPostName());
            values.put(DB_Data.COLUMN_POST_MODIFIED_DATE, categoryInfo.getPostModifiedDate());
            values.put(DB_Data.COLUMN_POST_TYPE, categoryInfo.getPostType());
            values.put(DB_Data.COLUMN_IMAGE_NAME, categoryInfo.getImage());
            values.put(DB_Data.COLUMN_IMAGE_URL, categoryInfo.getImagePath());
            values.put(DB_Data.COLUMN_PARENT, categoryInfo.getParent());
            values.put(DB_Data.COLUMN_TERM_TAXONOMY_ID, categoryInfo.getTermTaxonomyID());
            values.put(DB_Data.COLUMN_PRODUCT_QUANTITY, categoryInfo.getProduct_quantity());
             db = this.getWritableDatabase();

            db.insert(DB_Data.TABLE_NAME, null, values);
        }catch (Exception e){
            e.printStackTrace();
        }
        /*finally {
            db.close();
        }*/

    }

/*GET UNREAD CHAT MESSAGE COUNT*/

   /* public String getUnReadMessageCount(String sender, String receiver) {
        String qty = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(DB_Data.getUnReadMessage_query(),
                new String[]{"false",sender, receiver});

        ChatMessage data = new ChatMessage();
        if (cursor.moveToFirst()) {
            data.setID(cursor.getInt(0));
            Integer i=data.getID();
            qty=i.toString();
        }
        cursor.close();
        db.close();
        return qty;
    }

 *//*UPDATE READ MESSAGE COUNT FLAG*//*

    public void updateReadMessageCountFlag(String sender, String receiver) {
        ContentValues values = new ContentValues();
        values.put(DB_Data.COLUMN_MESSAGE_FLAG, "true");
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(DB_Data.TABLE_NAME, values, DB_Data.COLUMN_MESSAGE_FLAG + " = ? AND "
                        +DB_Data.COLUMN_SENDER_NAME+"=?"+" AND "
                        +DB_Data.COLUMN_RECEIVER_NAME+"=?",
                new String[]{"false", sender, receiver});

        db.close();

    }

     *//* DELIVERED/SEEN MESSAGE STATUS UPDATE*//*

    public void updateMessageDeliveryStatus(String sender, String receiver, String messageReceiptId, int statusId) {
        ContentValues values = new ContentValues();
        values.put(DB_Data.COLUMN_MESSAGE_DELIVERY_STATUS, statusId);
        SQLiteDatabase db = this.getWritableDatabase();
       long i= db.update(DB_Data.TABLE_NAME, values, DB_Data.COLUMN_MESSAGE_DELIVERY_RECEIPT_ID + " = ? AND "
                        +DB_Data.COLUMN_SENDER_NAME+"=?"+" AND "
                        +DB_Data.COLUMN_RECEIVER_NAME+"=?",
                new String[]{messageReceiptId, sender, receiver});

        Log.d("row updated count",""+i);

   //     db.close();

    }

    *//* DELIVERED/SEEN MESSAGE STATUS UPDATE*//*

    public void updateMessageDeliveryID(String sender, String receiver, String messageReceiptId, String updatedMessageReceiptId, int statusId) {
        ContentValues values = new ContentValues();
        values.put(DB_Data.COLUMN_MESSAGE_DELIVERY_RECEIPT_ID, updatedMessageReceiptId);
        values.put(DB_Data.COLUMN_MESSAGE_DELIVERY_STATUS, statusId);
        SQLiteDatabase db = this.getWritableDatabase();
        long i= db.update(DB_Data.TABLE_NAME, values, DB_Data.COLUMN_MESSAGE_DELIVERY_RECEIPT_ID + " = ? AND "
                        +DB_Data.COLUMN_SENDER_NAME+"=?"+" AND "
                        +DB_Data.COLUMN_RECEIVER_NAME+"=?",
                new String[]{messageReceiptId, sender, receiver});

        Log.d("row updated count",""+i);

    //    db.close();

    }
    public void updateMessageDeliveryStatus(String sender, String receiver, int statusId) {
        ContentValues values = new ContentValues();
        values.put(DB_Data.COLUMN_MESSAGE_DELIVERY_STATUS, statusId);
        SQLiteDatabase db = this.getWritableDatabase();
        long i= db.update(DB_Data.TABLE_NAME, values, DB_Data.COLUMN_MESSAGE_DELIVERY_STATUS + " = ? AND "
                        +DB_Data.COLUMN_SENDER_NAME+"=?"+" AND "
                        +DB_Data.COLUMN_RECEIVER_NAME+"=?",
                new String[]{"1", sender, receiver});

        Log.d("row updated count",""+i);

    //    db.close();

    }*/

}
