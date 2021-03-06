package com.codecool.validator;

import com.codecool.board.Board;
import com.codecool.game.Game;
import com.codecool.inputService.InputService;
import com.codecool.pawn.Pawn;
import com.codecool.player.Player;

import java.awt.image.PixelInterleavedSampleModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Validator {

    public boolean validatePawnSelection(Pawn[][] gameBoard, int rowIndex, int columnIndex, boolean playerIsWhite) {
        Pawn selectedField = gameBoard[rowIndex][columnIndex];
        return selectedField != null && selectedField.getIsWhite() == playerIsWhite;
    }

    public boolean validateMove(Pawn[][] gameBoard, int targetX, int targetY, String direction, boolean isWhite) {
        return isMoveInBounds(gameBoard, targetX, targetY)
                && (isFieldOccupied(gameBoard, targetX, targetY) ?
                isFieldOccupiedByEnemy(gameBoard, targetX, targetY, isWhite) && isShootPossible(gameBoard, targetX, targetY, direction, isWhite) :
                isMoveInBounds(gameBoard, targetX, targetY));
    }

    private boolean isMoveInBounds(Pawn[][] gameBoard, int targetX, int targetY) {
        return gameBoard.length > targetY && gameBoard[0].length > targetX && targetY >= 0 && targetX >= 0;
    }

    private boolean isFieldOccupied(Pawn[][] gameBoard, int targetX, int targetY) {
        return gameBoard[targetY][targetX] != null;
    }

    private boolean isFieldOccupiedByEnemy(Pawn[][] gameBoard, int targetX, int targetY, boolean playerIsWhite) {
        return gameBoard[targetY][targetX].getIsWhite() != playerIsWhite;
    }

    public boolean isShootPossible(Pawn[][] gameBoard, int targetX, int targetY, String direction, boolean isWhite) {

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


    public boolean isShootRequired(Pawn[][] gameBoard, Pawn selectedPawn){

        int selectedPawnY = selectedPawn.getPositionY();
        int selectedPawnX = selectedPawn.getPositionX();
        boolean pawnIsWhite = selectedPawn.getIsWhite();
        return checkForEnemyBottomRight(gameBoard, selectedPawnY, selectedPawnX, pawnIsWhite) ||
                checkForEnemyBottomLeft(gameBoard, selectedPawnY, selectedPawnX, pawnIsWhite) ||
                checkForEnemyTopRight(gameBoard, selectedPawnY, selectedPawnX, pawnIsWhite) ||
                checkForEnemyTopLeft(gameBoard, selectedPawnY, selectedPawnX, pawnIsWhite);
    }


    public List<String> checkShootOptions(Pawn[][] gameBoard, Pawn selectedPawn){
        int selectedPawnY = selectedPawn.getPositionY();
        int selectedPawnX = selectedPawn.getPositionX();
        boolean pawnIsWhite = selectedPawn.getIsWhite();

        String[] possibleOptions = {
                checkForEnemyBottomRight(gameBoard, selectedPawnY, selectedPawnX, pawnIsWhite) ?
                        (pawnIsWhite ? "back-right" : "right") : null,
                checkForEnemyBottomLeft(gameBoard, selectedPawnY, selectedPawnX, pawnIsWhite) ?
                        (pawnIsWhite ? "back-left" : "left") : null,
                checkForEnemyTopLeft(gameBoard, selectedPawnY, selectedPawnX, pawnIsWhite) ?
                        (pawnIsWhite ? "left" : "back-left") : null,
                checkForEnemyTopRight(gameBoard, selectedPawnY, selectedPawnX, pawnIsWhite) ?
                        (pawnIsWhite ? "right" : "back-right") : null
        };

        List<String> shootOptions = new ArrayList<>();

        for (String option : possibleOptions){
            if (option != null){
                shootOptions.add(option);
            }

        }
        return shootOptions;
    }


    private boolean checkForEnemyBottomRight(Pawn[][] gameBoard, int selectedPawnY, int selectedPawnX, boolean pawnIsWhite){
        return selectedPawnX != gameBoard.length - 1 &&
                selectedPawnY != gameBoard.length - 1 &&
                gameBoard[selectedPawnY + 1][selectedPawnX + 1] != null &&
                gameBoard[selectedPawnY + 1][selectedPawnX + 1].getIsWhite() != pawnIsWhite &&
                isShootPossible(gameBoard, selectedPawnX + 1, selectedPawnY + 1, (pawnIsWhite ? "back-right" : "right"), pawnIsWhite);
    }
    private boolean checkForEnemyBottomLeft(Pawn[][] gameBoard, int selectedPawnY, int selectedPawnX, boolean pawnIsWhite){
        return selectedPawnY != gameBoard.length - 1 &&
                selectedPawnX != 0 &&
                gameBoard[selectedPawnY + 1][selectedPawnX - 1] != null &&
                gameBoard[selectedPawnY + 1][selectedPawnX - 1].getIsWhite() != pawnIsWhite &&
                isShootPossible(gameBoard, selectedPawnX - 1, selectedPawnY + 1, (pawnIsWhite ? "back-left" : "left"), pawnIsWhite);
    }
    private boolean checkForEnemyTopRight(Pawn[][] gameBoard, int selectedPawnY, int selectedPawnX, boolean pawnIsWhite){
        return selectedPawnY != 0 &&
                selectedPawnX != gameBoard.length -1 &&
                gameBoard[selectedPawnY - 1][selectedPawnX + 1] != null &&
                gameBoard[selectedPawnY - 1][selectedPawnX + 1].getIsWhite() != pawnIsWhite &&
                isShootPossible(gameBoard, selectedPawnX + 1, selectedPawnY - 1, (pawnIsWhite ? "right" : "back-right"), pawnIsWhite);
    }
    private boolean checkForEnemyTopLeft(Pawn[][] gameBoard, int selectedPawnY, int selectedPawnX, boolean pawnIsWhite){
        return selectedPawnY != 0 &&
                selectedPawnX != 0 &&
                gameBoard[selectedPawnY - 1][selectedPawnX - 1] != null &&
                gameBoard[selectedPawnY - 1][selectedPawnX - 1].getIsWhite() != pawnIsWhite &&
                isShootPossible(gameBoard, selectedPawnX - 1, selectedPawnY - 1, (pawnIsWhite ? "left" : "back-left"), pawnIsWhite);
    }


    public int countWhites(Pawn[][] gameBoard) {
        int counterWhites = 0;
        int whitesScore = gameBoard.length;
        for (int x = 0; x < gameBoard.length; x++) {
            for (int y = 0; y < gameBoard.length; y++) {
                if (gameBoard[x][y] != null && gameBoard[x][y].getIsWhite()) {
                    counterWhites++;
                }
            }
        }
        return counterWhites;
    }

    public int countBlacks(Pawn[][] gameBoard) {
        int counterBlacks = 0;

        for (int x = 0; x < gameBoard.length; x++) {
            for (int y = 0; y < gameBoard.length; y++) {
                if (gameBoard[x][y] != null && !gameBoard[x][y].getIsWhite()) {
                    counterBlacks++;
                }
            }
        }
        return counterBlacks;
    }


    public String checkIsWin(Pawn[][] gameBoard) {
        int counterWhites = countWhites(gameBoard);
        int counterBlacks = countBlacks(gameBoard);

        if (counterBlacks == 0) {
            return "whites-win";
        }
        else if (counterWhites == 0){
            return "blacks-win";
        }
        else
            return "no-win";
    }

}
