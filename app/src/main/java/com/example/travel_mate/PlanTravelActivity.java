package com.example.travel_mate;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travel_mate.Classes.TravelPlan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PlanTravelActivity extends AppCompatActivity {

    private EditText destinationEditText, detailsEditText,travelDateEditText,emailEditText;
    private Button saveButton;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_travel);

        destinationEditText = findViewById(R.id.editTextDestination);
        detailsEditText = findViewById(R.id.editTextDetails);
        travelDateEditText = findViewById(R.id.travelDate);
        emailEditText = findViewById(R.id.editTextEmail);
        saveButton = findViewById(R.id.btnSave);

        databaseReference = FirebaseDatabase.getInstance().getReference("travel_plans");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTravelPlan();
            }
        });
        ImageButton backButton = findViewById(R.id.backButton_E);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the back button click here
                // You can use finish() to close the current activity
                finish();
            }
        });

    }




    private void saveTravelPlan() {
        String destination = destinationEditText.getText().toString().trim();
        String details = detailsEditText.getText().toString().trim();
        String travelDate = travelDateEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();

        if (!destination.isEmpty() && !details.isEmpty()) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String userId = user.getUid();
                String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                String planId = databaseReference.push().getKey();
                TravelPlan travelPlan = new TravelPlan(userId, destination, details, travelDate, email);

                if (planId != null) {
                    databaseReference.child(planId).setValue(travelPlan);
                    Toast.makeText(this, "Travel plan saved successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Failed to save travel plan", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "Please enter destination and details", Toast.LENGTH_SHORT).show();
        }
    }
}
