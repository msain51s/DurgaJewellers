package com.sdj_jewellers;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sdj_jewellers.utility.Utils;

public class AboutUsActivity extends BaseActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 //       setContentView(R.layout.activity_about_us);
        getLayoutInflater().inflate(R.layout.activity_about_us,frameLayout);

        setupToolbar();
        webView= (WebView) findViewById(R.id.about_us_webview);

        webView.setWebViewClient(new myWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://shridurgajewellers.com/about-us-mobile/");
    }

    private void setupToolbar() {
        toolbar.setVisibility(View.VISIBLE);
        toolbarTitle.setText("About Us");
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
            Utils.showLoader(AboutUsActivity.this);
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

