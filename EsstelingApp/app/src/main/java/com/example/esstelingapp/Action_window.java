package com.example.esstelingapp;

import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esstelingapp.data.DataSingleton;
import com.example.esstelingapp.mqtt.MQTTController;
import com.example.esstelingapp.ui.OnSwipeTouchListener;

import java.time.LocalTime;

public class Action_window extends Fragment {
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_COLOUR_BLIND_THEME = "colour_blind_theme";

    private Story subjectStory;
    private int marker;
    private int storyIndex;
    private View RootView;

    private static final String STORY_COMPLETE = "storyComplete";
    private static final String PROGRESS = "progress";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RootView = inflater.inflate(R.layout.activity_action_window, container, false);
        final SharedPreferences preferences = DataSingleton.getInstance().getMainContext().getSharedPreferences("userData", Context.MODE_PRIVATE);
        final SharedPreferences.Editor preferenceEditor = preferences.edit();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            subjectStory = bundle.getParcelable("storyInfo"); // Key
            this.storyIndex = bundle.getInt("storyIndex");
            try {
                marker = bundle.getInt("storyMarker");
            } catch (Exception e) {
                System.out.println("storymarker was empty");
                marker = 0;
            }
        }

        SharedPreferences sharedPreferences = DataSingleton.getInstance().getMainContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isColorBlind = sharedPreferences.getBoolean(PREF_COLOUR_BLIND_THEME, false);
        if (isColorBlind){
            RootView.setBackgroundResource(R.drawable.old_paper_cb);
        }else {
            RootView.setBackgroundResource(R.drawable.old_paper);
        }
        final ActionItem item = (ActionItem) subjectStory.getPieces().get(marker);
        final TextView actionText = RootView.findViewById(R.id.ActionText);
        actionText.setText(item.getPreActionText());

        //initializing image
        final ImageView imageView = RootView.findViewById(R.id.actionImageView);
        if (!item.getPreImage().isEmpty()) {
            imageView.getLayoutParams().height = 850;
            imageView.getLayoutParams().width = 850;
            int id = DataSingleton.getInstance().getMainContext().getResources().getIdentifier(item.getPreImage(), "drawable", DataSingleton.getInstance().getMainContext().getPackageName());
            imageView.setImageResource(id);
        }
        else {
            imageView.setVisibility(View.INVISIBLE);
            imageView.getLayoutParams().height = 400;
            imageView.getLayoutParams().width = 50;
        }

        Button actionButton = (Button) RootView.findViewById(R.id.actionButton);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = DataSingleton.getInstance().getMainContext().getSharedPreferences("userData", Context.MODE_PRIVATE);
                LocalTime lastActionTime = LocalTime.parse(preferences.getString("lastAction" + subjectStory.getStoryName(), LocalTime.now().minusHours(1).toString()));
                if (lastActionTime.plusHours(1).isBefore(LocalTime.now())) {
                    if (subjectStory.getStoryName().equals("Draak blaaskaak") || subjectStory.getStoryName().equals("Dragon argonat")) {
                        MQTTController.getInstance().sendRawMessage("B3/" + item.getMQTTTopic() + "In");
                        Handler dragonHandler = new Handler();
                        dragonHandler.post(new Thread() {
                            @Override
                            public void run() {
                                boolean isDragonDone = false;
                                MQTTController.getInstance().readRawMessage("B3/" + item.getMQTTTopic() + "Out");
                                while (!isDragonDone) {
                                    if (!MQTTController.getInstance().waitForMessage("B3/" + item.getMQTTTopic() + "Out").isEmpty()) {
                                        isDragonDone = true;
                                    }
                                }
                                updateActionImage(actionText, imageView, item);
                            }
                        });
                    } else {
                        MQTTController.getInstance().sendRawMessage("B3/" + item.getMQTTTopic());
                        updateActionImage(actionText, imageView, item);
                    }
                    if (!item.canGainPoints()) {
                        DataSingleton.getInstance().getUser().addPoints(400);
                        DataSingleton.getInstance().getUser().addToTotal(400);

                        float progress = preferences.getFloat(PROGRESS + storyIndex, 0);
                        float progressPercent = (400.0f / subjectStory.getStoryMaxPoints()) * 100;
                        progress += progressPercent;

                        preferenceEditor.putFloat(PROGRESS + storyIndex, progress);
                        preferenceEditor.putBoolean(STORY_COMPLETE + storyIndex + "." + marker, true);
                        preferenceEditor.apply();
                        item.setGainPoints(true);
                    }
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("lastAction" + subjectStory.getStoryName(), LocalTime.now().toString());
                    editor.apply();
                } else {
                    Toast toast = Toast.makeText(getContext(), getString(R.string.toastWaitText) + lastActionTime.plusHours(1).getHour() + ":" + lastActionTime.plusHours(1).getMinute(), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        Button backButton = (Button) RootView.findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTravel.fragmentTravel(-1,marker,subjectStory,getFragmentManager(), storyIndex);
            }
        });

        Button nextButton = (Button) RootView.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTravel.fragmentTravel(1, marker, subjectStory, getFragmentManager(), storyIndex);
            }
        });

        RootView.setOnTouchListener(new OnSwipeTouchListener(container.getContext()) {
            @Override
            public void onSwipeRight() {
                FragmentTravel.fragmentTravel(-1, marker, subjectStory, getFragmentManager(), storyIndex);
            }

            @Override
            public void onSwipeLeft() {
                FragmentTravel.fragmentTravel(1, marker, subjectStory, getFragmentManager(), storyIndex);
            }
        });

        ScrollView scrollview = RootView.findViewById(R.id.actionScrollView);

        scrollview.setOnTouchListener(new OnSwipeTouchListener(container.getContext()) {
            @Override
            public void onSwipeRight() {
                FragmentTravel.fragmentTravel(-1, marker, subjectStory, getFragmentManager(), storyIndex);
            }

            @Override
            public void onSwipeLeft() {
                FragmentTravel.fragmentTravel(1, marker, subjectStory, getFragmentManager(), storyIndex);
            }
        });
        return RootView;
    }

    private void updateActionImage(TextView actionText, ImageView imageView, ActionItem item){
        //updating image and text after button press
        actionText.setText(item.getPostActionText());
        if (!item.getPostImage().isEmpty()) {
            imageView.getLayoutParams().height = 850;
            imageView.getLayoutParams().width = 850;
            int id = DataSingleton.getInstance().getMainContext().getResources().getIdentifier(item.getPostImage(), "drawable", DataSingleton.getInstance().getMainContext().getPackageName());
            imageView.setImageResource(id);
        }
        else {
            imageView.setVisibility(View.INVISIBLE);
            imageView.getLayoutParams().height = 400;
            imageView.getLayoutParams().width = 50;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        TextView title = RootView.findViewById(R.id.titleTextView);
        title.setText(subjectStory.getStoryName());
        TextView partOfStory = RootView.findViewById(R.id.pageTextView);
        String text = getString(R.string.partText) + " " + (marker + 1) + " " + getString(R.string.partText2) + " " + subjectStory.getPieces().size();
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
