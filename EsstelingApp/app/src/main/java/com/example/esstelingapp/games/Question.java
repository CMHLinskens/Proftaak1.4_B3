package com.example.esstelingapp.games;

public class Question {
    private StoryTypes storyType;
    private String question;
    private String[] answers;

    //The first answers in the array is the correct one
    public Question(StoryTypes storyType, String question, String[] answers) {
        this.storyType = storyType;
        this.question = question;
        this.answers = answers;
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
