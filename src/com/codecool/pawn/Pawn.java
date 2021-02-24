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

    public static void validateMove(char[][] gameBoard, int targetX, int targetY){

    }

    private boolean isMoveInBounds(char[][] gameBoard, int targetX, int targetY){
        return gameBoard.length > targetY && gameBoard[targetY].length > targetX;
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
