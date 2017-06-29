package com.sdj_jewellers.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bhanwar on 01/03/2016.
 */
public class Preference {

    SharedPreferences sharedpreferences;
    public String pref_name="Jai_durga";
    SharedPreferences.Editor editor ;

    public String IS_LOGGED_IN="isLogin";
    public String USER_ID="user_id";
    public String EMAIL_ID="email_id";
    public String MOBILE_NUMBER="mobile_number";
    public String FIRST_NAME="firstName";

    public int getCART_COUNT() {
        return sharedpreferences.getInt(CART_COUNT,0);
    }

    public void setCART_COUNT(int CART_COUNT) {
        editor.putInt(this.CART_COUNT,CART_COUNT);
        editor.commit();
    }

    public String CART_COUNT="cartCount";
    public String getLAST_NAME() {
        return sharedpreferences.getString(LAST_NAME,"");
    }

    public void setLAST_NAME(String LAST_NAME) {

        editor.putString(LAST_NAME,LAST_NAME);
        editor.commit();
    }

    public String getFIRST_NAME() {
       return sharedpreferences.getString(FIRST_NAME,"");
    }

    public void setFIRST_NAME(String FIRST_NAME) {
        editor.putString(FIRST_NAME,FIRST_NAME);
        editor.commit();
    }

    public String getCOMPANY_NAME() {
        return sharedpreferences.getString(COMPANY_NAME,"");
    }

    public void setCOMPANY_NAME(String COMPANY_NAME) {
        editor.putString(COMPANY_NAME,COMPANY_NAME);
        editor.commit();
    }

    public String getCITY_NAME() {
        return sharedpreferences.getString(CITY_NAME,"");
    }

    public void setCITY_NAME(String CITY_NAME) {
        editor.putString(CITY_NAME,CITY_NAME);
        editor.commit();
    }

    public String LAST_NAME="lastName";
    public String COMPANY_NAME="companyName";
    public String CITY_NAME="cityName";
    public Preference(Context ctx)
    {
        sharedpreferences = ctx.getSharedPreferences(pref_name, Context.MODE_PRIVATE);
        editor= sharedpreferences.edit();
    }

    public void setUSER_ID(String id)
    {
        editor.putString(USER_ID,id);
        editor.commit();
    }
    public String getUSER_ID()
    {
        return sharedpreferences.getString(USER_ID,"");
    }

    public void setEMAIL_ID(String email_id)
    {
        editor.putString(EMAIL_ID,email_id);
        editor.commit();
    }
    public String getEMAIL_ID()
    {
        return sharedpreferences.getString(EMAIL_ID,"");
    }


    public void setMOBILE_NUMBER(String mobile_number)
    {
        editor.putString(MOBILE_NUMBER,mobile_number);
        editor.commit();
    }
    public String getMOBILE_NUMBER()
    {
        return sharedpreferences.getString(MOBILE_NUMBER,"");
    }

    public void setLoggedIn(boolean status)
    {
        editor.putBoolean(IS_LOGGED_IN,status);

        editor.commit();
    }
    public boolean isLoggedIn()
    {
        return sharedpreferences.getBoolean(IS_LOGGED_IN,false);
    }


    public void clearAllPrefereces()
    {
        editor.clear();
        editor.commit();
    }
}
