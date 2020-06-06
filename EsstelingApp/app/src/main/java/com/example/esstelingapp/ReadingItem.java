package com.example.esstelingapp;

import com.example.esstelingapp.StoryPiecesInterface;

public class ReadingItem implements StoryPiecesInterface {

    private String StoryPartOne;
    private String StoryPartThree;
    private String StoryPartFive;
    private String StoryPartTwo;
    private String StoryPartFour;
    private int Points;
    private boolean PointsReceived;

    public ReadingItem(String storyPartOne, String storyPartThree, String storyPartFive, String storyPartTwo, String storyPartFour, int points, boolean pointsReceived) {
        StoryPartOne = storyPartOne;
        StoryPartThree = storyPartThree;
        StoryPartFive = storyPartFive;
        StoryPartTwo = storyPartTwo;
        StoryPartFour = storyPartFour;
        Points = points;
        PointsReceived = pointsReceived;
    }



    public String getStoryPartOne() {
        return StoryPartOne;
    }

    public String getStoryPartThree() {
        return StoryPartThree;
    }

    public String getStoryPartFive() {
        return StoryPartFive;
    }

    public String getStoryPartTwo() {
        return StoryPartTwo;
    }

    public String getStoryPartFour() {
        return StoryPartFour;
    }

    @Override
    public int getPoints() {
        return Points;
    }

    @Override
    public boolean canGainPoints() {
        return PointsReceived;
    }
}
