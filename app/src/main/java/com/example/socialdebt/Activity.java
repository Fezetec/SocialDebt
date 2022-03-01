package com.example.socialdebt;


public class Activity {

    public String name;
    public int points;

    public Activity(){
        this.name = "";
        this.points = 0;
    }

    public Activity(String name, int points) {
        this.name = name;
        this.points = points;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
