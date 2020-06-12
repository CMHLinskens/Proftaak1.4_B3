package com.example.esstelingapp;

public class ActionItem implements StoryPiecesInterface {
     private String PreActionText;
     private String PostActionText;
     private String MQTTTopic;
     private String preImage;
    private String postImage;
     private int points;
     private boolean gainPoints;

    public ActionItem(String preActionText, String postActionText, String MQTTTopic, String preImage, String postImage, int points, boolean gainPoints) {
        this.PreActionText = preActionText;
        this.PostActionText = postActionText;
        this.points = points;
        this.gainPoints = gainPoints;
        this.MQTTTopic = MQTTTopic;
        this.preImage = preImage;
        this.postImage = postImage;
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

    public String getPreImage() {
        return preImage;
    }

    public String getPostImage() {
        return postImage;
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
