package com.sdj_jewellers.utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sdj_jewellers.CartActivity;
import com.sdj_jewellers.CategoryInfoActivity;
import com.sdj_jewellers.ProductDetailActivity;
import com.sdj_jewellers.R;
import com.sdj_jewellers.adapter.FilterListAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by bhanwar on 16/06/2017.
 */

public class Utils {

    public static AVLoadingIndicatorView loaderView;
    public static Dialog dialog;
    public static Dialog dialog11;
    public static void showSnakeBar(View layout_view, String msg, int color)
    {
        Snackbar snackbar = Snackbar.make(layout_view, msg, Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);

        snackbar.show();
    }


    public static boolean ChechInternetAvalebleOrNot(Activity act) {

        ConnectivityManager connectivityManager = (ConnectivityManager) act
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static TextView showLoader(Context context){

        dialog=new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view=LayoutInflater.from(context)
                .inflate(R.layout.custom_loader,null);
        loaderView= (AVLoadingIndicatorView) view.findViewById(R.id.custom_loader);
        final TextView textView= (TextView) view.findViewById(R.id.loader_text);

        dialog.setContentView(view);
        dialog.setCancelable(false);

        loaderView.show();
        dialog.show();

        return textView;
    }
    public static void dismissLoader(){
        if(loaderView!=null){
            loaderView.hide();
            dialog.dismiss();
        }
    }

    public static Typeface getCustomFont(Context context,int type){
        Typeface typeface=null;
        if(type==FontType.ROBOTO_LIGHT)
            typeface=Typeface.createFromAsset(Application.mContext.getAssets(), "fonts/Roboto-Light.ttf");
        else if(type==FontType.ROBOTO_THIN_ITALIC)
            typeface= Typeface.createFromAsset(Application.mContext.getAssets(), "fonts/Roboto-ThinItalic.ttf");
        else if(type==FontType.ROBOTO_THIN)
            typeface= Typeface.createFromAsset(Application.mContext.getAssets(), "fonts/Roboto-Thin.ttf");
        else if(type==FontType.ROBOTO_MEDIUM)
            typeface= Typeface.createFromAsset(Application.mContext.getAssets(), "fonts/Roboto-Medium.ttf");
        else if(type==FontType.ROBOTO_CONDENSED)
            typeface= Typeface.createFromAsset(Application.mContext.getAssets(), "fonts/Roboto-Condensed.ttf");
        else if(type==FontType.ROBOTO_BOLD)
            typeface= Typeface.createFromAsset(Application.mContext.getAssets(), "fonts/Roboto-Bold.ttf");
        else if(type==FontType.ROBOTO_REGULAR)
            typeface= Typeface.createFromAsset(Application.mContext.getAssets(), "fonts/Roboto-Regular.ttf");
        else if(type==FontType.MONESTER_RAT_REGULAR)
            typeface= Typeface.createFromAsset(Application.mContext.getAssets(), "fonts/Montserrat-Regular.otf");
        else if(type==FontType.MONESTER_RAT_LIGHT)
            typeface= Typeface.createFromAsset(Application.mContext.getAssets(), "fonts/Montserrat-Light.otf");
        else if(type==FontType.MONESTER_RAT_BLACK)
            typeface= Typeface.createFromAsset(Application.mContext.getAssets(), "fonts/Montserrat-Black.otf");
        else if(type==FontType.MONESTER_RAT_BOLD)
            typeface= Typeface.createFromAsset(Application.mContext.getAssets(), "fonts/Montserrat-Bold.otf");

        return typeface;
    }

    public static void showFilterPrompt(final Context context, String title){
//        Typeface roboto_ligh=getCustomFont(context, FontType.ROBOTO_MEDIUM);

        LayoutInflater inflater=LayoutInflater.from(context);
        View prompt_view=inflater.inflate(R.layout.dialog_list_prompt, null);
        dialog11=new Dialog(context);
        dialog11.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog11.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog11.setContentView(prompt_view);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        dialog11.getWindow().setLayout((6 * width)/9, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);

        final TextView title_txt= (TextView) prompt_view.findViewById(R.id.dialog_title_text);
   //     title_txt.setTypeface(roboto_ligh);
        title_txt.setText(title);
        ListView listView= (ListView) prompt_view.findViewById(R.id.filter_listview);

        ArrayList<String> list=new ArrayList<>();
        list.add("2-5 gms");
        list.add("5-10 gms");
        list.add("10-20 gms");
        list.add("20-30 gms");
        list.add("> 30 gms");
        listView.setAdapter(new FilterListAdapter(context,list));

        dialog11.show();
       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.print("done");
                if(type.equalsIgnoreCase("user")) {
                    selectedText.setText(list.get(position).getName());
                    Application.selectedUserTypeId = list.get(position).getId();
                    if(list.get(position).getName().equalsIgnoreCase("Tenant")) {
                        propertyTypeLayout.setVisibility(View.VISIBLE);
                        requestPropertyTypeList(context);
                    }
                    else {
                        propertyTypeLayout.setVisibility(View.GONE);
                        Application.selectedPropertyTypeId=-1;
                    }

                }else if(type.equalsIgnoreCase("property")) {
                    selectedText.setText(propertyList.get(position).getPropertyName());
                    Application.selectedPropertyTypeId=propertyList.get(position).getId();

                }
                dialog11.dismiss();
            }
        });*/
    }

    public static void showSortByPrompt(final Context context, String title){
//        Typeface roboto_ligh=getCustomFont(context, FontType.ROBOTO_MEDIUM);

        LayoutInflater inflater=LayoutInflater.from(context);
        View prompt_view=inflater.inflate(R.layout.dialog_list_prompt, null);
        dialog11=new Dialog(context);
        dialog11.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog11.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog11.setContentView(prompt_view);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        dialog11.getWindow().setLayout((6 * width)/9, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);

        final TextView title_txt= (TextView) prompt_view.findViewById(R.id.dialog_title_text);
        //     title_txt.setTypeface(roboto_ligh);
        title_txt.setText(title);
        ListView listView= (ListView) prompt_view.findViewById(R.id.filter_listview);

        ArrayList<String> list=new ArrayList<>();
        list.add("Weight : High to Low");
        list.add("Weight : Low to High");

        listView.setAdapter(new FilterListAdapter(context,list));

        dialog11.show();
       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.print("done");
                if(type.equalsIgnoreCase("user")) {
                    selectedText.setText(list.get(position).getName());
                    Application.selectedUserTypeId = list.get(position).getId();
                    if(list.get(position).getName().equalsIgnoreCase("Tenant")) {
                        propertyTypeLayout.setVisibility(View.VISIBLE);
                        requestPropertyTypeList(context);
                    }
                    else {
                        propertyTypeLayout.setVisibility(View.GONE);
                        Application.selectedPropertyTypeId=-1;
                    }

                }else if(type.equalsIgnoreCase("property")) {
                    selectedText.setText(propertyList.get(position).getPropertyName());
                    Application.selectedPropertyTypeId=propertyList.get(position).getId();

                }
                dialog11.dismiss();
            }
        });*/
    }


    public static void showQuantityPrompt(final Context context, String title, final int position, String message, final int itemUpdateOrAdd, String quantity, final String from){
//        Typeface roboto_ligh=getCustomFont(context, FontType.ROBOTO_MEDIUM);

        LayoutInflater inflater=LayoutInflater.from(context);
        View prompt_view=inflater.inflate(R.layout.quantity_prompt_view, null);
        dialog11=new Dialog(context);
        dialog11.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog11.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog11.setContentView(prompt_view);
        dialog11.setCancelable(false);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        dialog11.getWindow().setLayout((6 * width)/9, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);

        final TextView title_txt= (TextView) prompt_view.findViewById(R.id.dialog_title_text);
        //     title_txt.setTypeface(roboto_ligh);
        title_txt.setText(title);
        final TextView message_txt= (TextView) prompt_view.findViewById(R.id.dialog_message_text);
        message_txt.setText(message);
        final EditText quantityEditText= (EditText) prompt_view.findViewById(R.id.quantity_editText);
        quantityEditText.setText(quantity);
        TextView submitButton= (TextView) prompt_view.findViewById(R.id.quantity_submit_btn);
        TextView cancelButton= (TextView) prompt_view.findViewById(R.id.quantity_cancel_btn);
        if(itemUpdateOrAdd==0){
            submitButton.setText("Add To Bag");
        }else if(itemUpdateOrAdd==1){
            submitButton.setText("Update Quantity");
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quantity_value=quantityEditText.getText().toString().trim();
                if(!quantity_value.equalsIgnoreCase("") && !quantity_value.equalsIgnoreCase("0")){
                    if(from.equalsIgnoreCase("CategoryInfo"))
                    ((CategoryInfoActivity)context).productAddToCart(position,quantity_value,itemUpdateOrAdd);
                    else if(from.equalsIgnoreCase("CartScreen"))
                        ((CartActivity)context).editItem(position,quantity_value);
                    else if(from.equalsIgnoreCase("ProductDetail"))
                        ((ProductDetailActivity)context).productAddToCart(quantity_value,itemUpdateOrAdd);
                    dialog11.dismiss();

                }else{
                    Toast.makeText(context,"Please Enter Quantity",Toast.LENGTH_LONG).show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialog11.dismiss();
            }
        });

        dialog11.show();

    }

    public static void showCommonInfoPrompt(final Context context, String title,String message){
//        Typeface roboto_ligh=getCustomFont(context, FontType.ROBOTO_MEDIUM);

        LayoutInflater inflater=LayoutInflater.from(context);
        View prompt_view=inflater.inflate(R.layout.info_prompt, null);
        dialog11=new Dialog(context);
        dialog11.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog11.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog11.setContentView(prompt_view);
        dialog11.setCancelable(false);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        dialog11.getWindow().setLayout((6 * width)/9, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);

        final TextView title_txt= (TextView) prompt_view.findViewById(R.id.dialog_title_text);
        //     title_txt.setTypeface(roboto_ligh);
        title_txt.setText(title);
        TextView message_txt= (TextView) prompt_view.findViewById(R.id.messageText);
        message_txt.setText(message);

        TextView okButton= (TextView) prompt_view.findViewById(R.id.OK_btn);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialog11.dismiss();
            }
        });

        dialog11.show();

    }

}
