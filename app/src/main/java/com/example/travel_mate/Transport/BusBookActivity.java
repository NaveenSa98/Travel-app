package com.example.travel_mate.Transport;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travel_mate.R;

public class BusBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busbook);

        WebView webView = findViewById(R.id.webView_2);


        webView.getSettings().setJavaScriptEnabled(true);


        webView.setWebViewClient(new WebViewClient());

        // Load Booking.com in the WebView
        webView.loadUrl("https://sltb.eseat.lk/");
    }
}