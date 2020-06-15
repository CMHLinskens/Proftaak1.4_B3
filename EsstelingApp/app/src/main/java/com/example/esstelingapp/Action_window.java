package com.example.esstelingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.esstelingapp.data.DataSingleton;
import com.example.esstelingapp.games.RiddlePage;
import com.example.esstelingapp.mqtt.MQTTController;
import com.example.esstelingapp.ui.Activity_read_story;
import com.example.esstelingapp.ui.OnSwipeTouchListener;
import com.example.esstelingapp.ui.StoryPage;

public class Action_window extends Fragment {
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_COLOUR_BLIND_THEME = "colour_blind_theme";

    private Story subjectStory;
    private int marker;
    private View RootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RootView = inflater.inflate(R.layout.activity_action_window, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            subjectStory = bundle.getParcelable("storyInfo"); // Key
            try {
                marker = bundle.getInt("storyMarker");
            } catch (Exception e) {
                System.out.println("storymarker was empty");
                marker = 0;
            }
        }

        SharedPreferences sharedPreferences = DataSingleton.getInstance().getMainContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Boolean isColorBlind = sharedPreferences.getBoolean(PREF_COLOUR_BLIND_THEME, false);
        if (isColorBlind){
            RootView.setBackgroundResource(R.drawable.old_paper_cb);
        }else {
            RootView.setBackgroundResource(R.drawable.old_paper);
        }


        Button actionButton = (Button) RootView.findViewById(R.id.actionButton);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MQTTController.getInstance().sendRawMessage("B3/OVEN");
            }
        });


        Button nextButton = (Button) RootView.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTravel.fragmentTravel(1, marker, subjectStory, getFragmentManager());
            }
        });

        RootView.setOnTouchListener(new OnSwipeTouchListener(container.getContext()) {
            @Override
            public void onSwipeRight() {
                FragmentTravel.fragmentTravel(-1, marker, subjectStory, getFragmentManager());
            }

            @Override
            public void onSwipeLeft() {
                FragmentTravel.fragmentTravel(1, marker, subjectStory, getFragmentManager());
            }
        });


        return RootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        TextView title = RootView.findViewById(R.id.titleTextView);
        title.setText(subjectStory.getStoryName());
        TextView partOfStory = RootView.findViewById(R.id.pageTextView);
        String text = "part " + (marker + 1) + " of " + subjectStory.getPieces().size();
        partOfStory.setText(text);

        super.onViewCreated(view, savedInstanceState);
    }

    public int getMarker() {
        return marker;
    }
    public Story getSubjectStory() {
        return subjectStory;
    }
}
