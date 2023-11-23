package com.example.travel_mate.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travel_mate.Adapters.EventsAdapter;
import com.example.travel_mate.Classes.TravelPlan;
import com.example.travel_mate.PlanTravelActivity;
import com.example.travel_mate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EventPlanFragment extends Fragment {

    private View view;
    private CalendarView calendarView;
    private RecyclerView eventsRecyclerView;
    private Button btnPlanTrip;
    private List<TravelPlan> travelPlans;

    private EventsAdapter eventsAdapter;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_eventplan, container, false);



        // Initialize views
        calendarView = view.findViewById(R.id.calendarView);
        eventsRecyclerView = view.findViewById(R.id.eventsRecyclerView);
        btnPlanTrip = view.findViewById(R.id.btnPlanTrip);

        // Customize the appearance of the dates
        calendarView.setDateTextAppearance(R.style.CalendarDateTextAppearance);

        // Customize the appearance of the month name
        calendarView.setWeekDayTextAppearance(R.style.CalendarWeekDayTextAppearance);



        // Set up RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        eventsRecyclerView.setLayoutManager(layoutManager);

        // Initialize the list and adapter
        travelPlans = new ArrayList<>();
        eventsAdapter = new EventsAdapter(travelPlans);
        eventsRecyclerView.setAdapter(eventsAdapter);

        // Add click listener to the "Plan Trip" button
        btnPlanTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlanTravelActivity();
            }
        });

        loadAndDisplayTravelPlans();

        return view;
    }

    private void openPlanTravelActivity() {
        Intent intent = new Intent(getContext(), PlanTravelActivity.class);
        startActivity(intent);
    }

    private void loadAndDisplayTravelPlans() {
        travelPlans.clear();

        // Get the current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            // Reference to the "travel_plans" node in Firebase
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("travel_plans");

            // Query to retrieve travel plans for the current user
            databaseReference.orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot planSnapshot : dataSnapshot.getChildren()) {
                        TravelPlan travelPlan = planSnapshot.getValue(TravelPlan.class);
                        if (travelPlan != null) {
                            if (!isTravelDatePassed(travelPlan.getTravelDate())) {
                                travelPlans.add(travelPlan);
                            } else {
                                // Remove outdated plan from Firebase
                                planSnapshot.getRef().removeValue();
                            }
                        }
                    }

                    eventsAdapter.notifyDataSetChanged();

                    // Display dates on the calendar
                    displayDatesOnCalendar();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getContext(), "Failed to retrieve travel plans", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void displayDatesOnCalendar() {
        for (TravelPlan travelPlan : travelPlans) {
            if (travelPlan.getTravelDate() != null) {
                int year = Integer.parseInt(travelPlan.getTravelDate().substring(0, 4));
                int month = Integer.parseInt(travelPlan.getTravelDate().substring(5, 7)) - 1; // Calendar months are 0-indexed
                int day = Integer.parseInt(travelPlan.getTravelDate().substring(8));

                // Set the date on the calendar
                calendarView.setDate(calendarToMillis(year, month, day), true, false);
            }
        }
    }

    private long calendarToMillis(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    private boolean isTravelDatePassed(String travelDate) {
        // Check if the travel date has passed
        Calendar currentDate = Calendar.getInstance();
        Calendar travelDateCalendar = Calendar.getInstance();
        travelDateCalendar.set(
                Integer.parseInt(travelDate.substring(0, 4)),
                Integer.parseInt(travelDate.substring(5, 7)) - 1, // Calendar months are 0-indexed
                Integer.parseInt(travelDate.substring(8))
        );

        return currentDate.after(travelDateCalendar);
    }


}
