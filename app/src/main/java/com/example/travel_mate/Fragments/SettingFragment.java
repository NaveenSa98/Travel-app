package com.example.travel_mate.Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.travel_mate.R;

public class SettingFragment extends Fragment {

    private Switch switchNotification;
    private Switch switchNightMode;
    private SeekBar textSizeSeekBar;
    private TextView textView;
    private  TextView notificationTextView;

    private LinearLayout textSizeLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        switchNotification = view.findViewById(R.id.switch_1);
        switchNightMode = view.findViewById(R.id.switch_2);
        textSizeSeekBar = view.findViewById(R.id.textSizeSeekBar);
       textView = view.findViewById(R.id.textView_5);
       notificationTextView = view.findViewById(R.id.notification_1);
        textSizeLayout = view.findViewById(R.id.textSizeLayout);

        // Load saved settings
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean isNotificationEnabled = sharedPreferences.getBoolean("notification_enabled", true);
        boolean isNightModeEnabled = sharedPreferences.getBoolean("night_mode_enabled", false);
        int textSize = sharedPreferences.getInt("text_size", 16); // Default text size in SP

        switchNotification.setChecked(isNotificationEnabled);
        switchNightMode.setChecked(isNightModeEnabled);

        // Set the initial text size and SeekBar progress
       textView.setTextSize(textSize);
       notificationTextView.setTextSize(textSize);
        textSizeSeekBar.setProgress(textSize);

        // Set listeners to save settings when switches change
        switchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Save notification setting
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("notification_enabled", isChecked);
                editor.apply();
            }
        });

        switchNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Save night mode setting
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("night_mode_enabled", isChecked);
                editor.apply();

                // Apply night mode immediately
                int nightMode = isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
                AppCompatDelegate.setDefaultNightMode(nightMode);
                getActivity().recreate(); // Recreate the activity to apply the changes
            }
        });

        // Set a listener for the SeekBar to update text size dynamically
        textSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update text size when the SeekBar is moved
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, progress);
                notificationTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, progress);


                // Update text size for the whole layout
                updateTextSizeForLayout(textSizeLayout, TypedValue.COMPLEX_UNIT_SP, progress);
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not used in this example
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Save the selected text size when the user stops moving the SeekBar
                int textSize = seekBar.getProgress();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("text_size", textSize);
                editor.apply();
            }
        });

        return view;

    }

    private void updateTextSizeForLayout(ViewGroup layout, int unit, float size) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof TextView) {
                ((TextView) child).setTextSize(unit, size);
            } else if (child instanceof ViewGroup) {
                updateTextSizeForLayout((ViewGroup) child, unit, size);
            }
        }
    }

}
