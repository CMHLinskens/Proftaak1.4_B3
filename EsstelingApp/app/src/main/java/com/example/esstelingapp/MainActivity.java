package com.example.esstelingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.esstelingapp.games.RiddlePage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        runRiddle();
    }

    private void runRiddle() {
        Intent gameIntent = new Intent(this, RiddlePage.class);
        startActivity(gameIntent);
    }
}
