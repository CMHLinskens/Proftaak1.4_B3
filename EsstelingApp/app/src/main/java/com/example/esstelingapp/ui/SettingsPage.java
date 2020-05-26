package com.example.esstelingapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    private RadioGroup group;
    private RadioButton radioButton;
    private TextView txtView1;
    private TextView txtView2;
    private ToggleButton button;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.button = findViewById(R.id.settings_colour_blind_button);
        this.group = findViewById(R.id.settings_radio_buttons);

    }

    public void onClick(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button.isChecked()){
                    Log.d("CLICKABLE BUTTON", "");
                } else {
                    Log.d("CLICKABLE BUTTON", "Button has turned off!");
                }
            }
        });
    }

    public void checkButton(View view) {
        int radioId = group.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);

        Toast.makeText(this, "Language has changed to: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }
}

