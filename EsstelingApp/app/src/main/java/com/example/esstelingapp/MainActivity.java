package com.example.esstelingapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.esstelingapp.data.DataSingleton;
import com.example.esstelingapp.games.RiddlePage;
import com.example.esstelingapp.json.JSonLoader;
import com.example.esstelingapp.mqtt.MQTTController;
import com.example.esstelingapp.ui.AchievementPage;
import com.example.esstelingapp.ui.Activity_read_story;
import com.example.esstelingapp.ui.HomePage;
import com.example.esstelingapp.ui.SettingsPage;
import com.example.esstelingapp.ui.StoryPage;
import com.example.esstelingapp.ui.StoryUnlockPopup;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
        loadData();
        if (preferences.getBoolean("isFirstTime", true)){
            Story Tutorial = DataSingleton.getInstance().getStories().get(0);
            Fragment readstoryFragment = new Activity_read_story();
            Bundle bundle = new Bundle();
            bundle.putParcelable("storyInfo", Tutorial);  // Key, value
            readstoryFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,readstoryFragment).commit();
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomePage()).commit();
        }

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
