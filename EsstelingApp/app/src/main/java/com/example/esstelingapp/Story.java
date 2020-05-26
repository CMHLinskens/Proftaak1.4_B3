package com.example.esstelingapp;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.ResourceBundle;

    public class Story {
        private String StoryName;
        private int StoryProgress;
        private ArrayList<String> pieces;
        private int StoryImageURL;
        private boolean StoryStatus;

        public Story(String storyName, int storyImageURL, boolean storyStatus, int storyProgress) {
            StoryName = storyName;
            StoryImageURL = storyImageURL;
            StoryStatus = storyStatus;
            StoryProgress = storyProgress;
        }

        public String getStoryName() {
            return StoryName;
        }

        public void setStoryName(String storyName) {
            StoryName = storyName;
        }

        public int getStoryImageURL() {
            return StoryImageURL;
        }

        public void setStoryImageURL(int storyImageURL) {
            StoryImageURL = storyImageURL;
        }

        public int getStoryProgress() {
            return StoryProgress;
        }

        public boolean getStoryStatus(){
            return StoryStatus;
        }
    }


