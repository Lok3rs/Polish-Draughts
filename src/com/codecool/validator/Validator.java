package com.codecool.validator;

import com.codecool.pawn.Pawn;

public class Validator {
    public boolean validatePawnSelection(Pawn[][] gameBoard, int rowIndex, int columnIndex, boolean playerIsWhite) {
        Pawn selectedField = gameBoard[rowIndex][columnIndex];
        return selectedField != null && selectedField.getIsWhite() == playerIsWhite;
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

    public boolean isShootPossible(Pawn[][] gameBoard, int targetX, int targetY, String direction, boolean isWhite){

        int[] afterShootCoordsChange;
        byte y = 0;
        byte x = 1;

        switch (direction) {
            case "left" -> afterShootCoordsChange = isWhite ? new int[]{-1, -1} : new int[]{1, -1};
            case "back-left" -> afterShootCoordsChange = isWhite ? new int[]{1, -1} : new int[]{-1, -1};
            case "right" -> afterShootCoordsChange = isWhite ? new int[]{-1, 1} : new int[]{1, 1};
            case "back-right" -> afterShootCoordsChange = isWhite ? new int[]{1, 1} : new int[]{-1, 1};
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }

        boolean enemyOnTargetField = isFieldOccupied(gameBoard, targetX, targetY) && isFieldOccupiedByEnemy(gameBoard, targetX, targetY, isWhite);
        boolean emptyFieldAfterShoot = isMoveInBounds(gameBoard, targetX + afterShootCoordsChange[x], targetY + afterShootCoordsChange[y])
                && !isFieldOccupied(gameBoard, targetX + afterShootCoordsChange[x], targetY + afterShootCoordsChange[y]);
        boolean fieldAfterShootInBounds = isMoveInBounds(gameBoard, targetX + afterShootCoordsChange[x], targetY + afterShootCoordsChange[y]);

        return enemyOnTargetField && emptyFieldAfterShoot && fieldAfterShootInBounds;
    }

    public boolean checkIfShootRequired(Pawn[][] gameBoard, Pawn selectedPawn){

        int selectedPawnY = selectedPawn.getPositionY();
        int selectedPawnX = selectedPawn.getPositionX();
        boolean pawnIsWhite = selectedPawn.getIsWhite();

        // Left top corner
        if (selectedPawnY == 0 && selectedPawnX == 0) {
            return checkForEnemyBottomRight(gameBoard, selectedPawnY, selectedPawnX, pawnIsWhite);
        }
        // right top corner
        else if (selectedPawnY == 0 && selectedPawnX == gameBoard.length - 1) {
            return checkForEnemyBottomLeft(gameBoard, selectedPawnY, selectedPawnX, pawnIsWhite);
        }
        // right bottom corner
        else if (selectedPawnY == gameBoard.length - 1 && selectedPawnX == gameBoard.length - 1){
            return checkForEnemyTopLeft(gameBoard, selectedPawnY, selectedPawnX, pawnIsWhite);
        }
        // left bottom corner
        else if (selectedPawnY == gameBoard.length - 1 && selectedPawnX == 0) {
            return checkForEnemyTopRight(gameBoard, selectedPawnY, selectedPawnX, pawnIsWhite);
        }
        // just top line
        else if (selectedPawnY == 0) {
            return checkForEnemyBottomRight(gameBoard, selectedPawnY, selectedPawnX, pawnIsWhite)
                    ||
                    checkForEnemyBottomLeft(gameBoard, selectedPawnY, selectedPawnX, pawnIsWhite);
        }
        // just bottom line
        else if (selectedPawnY == gameBoard.length - 1){
            return checkForEnemyTopRight(gameBoard, selectedPawnY, selectedPawnX, pawnIsWhite)
                    ||
                    checkForEnemyTopLeft(gameBoard, selectedPawnY, selectedPawnX, pawnIsWhite);
        }
        // left side
        else if (selectedPawnX == 0){
            return checkForEnemyTopRight(gameBoard, selectedPawnY, selectedPawnX, pawnIsWhite)
                    ||
                    checkForEnemyBottomRight(gameBoard, selectedPawnY, selectedPawnX, pawnIsWhite);
        }
        // right side
        else if (selectedPawnX == gameBoard.length - 1){
            return checkForEnemyTopLeft(gameBoard, selectedPawnY, selectedPawnX, pawnIsWhite)
                    ||
                    checkForEnemyBottomLeft(gameBoard, selectedPawnY, selectedPawnX, pawnIsWhite);
        }
        // all other cases
        else {
            return checkForEnemyTopLeft(gameBoard, selectedPawnY, selectedPawnX, pawnIsWhite)
                    ||
                    checkForEnemyTopRight(gameBoard, selectedPawnY, selectedPawnX, pawnIsWhite)
                    ||
                    checkForEnemyBottomLeft(gameBoard, selectedPawnY, selectedPawnX, pawnIsWhite)
                    ||
                    checkForEnemyBottomRight(gameBoard, selectedPawnY, selectedPawnX, pawnIsWhite);
        }
    }

    private boolean checkForEnemyBottomRight(Pawn[][] gameBoard, int selectedPawnY, int selectedPawnX, boolean pawnIsWhite){
        return gameBoard[selectedPawnY + 1][selectedPawnX + 1] != null
                && gameBoard[selectedPawnY + 1][selectedPawnX + 1].getIsWhite() != pawnIsWhite;
    }
    private boolean checkForEnemyBottomLeft(Pawn[][] gameBoard, int selectedPawnY, int selectedPawnX, boolean pawnIsWhite){
        return gameBoard[selectedPawnY + 1][selectedPawnX - 1] != null
                && gameBoard[selectedPawnY + 1][selectedPawnX - 1].getIsWhite() != pawnIsWhite;
    }
    private boolean checkForEnemyTopRight(Pawn[][] gameBoard, int selectedPawnY, int selectedPawnX, boolean pawnIsWhite){
        return gameBoard[selectedPawnY - 1][selectedPawnX + 1] != null
                && gameBoard[selectedPawnY - 1][selectedPawnX + 1].getIsWhite() != pawnIsWhite;
    }
    private boolean checkForEnemyTopLeft(Pawn[][] gameBoard, int selectedPawnY, int selectedPawnX, boolean pawnIsWhite){
        return gameBoard[selectedPawnY - 1][selectedPawnX - 1] != null
                && gameBoard[selectedPawnY - 1][selectedPawnX - 1].getIsWhite() != pawnIsWhite;
    }

}
