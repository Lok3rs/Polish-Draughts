package com.codecool.game;

import com.codecool.board.Board;
import com.codecool.inputService.InputService;
import com.codecool.pawn.Pawn;
import com.codecool.printer.Printer;
import com.codecool.validator.Validator;

public class Game {

    InputService inputService = new InputService();
    Validator validator = new Validator();

    public void startGame() {
        int size = inputService.askForBoardSize();
        Pawn[][] gameBoard = Board.initBoard(size);
        boolean whitesTurn = true;
        while (true) {
            round(gameBoard, whitesTurn);
            whitesTurn = !whitesTurn;
        }
    }

    private void round(Pawn[][] gameBoard, boolean isWhitesTurn) {
        int columnIndex = 1;
        int rowIndex = 0;
        Printer.clearScreen();
        Printer.printBoard(gameBoard);
        System.out.println(isWhitesTurn ? "Whites turn" : "Blacks turn");
        Pawn selectedPawn = choosePawnForMove(gameBoard, isWhitesTurn);
        if (validator.isShootRequired(gameBoard, selectedPawn)){
            int[] moveTargetCoordinates = inputService.getShootOption(gameBoard, selectedPawn);
            makeMove(gameBoard, selectedPawn, moveTargetCoordinates);
            while(validator.isShootRequired(gameBoard, selectedPawn)){
                Printer.clearScreen();
                Printer.printBoard(gameBoard);
                System.out.println("Another shoot possible!");
                moveTargetCoordinates = inputService.getShootOption(gameBoard, selectedPawn);
                makeMove(gameBoard, selectedPawn, moveTargetCoordinates);
            }
        }
        else {
            String moveDirection = inputService.getMoveDirection();
            int[] moveTargetCoordinates = getMoveCoordinates(moveDirection, isWhitesTurn);
            while (!validator.validateMove(
                    gameBoard,
                    selectedPawn.getPositionX() + moveTargetCoordinates[columnIndex],
                    selectedPawn.getPositionY() + moveTargetCoordinates[rowIndex],
                    moveDirection,
                    isWhitesTurn)) {
                System.out.println("You can't make that move, try again.");
                selectedPawn = choosePawnForMove(gameBoard, isWhitesTurn);
                moveDirection = inputService.getMoveDirection();
                moveTargetCoordinates = getMoveCoordinates(moveDirection, isWhitesTurn);
            }
            if (validator.isShootPossible(
                    gameBoard,
                    selectedPawn.getPositionX() + moveTargetCoordinates[columnIndex],
                    selectedPawn.getPositionY() + moveTargetCoordinates[rowIndex],
                    moveDirection, isWhitesTurn)){
                gameBoard[selectedPawn.getPositionY() + moveTargetCoordinates[rowIndex]][selectedPawn.getPositionX() + moveTargetCoordinates[columnIndex]] = null;

                if (isWhitesTurn){
                    moveTargetCoordinates[rowIndex] += moveDirection.equals("back-left") || moveDirection.equals("back-right") ? 1 : -1;
                }
                else {
                    moveTargetCoordinates[rowIndex] += moveDirection.equals("back-left") || moveDirection.equals("back-right") ? -1 : 1;
                }
                moveTargetCoordinates[columnIndex] += moveDirection.equals("left") || moveDirection.equals("back-left") ? -1 : 1;
            }
            makeMove(gameBoard, selectedPawn, moveTargetCoordinates);
        }

    }

    private Pawn choosePawnForMove(Pawn[][] gameBoard, boolean whitesTurn) {
        int rowIndex = inputService.getRowIndex(gameBoard);
        int columnIndex = inputService.getColumnIndex(gameBoard);
        while (!validator.validatePawnSelection(gameBoard, rowIndex, columnIndex, whitesTurn)) {
            System.out.println("You can't choose that field, try again.");
            rowIndex = inputService.getRowIndex(gameBoard);
            columnIndex = inputService.getColumnIndex(gameBoard);
        }
        return gameBoard[rowIndex][columnIndex];
    }

    private int[] getMoveCoordinates(String direction, boolean isWhite) {
        int[] coordsChange = new int[0];
        switch (direction) {
            case "left" -> coordsChange = isWhite ? new int[]{-1, -1} : new int[]{1, -1};
            case "back-left" -> coordsChange = isWhite ? new int[]{1, -1} : new int[]{-1, -1};
            case "right" -> coordsChange = isWhite ? new int[]{-1, 1} : new int[]{1, 1};
            case "back-right" -> coordsChange = isWhite ? new int[]{1, 1} : new int[]{-1, 1};

        }
        return coordsChange;
    }

    private void makeMove(Pawn[][] gameBoard, Pawn selectedPawn, int[] coordsChange) {
        int currentX = selectedPawn.getPositionX();
        int currentY = selectedPawn.getPositionY();
        int coordsChangeX = coordsChange[1];
        int coordsChangeY = coordsChange[0];

        selectedPawn.setPositionX(currentX + coordsChangeX);
        selectedPawn.setPositionY(currentY + coordsChangeY);
        gameBoard[currentY][currentX] = null;
        gameBoard[currentY + coordsChangeY][currentX + coordsChangeX] = selectedPawn;
    }
}
