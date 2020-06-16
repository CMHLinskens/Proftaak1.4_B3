package com.example.esstelingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.esstelingapp.data.DataSingleton;
import com.example.esstelingapp.games.RiddlePage;
import com.example.esstelingapp.ui.Activity_read_story;
import com.example.esstelingapp.ui.HomePage;
import com.example.esstelingapp.ui.StoryPage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentTravel {

    private static final String USER_DATA = "userData";
    private static final String USER_POINTS = "points";
    private static final String USER_TOTAL_POINTS = "totalPoints";
    private static final String STORY_COMPLETE = "storyComplete";
    private static final String PROGRESS = "progress";
    private static final String ACHIEVEMENT_PROGRESS = "achievementProgress";
    private static final String ACHIEVEMENT_COMPLETED = "achievementCompleted";

    public static void fragmentTravel(int direction, int marker, Story subjectStory, FragmentManager fragmentManager, int storyIndex) {
        marker += direction;
        Bundle bundle = new Bundle();

        bundle.putInt("storyMarker", marker);
        bundle.putParcelable("storyInfo", subjectStory);  // Key, value
        bundle.putInt("storyIndex", storyIndex);

        if (marker < subjectStory.getPieces().size() && marker >= 0) {
            if (subjectStory.getPieces().get(marker) instanceof ReadingItem) {
                Fragment readstoryFragment = new Activity_read_story();
                readstoryFragment.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                if (direction == 1)
                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                else transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                transaction.replace(R.id.fragment_container, readstoryFragment, "STORY_FRAGMENT").commit();

            } else if (subjectStory.getPieces().get(marker) instanceof GameItem) {
                Fragment riddlePage = new RiddlePage();
                riddlePage.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                if (direction == 1)
                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                else transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                transaction.replace(R.id.fragment_container, riddlePage, "STORY_FRAGMENT").commit();
            } else if (subjectStory.getPieces().get(marker) instanceof ActionItem) {
                Fragment actionWindow = new Action_window();
                actionWindow.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                if (direction == 1)
                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                else transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);

                transaction.replace(R.id.fragment_container, actionWindow, "STORY_FRAGMENT").commit();
            }
        } else {
            SharedPreferences preferences = DataSingleton.getInstance().getMainContext().getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            float progress = preferences.getFloat(PROGRESS + storyIndex, 0);
            boolean storyStatus = preferences.getBoolean(STORY_COMPLETE + storyIndex, false);

            if (progress >= 100.0f && !storyStatus) {
                editor.putBoolean(STORY_COMPLETE + storyIndex, true);
                editor.putBoolean(ACHIEVEMENT_COMPLETED + storyIndex, true);
                editor.putFloat(ACHIEVEMENT_PROGRESS + storyIndex, 100.0f);
                Toast.makeText(DataSingleton.getInstance().getMainContext(),
                        "Achievement unlocked: " + DataSingleton.getInstance().getAchievements().get(storyIndex).getAchievementName(),
                        Toast.LENGTH_SHORT).show();
                DataSingleton.getInstance().getAchievements().get(storyIndex).setAchievementStatus(true);
                storyStatus = true;
                DataSingleton.getInstance().getUser().addToTotal(subjectStory.getStoryCompletionReward());
            }
            float masterAchievement;
            masterAchievement = (DataSingleton.getInstance().getUser().getTotalPoints() /
                    DataSingleton.getInstance().getAchievements().get((DataSingleton.getInstance().getStories().size() + 1)).getMasterMax()) * 100;
            float juniorAchievement;
            juniorAchievement = (DataSingleton.getInstance().getUser().getTotalPoints() /
                    DataSingleton.getInstance().getAchievements().get((DataSingleton.getInstance().getStories().size())).getJuniorMax()) * 100;

            editor.putFloat(ACHIEVEMENT_PROGRESS + (DataSingleton.getInstance().getStories().size()), juniorAchievement);
            editor.putFloat(ACHIEVEMENT_PROGRESS + (DataSingleton.getInstance().getStories().size() + 1), masterAchievement);

            boolean achievementStatusJunior = preferences.getBoolean(ACHIEVEMENT_COMPLETED + (DataSingleton.getInstance().getStories().size()), false);
            boolean achievementStatusMaster = preferences.getBoolean(ACHIEVEMENT_COMPLETED + (DataSingleton.getInstance().getStories().size() + 1), false);
            if ((juniorAchievement >= 100.0f) && !achievementStatusJunior) {
                editor.putBoolean(ACHIEVEMENT_COMPLETED + (DataSingleton.getInstance().getStories().size()), true);
                Toast.makeText(DataSingleton.getInstance().getMainContext(),
                        "Achievement unlocked: " + DataSingleton.getInstance().getAchievements().get((DataSingleton.getInstance().getStories().size())).getAchievementName(),
                        Toast.LENGTH_SHORT).show();
                achievementStatusJunior = true;
            }
            if ((masterAchievement >= 100.0f) && !achievementStatusMaster) {
                editor.putFloat(ACHIEVEMENT_PROGRESS + (DataSingleton.getInstance().getStories().size() + 1), 100.0f);
                editor.putBoolean(ACHIEVEMENT_COMPLETED + (DataSingleton.getInstance().getStories().size() + 1), true);

                Toast.makeText(DataSingleton.getInstance().getMainContext(),
                        "Achievement unlocked: " + DataSingleton.getInstance().getAchievements().get((DataSingleton.getInstance().getStories().size() + 1)).getAchievementName(),
                        Toast.LENGTH_SHORT).show();
                achievementStatusMaster = true;
            }

            editor.putInt(USER_POINTS, DataSingleton.getInstance().getUser().getPoints());

            DataSingleton.getInstance().getUser().setPoints(0);
            editor.putInt(USER_TOTAL_POINTS, DataSingleton.getInstance().getUser().getTotalPoints());
            editor.apply();
            Log.d("tutorial:", "here");
            if (subjectStory.getStoryName().equals("Tutorial") && preferences.getBoolean("isFirstTime", true)) {
                SharedPreferences.Editor prefEditor = preferences.edit();
                prefEditor.putBoolean("isFirstTime", false);
                prefEditor.apply();
                Fragment HomePageFragment = new HomePage();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.fragment_container, HomePageFragment).commit();
            } else {
                Fragment storyListFragment = new StoryPage();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.fragment_container, storyListFragment).commit();
            }
        }
    }

    public static void returnHome(BottomNavigationView bottomNav, FragmentManager fragmentManager) {
        // Set the bottom nav icon
        int homePageID = 2131230931;
        bottomNav.setSelectedItemId(homePageID);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);

        transaction.replace(R.id.fragment_container, new HomePage()).commit();
    }
}
