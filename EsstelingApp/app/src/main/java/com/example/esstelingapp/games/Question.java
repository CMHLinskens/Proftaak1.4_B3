package com.example.esstelingapp.games;

import android.util.JsonReader;
import org.json.JSONObject;

public class Question {
    private StoryTypes storyType;
    private String question;
    private String[] answers;

    //A is always the correct answer
    public Question(StoryTypes storyType, String question, String A, String B, String C, String D) {
        this.storyType = storyType;
        this.question = question;
        answers = new String[]{A, B, C, D};
    }

    public StoryTypes getStoryType() {
        return storyType;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

}
