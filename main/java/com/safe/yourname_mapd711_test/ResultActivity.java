package com.safe.yourname_mapd711_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView textVehicle, textDist, texttime, textdirection, transport, tolltotal;
    WebView webView;
    TextView textView;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        textVehicle = findViewById(R.id.vihecle);
        textDist = findViewById(R.id.distance);
        texttime = findViewById(R.id.time);
        textdirection = findViewById(R.id.direction);
        transport = findViewById(R.id.transpornder);
        tolltotal = findViewById(R.id.tolltotal);
        textView = findViewById(R.id.webpage);
        webView = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progress);
        String light = sh.getString("light", "");
        String distance = sh.getString("distance", "");
        String day = sh.getString("day", "");
        String diret = sh.getString("direction", "");
        String transports = sh.getString("transport", "");
        String toll = String.valueOf(sh.getFloat("toll", 0));
        textVehicle.setText(light);
        textDist.setText(distance);
        texttime.setText(day);
        textdirection.setText(diret);
        transport.setText(transports);
        tolltotal.setText(toll);
        boolean checks = sh.getBoolean("checked", true);
        if(checks) {
            String weburl = "https://www.407etr.com/en/tolls/tolls/toll-calculator.html";
            webView = findViewById(R.id.webview);
            swipeRefreshLayout = findViewById(R.id.swipe);
            WebSettings webSettings = webView.getSettings();
            webView.setWebViewClient(new webViewClient());
            webView.loadUrl(weburl);
            webSettings.setJavaScriptEnabled(true);
            webView.getSettings().setAllowFileAccess(true);
            webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            webView.getSettings().setAppCacheEnabled(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webSettings.setDomStorageEnabled(true);
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            webSettings.setUseWideViewPort(true);
            webSettings.setSavePassword(true);
            webSettings.setSaveFormData(true);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(true);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                            webView.loadUrl(weburl);
                        }
                    }, 3000);
                }
            });
            swipeRefreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright),
                    getResources().getColor(android.R.color.holo_orange_dark),
                    getResources().getColor(android.R.color.holo_green_dark),
                    getResources().getColor(android.R.color.holo_red_dark));
        }
        else{
            textView.setText("Check box not checked");
            textView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

    }
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }
        else {
            super.onBackPressed();
        }
    }
        public class webViewClient extends WebViewClient {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
            }

        }
}