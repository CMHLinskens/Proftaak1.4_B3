package com.example.esstelingapp;

public class Achievement {

    private String AchievementName;
    private float juniorMax;
    private float masterMax;
    private boolean AchievementStatus;

    public Achievement(String achievementName, boolean achievementStatus, float juniorMax, float masterMax) {
        AchievementName = achievementName;
        AchievementStatus = achievementStatus;
        this.juniorMax = juniorMax;
        this.masterMax = masterMax;
    }

    public String getAchievementName() {
        return AchievementName;
    }

    public void setAchievementStatus(boolean status) { this.AchievementStatus = status; }

    public float getJuniorMax() {
        return juniorMax;
    }

    public float getMasterMax() {
        return masterMax;
    }
}


