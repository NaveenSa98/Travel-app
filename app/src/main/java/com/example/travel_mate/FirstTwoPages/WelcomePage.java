package com.example.travel_mate.FirstTwoPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travel_mate.HomeActivity;
import com.example.travel_mate.LoginPages.LoginAuthentication;
import com.example.travel_mate.R;

public class WelcomePage extends AppCompatActivity {

    private ImageButton imageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        imageButton = (android.widget.ImageButton) findViewById(R.id.imageButton_1);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();

            }
        });

    }

    public void openActivity2(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}