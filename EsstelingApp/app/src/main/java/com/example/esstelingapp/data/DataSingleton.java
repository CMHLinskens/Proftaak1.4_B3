package com.example.esstelingapp.data;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.example.esstelingapp.Story;

import java.util.ArrayList;
import java.util.HashMap;

public final class DataSingleton {

    private static DataSingleton INSTANCE;
    private String info = "Initial info class";
    private FragmentManager storyFragmentManager;
    private Context mainContext;
    private HashMap<String, HashMap<Integer, HashMap<String, ArrayList<String>>>> quizQuestions;
    private ArrayList<String> randomFacts;
    private ArrayList<Story> stories;


    private DataSingleton() { this.stories = new ArrayList<>(); }

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
    public FragmentManager getStoryFragmentManager() { return this.storyFragmentManager; }
    public void setStoryFragmentManager(FragmentManager storyFragmentManager) {this.storyFragmentManager = storyFragmentManager; }

    public ArrayList<Story> getStories() {
        return stories;
    }

    public void addStory (Story story) {
        this.stories.add(story);
    }
}
