package com.example.esstelingapp;

public class GameItem implements StoryPiecesInterface {

    private int ID = 2;
    private String tieInText;
    private int points;
    private boolean pointsReceived;

    public GameItem(String tieInText, int points, boolean pointsReceived) {
        this.tieInText = tieInText;
        this.points = points;
        this.pointsReceived = pointsReceived;
    }

    public String getTieInText() {
        return tieInText;
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public boolean canGainPoints() {
        return pointsReceived;
    }
}
