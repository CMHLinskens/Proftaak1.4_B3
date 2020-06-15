package com.example.esstelingapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.esstelingapp.Achievement;
import com.example.esstelingapp.R;
import com.example.esstelingapp.Story;
import com.example.esstelingapp.data.DataSingleton;

import java.util.Random;

public class HomePage extends Fragment {

    private static final String USER_DATA = "userData";
    private static final String USER_POINTS = "points";
    private static final String USER_TOTAL_POINTS = "totalPoints";
    private static final String STORY_COMPLETE = "storyComplete";
    private static final String PROGRESS = "progress";
    private static final String ACHIEVEMENT_PROGRESS = "achievementProgress";
    private static final String ACHIEVEMENT_COMPLETED = "achievementCompleted";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences preferences = DataSingleton.getInstance().getMainContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences userPrefs = DataSingleton.getInstance().getMainContext().getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        boolean isColourblind = preferences.getBoolean("colour_blind_theme", false);
        ImageView appLogo = (ImageView) getView().findViewById(R.id.logo_image);
        ImageView backgroundImage = getView().findViewById(R.id.background_image);

        if(isColourblind){
            appLogo.setImageResource(R.drawable.applogo_cb);
            backgroundImage.setImageResource(R.drawable.home_background_image_cb);
        } else {
            appLogo.setImageResource(R.drawable.applogo);
            backgroundImage.setImageResource(R.drawable.home_background_image);
        }
        // Finding the random fact text and filling it with a fact
        TextView randomFactText = (TextView) getView().findViewById(R.id.random_fact_text);
        Random rand = new Random();
        int randomFactIndex = rand.nextInt(DataSingleton.getInstance().getRandomFacts().size());
        String randomFact = DataSingleton.getInstance().getRandomFacts().get(randomFactIndex);
        randomFactText.setText(randomFact);

        ProgressBar storyProgressBar = (ProgressBar) getView().findViewById(R.id.story_progressBar);
        ProgressBar achievementProgressBar = (ProgressBar) getView().findViewById(R.id.achievement_progressBar);
        // Finding the progress bars and setting it a random value
        int i = 0;
        int j = 0;
        boolean placeHolder, achievementUnlocked;
        for (Story story : DataSingleton.getInstance().getStories()){
            placeHolder = userPrefs.getBoolean(STORY_COMPLETE + i, false);
            if(placeHolder){
                j++;
            }
            i++;
        }
        int storyProgress = (int) (((double) j/i) * 100);
        // TODO connect it to the user data and calculate progress
        storyProgressBar.setProgress(storyProgress);
        int x = 0;
        int y = 0;
        for (Achievement achievement : DataSingleton.getInstance().getAchievements()){
            achievementUnlocked = userPrefs.getBoolean(ACHIEVEMENT_COMPLETED + x, false);
            if (achievementUnlocked){
                y++;
            }
            x++;
        }

        int achievementProgress = (int) (((double) y/x) * 100);
        achievementProgressBar.setProgress(achievementProgress);
    }
}
