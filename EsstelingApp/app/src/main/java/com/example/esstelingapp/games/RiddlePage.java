package com.example.esstelingapp.games;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.esstelingapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RiddlePage extends Fragment {
    private int timesTried;
    private RiddleController controller;
    private RadioButton correctAnswer;
    private RadioButton answerA;
    private RadioButton answerB;
    private RadioButton answerC;
    private RadioButton answerD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_riddle, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initializing all components
//        controller = new RiddleController(StoryTypes.BIGGETJES);

        TextView title = getView().findViewById(R.id.storyTitle);
        TextView partOfStory = getView().findViewById(R.id.partOfStory);
        TextView question = getView().findViewById(R.id.question);
        Button skipButton = getView().findViewById(R.id.skipButton);

        answerA = getView().findViewById(R.id.answerA);
        answerB = getView().findViewById(R.id.answerB);
        answerC = getView().findViewById(R.id.answerC);
        answerD = getView().findViewById(R.id.answerD);

        Button submitButton = getView().findViewById(R.id.submitButton);

//        showNewQuestion();
    }

    public void showNewQuestion() {
        timesTried++;

        Question question = controller.getNewQuestion();
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

    public void  submitAnswer(View v) {
        RiddleSubmitPopup popup = new RiddleSubmitPopup();
        if (answerA.isChecked() && answerA == correctAnswer) {
            System.out.println("Correct");
            popup.popupType(true, timesTried);
        }
        else if (answerB.isChecked() && answerB == correctAnswer) {
            System.out.println("Correct");
            popup.popupType(true, timesTried);
        }
        else if (answerC.isChecked() && answerC == correctAnswer) {
            System.out.println("Correct");
            popup.popupType(true, timesTried);
        }
        else if (answerD.isChecked() && answerD == correctAnswer) {
            System.out.println("Correct");
            popup.popupType(true, timesTried);
        }
        else {
            System.out.println("Incorrect");
            popup.popupType(false, timesTried);
        }
        popup.show(getFragmentManager(), "Submitted");
    }

    public void skipQuestion(View v) {
        //skip
    }
}
