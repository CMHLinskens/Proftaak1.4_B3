package com.example.esstelingapp;

import android.app.admin.DelegatedAdminReceiver;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.esstelingapp.data.DataSingleton;
import com.example.esstelingapp.games.RiddlePage;
import com.example.esstelingapp.ui.Activity_read_story;
import com.example.esstelingapp.ui.HomePage;
import com.example.esstelingapp.ui.StoryPage;

import java.io.DataInputStream;

public class FragmentTravel {

    private static final String USER_DATA = "userData";
    private static final String USER_POINTS = "points";
    private static final String USER_TOTAL_POINTS = "totalPoints";
    private static final String STORY_COMPLETE = "storyComplete";
    private static final String PROGRESS = "progress";

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
                transaction.replace(R.id.fragment_container, readstoryFragment).commit();

            } else if (subjectStory.getPieces().get(marker) instanceof GameItem) {
                Fragment riddlePage = new RiddlePage();
                riddlePage.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                if (direction == 1)
                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                else transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                transaction.replace(R.id.fragment_container, riddlePage).commit();

            } else if (subjectStory.getPieces().get(marker) instanceof ActionItem) {
                Fragment actionWindow = new Action_window();
                actionWindow.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                if (direction == 1)
                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                else transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);

                transaction.replace(R.id.fragment_container, actionWindow).commit();
            }
        } else {
            SharedPreferences preferences = DataSingleton.getInstance().getMainContext().getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            float progress = preferences.getFloat(PROGRESS + storyIndex, 0);
            boolean storyStatus = preferences.getBoolean(STORY_COMPLETE + storyIndex, false);

            if (progress == 100.0f && !storyStatus) {
                editor.putBoolean(STORY_COMPLETE + storyIndex, true);
                storyStatus = true;
                DataSingleton.getInstance().getUser().addToTotal(subjectStory.getStoryCompletionReward());
                Log.d("STORY COMPLETED", "I HAVE REACHED THE IF STATEMENT");
            }
            Log.d("STORY STATUS", String.valueOf(storyStatus));

            editor.putInt(USER_POINTS, DataSingleton.getInstance().getUser().getPoints());

            Log.d("USER TOTAL POINTS", String.valueOf(DataSingleton.getInstance().getUser().getTotalPoints()));

            DataSingleton.getInstance().getUser().setPoints(0);
            editor.putInt(USER_TOTAL_POINTS, DataSingleton.getInstance().getUser().getTotalPoints());
            editor.apply();

            if (subjectStory.getStoryName().equals("Tutorial")/*&&shared preference isFirstTime==true*/) {
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


}
