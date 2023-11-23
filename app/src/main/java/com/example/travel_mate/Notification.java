package com.example.travel_mate;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.travel_mate.Adapters.NotificationAdapter;
import com.example.travel_mate.Classes.TravelPlan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.List;

public class Notification extends AppCompatActivity {

    private static final String CHANNEL_ID = "travel_mate_channel";
    private static final int NOTIFICATION_ID = 1;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String FIRST_TIME_LOGIN_KEY = "first_time_login";

    private List<TravelPlan> travelPlans;
    private NotificationAdapter notificationAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         // Assuming you have a layout resource named "activity_notification"

        createNotificationChannel();


        if (isFirstTimeLogin()) {
            // Trigger a welcome notification when the app opens for the first time
            displayNotification("Welcome to Travel Mate", "Explore your travel plans!");

            // Update the flag to indicate that the user has logged in
            setFirstTimeLogin(false);
        }





//        RecyclerView notificationRecyclerView = findViewById(R.id.notificationRecyclerView);
//        travelPlans = new ArrayList<>();
//        notificationAdapter = new NotificationAdapter(travelPlans);
//        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        notificationRecyclerView.setAdapter(notificationAdapter);




        // Retrieve travel plans from Firebase
        loadAndDisplayTravelPlans();

        checkUpcomingTravelPlans();
    }

    private boolean isFirstTimeLogin() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(FIRST_TIME_LOGIN_KEY, true);
    }

    private void setFirstTimeLogin(boolean isFirstTime) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(FIRST_TIME_LOGIN_KEY, isFirstTime);
        editor.apply();
    }


    private void checkUpcomingTravelPlans() {
        // Implement logic to check if there are upcoming travel plans
        // For example, you can compare the travel date with the current date
        for (TravelPlan travelPlan : travelPlans) {
            // Assuming the travel date is in the format "yyyy-MM-dd"
            String travelDate = travelPlan.getTravelDate();
            // Parse travel date to extract year, month, and day
            int year = Integer.parseInt(travelDate.substring(0, 4));
            int month = Integer.parseInt(travelDate.substring(5, 7)) - 1;
            int day = Integer.parseInt(travelDate.substring(8));

            // Check if the travel date is within a certain threshold (e.g., one day)
            Calendar travelCalendar = Calendar.getInstance();
            travelCalendar.set(year, month, day);
            travelCalendar.add(Calendar.DAY_OF_MONTH, -1); // Subtract one day

            Calendar currentCalendar = Calendar.getInstance();

            if (currentCalendar.before(travelCalendar)) {
                // If the current date is before the travel date (within the threshold),
                // schedule a reminder for the upcoming travel plan
                scheduleReminder(travelPlan);
            }
        }
    }

    private void loadAndDisplayTravelPlans() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("travel_plans");

            // Query to retrieve travel plans for the current user
            databaseReference.orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot planSnapshot : dataSnapshot.getChildren()) {
                        TravelPlan travelPlan = planSnapshot.getValue(TravelPlan.class);
                        if (travelPlan != null) {
                            travelPlans.add(travelPlan);
                            // Schedule travel plan reminders
                            scheduleReminder(travelPlan);
                        }
                    }
                    // Update the adapter
                    notificationAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle database error
                }
            });
        }
    }

    private void scheduleReminder(TravelPlan travelPlan) {
        // Parse travel date to extract year, month, and day
        int year = Integer.parseInt(travelPlan.getTravelDate().substring(0, 4));
        int month = Integer.parseInt(travelPlan.getTravelDate().substring(5, 7)) - 1;
        int day = Integer.parseInt(travelPlan.getTravelDate().substring(8));

        // Set the reminder date to the day before the travel date
        Calendar reminderCalendar = Calendar.getInstance();
        reminderCalendar.set(year, month, day - 1, 9, 0);

        // Create an alarm intent
        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("title", "Travel Reminder");
        intent.putExtra("content", "Your trip to " + travelPlan.getDestination() + " is tomorrow!");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        // Set up the alarm manager to trigger the reminder
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, reminderCalendar.getTimeInMillis(), pendingIntent);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Travel Mate Channel";
            String description = "Channel for Travel Mate notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private boolean hasNotificationPermission() {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        return notificationManager.areNotificationsEnabled();
    }

    private void requestNotificationPermission() {
        // You can open the app settings to let the user enable notifications
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    private void displayNotification(String title, String content) {
        if (hasNotificationPermission()) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.baseline_notifications_24)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

            // Check for notification permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                notificationManager.notify(NOTIFICATION_ID, builder.build());
            } else {
                // Handle the case where permission is not granted
                // You may want to request the permission or take appropriate action
            }
        } else {
            requestNotificationPermission();
        }
    }
}

