package com.example.esstelingapp;

import java.util.ArrayList;

public class Achievement {

    private String AchievementName;
    private float AchievementProgress;
    private float juniorMax;
    private float masterMax;
    private ArrayList<String> conditions;
    private int AchievementImageURL;
    private boolean AchievementStatus;

    public Achievement(String achievementName, boolean achievementStatus, float achievementProgress, float juniorMax, float masterMax) {
        AchievementName = achievementName;
//        AchievementImageURL = achievementImageURL;
        AchievementStatus = achievementStatus;
        AchievementProgress = achievementProgress;
        this.juniorMax = juniorMax;
        this.masterMax = masterMax;
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

    public float getAchievementProgress() {
        return AchievementProgress;
    }

    public boolean getAchievementStatus(){
        return AchievementStatus;
    }

    public void setAchievementStatus(boolean status) { this.AchievementStatus = status; }

    public float getJuniorMax() {
        return juniorMax;
    }

    public void setJuniorMax(float juniorMax) {
        this.juniorMax = juniorMax;
    }

    public float getMasterMax() {
        return masterMax;
    }

    public void setMasterMax(float masterMax) {
        this.masterMax = masterMax;
    }
}


