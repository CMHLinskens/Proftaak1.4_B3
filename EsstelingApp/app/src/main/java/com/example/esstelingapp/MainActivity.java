package com.example.esstelingapp;

import android.os.Bundle;

import com.example.esstelingapp.data.DataSingleton;
import com.example.esstelingapp.json.JSonLoader;

import java.util.ArrayList;
import java.util.HashSet;

import com.example.esstelingapp.games.RiddlePage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        runRiddle();
    }

    private void runRiddle() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RiddlePage()).commit();
//        Intent gameIntent = new Intent(this, RiddlePage.class);
//        startActivity(gameIntent);
    }
}
