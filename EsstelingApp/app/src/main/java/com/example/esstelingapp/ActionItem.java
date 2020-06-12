package com.example.esstelingapp;

public class ActionItem implements StoryPiecesInterface {
     private String PreActionText;
     private String PostActionText;
     private String MQTTTopic;
     private int points;
     private boolean gainPoints;

    public ActionItem(String preActionText, String postActionText, String MQTTTopic, int points, boolean gainPoints) {
        PreActionText = preActionText;
        PostActionText = postActionText;
        this.points = points;
        this.gainPoints = gainPoints;
        this.MQTTTopic = MQTTTopic;
    }

    public String getPostActionText() {
        return PostActionText;
    }

    public String getPreActionText() {
        return PreActionText;
    }

    public String getMQTTTopic() {
        return MQTTTopic;
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
