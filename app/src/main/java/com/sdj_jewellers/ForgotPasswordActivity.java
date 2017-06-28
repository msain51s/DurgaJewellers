package com.sdj_jewellers;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sdj_jewellers.service.Response;
import com.sdj_jewellers.service.ResponseListener;
import com.sdj_jewellers.service.ServerRequest;
import com.sdj_jewellers.utility.Connection;
import com.sdj_jewellers.utility.Utils;

import org.json.JSONObject;

public class ForgotPasswordActivity extends AppCompatActivity implements ResponseListener{

    private EditText email;
    Handler h;
    String emailValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        h=new Handler();

        email= (EditText) findViewById(R.id.email_editText_forgotPass);
    }

    public void backToLogin(View view){
        finish();
    }
    public void clickConfirm(View view){
       attemptForgotPassword();
    }

    public void attemptForgotPassword(){
        String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailValue=email.getText().toString();

        boolean cancel=false;
        View focusView=null;

        if(TextUtils.isEmpty(emailValue)){
    //        email.setError("Email should not be empty !!!");
            Utils.showCommonInfoPrompt(this,"Alert","Please Enter Email Address !!!");
            focusView=email;
            cancel=true;
        }else if(!emailValue.matches(emailPattern)){
    //        email.setError("Enter valid email address !!!");
            Utils.showCommonInfoPrompt(this,"Alert","Please Enter valid Email Address !!!");
            focusView=email;
            cancel=true;
        }

        if(cancel){
            focusView.requestFocus();
        }else{
            // business logic for forgot password
            if (Utils.ChechInternetAvalebleOrNot(ForgotPasswordActivity.this)) {
                this.emailValue=emailValue;
                Utils.showLoader(ForgotPasswordActivity.this);
                ServerRequest
                        .postRequest(
                                Connection.BASE_URL + "forgetPsw",
                                getForgotPassData(emailValue),
                                ForgotPasswordActivity.this,
                                ResponseListener.REQUEST_FORGET_PASSWORD);

            } else {
                //   Utils.showSnakeBar(layout_view, "internet not connected !!!", Color.RED);Toast.makeText(LoginActivity.this,"Internet not connected !!!",Toast.LENGTH_LONG).show();
                return;
            }
        }
    }

    public JSONObject getForgotPassData(String email) {
        JSONObject json = new JSONObject();
        try {
            json.put("email", email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;

    }

    @Override
    public void onResponse(final Response response, final int rid) {


        h.post(new Runnable() {

            @Override
            public void run() {
                Utils.dismissLoader();
                if (rid == ResponseListener.REQUEST_FORGET_PASSWORD) {

                    if (response.isError()) {
                        Toast.makeText(ForgotPasswordActivity.this, response.getErrorMsg(),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (response.getData() != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.getData());
                            String status=jsonObject.getString("status");

                    //            Toast.makeText(ForgotPasswordActivity.this,jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
                            if(status.equalsIgnoreCase("true"))
                                showCommonInfoPrompt(ForgotPasswordActivity.this,"Success",jsonObject.getString("msg"));
                            else
                                Utils.showCommonInfoPrompt(ForgotPasswordActivity.this,"Failed",jsonObject.getString("msg"));
                            Log.d("json_response", response.getData());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });
    }

    public void showCommonInfoPrompt(final Context context, String title, String message){
//        Typeface roboto_ligh=getCustomFont(context, FontType.ROBOTO_MEDIUM);

        LayoutInflater inflater=LayoutInflater.from(context);
        View prompt_view=inflater.inflate(R.layout.info_prompt, null);
        final Dialog dialog11=new Dialog(context);
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
                Intent intent=new Intent(ForgotPasswordActivity.this,ChangePasswordActivity.class);
                intent.putExtra("email",emailValue);
                ForgotPasswordActivity.this.startActivity(intent);
            }
        });

        dialog11.show();

    }
}
