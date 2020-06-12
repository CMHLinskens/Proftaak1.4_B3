package com.example.esstelingapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.esstelingapp.games.StoryTypes;

import java.util.ArrayList;

import javax.security.auth.Subject;

public class Story implements Parcelable {
        private String StoryName;
        private int StoryProgress;
        private ArrayList<StoryPiecesInterface> StoryPieces;
        private int StoryImageURL;
        private boolean StoryStatus;
        private int StoryCompleted;
        private int StoryMaxPoints;
        private int StoryCompletionReward;
        private StoryTypes storyType;

        public Story(String storyName, int storyImageURL, boolean storyStatus, ArrayList<StoryPiecesInterface> pieces, int storyProgress, int pointsOfStory, int storyMaxPoints, int storyCompletionReward, StoryTypes storyType) {
            this.StoryName = storyName;
            this.StoryImageURL = storyImageURL;
            this.StoryStatus = storyStatus;
            this.StoryProgress = storyProgress;
            this.StoryPieces = pieces;
            this.StoryMaxPoints = storyMaxPoints;
            this.StoryCompleted = pointsOfStory;
            this.StoryCompletionReward = storyCompletionReward;
            this.storyType = storyType;
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

        public int getStoryProgress() {
            return StoryProgress;
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

        public boolean getStoryStatus(){
            return StoryStatus;
        }

        public void addPointsToStory(int points){
            if (StoryProgress!=StoryMaxPoints){
                StoryProgress+=points;
            }
            if (StoryProgress==StoryCompleted){
                addPointsToStory(StoryCompletionReward);
            }
        }

    public void setStoryStatus(boolean storyStatus) {
        StoryStatus = storyStatus;
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


