package com.prajwoladhikari.com.fypnfc.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.prajwoladhikari.com.fypnfc.R;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        String url = getIntent().getStringExtra("URL");
        WebView webView = (WebView)findViewById(R.id.webView);
        webView.loadUrl(url);

    }
}
