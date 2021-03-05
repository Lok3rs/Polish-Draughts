package com.codecool.pawn;

public class Pawn {

    int positionX;
    int positionY;
    boolean isWhite;

    public Pawn(int positionX, int positionY, boolean isWhite){
        this.positionX = positionX;
        this.positionY = positionY;
        this.isWhite = isWhite;
    }

    public void setPositionX(int positionX){
        this.positionX = positionX;
    }

    public void setPositionY(int positionY){
        this.positionY = positionY;
    }

    public boolean getIsWhite(){
        return isWhite;
    }

    public int getPositionX(){
        return positionX;
    }
    public int getPositionY(){
        return positionY;
    }

}
