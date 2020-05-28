package com.example.esstelingapp.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

public final class DataSingleton {

    private static DataSingleton INSTANCE;
    private String info = "Initial info class";
    private Context mainContext;
    private HashMap<String, HashMap<Integer, HashMap<String, ArrayList<String>>>> quizQuestions;
    private ArrayList<String> randomFacts;

    private DataSingleton() { }

    public static DataSingleton getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new DataSingleton();
        }

        return INSTANCE;
    }

    // getters and setters
    public Context getMainContext() { return mainContext; }
    public void setMainContext(Context mainContext) { this.mainContext = mainContext; }
    public HashMap<String, HashMap<Integer, HashMap<String, ArrayList<String>>>> getQuizQuestions() { return this.quizQuestions; }
    public void setQuizQuestions(HashMap<String, HashMap<Integer, HashMap<String, ArrayList<String>>>> quizQuestions) { this.quizQuestions = quizQuestions; }
    public ArrayList<String> getRandomFacts() { return randomFacts; }
    public void setRandomFacts(ArrayList<String> randomFacts) { this.randomFacts = randomFacts; }
}