package com.example.esstelingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.esstelingapp.ui.SettingsPage;

public class MainActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_COLOUR_BLIND_THEME = "colour_blind_theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useColourBlindTheme = preferences.getBoolean(PREF_COLOUR_BLIND_THEME, false);
        if (useColourBlindTheme) {
            setTheme(R.style.ColourBlindTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}
