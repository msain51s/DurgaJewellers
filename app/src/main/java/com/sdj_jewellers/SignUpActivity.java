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

public class SignUpActivity extends AppCompatActivity implements ResponseListener{

    private EditText firstName,lastName,companyName,city,contactNo,email,password,confirmPassword;
    Handler h;
    String emailValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        h=new Handler();

        firstName= (EditText) findViewById(R.id.firstName_editText_signUp);
        lastName= (EditText) findViewById(R.id.lastName_editText_signUp);
        companyName= (EditText) findViewById(R.id.companyName_editText_signUp);
        city= (EditText) findViewById(R.id.city_editText_signUp);
        contactNo= (EditText) findViewById(R.id.contactNo_editText_signUp);
        email= (EditText) findViewById(R.id.email_editText_signUp);
        password= (EditText) findViewById(R.id.password_editText_signUp);
        confirmPassword= (EditText) findViewById(R.id.confirm_password_editText_signUp);
    }
    public void backToLogin(View view){
        finish();
    }
    public void attemptSignUp(){
        String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        String firstNameValue=firstName.getText().toString();
        String lastNameValue=lastName.getText().toString();
        String companyNameValue=companyName.getText().toString();
        String cityValue=city.getText().toString();
        String contactNoValue=contactNo.getText().toString();
        String emailValue=email.getText().toString();
        String passwordValue=password.getText().toString();
        String confirmPasswordValue=confirmPassword.getText().toString();

        boolean cancel=false;
        View focusView=null;

        if(TextUtils.isEmpty(firstNameValue)){
        //    firstName.setError("This field is required !!!");
            Utils.showCommonInfoPrompt(this,"Alert","Please Enter First Name !!!");
            focusView=firstName;
            cancel=true;
        }else if(TextUtils.isEmpty(lastNameValue)){
            //    firstName.setError("This field is required !!!");
            Utils.showCommonInfoPrompt(this,"Alert","Please Enter Last Name !!!");
            focusView=lastName;
            cancel=true;
        }else if(TextUtils.isEmpty(companyNameValue)){
        //    companyName.setError("This field is required !!!");
            Utils.showCommonInfoPrompt(this,"Alert","Please Enter Company Name !!!");
            focusView=companyName;
            cancel=true;
        }else if(TextUtils.isEmpty(cityValue)){
        //    city.setError("This field is required !!!");
            Utils.showCommonInfoPrompt(this,"Alert","Please Enter City !!!");
            focusView=city;
            cancel=true;
        }else if(TextUtils.isEmpty(contactNoValue)){
       //     contactNo.setError("This field is required !!!");
            Utils.showCommonInfoPrompt(this,"Alert","Please Enter Contact No. !!!");
            focusView=contactNo;
            cancel=true;
        }else if(contactNoValue.length()!=10){
        //    contactNo.setError("Contact no should be in 10 digit !!!");
            Utils.showCommonInfoPrompt(this,"Alert","Contact no should be in 10 digit !!!");
            focusView=contactNo;
            cancel=true;
        }else if(TextUtils.isEmpty(emailValue)){
       //     email.setError("This field is required !!!");
            Utils.showCommonInfoPrompt(this,"Alert","Please Enter Email Address !!!");
            focusView=email;
            cancel=true;
        }else if(!emailValue.matches(emailPattern)){
        //    email.setError("Enter valid email address !!!");
            Utils.showCommonInfoPrompt(this,"Alert","Please Enter valid Email Address !!!");
            focusView=email;
            cancel=true;
        }else if(TextUtils.isEmpty(passwordValue)){
        //    password.setError("This field is required !!!");
            Utils.showCommonInfoPrompt(this,"Alert","Please Enter Password !!!");
            focusView=password;
            cancel=true;
        }else if(TextUtils.isEmpty(confirmPasswordValue)){
       //     confirmPassword.setError("This field is required !!!");
            Utils.showCommonInfoPrompt(this,"Alert","Please Enter Confirm Password !!!");
            focusView=confirmPassword;
            cancel=true;
        }else if(!passwordValue.equals(confirmPasswordValue)){
       //     confirmPassword.setError("password should be matched !!!");
            Utils.showCommonInfoPrompt(this,"Alert","Confirm Password should be same as Password!!!");
            focusView=confirmPassword;
            cancel=true;
        }

        if(cancel){
            focusView.requestFocus();
        }else{
            if (Utils.ChechInternetAvalebleOrNot(SignUpActivity.this)) {
               this.emailValue=emailValue;
                Utils.showLoader(SignUpActivity.this);
                ServerRequest
                        .postRequest(
                                Connection.BASE_URL + "signUp",
                                getSignUpData(firstNameValue,lastNameValue,companyNameValue,emailValue,passwordValue,cityValue,contactNoValue),
                                SignUpActivity.this,
                                ResponseListener.REQUEST_SIGN_UP);

            } else {
                //   Utils.shonterwSnakeBar(layout_view, "internet not connected !!!", Color.RED);Toast.makeText(LoginActivity.this,"Internet not connected !!!",Toast.LENGTH_LONG).show();
                Toast.makeText(SignUpActivity.this,"Internet not connected !!!",Toast.LENGTH_LONG).show();
                return;
            }
        }
    }

    public JSONObject getSignUpData(String firstName,String lastName,String company,String email,String password,String city,String mobileNo) {
        JSONObject json = new JSONObject();
        try {

            json.put("firstName", firstName);
            json.put("lastName", lastName);
            json.put("company", company);
            json.put("email", email);
            json.put("password", password);
            json.put("city", city);
            json.put("contactNo", mobileNo);
            json.put("mobileType", "0");

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
                if (rid == ResponseListener.REQUEST_SIGN_UP) {

                    if (response.isError()) {
                        Toast.makeText(SignUpActivity.this, response.getErrorMsg(),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (response.getData() != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.getData());
                            String status=jsonObject.getString("status");

                  //          Toast.makeText(SignUpActivity.this,jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
                            if(status.equalsIgnoreCase("true"))
                                showCommonInfoPrompt(SignUpActivity.this,"Success",jsonObject.getString("msg"));
                            else
                                Utils.showCommonInfoPrompt(SignUpActivity.this,"Failed",jsonObject.getString("msg"));
                            Log.d("json_response", response.getData());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });
    }
    public void clickSignUp(View view){
       attemptSignUp();
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
                Intent intent=new Intent(SignUpActivity.this,SignUpVerifyActivity.class);
                intent.putExtra("email",emailValue);
                SignUpActivity.this.startActivity(intent);
            }
        });

        dialog11.show();

    }
}
