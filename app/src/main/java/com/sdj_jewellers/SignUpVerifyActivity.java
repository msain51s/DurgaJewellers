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

public class SignUpVerifyActivity extends AppCompatActivity implements ResponseListener{
    private EditText otp;
    Handler h;
    String emailValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_verify);
        h=new Handler();

        otp= (EditText) findViewById(R.id.otp_editText_signUpVerify);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            emailValue=bundle.getString("email");
        }
    }

    public void backToLogin(View view){
        Intent intent=new Intent(SignUpVerifyActivity.this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
    public void clickConfirm(View view){
        attemptVerifySignUp();
    }

    public void clickResendOTP(View view){
        resendOtp(emailValue);
    }

    public void attemptVerifySignUp(){
        String otpValue=otp.getText().toString();

        boolean cancel=false;
        View focusView=null;

        if(TextUtils.isEmpty(otpValue)){
        //    otp.setError("OTP should not be empty !!!");
            Utils.showCommonInfoPrompt(this,"Alert","Please Enter OTP !!!");
            focusView=otp;
            cancel=true;
        }

        if(cancel){
            focusView.requestFocus();
        }else{
            // business logic for forgot password
            if (Utils.ChechInternetAvalebleOrNot(SignUpVerifyActivity.this)) {

                Utils.showLoader(SignUpVerifyActivity.this);
                ServerRequest
                        .postRequest(
                                Connection.BASE_URL + "verifyOTPSignup",
                                getVerifyOTPSignupData(otpValue),
                                SignUpVerifyActivity.this,
                                ResponseListener.REQUEST_SIGNUP_VERIFICATION);

            } else {
                //   Utils.showSnakeBar(layout_view, "internet not connected !!!", Color.RED);Toast.makeText(LoginActivity.this,"Internet not connected !!!",Toast.LENGTH_LONG).show();
                return;
            }
        }
    }

    public JSONObject getVerifyOTPSignupData(String otp) {
        JSONObject json = new JSONObject();
        try {
            json.put("email", emailValue);
            json.put("OTP", otp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;

    }


    public void resendOtp(String emailValue){
        if (Utils.ChechInternetAvalebleOrNot(SignUpVerifyActivity.this)) {

            Utils.showLoader(SignUpVerifyActivity.this);
            ServerRequest
                    .postRequest(
                            Connection.BASE_URL + "resendOTP",
                            getResendOTPData(emailValue),
                            SignUpVerifyActivity.this,
                            ResponseListener.REQUEST_RESEND_OTP_FOR_SIGNUP_VERIFICATION);

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
                if (rid == ResponseListener.REQUEST_SIGNUP_VERIFICATION) {

                    if (response.isError()) {
                        Toast.makeText(SignUpVerifyActivity.this, response.getErrorMsg(),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (response.getData() != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.getData());
                            String status=jsonObject.getString("status");

                 //           Toast.makeText(SignUpVerifyActivity.this,jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
                            if(status.equalsIgnoreCase("true"))
                                Utils.showCommonInfoPrompt(SignUpVerifyActivity.this,"Success",jsonObject.getString("msg"));
                            else
                                Utils.showCommonInfoPrompt(SignUpVerifyActivity.this,"Failed",jsonObject.getString("msg"));
                            Log.d("json_response", response.getData());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }else if (rid == ResponseListener.REQUEST_RESEND_OTP_FOR_SIGNUP_VERIFICATION) {

                    if (response.isError()) {
                        Toast.makeText(SignUpVerifyActivity.this, response.getErrorMsg(),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (response.getData() != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.getData());
                            String status=jsonObject.getString("status");

                            //           Toast.makeText(SignUpVerifyActivity.this,jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
                            if(status.equalsIgnoreCase("true"))
                                Utils.showCommonInfoPrompt(SignUpVerifyActivity.this,"Success",jsonObject.getString("msg"));
                            else
                                Utils.showCommonInfoPrompt(SignUpVerifyActivity.this,"Failed",jsonObject.getString("msg"));
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
