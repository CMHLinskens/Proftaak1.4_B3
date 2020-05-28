package com.example.esstelingapp.games;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.esstelingapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RiddlePage extends AppCompatActivity {
    private RiddleController controller;
    private RadioButton correctAnswer;
    private RadioButton answerA;
    private RadioButton answerB;
    private RadioButton answerC;
    private RadioButton answerD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riddle);

        //initializing all components
//        controller = new RiddleController(StoryTypes.BIGGETJES);

        TextView title = findViewById(R.id.storyTitle);
        TextView question = findViewById(R.id.question);
        Button skipButton = findViewById(R.id.skipButton);

        answerA = findViewById(R.id.answerA);
        answerB = findViewById(R.id.answerB);
        answerC = findViewById(R.id.answerC);
        answerD = findViewById(R.id.answerD);

        Button submitButton = findViewById(R.id.submitButton);

//        showNewQuestion();
    }

    public void showNewQuestion() {
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
        if (answerA.isChecked() && answerA == correctAnswer) {
            System.out.println("Correct");
            //win
        }
        else if (answerB.isChecked() && answerB == correctAnswer) {
            System.out.println("Correct");
            //win
        }
        else if (answerC.isChecked() && answerC == correctAnswer) {
            System.out.println("Correct");
            //win
        }
        else if (answerD.isChecked() && answerC == correctAnswer) {
            System.out.println("Correct");
            //win
        }
        else {
            System.out.println("Incorrect");
            //lose
        }
    }

    public void skipQuestion(View v) {
        //skip
    }
}
