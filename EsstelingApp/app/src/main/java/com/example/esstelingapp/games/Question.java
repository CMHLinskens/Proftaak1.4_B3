package com.example.esstelingapp.games;

public class Question {
    private String question;
    private String[] answers;

    //A is always the correct answer
    public Question(String question, String A, String B, String C, String D) {
        this.question = question;
        answers = new String[]{A, B, C, D};
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }
}
