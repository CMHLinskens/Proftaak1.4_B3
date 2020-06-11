package com.example.esstelingapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.esstelingapp.data.DataSingleton;
import com.example.esstelingapp.games.RiddlePage;
import com.example.esstelingapp.json.JSonLoader;
import com.example.esstelingapp.mqtt.MQTTController;
import com.example.esstelingapp.ui.AchievementPage;
import com.example.esstelingapp.ui.HomePage;
import com.example.esstelingapp.ui.SettingsPage;
import com.example.esstelingapp.ui.StoryPage;
import com.example.esstelingapp.ui.StoryUnlockPopup;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements StoryUnlockPopup.ExampleDialogListener {
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_COLOUR_BLIND_THEME = "colour_blind_theme";
    private int currentTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useColourBlindTheme = preferences.getBoolean(PREF_COLOUR_BLIND_THEME, false);
        if (useColourBlindTheme) {
            setTheme(R.style.ColourBlindTheme);
        }

        // Put the app in the preferred language
        if(preferences.getBoolean("isDutch", true)){
            SettingsPage.setAppLocale("nl", getResources());
        } else {
            SettingsPage.setAppLocale("en", getResources());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        currentTab = 0;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomePage()).commit();
        loadData();
        MQTTController.getInstance().connectToServer(this);
    }

    private void loadData(){
        if(!DataSingleton.getInstance().isMainLoaded()){
            DataSingleton.getInstance().setMainContext(this);
            JSonLoader.readAllJsonFiles();
            DataSingleton.getInstance().setMainLoaded(true);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int animationDirection = 0;
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomePage();
                    if (currentTab != 0 && currentTab > 0) {
                        animationDirection = 1;
                    }
                    currentTab = 0;
                    break;
                case R.id.nav_story:
                    selectedFragment = new StoryPage();
                    if (currentTab != 1 && currentTab > 1) {
                        animationDirection = 1;
                    }
                    else if (currentTab != 1 && currentTab < 1) {
                        animationDirection = 2;
                    }
                    currentTab = 1;
                    break;
                case R.id.nav_achievements:
                    selectedFragment = new AchievementPage();
                    if (currentTab != 2 && currentTab > 2) {
                        animationDirection = 1;
                    }
                    else if (currentTab != 2 && currentTab < 2) {
                        animationDirection = 2;
                    }
                    currentTab = 2;
                    break;
                case R.id.nav_settings:
                    selectedFragment = new SettingsPage();
                    if (currentTab != 3 && currentTab < 3) {
                        animationDirection = 2;
                    }
                    currentTab = 3;
                    break;
            }

            if (animationDirection == 0) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            }
            else if (animationDirection == 1) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.fragment_container, selectedFragment).commit();
            }
            else if (animationDirection == 2){
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                fragmentTransaction.replace(R.id.fragment_container, selectedFragment).commit();
            }
            return true;
        }
    };

    @Override
    public void applyCode(String code, Story story) {
        if (code.equals("wachtwoord")) {
            System.out.println("Correct");
            story.setStoryStatus(true);
        }
        else
            System.out.println("Incorrect");
    }

    private void runRiddle() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RiddlePage()).commit();
    }
}
