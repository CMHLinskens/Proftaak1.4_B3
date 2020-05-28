package com.example.esstelingapp;

import java.util.ArrayList;

public class Achievement {

    private String AchievementName;
    private int AchievementProgress;
    private ArrayList<String> conditions;
    private int AchievementImageURL;
    private boolean AchievementStatus;

    public Achievement(String achievementName, boolean achievementStatus, int achievementProgress) {
        AchievementName = achievementName;
//        AchievementImageURL = achievementImageURL;
        AchievementStatus = achievementStatus;
        AchievementProgress = achievementProgress;
    }

    public String getAchievementName() {
        return AchievementName;
    }

    public void setAchievementName(String achievementName) {
        AchievementName = achievementName;
    }

    public int getAchievementImageURL() {
        return AchievementImageURL;
    }

    public void setAchievementImageURL(int achievementImageURL) {
        AchievementImageURL = achievementImageURL;
    }

    public int getAchievementProgress() {
        return AchievementProgress;
    }

    public boolean getAchievementStatus(){
        return AchievementStatus;
    }
}


