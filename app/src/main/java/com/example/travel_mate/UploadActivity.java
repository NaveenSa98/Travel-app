package com.example.travel_mate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 2;
    private ImageView uploadImage;
    private EditText uploadTopic, uploadDistrict, uploadDesc, uploadLang;
    private Button saveButton;
    private Bitmap imageBitmap;
    private FirebaseFirestore firestore;
    private FirebaseStorage storage;
    private List<Bitmap> imageBitmapList;

    private List<ImageView> imageViewsList;
    private LinearLayout imageContainer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();


        imageBitmapList = new ArrayList<>();
        imageViewsList = new ArrayList<>();
        imageContainer = findViewById(R.id.imageContainer);
        uploadTopic = findViewById(R.id.uploadTopic);
        uploadDistrict = findViewById(R.id.uploadDistrict);
        uploadDesc = findViewById(R.id.uploadDesc);
        uploadLang = findViewById(R.id.uploadLang);
        saveButton = findViewById(R.id.saveButton);

        Button addImageButton = findViewById(R.id.addImageButton);
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                Bitmap selectedImageBitmap = BitmapFactory.decodeStream(inputStream);
                imageBitmapList.add(selectedImageBitmap);

                // Display the selected images
                displayImages();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayImages() {
        imageContainer.removeAllViews(); // Clear existing views

        for (int i = 0; i < imageBitmapList.size(); i++) {
            final Bitmap imageBitmap = imageBitmapList.get(i);

            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(
                    getResources().getDimensionPixelSize(R.dimen.image_width),
                    getResources().getDimensionPixelSize(R.dimen.image_height)
            ));
            imageView.setImageBitmap(imageBitmap);

            // Add a remove button to each image
            ImageButton removeButton = new ImageButton(this);
            removeButton.setImageResource(R.drawable.baseline_cancel_24); // Customize this icon
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Remove the corresponding image
                    removeImage(imageBitmap);
                }
            });

            LinearLayout imageLayout = new LinearLayout(this);
            imageLayout.setOrientation(LinearLayout.VERTICAL);
            imageLayout.addView(imageView);
            imageLayout.addView(removeButton);

            imageContainer.addView(imageLayout);
            imageViewsList.add(imageView);
        }
    }

    private void removeImage(Bitmap imageBitmap) {
        int index = imageBitmapList.indexOf(imageBitmap);
        if (index != -1) {
            imageBitmapList.remove(index);
            imageViewsList.remove(index);
            displayImages();
        }
    }



    private void uploadData() {
        // Get the data from the UI elements
        final String topic = uploadTopic.getText().toString();
        String district = uploadDistrict.getText().toString();
        String description = uploadDesc.getText().toString();
        String howToReach = uploadLang.getText().toString();

        // Validate data before uploading (add your validation logic)

        // Upload image to Firebase Storage
        uploadImageToStorage(topic, district, description, howToReach);
    }


    private void uploadImageToStorage(final String topic, final String district, final String description, final String howToReach) {
        for (int i = 0; i < imageBitmapList.size(); i++) {
            Bitmap imageBitmap = imageBitmapList.get(i);

            if (imageBitmap != null) {
                // Upload each image to Firebase Storage
                // You can modify the image file name as needed
                String imageName = topic + "_image_" + (i + 1);
                uploadSingleImageToStorage(imageBitmap, imageName, topic, district, description, howToReach);
            }
        }
    }

    private void uploadSingleImageToStorage(Bitmap imageBitmap, String imageName, final String topic, final String district, final String description, final String howToReach) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        StorageReference storageRef = storage.getReference().child("images/" + imageName + ".jpg");

        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(UploadActivity.this, "Image upload failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Image uploaded successfully
                // Now, save other details to Firestore
                saveDataToFirestore(topic, district, description, howToReach);
            }
        });
    }

    private void saveDataToFirestore(String topic, String district, String description, String howToReach) {
        Map<String, Object> data = new HashMap<>();
        data.put("name", topic);
        data.put("district", district);
        data.put("description", description);
        data.put("howToReach", howToReach);


        firestore.collection("destinations").document(topic)
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UploadActivity.this, "Data uploaded successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadActivity.this, "Data upload failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
