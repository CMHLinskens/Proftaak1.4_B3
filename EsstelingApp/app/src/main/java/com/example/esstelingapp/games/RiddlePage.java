package com.example.esstelingapp.games;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.esstelingapp.FragmentTravel;
import com.example.esstelingapp.GameItem;
import com.example.esstelingapp.R;
import com.example.esstelingapp.Story;
import com.example.esstelingapp.StoryPiecesInterface;
import com.example.esstelingapp.data.DataSingleton;
import com.example.esstelingapp.ui.OnSwipeTouchListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RiddlePage extends Fragment {
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_COLOUR_BLIND_THEME = "colour_blind_theme";

    private GameItem gameItem;
    private Story subjectStory;
    private int storyIndex;
    private int marker;
    private int timesTried;
    private RiddleController controller;
    private TextView question;
    private RadioButton correctAnswer;
    private RadioButton answerA;
    private RadioButton answerB;
    private RadioButton answerC;
    private RadioButton answerD;
    private StoryTypes storyType;

    private static final String USER_DATA = "userData";
    private static final String USER_POINTS = "points";
    private static final String USER_TOTAL_POINTS = "totalPoints";
    private static final String STORY_COMPLETE = "storyComplete";
    private static final String PROGRESS = "progress";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_riddle, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        Boolean isColorBlind = sharedPreferences.getBoolean(PREF_COLOUR_BLIND_THEME, false);
        if (isColorBlind){
            getView().setBackgroundResource(R.drawable.old_paper_cb);
        }else {
            getView().setBackgroundResource(R.drawable.old_paper);
        }

        this.storyType = subjectStory.getStoryType();
        ArrayList<StoryPiecesInterface> storyArrayList = subjectStory.getPieces();
        this.gameItem = (GameItem) storyArrayList.get(marker);

        TextView title = getView().findViewById(R.id.storyTitle);
        title.setText(subjectStory.getStoryName());
        TextView partOfStory = getView().findViewById(R.id.partOfStory);
        String text = "part " + (marker + 1) + " of " + subjectStory.getPieces().size();
        partOfStory.setText(text);
        TextView tieInText = getView().findViewById(R.id.tieInText);
        tieInText.setText(this.gameItem.getTieInText());

        //initializing all components
        controller = new RiddleController(storyType);

        question = getView().findViewById(R.id.question);
        final Button skipButton = getView().findViewById(R.id.skipButton);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipQuestion(v);
            }
        });
        answerA = getView().findViewById(R.id.answerA);
        answerB = getView().findViewById(R.id.answerB);
        answerC = getView().findViewById(R.id.answerC);
        answerD = getView().findViewById(R.id.answerD);

        final Button submitButton = getView().findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAnswer(v);
            }
        });

        Button backButton = getView().findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTravel.fragmentTravel(-1,marker,subjectStory,getFragmentManager());
            }
        });


        showNewQuestion();

        view.setOnTouchListener(new OnSwipeTouchListener(view.getContext()) {

            @Override
            public void onSwipeRight() {
                FragmentTravel.fragmentTravel(-1, marker, subjectStory, getFragmentManager(), storyIndex);
            }

            @Override
            public void onSwipeLeft() {
                FragmentTravel.fragmentTravel(1, marker, subjectStory, getFragmentManager(), storyIndex);
            }
        });
    }

    public void showNewQuestion() {
        timesTried++;

        Question question = controller.getNewQuestion();
        answerA.setChecked(false);
        answerB.setChecked(false);
        answerC.setChecked(false);
        answerD.setChecked(false);

        this.question.setText(question.getQuestion());

        List<Integer> randomOrder = new ArrayList<>();
        randomOrder.add(0);
        randomOrder.add(1);
        randomOrder.add(2);
        randomOrder.add(3);
        Collections.shuffle(randomOrder);

        answerA.setText(question.getAnswers()[randomOrder.get(0)]);
        answerB.setText(question.getAnswers()[randomOrder.get(1)]);
        answerC.setText(question.getAnswers()[randomOrder.get(2)]);
        answerD.setText(question.getAnswers()[randomOrder.get(3)]);

        switch (randomOrder.indexOf(0)) {
            case 0:
                correctAnswer = answerA;
                break;
            case 1:
                correctAnswer = answerB;
                break;
            case 2:
                correctAnswer = answerC;
                break;
            case 3:
                correctAnswer = answerD;
                break;
        }
    }

    public void submitAnswer(View v) {
        SharedPreferences preferences = DataSingleton.getInstance().getMainContext().getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = preferences.edit();
        if (!answerA.isChecked() && !answerB.isChecked() && !answerC.isChecked() && !answerD.isChecked()) {
            return;
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        if (answerA.isChecked() && answerA == correctAnswer ||
                answerB.isChecked() && answerB == correctAnswer ||
                answerC.isChecked() && answerC == correctAnswer ||
                answerD.isChecked() && answerD == correctAnswer) {

            System.out.println("Correct");
            alert.setMessage("Correct");
            if(!this.gameItem.canGainPoints()){
                DataSingleton.getInstance().getUser().addPoints(400);
                DataSingleton.getInstance().getUser().addToTotal(400);

                float progress = preferences.getFloat(PROGRESS + storyIndex, 0);
                float progressPercent = (400.0f/ subjectStory.getStoryMaxPoints()) * 100;
                progress += progressPercent;

                Log.d("USER POINTS ", String.valueOf(DataSingleton.getInstance().getUser().getPoints()));
                prefEditor.putFloat(PROGRESS + storyIndex, progress);
                prefEditor.putBoolean(STORY_COMPLETE + storyIndex + "." + marker, true);
                prefEditor.apply();
                this.gameItem.setGainPoints(true);
                Log.d("GAMEPOINTS", String.valueOf(DataSingleton.getInstance().getUser().getPoints()));
            }
            alert.setCancelable(false);
            alert.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FragmentTravel.fragmentTravel(1, marker, subjectStory, getFragmentManager(), storyIndex);
                }
            }).show();

        } else if (timesTried < 3) {
            System.out.println("Incorrect");
            alert.setMessage("Incorrect");
            alert.setCancelable(false);
            alert.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showNewQuestion();
                }
            }).show();
        } else {
            System.out.println("Incorrect");
            alert.setMessage("Incorrect \nBetter luck next time");
            alert.setCancelable(false);
            Log.d("GAMEPOINTS", String.valueOf(DataSingleton.getInstance().getUser().getPoints()));
            alert.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FragmentTravel.fragmentTravel(1, marker, subjectStory, getFragmentManager(), storyIndex);
                }
            }).show();
        }
    }


    public void skipQuestion(View v) {
        FragmentTravel.fragmentTravel(1, marker, subjectStory, getFragmentManager(), storyIndex);
    }

    public int getMarker() {
        return marker;
    }
    public Story getSubjectStory() {
        return subjectStory;
    }
}
