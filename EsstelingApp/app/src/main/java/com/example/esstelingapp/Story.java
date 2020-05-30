package com.example.esstelingapp;

import java.util.ArrayList;

public class Story {
        private String StoryName;
        private int StoryProgress;
        private ArrayList<String> pieces;
        private int storyImageResource;
        private boolean StoryStatus;

        public Story(String storyName, int storyImageURL, boolean storyStatus, int storyProgress) {
            StoryName = storyName;
            storyImageResource = storyImageURL;
            StoryStatus = storyStatus;
            StoryProgress = storyProgress;
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

        public boolean getStoryStatus(){
            return StoryStatus;
        }
    }


