package com.example.esstelingapp.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.esstelingapp.R;

public class SettingsPage extends AppCompatActivity {
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_COLOUR_BLIND_THEME = "colour_blind_theme";


    private ConstraintLayout layout;
    private RadioGroup group;
    private RadioButton radioButton;
    private TextView txtView1;
    private TextView txtView2;
    private ToggleButton button;
    private boolean colourBlindTheme;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useColourBlindTheme = preferences.getBoolean(PREF_COLOUR_BLIND_THEME, false);
        if (useColourBlindTheme) {
            setTheme(R.style.ColourBlindTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.button = findViewById(R.id.settings_colour_blind_button);
        this.layout = findViewById(R.id.settings_layout);
        this.txtView1 = findViewById(R.id.settings_colour_blind);
        this.txtView2 = findViewById(R.id.settings_language);
        this.group = findViewById(R.id.settings_radio_buttons);

        this.button.setChecked(useColourBlindTheme);

        this.button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.d("CLICKABLE BUTTON", "Button has turned on!");
                    toggleColourBlindMode(true);

                } else {
                    Log.d("CLICKABLE BUTTON", "Button has turned off!");
                    toggleColourBlindMode(false);
                }
            }
        });
    }

    private void toggleColourBlindMode(boolean colourBlindTheme) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(PREF_COLOUR_BLIND_THEME, colourBlindTheme);
        editor.apply();

        finish();
        Intent intent = getIntent();
        startActivity(intent);
    }

    public void onClick(View view) {

    }


    public void checkButton(View view) {
        int radioId = group.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);

        Toast.makeText(this, "Language has changed to: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }

}

