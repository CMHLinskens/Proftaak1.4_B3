package com.example.esstelingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.DragAndDropPermissions;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.esstelingapp.data.DataSingleton;
import com.example.esstelingapp.data.User;
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

import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity implements StoryUnlockPopup.ExampleDialogListener {
    private static final String PREFS_NAME = "prefs";
    private static final String USER_DATA = "userData";
    private static final String PROGRESS = "progress";
    private static final String USER_TOTAL_POINTS = "totalPoints";
    private static final String USER_POINTS = "points";
    private static final String STORY_COMPLETE = "storyComplete";
    private static final String UNLOCK = "storyUnlock";
    private static final String PREF_COLOUR_BLIND_THEME = "colour_blind_theme";
    private SharedPreferences userPref;
    private int currentTab;
    private BottomNavigationView bottomNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        this.userPref = getSharedPreferences(USER_DATA, MODE_PRIVATE);
        boolean useColourBlindTheme = preferences.getBoolean(PREF_COLOUR_BLIND_THEME, false);
        if (useColourBlindTheme) {
            setTheme(R.style.ColourBlindTheme);
        } else {
            setTheme(R.style.EsstelingTheme);
        }
        // FOR TESTING
        clearPrefs();
        // ---
        // Put the app in the preferred language
        if (preferences.getBoolean("isDutch", true)) {
            SettingsPage.setAppLocale("nl", getResources());
        } else {
            SettingsPage.setAppLocale("en", getResources());
        }

        SharedPreferences.Editor editor = this.userPref.edit();
        editor.putBoolean("storyUnlock0", true);
        editor.apply();

        loadData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.bottomNav = findViewById(R.id.bottomNavigationView);
        this.bottomNav.setOnNavigationItemSelectedListener(navListener);
        if (this.userPref.getBoolean("isFirstTime", true)){
            Story Tutorial = DataSingleton.getInstance().getStories().get(0);
            Fragment readstoryFragment = new Activity_read_story();
            Bundle bundle = new Bundle();
            bundle.putParcelable("storyInfo", Tutorial);  // Key, value
            bundle.putInt("storyIndex", 0);
            readstoryFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,readstoryFragment).commit();
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomePage()).commit();
        }

        currentTab = 0;
        MQTTController.getInstance().connectToServer(this);
    }

    private void loadData() {
        if (!DataSingleton.getInstance().isMainLoaded()) {
            Log.d("INIT", "Loading data");
            DataSingleton.getInstance().setMainContext(this);
            DataSingleton.getInstance().setUser(new User(0));
            DataSingleton.getInstance().getUser().setTotalPoints(this.userPref.getInt(USER_TOTAL_POINTS, 0));
            JSonLoader.readAllJsonFiles();
            DataSingleton.getInstance().setMainLoaded(true);
            Log.d("INIT", "Data loaded");
        }
    }

    private void clearPrefs() {
        SharedPreferences.Editor editor = this.userPref.edit();
        editor.clear();
        editor.apply();
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
                    } else if (currentTab != 1 && currentTab < 1) {
                        animationDirection = 2;
                    }
                    currentTab = 1;
                    break;
                case R.id.nav_achievements:
                    selectedFragment = new AchievementPage();
                    if (currentTab != 2 && currentTab > 2) {
                        animationDirection = 1;
                    } else if (currentTab != 2 && currentTab < 2) {
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
            } else if (animationDirection == 1) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.fragment_container, selectedFragment).commit();
            } else if (animationDirection == 2) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                fragmentTransaction.replace(R.id.fragment_container, selectedFragment).commit();
            }
            return true;
        }
    };

    @Override
    public void applyCode(String code, Story story, int position) {
        SharedPreferences.Editor editor = DataSingleton.getInstance().getMainContext().getSharedPreferences(USER_DATA, MODE_PRIVATE).edit();
        if (code.equals("wachtwoord")) {
            System.out.println("Correct");
            editor.putBoolean(UNLOCK + position, true);
            editor.apply();
            story.setUnlocked(true);
        } else
            System.out.println("Incorrect");
    }

    private void runRiddle() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RiddlePage()).commit();
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("STORY_FRAGMENT");
        if(currentFragment != null){
            if(currentFragment.isVisible()) {
                if(currentFragment instanceof Activity_read_story) {
                    FragmentTravel.fragmentTravel(-1, ((Activity_read_story)currentFragment).getMarker(), ((Activity_read_story)currentFragment).getSubjectStory(), getSupportFragmentManager(), 0);
                } else if(currentFragment instanceof Action_window){
                    FragmentTravel.fragmentTravel(-1, ((Action_window)currentFragment).getMarker(), ((Action_window)currentFragment).getSubjectStory(), getSupportFragmentManager(), 0);
                } else if(currentFragment instanceof RiddlePage){
                    FragmentTravel.fragmentTravel(-1, ((RiddlePage)currentFragment).getMarker(), ((RiddlePage)currentFragment).getSubjectStory(), getSupportFragmentManager(), 0);
                }
            }
        } else {
            FragmentTravel.returnHome(this.bottomNav, getSupportFragmentManager());
        }
    }
}
