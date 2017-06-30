package com.sdj_jewellers;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sdj_jewellers.service.Response;
import com.sdj_jewellers.service.ResponseListener;
import com.sdj_jewellers.service.ServerRequest;
import com.sdj_jewellers.utility.Connection;
import com.sdj_jewellers.utility.Utils;

import org.json.JSONObject;

public class ChangePasswordActivity extends AppCompatActivity implements ResponseListener{
    private EditText otp,newPassword,confirmPassword;
    Handler h;
    String emailValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        h=new Handler();

        otp= (EditText) findViewById(R.id.opt_editText_changePasswor);
        newPassword= (EditText) findViewById(R.id.newPassword_editText_changePassword);
        confirmPassword= (EditText) findViewById(R.id.confirmPassword_editText_changePassword);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            emailValue=bundle.getString("email");
        }
    }

    public void backToLogin(View view){
        loginNavigation();
    }

    public void loginNavigation(){
        Intent intent=new Intent(ChangePasswordActivity.this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
    public void clickConfirm(View view){
        attemptForgotPassword();
    }

    public void clickResendOTP(View view){
        resendOtp(emailValue);
    }

    public void attemptForgotPassword(){
        String otpValue=otp.getText().toString();
        String newPasswordValue=newPassword.getText().toString();
        String confirmPasswordValue=confirmPassword.getText().toString();

        boolean cancel=false;
        View focusView=null;

        if(TextUtils.isEmpty(otpValue)){
        //    otp.setError("OTP should not be empty !!!");
            Utils.showCommonInfoPrompt(this,"Alert","Please Enter OTP !!!");
            focusView=otp;
            cancel=true;
        }else if(TextUtils.isEmpty(newPasswordValue)){
        //    newPassword.setError("Password should not be empty !!!");
            Utils.showCommonInfoPrompt(this,"Alert","Please Enter Password !!!");
            focusView=newPassword;
            cancel=true;
        }else if(TextUtils.isEmpty(confirmPasswordValue)){
        //    confirmPassword.setError("Confirm Password should not be empty !!!");
            Utils.showCommonInfoPrompt(this,"Alert","Please Enter Confirm Password !!!");
            focusView=confirmPassword;
            cancel=true;
        } else if(!confirmPasswordValue.equals(newPasswordValue)){
         //   confirmPassword.setError("Confirm Password didn't match !!!");
            Utils.showCommonInfoPrompt(this,"Alert","Confirm Password should be same as Password!!!");
            focusView=confirmPassword;
            cancel=true;
        }

        if(cancel){
            focusView.requestFocus();
        }else{
            // business logic for forgot password
            if (Utils.ChechInternetAvalebleOrNot(ChangePasswordActivity.this)) {

                Utils.showLoader(ChangePasswordActivity.this);
                ServerRequest
                        .postRequest(
                                Connection.BASE_URL + "verifyOTPforgetPsw",
                                getForgotPassData(emailValue,otpValue,newPasswordValue),
                                ChangePasswordActivity.this,
                                ResponseListener.REQUEST_CHANGE_PASSWORD);

            } else {
                //   Utils.showSnakeBar(layout_view, "internet not connected !!!", Color.RED);Toast.makeText(LoginActivity.this,"Internet not connected !!!",Toast.LENGTH_LONG).show();
                return;
            }
        }
    }

    public JSONObject getForgotPassData(String email,String otp,String newPassword) {
        JSONObject json = new JSONObject();
        try {
            json.put("email", email);
            json.put("OTP", otp);
            json.put("nPsw", newPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;

    }

    public void resendOtp(String emailValue){
        if (Utils.ChechInternetAvalebleOrNot(ChangePasswordActivity.this)) {

            Utils.showLoader(ChangePasswordActivity.this);
            ServerRequest
                    .postRequest(
                            Connection.BASE_URL + "resendOTP",
                            getResendOTPData(emailValue),
                            ChangePasswordActivity.this,
                            ResponseListener.REQUEST_RESEND_OTP_FOR_FORGOT_PASSWORD);

        } else {
            //   Utils.showSnakeBar(layout_view, "internet not connected !!!", Color.RED);Toast.makeText(LoginActivity.this,"Internet not connected !!!",Toast.LENGTH_LONG).show();
            return;
        }
    }

    public JSONObject getResendOTPData(String emailValue) {
        JSONObject json = new JSONObject();
        try {
            json.put("email", emailValue);
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
                if (rid == ResponseListener.REQUEST_CHANGE_PASSWORD) {

                    if (response.isError()) {
                        Toast.makeText(ChangePasswordActivity.this, response.getErrorMsg(),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (response.getData() != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.getData());
                            String status=jsonObject.getString("status");

                    //        Toast.makeText(ChangePasswordActivity.this,jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
                            if(status.equalsIgnoreCase("true")) {
                                Utils.showCommonInfoPromptWithLoginNavigation(ChangePasswordActivity.this, "Success", jsonObject.getString("msg"),"ChangePassword");
                            }
                            else
                                Utils.showCommonInfoPrompt(ChangePasswordActivity.this,"Failed",jsonObject.getString("msg"));

                            Log.d("json_response", response.getData());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }else if (rid == ResponseListener.REQUEST_RESEND_OTP_FOR_FORGOT_PASSWORD) {

                    if (response.isError()) {
                        Toast.makeText(ChangePasswordActivity.this, response.getErrorMsg(),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (response.getData() != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.getData());
                            String status=jsonObject.getString("status");

                            //           Toast.makeText(SignUpVerifyActivity.this,jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
                            if(status.equalsIgnoreCase("true"))
                                Utils.showCommonInfoPrompt(ChangePasswordActivity.this,"Success",jsonObject.getString("msg"));
                            else
                                Utils.showCommonInfoPrompt(ChangePasswordActivity.this,"Failed",jsonObject.getString("msg"));
                            Log.d("json_response", response.getData());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });
    }
}
