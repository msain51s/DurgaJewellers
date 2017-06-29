package com.sdj_jewellers;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sdj_jewellers.utility.Utils;

public class EventActivity extends BaseActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    setContentView(R.layout.activity_education);
        getLayoutInflater().inflate(R.layout.activity_education,frameLayout);
        toolbar.setVisibility(View.VISIBLE);
        toolbarTitle.setText("Event");

        webView= (WebView) findViewById(R.id.event_webview);

        webView.setWebViewClient(new myWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://durgajewellerskundan.com/event/");

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
            Utils.showLoader(EventActivity.this);
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
