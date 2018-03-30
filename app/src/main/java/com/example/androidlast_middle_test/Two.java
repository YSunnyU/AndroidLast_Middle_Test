package com.example.androidlast_middle_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Two extends AppCompatActivity {

    private WebView web_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        initView();
        initData();
    }
    private void initData() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        web_view.loadUrl(url);
        web_view.setWebViewClient(new WebViewClient());
    }

    private void initView() {
        web_view = (WebView) findViewById(R.id.web_view);
    }
}
