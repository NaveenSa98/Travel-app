package com.example.travel_mate;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class Accommodation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation);

        WebView webView = findViewById(R.id.webView);


        webView.getSettings().setJavaScriptEnabled(true);


        webView.setWebViewClient(new WebViewClient());

        // Load Booking.com in the WebView
        webView.loadUrl("https://www.booking.com");
    }
}
