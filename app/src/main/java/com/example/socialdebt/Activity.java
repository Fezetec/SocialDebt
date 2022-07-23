package com.example.socialdebt;


public class Activity {
    public int id;
    public String name;
    public int points;

    public Activity(){
        this.id  = -1;
        this.name = "";
        this.points = 0;
    }

    public Activity(int id, String name, int points) {
        this.id = id;
        this.name = name;
        this.points = points;

    }

    @Override
    public String toString() {
        return this.name;
    }

    public int getId() { return this.id; }

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
