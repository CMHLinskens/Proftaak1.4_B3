package com.example.esstelingapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.esstelingapp.R;
import com.example.esstelingapp.data.DataSingleton;

import java.util.Random;

public class HomePage extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences preferences = DataSingleton.getInstance().getMainContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
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

        // Finding the progress bars and setting it a random value
        ProgressBar storyProgressBar = (ProgressBar) getView().findViewById(R.id.story_progressBar);
        ProgressBar achievementProgressBar = (ProgressBar) getView().findViewById(R.id.achievement_progressBar);
        // TODO connect it to the user data and calculate progress
        storyProgressBar.setProgress(80);
        achievementProgressBar.setProgress(33);
    }
}
