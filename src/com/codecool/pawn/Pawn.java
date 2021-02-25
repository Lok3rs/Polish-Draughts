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

    public boolean validateMove(Pawn[][] gameBoard, int targetX, int targetY, String direction){
        return isFieldOccupied(gameBoard, targetX, targetY) ?
                isFieldOccupiedByEnemy(gameBoard, targetX, targetY) && isShootPossible(gameBoard, targetX, targetY, direction) :
                isMoveInBounds(gameBoard, targetX, targetY);
    }

    private boolean isMoveInBounds(Pawn[][] gameBoard, int targetX, int targetY){
        return gameBoard.length > targetY && gameBoard[targetY].length > targetX;
    }

    private boolean isFieldOccupied(Pawn[][] gameBoard, int targetX, int targetY){
        return gameBoard[targetY][targetX] != null;
    }

    private boolean isFieldOccupiedByEnemy(Pawn[][] gameBoard, int targetX, int targetY){
        return gameBoard[targetY][targetX].getIsWhite();
    }

    private boolean isShootPossible(Pawn[][] gameBoard, int targetX, int targetY, String direction){

        int[] afterShootCoordsChange;
        byte y = 0;
        byte x = 1;

        switch (direction) {
            case "NE" -> afterShootCoordsChange = new int[]{1, 1};
            case "NW" -> afterShootCoordsChange = new int[]{1, -1};
            case "SE" -> afterShootCoordsChange = new int[]{-1, 1};
            case "SW" -> afterShootCoordsChange = new int[]{-1, -1};
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }

        boolean enemyOnTargetField = isFieldOccupiedByEnemy(gameBoard, targetX, targetY);
        boolean emptyFieldAfterShoot = !isFieldOccupied(gameBoard, targetX + afterShootCoordsChange[x], targetY + afterShootCoordsChange[y]);
        boolean fieldAfterShootInBounds = isMoveInBounds(gameBoard, targetX + afterShootCoordsChange[x], targetY + afterShootCoordsChange[y]);

        return enemyOnTargetField && emptyFieldAfterShoot && fieldAfterShootInBounds;
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
