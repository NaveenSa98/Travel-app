package com.example.travel_mate.LoginPages;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travel_mate.Classes.User;
import com.example.travel_mate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupPage extends AppCompatActivity {
    EditText inputEmail,inputUsername,inputPassword,inputConfirmPassword;
    Button btnSignup;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    ImageView btnChangeProfilePicture;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase mDatabase;

    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        inputEmail = findViewById(R.id.signup_email);
        inputPassword = findViewById(R.id.signup_password);
        inputConfirmPassword = findViewById(R.id.signup_confirm_password);
        inputUsername = findViewById(R.id.Signup_username);
        btnSignup = findViewById(R.id.signup_button);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        reference = mDatabase.getReference("users");


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perforAuth();

            }
        });

        btnChangeProfilePicture = findViewById(R.id.profileBackBtn);

        btnChangeProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }


    private void perforAuth() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPassword = inputConfirmPassword.getText().toString();
        String username = inputUsername.getText().toString();

        if (!email.matches(emailPattern)) {
            inputEmail.setError("Enter Correct Email");
        } else if (password.isEmpty() || password.length() < 6) {
            inputPassword.setError("Enter Proper Password ");
        } else if (!password.equals(confirmPassword)) {
            inputConfirmPassword.setError("Password Not match Both field");
        } else {
            progressDialog.setMessage("Please Wait While SignUp...");
            progressDialog.setTitle("SignUp");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // User is successfully registered, now store additional details in Firebase
                        String userId = mAuth.getCurrentUser().getUid(); // Get the current user's ID
                        DatabaseReference userRef = reference.child(userId);

                        // Create a User object with the provided detail
                        User user = new User(email,username,password,"defaultCountry");

                        // Set the user details in the database
                        userRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    sendUserTONextActivity();
                                    Toast.makeText(SignupPage.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(SignupPage.this, "Failed to store user details in the database", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(SignupPage.this, "Login Failed" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

// ... (previous code)

    private void sendUserTONextActivity() {
        Intent intent = new Intent(SignupPage.this, LoginPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }


}