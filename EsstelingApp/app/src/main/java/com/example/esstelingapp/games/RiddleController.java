package com.example.esstelingapp.games;

import java.util.ArrayList;
import java.util.List;

public class RiddleController {
    private StoryTypes storyType;
    private List<Question> questions;


    public RiddleController(StoryTypes storyType) {
        this.storyType = storyType;
        getData();

    }

    private void getData() {
        questions = new ArrayList<>();

        //Put all the questions of that story in the List
    }

    public Question getNewQuestion() {
        int random = (int)(Math.random() * questions.size());
        Question question = questions.get(random);
        questions.remove(random);
        return question;
    }
}


