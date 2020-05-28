package com.example.esstelingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.esstelingapp.data.DataSingleton;
import com.example.esstelingapp.json.JSonLoader;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataSingleton.getInstance().setMainContext(this);

        JSonLoader.readAllJsonFiles();
    }
}
