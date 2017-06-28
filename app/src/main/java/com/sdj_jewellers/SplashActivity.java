package com.sdj_jewellers;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sdj_jewellers.utility.Preference;

public class SplashActivity extends AppCompatActivity {

    Preference preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        preference=new Preference(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=null;
             if(preference.isLoggedIn()){
                 intent=new Intent(SplashActivity.this,HomeActivity.class);
             }else{
                 intent=new Intent(SplashActivity.this,LoginActivity.class);
             }
             startActivity(intent);
                finish();
            }
        },3000);
    }
}
