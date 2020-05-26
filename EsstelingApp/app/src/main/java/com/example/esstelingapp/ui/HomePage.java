package com.example.esstelingapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.esstelingapp.R;

public class HomePage extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Finding the random fact text and filling it with a fact
        TextView randomFactText = (TextView) getView().findViewById(R.id.random_fact_text);
        // TODO make it actually take a random fact from a file
        randomFactText.setText("De Essteling geeft niet om klantvriendelijkheid.");

        // Finding the progress bars and setting it a random value
        ProgressBar storyProgressBar = (ProgressBar) getView().findViewById(R.id.story_progressBar);
        ProgressBar achievementProgressBar = (ProgressBar) getView().findViewById(R.id.achievement_progressBar);
        // TODO connect it to the user data and calculate progress
        storyProgressBar.setProgress(80);
        achievementProgressBar.setProgress(33);
    }
}
