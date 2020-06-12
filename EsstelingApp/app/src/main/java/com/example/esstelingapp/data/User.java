package com.example.esstelingapp.data;

import com.example.esstelingapp.ReadingItem;
import com.example.esstelingapp.Story;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private int points;
    private boolean achievementUnlocked;
    private HashMap<Story, HashMap<ReadingItem, String>> storiesCompleted;

    public User (){
        this.points = 0;
        this.achievementUnlocked = false;
        this.storiesCompleted = new HashMap<>();
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isAchievementUnlocked() {
        return achievementUnlocked;
    }

    public void setAchievementUnlocked(boolean achievementUnlocked) {
        this.achievementUnlocked = achievementUnlocked;
    }

    public HashMap<Story, HashMap<ReadingItem, String>> getStoriesCompleted() {
        return storiesCompleted;
    }

    public void setStoriesCompleted(HashMap<Story, HashMap<ReadingItem, String>> storiesCompleted) {
        this.storiesCompleted = storiesCompleted;
    }


}
