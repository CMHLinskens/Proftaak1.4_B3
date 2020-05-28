package com.example.esstelingapp.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.esstelingapp.R;

import static android.content.Context.MODE_PRIVATE;

public class SettingsPage extends Fragment {
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_COLOUR_BLIND_THEME = "colour_blind_theme";

    private ToggleButton toggleButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        this.toggleButton = getActivity().findViewById(R.id.settings_colour_blind_button);
        this.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        return inflater.inflate(R.layout.activity_settings, container, false);
    }

    private void toggleColourBlindMode(boolean colourBlindTheme) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(PREF_COLOUR_BLIND_THEME, colourBlindTheme);
        editor.apply();

        getActivity().finish();
        Intent intent = getActivity().getIntent();
        intent.putExtra("colourBlind_theme", colourBlindTheme);
        startActivity(intent);
    }
}

