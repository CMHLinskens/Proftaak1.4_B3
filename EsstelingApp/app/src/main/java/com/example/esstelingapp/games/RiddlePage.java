package com.example.esstelingapp.games;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
import com.example.esstelingapp.ReadingItem;
import com.example.esstelingapp.Story;
import com.example.esstelingapp.StoryPiecesInterface;
import com.example.esstelingapp.ui.OnSwipeTouchListener;
import com.example.esstelingapp.ui.StoryPage;
import com.example.esstelingapp.ui.Activity_read_story;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RiddlePage extends Fragment {
    private Story subjectStory;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        storyType = StoryTypes.BIGGETJES;
        return inflater.inflate(R.layout.activity_riddle, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        GameItem item = (GameItem) storyArrayList.get(marker);

        TextView title = getView().findViewById(R.id.storyTitle);
        title.setText(subjectStory.getStoryName());
        TextView partOfStory = getView().findViewById(R.id.partOfStory);
        String text = "part " + (marker + 1) + " of " + subjectStory.getPieces().size();
        partOfStory.setText(text);
        TextView tieInText = getView().findViewById(R.id.tieInText);
        tieInText.setText(item.getTieInText());

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


        showNewQuestion();

        view.setOnTouchListener(new OnSwipeTouchListener(view.getContext()){

            @Override
            public void onSwipeRight() {
     FragmentTravel.fragmentTravel(-1,marker,subjectStory,getFragmentManager());
            }

            @Override
            public void onSwipeLeft() {
                FragmentTravel.fragmentTravel(1,marker,subjectStory,getFragmentManager());
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

        if (!answerA.isChecked() && !answerB.isChecked() && !answerC.isChecked() && !answerD.isChecked()) {
            return;
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        if (    answerA.isChecked() && answerA == correctAnswer ||
                answerB.isChecked() && answerB == correctAnswer ||
                answerC.isChecked() && answerC == correctAnswer ||
                answerD.isChecked() && answerD == correctAnswer) {

            System.out.println("Correct");
            alert.setMessage("Correct");
            alert.setCancelable(false);
            alert.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FragmentTravel.fragmentTravel(1,marker,subjectStory,getFragmentManager());
                }
            }).show();

        }
        else if (timesTried < 3) {
            System.out.println("Incorrect");
            alert.setMessage("Incorrect");
            alert.setCancelable(false);
            alert.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showNewQuestion();
                }
            }).show();
        }
        else {
            System.out.println("Incorrect");
            alert.setMessage("Incorrect \nBetter luck next time");
            alert.setCancelable(false);
            alert.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FragmentTravel.fragmentTravel(1,marker,subjectStory,getFragmentManager());
                }
            }).show();
        }
    }


    public void skipQuestion(View v) {
        FragmentTravel.fragmentTravel(1,marker,subjectStory,getFragmentManager());
    }
}
