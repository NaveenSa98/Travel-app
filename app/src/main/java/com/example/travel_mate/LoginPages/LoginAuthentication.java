package com.example.travel_mate.LoginPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travel_mate.HomeActivity;
import com.example.travel_mate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginAuthentication extends AppCompatActivity {
    Button btn1,btn2;

    FirebaseAuth mAuth;



    ImageView googleAuth;
    ImageView facebookAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_authentication);

        mAuth = FirebaseAuth.getInstance();

        // Check if the user is already logged in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is already logged in, navigate to the next activity
            sendUserToNextActivity();
            finish(); // Finish this activity so that the user can't come back to the login screen using the back button
        }

         btn1 = findViewById(R.id.button_1);
         btn2 = findViewById(R.id.button_2);
         googleAuth = findViewById(R.id.btnGoogle);
         facebookAuth = findViewById(R.id.btnFb);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAuthentication.this, LoginPage.class);
                startActivity(intent);


            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAuthentication.this, SignupPage.class);
                startActivity(intent);

            }
        });

        googleAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAuthentication.this, GoogleSignActivity.class);
                startActivity(intent);
            }
        });



        facebookAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAuthentication.this,FbSignActivity.class);
                startActivity(intent);

            }
        });

    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(LoginAuthentication.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}