package com.codecool.player;

public class Player {
    public int id;
    private String name;
    private int points;

    public Player(int id, int points, String name) {
        this.id = id;
        this.name = name;
        this.points = points;
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
