package com.sdj_jewellers.service;

import com.sdj_jewellers.service.Response;

/**
 * Created by Administrator on 1/28/2016.
 */
public interface ResponseListener {
    public static final int REQUEST_TEXTPAD = 111;
    public static final int REQUEST_LOGIN=1;
    public static final int REQUEST_FORGET_PASSWORD=2;
    public static final int REQUEST_SIGN_UP=3;
    public static final int REQUEST_ALL_CATEGORY=4;
    public static final int REQUEST_CATEGORY_INFO=5;
    public static final int REQUEST_CHANGE_PASSWORD=6;
    public static final int REQUEST_SIGNUP_VERIFICATION=7;
    public static final int REQUEST_CHECK_CART_PRODUCTS=8;
    public static final int REQUEST_ADD_PRODUCT_TO_CART=9;
    public static final int REQUEST_UPDATE_CART_PRODUCTS=10;
    public static final int REQUEST_GET_CART_PRODUCTS_LIST=11;
    public static final int REQUEST_REMOVE_ITEM_FROM_CART=12;
    public static final int REQUEST_PLACE_ORDER=13;
    public static final int REQUEST_RESEND_OTP_FOR_SIGNUP_VERIFICATION=14;
    public static final int REQUEST_RESEND_OTP_FOR_FORGOT_PASSWORD=15;
    public static final int REQUEST_GET_SEARCH_PRODUCT_LIST=16;
    public static final int REQUEST_SEND_FCMID_TO_SERVER=17;

    public void onResponse(Response response, int rid);
}
