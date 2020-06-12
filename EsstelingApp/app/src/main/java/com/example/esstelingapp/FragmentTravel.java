package com.example.esstelingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.esstelingapp.games.RiddlePage;
import com.example.esstelingapp.ui.Activity_read_story;
import com.example.esstelingapp.ui.HomePage;
import com.example.esstelingapp.ui.StoryPage;

public class FragmentTravel {

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
                Fragment storylistFragment = new StoryPage();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.fragment_container, storylistFragment).commit();
            }
        }
    }


}
