package com.example.esstelingapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.esstelingapp.data.DataSingleton;
import com.example.esstelingapp.mqtt.MQTTController;
import com.example.esstelingapp.ui.OnSwipeTouchListener;

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
                if (subjectStory.getStoryName().equals("Draak blaaskaak")||subjectStory.getStoryName().equals("Dragon argonat")){
                    MQTTController.getInstance().sendRawMessage("B3/"+item.getMQTTTopic()+"In");
                    Handler dragonHandler = new Handler();
                    dragonHandler.post(new Thread(){
                        @Override
                        public void run() {
                            boolean isDragonDone = false;
                            MQTTController.getInstance().readRawMessage("B3/"+item.getMQTTTopic()+"Out");
                            while (!isDragonDone){
                                if(!MQTTController.getInstance().waitForMessage("B3/"+item.getMQTTTopic()+"Out").isEmpty()) {
                                    isDragonDone = true;
                                }
                            }
                            updateActionImage(actionText, imageView, item);
                        }
                    });
                }
                else{
                    MQTTController.getInstance().sendRawMessage("B3/"+item.getMQTTTopic());
                    updateActionImage(actionText, imageView, item);
                }
            }
        });

        Button backButton = (Button) RootView.findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTravel.fragmentTravel(-1,marker,subjectStory,getFragmentManager());
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

        ScrollView scrollview = RootView.findViewById(R.id.actionScrollView);

        scrollview.setOnTouchListener(new OnSwipeTouchListener(container.getContext()) {

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
