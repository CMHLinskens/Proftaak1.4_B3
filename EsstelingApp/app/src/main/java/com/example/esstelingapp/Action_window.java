package com.example.esstelingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.esstelingapp.games.RiddlePage;
import com.example.esstelingapp.mqtt.MQTTController;
import com.example.esstelingapp.ui.Activity_read_story;
import com.example.esstelingapp.ui.StoryPage;

public class Action_window extends Fragment {
    private Story subjectStory;
    private int marker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.activity_action_window, container, false);
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


        Button actionButton = (Button)RootView.findViewById(R.id.actionButton);
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
                marker++;
        if (marker<subjectStory.getPieces().size()) {
            if (subjectStory.getPieces().get(marker) instanceof ReadingItem) {
                Fragment readstoryFragment = new Activity_read_story();
                Bundle bundle = new Bundle();

                bundle.putInt("storyMarker", marker);
                bundle.putParcelable("storyInfo", subjectStory);  // Key, value
                readstoryFragment.setArguments(bundle);

                getFragmentManager().beginTransaction().replace(R.id.fragment_container, readstoryFragment).commit();
            }
            else if (subjectStory.getPieces().get(marker) instanceof GameItem){
                Fragment riddlePage = new RiddlePage();
                Bundle bundle = new Bundle();

                bundle.putInt("storyMarker", marker);
                bundle.putParcelable("storyInfo", subjectStory);  // Key, value
                riddlePage.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, riddlePage).commit();
            }
            else if(subjectStory.getPieces().get(marker)instanceof ActionItem){
                Fragment actionWindow = new Action_window();
                Bundle bundle = new Bundle();

                bundle.putInt("storyMarker", marker);
                bundle.putParcelable("storyInfo", subjectStory);  // Key, value
                actionWindow.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, actionWindow).commit();
            }
        }
        else {
            Fragment storylistFragment = new StoryPage();
            getFragmentManager().beginTransaction().replace(R.id.fragment_container,storylistFragment).commit();
        }
    }});


        return RootView;
    }
}
