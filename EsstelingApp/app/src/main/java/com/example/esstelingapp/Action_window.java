package com.example.esstelingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.esstelingapp.mqtt.MQTTController;

public class Action_window extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_window);
        Button nextButton = (Button) findViewById(R.id.nextButton);
        Button actionButton = (Button)findViewById(R.id.actionButton);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MQTTController.getInstance().sendRawMessage("B3/OVEN");
            }
        });
    }

}
