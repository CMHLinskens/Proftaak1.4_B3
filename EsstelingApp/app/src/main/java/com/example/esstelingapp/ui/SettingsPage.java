package com.example.esstelingapp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.DragAndDropPermissions;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.esstelingapp.R;
import com.example.esstelingapp.data.DataSingleton;
import com.example.esstelingapp.json.JSonLoader;

import java.util.Locale;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class SettingsPage extends Fragment {
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_COLOUR_BLIND_THEME = "colour_blind_theme";

    private ToggleButton toggleButton;
    private RadioGroup group;
    private RadioButton buttonDutch;
    private RadioButton buttonEnglish;
    private Button apply;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.toggleButton = getView().findViewById(R.id.settings_colour_blind_button);
        this.group = getView().findViewById(R.id.settings_radio_buttons);
        this.apply = getView().findViewById(R.id.settings_apply);
        this.buttonDutch = getView().findViewById(R.id.settings_language_dutch);
        this.buttonEnglish = getView().findViewById(R.id.settings_language_english);

        this.buttonDutch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = DataSingleton.getInstance().getMainContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                editor.putBoolean("isDutch", true);
                editor.apply();
                JSonLoader.readAllJsonFiles();
            }
        });
        this.buttonEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = DataSingleton.getInstance().getMainContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                editor.putBoolean("isDutch", false);
                editor.apply();
                JSonLoader.readAllJsonFiles();
            }
        });

        this.apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).finish();
                Intent intent = getActivity().getIntent();
                startActivity(intent);
            }
        });

        this.toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggleButton.isChecked()){
                    SharedPreferences.Editor editor = DataSingleton.getInstance().getMainContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean("switchKey", true);
                    editor.apply();
                    toggleColourBlindMode(true);
                } else {
                    SharedPreferences.Editor editor = DataSingleton.getInstance().getMainContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean("switchKey", false);
                    editor.apply();
                    toggleColourBlindMode(false);
                }
            }
        });
        SharedPreferences preferences = DataSingleton.getInstance().getMainContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isChecked = preferences.getBoolean("switchKey", false);
        this.toggleButton.setChecked(isChecked);
    }

    public static void setAppLocale(String localeCode, Resources resources) {
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            config.setLocale(new Locale(localeCode.toLowerCase()));
        } else {
            config.locale = new Locale(localeCode.toLowerCase());
        }
        resources.updateConfiguration(config, displayMetrics);
    }

    private void toggleColourBlindMode(boolean colourBlindTheme) {
        SharedPreferences.Editor editor = DataSingleton.getInstance().getMainContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(PREF_COLOUR_BLIND_THEME, colourBlindTheme);
        editor.apply();
    }
}

