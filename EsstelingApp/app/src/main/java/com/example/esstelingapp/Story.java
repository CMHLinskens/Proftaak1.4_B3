package com.example.esstelingapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Story implements Parcelable {
        private String StoryName;
        private int StoryProgress;
        private ArrayList<StoryPiecesInterface> StoryPieces;
        private int StoryImageURL;
        private boolean StoryStatus;
        private int StoryCompleted;
        private int StoryMaxPoints;
        private int StoryCompletionReward;

        public Story(String storyName, int storyImageURL, boolean storyStatus, ArrayList<StoryPiecesInterface> pieces, int storyProgress, int pointsOfStory, int storyMaxPoints, int storyCompletionReward) {
            StoryName = storyName;
            StoryImageURL = storyImageURL;
            StoryStatus = storyStatus;
            StoryProgress = storyProgress;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}


