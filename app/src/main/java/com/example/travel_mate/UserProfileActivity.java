package com.example.travel_mate;

// UserProfileActivity.java

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.travel_mate.Classes.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

    EditText editTextUsername, editTextEmail, editTextCurrentPassword, editTextNewPassword, editTextReenterPassword, editTextCountry;
    Button btnSaveProfile;
   ImageView btnChangeProfilePicture;

    CircleImageView profilePictureImageView;
    TextView forgetPassword2;

    Uri selectedImageUri;
    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase mDatabase;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextCurrentPassword = findViewById(R.id.CurrentPassword);
        editTextNewPassword = findViewById(R.id.editTextPassword);
        editTextReenterPassword = findViewById(R.id.reenterPassword);
        editTextCountry = findViewById(R.id.editTextCountry);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        forgetPassword2 = findViewById(R.id.forget_password2);
        profilePictureImageView = findViewById(R.id.profilePicture);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        userRef = mDatabase.getReference("users").child(mUser.getUid());

        // Retrieve user details and populate the EditText fields
        retrieveUserDetails();

        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
                uploadProfilePicture();
            }
        });

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        displayCurrentProfilePicture();

        checkGoogleSignIn();

        profilePictureImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open image picker when the profile picture is clicked
                openImagePicker();
            }
        });


        btnChangeProfilePicture = findViewById(R.id.profileBackBtn);

        btnChangeProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        forgetPassword2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_forget,null);
                EditText emailbox = dialogView.findViewById(R.id.emailBox);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                dialogView.findViewById(R.id.btnReset).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userEmail = emailbox.getText().toString();

                        if (TextUtils.isEmpty(userEmail)&& !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                            Toast.makeText(UserProfileActivity.this, "Enter your registered email id", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(UserProfileActivity.this, "Check your email", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }else {
                                    Toast.makeText(UserProfileActivity.this, "Unable to send, failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                });
                dialogView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
            }
        });

    }


    private void displayCurrentProfilePicture() {
        // Retrieve profile picture URL from Firebase Realtime Database and display using Glide
        // Replace "profilePicUrl" with the actual field name in your database
        userRef.child("profilePicUrl").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String profilePicUrl = Objects.requireNonNull(snapshot.getValue()).toString();
                    Glide.with(UserProfileActivity.this).load(profilePicUrl).into(profilePictureImageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfileActivity.this, "Failed to retrieve profile picture", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                if (data != null) {
                    selectedImageUri = data.getData();
                    profilePictureImageView.setImageURI(selectedImageUri);
                }
            }
        }
    }

    private void uploadProfilePicture() {
        if (selectedImageUri != null) {
            StorageReference imageRef = storageReference.child("profile_pictures/" + mUser.getUid() + ".jpg");

            imageRef.putFile(selectedImageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Image uploaded successfully, get the download URL
                        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            // Update the profile picture URL in the database
                            userRef.child("profilePicUrl").setValue(uri.toString());
                            displayCurrentProfilePicture();
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Handle the failure case
                        Toast.makeText(UserProfileActivity.this, "Failed to upload profile picture", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayCurrentProfilePicture();
    }

    // ... existing methods


    private void retrieveUserDetails() {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        editTextUsername.setText(user.getUsername());
                        editTextEmail.setText(user.getEmail());
                        editTextCountry.setText(user.getCountry());
                        // You can populate other fields as needed
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfileActivity.this, "Failed to retrieve user details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateProfile() {
        String newUsername = editTextUsername.getText().toString().trim();
        String newCountry = editTextCountry.getText().toString().trim();
        String currentPassword = editTextCurrentPassword.getText().toString().trim();
        String newPassword = editTextNewPassword.getText().toString().trim();
        String reenterPassword = editTextReenterPassword.getText().toString().trim();
        String newEmail = editTextEmail.getText().toString().trim();


        if (TextUtils.isEmpty(newUsername) || TextUtils.isEmpty(newCountry)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.isEmpty(newEmail)) {
            userRef.child("email").setValue(newEmail);
        }

        if (!TextUtils.isEmpty(newPassword)) {
            if (!newPassword.equals(reenterPassword)) {
                Toast.makeText(this, "New passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }
            // Update password
            updatePassword(currentPassword, newPassword);
        }

        // Update other profile details
        userRef.child("username").setValue(newUsername);
        userRef.child("country").setValue(newCountry);

        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
    }

    private void updatePassword(String currentPassword, String newPassword) {
        FirebaseUser user = mAuth.getCurrentUser();

        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);

        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> reauthTask) {
                        if (reauthTask.isSuccessful()) {
                            user.updatePassword(newPassword)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> updateTask) {
                                            if (updateTask.isSuccessful()) {
                                                // Password updated successfully, now update in Firebase database
                                                updatePasswordInDatabase(newPassword);
                                                Toast.makeText(UserProfileActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(UserProfileActivity.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(UserProfileActivity.this, "Reauthentication failed. Please check your current password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updatePasswordInDatabase(String newPassword) {
        // Update the password field in the database
        userRef.child("password").setValue(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Password updated in the database
                            Toast.makeText(UserProfileActivity.this, "Password updated", Toast.LENGTH_SHORT).show();
                        } else {
                            // Handle the case where updating the password in the database failed
                            Toast.makeText(UserProfileActivity.this, "Failed to update password in the database", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void checkGoogleSignIn() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            // If signed in with Google, display the Google account email
            String googleEmail = account.getEmail();
            editTextEmail.setText(googleEmail);
            editTextEmail.setEnabled(false); // Disable editing for Google sign-in email
        }
    }
}
