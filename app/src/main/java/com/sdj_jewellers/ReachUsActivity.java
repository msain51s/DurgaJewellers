package com.sdj_jewellers;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sdj_jewellers.utility.Utils;

public class ReachUsActivity extends BaseActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 //       setContentView(R.layout.activity_reach_us);
        getLayoutInflater().inflate(R.layout.activity_reach_us,frameLayout);

        toolbar.setVisibility(View.VISIBLE);
        toolbarTitle.setText("Reach Us");

        webView= (WebView) findViewById(R.id.reachUs_webview);

        webView.setWebViewClient(new myWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://shridurgajewellers.com/contact-mobi/");
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(preference.getCART_COUNT()!=0) {
            cart_countText.setVisibility(View.VISIBLE);
            cart_countText.setText(""+preference.getCART_COUNT());
        }else
            cart_countText.setVisibility(View.GONE);
    }
    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            Utils.showLoader(ReachUsActivity.this);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            Utils.dismissLoader();
        }
    }

}
