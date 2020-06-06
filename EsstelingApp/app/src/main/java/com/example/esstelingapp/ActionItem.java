package com.example.esstelingapp;

public class ActionItem implements StoryPiecesInterface {
     private String PreActionText;
     private String PostActionText;
     private int points;
     private boolean gainPoints;

    public ActionItem(String preActionText, String postActionText, int points, boolean gainPoints) {
        PreActionText = preActionText;
        PostActionText = postActionText;
        this.points = points;
        this.gainPoints = gainPoints;
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public boolean canGainPoints() {
        return gainPoints;
    }
}
