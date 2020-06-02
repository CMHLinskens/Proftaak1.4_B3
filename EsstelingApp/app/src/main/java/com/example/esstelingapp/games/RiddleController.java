package com.example.esstelingapp.games;

import com.example.esstelingapp.data.DataSingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RiddleController {
    private StoryTypes storyType;
    private List<Question> questions;


    public RiddleController(StoryTypes storyType) {
        this.storyType = storyType;
        getData(storyType);

    }

    private void getData(StoryTypes storyType) {
        questions = new ArrayList<>();
        HashMap<String, HashMap<Integer, HashMap<String, ArrayList<String>>>> questionData = DataSingleton.getInstance().getQuizQuestions();

        final String nameStoryBiggetjes = storyType.toString();

//        System.out.println(questionData.get(nameStoryBiggetjes));

        for (int i = 0; i < 6; i++) { //amount of stories is still hardcoded
            Question question = questionData.get(nameStoryBiggetjes).get(i);
            questions.add(question);
        }
    }

    public Question getNewQuestion() {
        int random = (int)(Math.random() * questions.size());
        Question question = questions.get(random);
        questions.remove(random);
        return question;
    }
}


