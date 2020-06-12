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
import android.widget.TextView;

import com.example.esstelingapp.games.RiddlePage;
import com.example.esstelingapp.mqtt.MQTTController;
import com.example.esstelingapp.ui.Activity_read_story;
import com.example.esstelingapp.ui.OnSwipeTouchListener;
import com.example.esstelingapp.ui.StoryPage;

import java.util.ArrayList;

public class Action_window extends Fragment {
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

        ArrayList<StoryPiecesInterface> storyArrayList = subjectStory.getPieces();
        final ActionItem actionItem = (ActionItem) storyArrayList.get(marker);

        final Button actionButton = (Button) RootView.findViewById(R.id.actionButton);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionItem.canGainPoints()){
                    subjectStory.addPointsToStory(actionItem.getPoints());
                    actionItem.setGainPoints(false);
                }
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
}
