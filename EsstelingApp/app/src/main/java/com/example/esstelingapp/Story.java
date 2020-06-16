package com.example.esstelingapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.esstelingapp.games.StoryTypes;

import java.util.ArrayList;

public class Story implements Parcelable {
    private String StoryName;
    private ArrayList<StoryPiecesInterface> StoryPieces;
    private int StoryImageURL;
    private boolean isUnlocked;
    private int StoryMaxPoints;
    private int StoryCompletionReward;
    private StoryTypes storyType;
    private String mqttTopic;

    public Story(String storyName, int storyImageURL, boolean isUnlocked, ArrayList<StoryPiecesInterface> pieces, int storyMaxPoints, int storyCompletionReward, StoryTypes storyType, String mqttTopic) {
        StoryName = storyName;
        StoryImageURL = storyImageURL;
        this.isUnlocked = isUnlocked;
        StoryPieces = pieces;
        StoryMaxPoints = storyMaxPoints;
        StoryCompletionReward = storyCompletionReward;
        this.storyType = storyType;
        this.mqttTopic = mqttTopic;
    }

    public String getStoryName() {
        return StoryName;
    }

    public int getStoryImageResource() {
        return StoryImageURL;
    }

    public int getStoryMaxPoints() {
        return StoryMaxPoints;
    }

    public int getStoryCompletionReward() {
        return StoryCompletionReward;
    }

    public ArrayList<StoryPiecesInterface> getPieces() {
        return StoryPieces;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setUnlocked(boolean unlocked) {
        isUnlocked = unlocked;
    }

    public String getMqttTopic() { return mqttTopic; }

    public StoryTypes getStoryType() {
        return storyType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) { }
}


