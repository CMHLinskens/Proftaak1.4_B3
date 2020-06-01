package com.example.esstelingapp;

import java.util.ArrayList;

public class Story {
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
            storyImageResource = storyImageURL;
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
            return storyImageResource;
        }

        public void setStoryImageResource(int storyImageResource) {
            this.storyImageResource = storyImageResource;
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

    }


