package com.example.esstelingapp.data;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.example.esstelingapp.Achievement;
import com.example.esstelingapp.Story;
import com.example.esstelingapp.games.Question;

import java.util.ArrayList;
import java.util.HashMap;

public final class DataSingleton {

    private static DataSingleton INSTANCE;
    private boolean mainLoaded;
    private String info = "Initial info class";
    private FragmentManager storyFragmentManager;
    private Context mainContext;
    private HashMap<String, HashMap<Integer, Question>> quizQuestions;
    private ArrayList<String> randomFacts;
    private ArrayList<Achievement> achievements;
    private ArrayList<Story> stories;
    private ThemeState state;

    private DataSingleton() {
        this.mainLoaded = false;
        this.stories = new ArrayList<>();
        this.state = ThemeState.NORMALISE;
    }


    public static DataSingleton getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new DataSingleton();
        }

        return INSTANCE;
    }
    // getters and setters
    public Context getMainContext() { return mainContext; }
    public void setMainContext(Context mainContext) { this.mainContext = mainContext; }
    public HashMap<String, HashMap<Integer, Question>> getQuizQuestions() { return this.quizQuestions; }
    public void setQuizQuestions(HashMap<String, HashMap<Integer, Question>> quizQuestions) { this.quizQuestions = quizQuestions; }
    public ArrayList<String> getRandomFacts() { return randomFacts; }
    public void setRandomFacts(ArrayList<String> randomFacts) { this.randomFacts = randomFacts; }
    public FragmentManager getStoryFragmentManager() { return this.storyFragmentManager; }
    public void setStoryFragmentManager(FragmentManager storyFragmentManager) {this.storyFragmentManager = storyFragmentManager; }
    public ArrayList<Achievement> getAchievements() {
        return achievements;
    }
    public void setAchievements(ArrayList<Achievement> achievements) { this.achievements = achievements; }
    public ArrayList<Story> getStories() {
        return stories;
    }
    public void addStory (Story story) {
        this.stories.add(story);
    }
    public boolean isMainLoaded() {
        return mainLoaded;
    }
    public void setMainLoaded(boolean mainLoaded) {
        this.mainLoaded = mainLoaded;
    }

    public ThemeState getState() {
        return state;
    }

    public void setState(ThemeState state) {
        this.state = state;
    }
}
