package com.sdj_jewellers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sdj_jewellers.service.Response;
import com.sdj_jewellers.service.ResponseListener;
import com.sdj_jewellers.service.ServerRequest;
import com.sdj_jewellers.utility.Connection;
import com.sdj_jewellers.utility.Preference;
import com.sdj_jewellers.utility.Utils;

import org.json.JSONObject;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginActivity extends AppCompatActivity implements ResponseListener{
    private EditText email,password;
    Handler h;
    Preference preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        h=new Handler();
        preference=new Preference(this);

        setContentView(R.layout.activity_login);

        email= (EditText) findViewById(R.id.email_editText);
        password= (EditText) findViewById(R.id.password_editText);

    }

    public void clickLogin(View view){
        attemptLogin();
    }
    public void clickForgotPassword(View view){
      Intent intent=new Intent(LoginActivity.this,ForgotPasswordActivity.class);
        startActivity(intent);
    }

    public void clickSignUp(View view){
        Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
        startActivity(intent);
    }

    public void attemptLogin(){
        String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
       String emailValue=email.getText().toString();
      String passwordValue=password.getText().toString();

        boolean cancel=false;
        View focusView=null;

        if(TextUtils.isEmpty(emailValue)){
        //    email.setError("Email should not be empty !!!");
            Utils.showCommonInfoPrompt(this,"Alert","Email should not be empty !!!");
            focusView=email;
            cancel=true;
        }else if(!emailValue.matches(emailPattern)){
         //   email.setError("Enter valid email address !!!");
            Utils.showCommonInfoPrompt(this,"Alert","Enter valid email address !!!");
            focusView=email;
            cancel=true;
        }else if(TextUtils.isEmpty(passwordValue)){
        //    password.setError("Password should not be empty !!!");
            Utils.showCommonInfoPrompt(this,"Alert","Password should not be empty !!!");
            focusView=password;
            cancel=true;
        }

        if(cancel){
            focusView.requestFocus();
        }else{
            if (Utils.ChechInternetAvalebleOrNot(LoginActivity.this)) {

                Utils.showLoader(LoginActivity.this);
                ServerRequest
                        .postRequest(
                                Connection.BASE_URL + "login",
                                getLoginData(emailValue, passwordValue),
                                LoginActivity.this,
                                ResponseListener.REQUEST_LOGIN);

            } else {
             //   Utils.showSnakeBar(layout_view, "internet not connected !!!", Color.RED);Toast.makeText(LoginActivity.this,"Internet not connected !!!",Toast.LENGTH_LONG).show();
                return;
            }
        }
    }

    public JSONObject getLoginData(String email,String passwd) {
        JSONObject json = new JSONObject();
        try {
            json.put("email", email);
            json.put("password", passwd);
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
                if (rid == ResponseListener.REQUEST_LOGIN) {

                    if (response.isError()) {
                        Toast.makeText(LoginActivity.this, response.getErrorMsg(),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (response.getData() != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.getData());
                            String status=jsonObject.getString("status");
                            if(status.equalsIgnoreCase("true")) {
                                JSONObject jsonObject1=jsonObject.getJSONObject("record");
                                preference.setEMAIL_ID(jsonObject1.getString("user_email"));
                                preference.setUSER_ID(jsonObject1.getString("userId"));
                                preference.setMOBILE_NUMBER(jsonObject1.getString("user_contactNo"));
                                preference.setFIRST_NAME(jsonObject1.getString("user_fName"));
                                preference.setLAST_NAME(jsonObject1.getString("user_lName"));
                                preference.setCOMPANY_NAME(jsonObject1.getString("user_company"));
                                preference.setCITY_NAME(jsonObject1.getString("user_city"));
                                preference.setLoggedIn(true);

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else if(status.equalsIgnoreCase("false")){
                                Toast.makeText(LoginActivity.this,jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
                            }
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
