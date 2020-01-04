package com.safepayu.wallet.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;

public class Geneology extends BaseActivity {

    private Button send_back_btn;
    private Button refresh_btn;
    String url;
    WebView myWebView;
    Button BackBtn;
    private boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        BackBtn=findViewById(R.id.send_back_btn);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSupportZoom(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccess(true);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            webSettings.setDatabasePath("/data/data/" + this.getPackageName() + "/databases/");
        }
        myWebView.setWebChromeClient(new WebChromeClient());

        String userid = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_ID);
        url = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().GENEOLOGY_URL) + userid;
       // alias.safepeindia.com/api/secure/payment/api/Genealogy/
       // http://alias.safepeindia.com/Genealogy/u7207405714
        myWebView.loadUrl(url);

        send_back_btn = findViewById(R.id.send_back_btn);
        refresh_btn = findViewById(R.id.refresh_btn);
        send_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (doubleBackToExitPressedOnce) {
//                    finish();
//                }
//                doubleBackToExitPressedOnce = true;
//                myWebView.loadUrl(url);

                boolean goBackCheck=myWebView.canGoBack();
                if (goBackCheck) {
                    myWebView.loadUrl(url);
                } else {
                    finish();
                }
            }
        });
        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myWebView.loadUrl(url);
            }
        });

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.geneology;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        myWebView.loadUrl(url);
    }
}
