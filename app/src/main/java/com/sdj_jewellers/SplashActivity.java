package com.sdj_jewellers;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.sdj_jewellers.utility.Preference;
import com.sdj_jewellers.utility.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SplashActivity extends AppCompatActivity {

    Preference preference;
    Calendar calendar,calendar1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        preference=new Preference(this);

        if(isAppUpgradeTime()){
            if (Utils.ChechInternetAvalebleOrNot(SplashActivity.this)) {
                new getAppMarketVersionAsync().execute("https://play.google.com/store/apps/details?id=com.sdj_jewellers");
            } else {
                Toast.makeText(SplashActivity.this, "Internet not connected !!!", Toast.LENGTH_LONG).show();
            }
        }else {
            continueAppFlow();
        }
    }

    public void continueAppFlow(){
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
    public boolean isAppUpgradeTime() {
        String appUpgradeTime=preference.getAppUpgradeTime();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MMM-yyyy");
        calendar= Calendar.getInstance();
        calendar1=Calendar.getInstance();
        String currentDate=null;
        try {

            calendar.setTime(dateFormat.parse(appUpgradeTime));
            currentDate=dateFormat.format(calendar1.getTime());
            calendar1.setTime(dateFormat.parse(currentDate));

            if (calendar.equals(calendar1))
                return true;
        }
        catch (Exception e){
            return true;
        }
        return false;
    }

    /*Manoj Says...
   *      checking market version of the app
   * */
    public class getAppMarketVersionAsync extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //        Utils.showLoader(SplashActivity.this);
        }

        @Override
        protected String doInBackground(String... params) {
            return Utils.getAppVersionFromMarket(params[0]);
        }

        @Override
        protected void onPostExecute(String version) {
            super.onPostExecute(version);
            //        Utils.dismissLoader();

            String market_version=version;
            String app_version=Utils.getAppVersionCode(SplashActivity.this);
            if(market_version==null){
                continueAppFlow();
                return;
            }
            boolean upgradeFlag=false;
            double market_version_value=Double.parseDouble(market_version);
            double app_version_value=Double.parseDouble(app_version);

            if(market_version.equalsIgnoreCase(app_version))
            {
                continueAppFlow();
            }
            else if(app_version_value<market_version_value)
            {
                upgradeFlag=true;
                Utils.showAppUpgradeDialog(SplashActivity.this,"Application Update is available ",upgradeFlag);
            }
            else{
                continueAppFlow();
            }
        }
    }
}
