package com.example.esstelingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.esstelingapp.data.DataSingleton;
import com.example.esstelingapp.ui.AchievementPage;
import com.example.esstelingapp.ui.HomePage;
import com.example.esstelingapp.ui.SettingsPage;
import com.example.esstelingapp.ui.StoryPage;
import com.example.esstelingapp.ui.StoryUnlockPopup;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.esstelingapp.json.JSonLoader;

import com.example.esstelingapp.games.RiddlePage;

public class MainActivity extends AppCompatActivity implements StoryUnlockPopup.ExampleDialogListener {
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_COLOUR_BLIND_THEME = "colour_blind_theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useColourBlindTheme = preferences.getBoolean(PREF_COLOUR_BLIND_THEME, false);
        if (useColourBlindTheme) {
            setTheme(R.style.ColourBlindTheme);
        }

        if(preferences.getBoolean("isDutch", true)){
            SettingsPage.setAppLocale("nl", getResources());
        } else {
            SettingsPage.setAppLocale("en", getResources());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomePage()).commit();
        loadData();
    }

    private void loadData(){
        if(!DataSingleton.getInstance().isMainLoaded()){
            DataSingleton.getInstance().setMainContext(this);
            JSonLoader.readAllJsonFiles();
            DataSingleton.getInstance().setMainLoaded(true);
        }
    }

    // Use this to start the riddle
    private void runRiddle() {
        Intent gameIntent = new Intent(this, RiddlePage.class);
        startActivity(gameIntent);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomePage();
                    break;
                case R.id.nav_story:
                    selectedFragment = new StoryPage();
                    break;
                case R.id.nav_achievements:
                    selectedFragment = new AchievementPage();
                    break;
                case R.id.nav_settings:
                    selectedFragment = new SettingsPage();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            return true;
        }

        ;
    };

    @Override
    public void applyCode(String code) {
        if (code.equals("epic"))
            System.out.println("Correct");
        else
            System.out.println("Incorrect");
    }
}
