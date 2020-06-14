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

    public static void fragmentTravel(int direction, int marker, Story subjectStory, FragmentManager fragmentManager) {
        marker += direction;
        if (marker < subjectStory.getPieces().size() && marker >= 0) {
            if (subjectStory.getPieces().get(marker) instanceof ReadingItem) {
                Fragment readstoryFragment = new Activity_read_story();
                Bundle bundle = new Bundle();

                bundle.putInt("storyMarker", marker);
                bundle.putParcelable("storyInfo", subjectStory);  // Key, value
                readstoryFragment.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                if (direction == 1)
                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                else transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                transaction.replace(R.id.fragment_container, readstoryFragment).commit();

            } else if (subjectStory.getPieces().get(marker) instanceof GameItem) {
                Fragment riddlePage = new RiddlePage();
                Bundle bundle = new Bundle();

                bundle.putInt("storyMarker", marker);
                bundle.putParcelable("storyInfo", subjectStory);  // Key, value
                riddlePage.setArguments(bundle);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                if (direction == 1)
                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                else transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                transaction.replace(R.id.fragment_container, riddlePage).commit();

            } else if (subjectStory.getPieces().get(marker) instanceof ActionItem) {
                Fragment actionWindow = new Action_window();
                Bundle bundle = new Bundle();

                bundle.putInt("storyMarker", marker);
                bundle.putParcelable("storyInfo", subjectStory);  // Key, value
                actionWindow.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                if (direction == 1)
                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                else transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);

                transaction.replace(R.id.fragment_container, actionWindow).commit();
            }
        } else {
            if (subjectStory.getStoryName().equals("Tutorial")/*&&shared preference isFirstTime==true*/) {
                Fragment HomePageFragement = new HomePage();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.fragment_container, HomePageFragement).commit();
            } else {
                Fragment storyListFragment = new StoryPage();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.fragment_container, storyListFragment).commit();

                SharedPreferences preferences = DataSingleton.getInstance().getMainContext().getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putInt(USER_POINTS, DataSingleton.getInstance().getUser().getPoints());
                editor.putInt(USER_TOTAL_POINTS, DataSingleton.getInstance().getUser().getTotalPoints());
                int i = 0;
                for (Story story : DataSingleton.getInstance().getStories()) {
                    if (story.getStoryName().equals(subjectStory.getStoryName())) {
                        editor.putInt(PROGRESS + i, subjectStory.getStoryProgress());
                        editor.putBoolean(STORY_COMPLETE + i, true);
                        Log.d("STORY PROGRESS", String.valueOf(subjectStory.getStoryProgress()));
                    }
                    i++;
                }

                if (subjectStory.getStoryStatus()) {
                    DataSingleton.getInstance().getUser().addToTotal(subjectStory.getStoryCompletionReward());
                    DataSingleton.getInstance().getUser().setPoints(0);
                    Log.d("POINTS", String.valueOf(DataSingleton.getInstance().getUser().getPoints()));
                }
                editor.apply();

                Log.d("USER TOTAL POINTS", String.valueOf(DataSingleton.getInstance().getUser().getTotalPoints()));

            }
        }
    }


}
