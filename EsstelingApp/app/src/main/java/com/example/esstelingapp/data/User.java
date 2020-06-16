package com.example.esstelingapp.data;

public class User {
    private int points;
    private int totalPoints;

    public User(int points) {
        this.points = points;
        this.totalPoints = 0;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int points){
        this.points += points;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public void addToTotal(int points){
        this.totalPoints += points;
    }
}
