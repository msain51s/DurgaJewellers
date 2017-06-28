package com.sdj_jewellers.db;

/**
 * Created by Administrator on 4/18/2017.
 */

public class DB_Data {
    public final static String DB_NAME="sdj";
    public final static int DB_VERSION = 1;
    public static final String TABLE_NAME = "sdj_data_table";

    /*TABLE COLUMN*/
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_POST_TITLE = "post_title";
    public static final String COLUMN_POST_DATE = "post_date";
    public static final String COLUMN_POST_CONTENT = "post_content";
    public static final String COLUMN_POST_EXCERPT = "post_excerpt";
    public static final String COLUMN_POST_STATUS = "post_status";
    public static final String COLUMN_POST_NAME = "post_name";
    public static final String COLUMN_POST_MODIFIED_DATE = "post_modified_date";
    public static final String COLUMN_POST_TYPE = "post_type";
    public static final String COLUMN_IMAGE_NAME = "image_name";
    public static final String COLUMN_IMAGE_URL = "image_url";
    public static final String COLUMN_PARENT = "parent";
    public static final String COLUMN_TERM_TAXONOMY_ID = "term_taxonomy_id";

    public static final String COLUMN_PRODUCT_QUANTITY = "product_quantity";


    /*CREATE ORDER TABLE QUERY*/
    public static String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_NAME
            + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"+ COLUMN_POST_TITLE + " TEXT,"
            + COLUMN_POST_DATE + " TEXT," + COLUMN_POST_CONTENT + " TEXT,"
            + COLUMN_POST_EXCERPT + " TEXT," + COLUMN_POST_STATUS + " TEXT,"+
            COLUMN_POST_NAME+" TEXT,"+COLUMN_POST_MODIFIED_DATE+" TEXT,"+
            COLUMN_POST_TYPE+" TEXT,"+COLUMN_IMAGE_NAME+" TEXT,"+COLUMN_IMAGE_URL+" TEXT," +
            COLUMN_PARENT+" TEXT,"+COLUMN_TERM_TAXONOMY_ID+" TEXT,"+COLUMN_PRODUCT_QUANTITY+" INTEGER )";

    /*DROP TABLE QUERY*/
    public static String DROP_TABLE_PRODUCT ="DROP TABLE IF EXISTS " + TABLE_NAME;

    public static String createProductTable() {
        return CREATE_PRODUCT_TABLE;
    }

    public static String getDropTableProduct() {
        return DROP_TABLE_PRODUCT;
    }

    public static String getCartData_query()
    {
        String orderListQuery = " select * from " + TABLE_NAME ;
        return orderListQuery;
    }
}
