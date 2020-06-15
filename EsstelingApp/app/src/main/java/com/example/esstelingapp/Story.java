package com.example.esstelingapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.esstelingapp.games.StoryTypes;

import java.util.ArrayList;

import javax.security.auth.Subject;

public class Story implements Parcelable {
    private String StoryName;
    private ArrayList<StoryPiecesInterface> StoryPieces;
    private int StoryImageURL;
    private boolean isUnlocked;
    private int StoryCompleted;
    private int StoryMaxPoints;
    private int StoryCompletionReward;
        private StoryTypes storyType;

    public Story(String storyName, int storyImageURL, boolean isUnlocked, ArrayList<StoryPiecesInterface> pieces, int pointsOfStory, int storyMaxPoints, int storyCompletionReward) {
        StoryName = storyName;
        StoryImageURL = storyImageURL;
        this.isUnlocked = isUnlocked;
        StoryPieces = pieces;
        StoryMaxPoints = storyMaxPoints;
        StoryCompleted = pointsOfStory;
        StoryCompletionReward = storyCompletionReward;
    }

    public String getStoryName() {
        return StoryName;
    }

    public void setStoryName(String storyName) {
        StoryName = storyName;
    }

    public int getStoryImageResource() {
        return StoryImageURL;
    }

    public void setStoryImageResource(int StoryImageURL) {
        this.StoryImageURL = StoryImageURL;
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

    public StoryTypes getStoryType() {
        return storyType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}


