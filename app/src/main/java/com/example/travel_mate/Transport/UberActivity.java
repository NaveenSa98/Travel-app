package com.example.travel_mate.Transport;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travel_mate.R;

public class UberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uber);


        WebView webView = findViewById(R.id.webView_1);


        webView.getSettings().setJavaScriptEnabled(true);


        webView.setWebViewClient(new WebViewClient());

        // Load Booking.com in the WebView
        webView.loadUrl("https://www.uber.com/us/en/ride/");
    }
}