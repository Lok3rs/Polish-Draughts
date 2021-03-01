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

    public boolean validateMove(Pawn[][] gameBoard, int targetX, int targetY, String direction, boolean isWhite){
        return isMoveInBounds(gameBoard, targetX, targetY)
                && (isFieldOccupied(gameBoard, targetX, targetY) ?
                isFieldOccupiedByEnemy(gameBoard, targetX, targetY, isWhite) && isShootPossible(gameBoard, targetX, targetY, direction, isWhite) :
                isMoveInBounds(gameBoard, targetX, targetY));
    }

    private boolean isMoveInBounds(Pawn[][] gameBoard, int targetX, int targetY){
        return gameBoard.length > targetY && gameBoard[0].length > targetX && targetY >= 0 && targetX >= 0;
    }

    private boolean isFieldOccupied(Pawn[][] gameBoard, int targetX, int targetY){
        return gameBoard[targetY][targetX] != null;
    }

    private boolean isFieldOccupiedByEnemy(Pawn[][] gameBoard, int targetX, int targetY, boolean playerIsWhite){
        return gameBoard[targetY][targetX].getIsWhite() != playerIsWhite;
    }

    private boolean isShootPossible(Pawn[][] gameBoard, int targetX, int targetY, String direction, boolean isWhite){

        int[] afterShootCoordsChange;
        byte y = 0;
        byte x = 1;

        switch (direction) {
            case "left" -> afterShootCoordsChange = isWhite ? new int[]{-1, -1} : new int[]{1, -1};
            case "right" -> afterShootCoordsChange = isWhite ? new int[]{-1, 1} : new int[]{1, 1};
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }

        boolean enemyOnTargetField = isFieldOccupiedByEnemy(gameBoard, targetX, targetY, isWhite);
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
