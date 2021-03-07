package com.codecool.inputService;

import com.codecool.pawn.Pawn;
import com.codecool.printer.Printer;
import com.codecool.validator.Validator;

import java.util.*;

public class InputService {
    final Scanner sc = new Scanner(System.in);
    final Validator validator = new Validator();

    public int askForBoardSize() {
        System.out.print("Choose game board size (8 - 20): ");
        int boardSize = 0;
        while (boardSize < 8 || boardSize > 20) {
            String userInput = sc.next();
            try {
                boardSize = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.print("Size needs to be an integer 8 - 20, try again: ");
            }
            System.out.print(
                    (boardSize < 8 || boardSize > 20) ? "Invalid number, try again: " : ""
            );
        }
        return boardSize;
    }

    public String getMoveDirection() {
        System.out.println("Choose move direction:\n1 - Diagonally to the left\n2 - Diagonally to the right");
        String moveDirection = "";
        while (moveDirection.equals("")) {
            String userChoose = sc.next();
            switch (userChoose) {
                case "1" -> moveDirection = "left";
                case "2" -> moveDirection = "right";
                default -> System.out.print("Invalid choice, try again: ");
            }
        }
        return moveDirection;
    }

    public int getRowIndex(Pawn[][] gameBoard) {
        System.out.print("Provide row character: ");
        String rowChar = sc.next().toUpperCase();
        int rowIndex = new String(Printer.alphabet).indexOf(rowChar);
        while (rowIndex == -1 || rowIndex >= gameBoard.length) {
            System.out.print("Invalid row character, try again: ");
            rowChar = sc.next().toUpperCase();
            rowIndex = new String(Printer.alphabet).indexOf(rowChar);
        }
        return rowIndex;
    }

    public int getColumnIndex(Pawn[][] gameBoard) {
        int columnIdentifier = 0;
        System.out.print("Provide column number: ");
        while (columnIdentifier == 0 || columnIdentifier > gameBoard.length) {
            String columnIndexStr = sc.next();
            try {
                columnIdentifier = Integer.parseInt(columnIndexStr);
            } catch (NumberFormatException e) {
                System.out.print("Column index needs to be a number, try again: ");
            }
            System.out.print(
                    columnIdentifier == 0 || columnIdentifier > gameBoard.length ? "Index can't be 0 and can't be bigger than " + gameBoard.length : ""
            );
        }
        return columnIdentifier - 1;
    }

    public int[] getShootOption(Pawn[][] gameBoard, Pawn selectedPawn){
        boolean pawnIsWhite = selectedPawn.getIsWhite();
        List<String> shootingOptions = validator.checkShootOptions(gameBoard, selectedPawn);
        List<List<String>> optionsForDisplay = new ArrayList<>();

        for (int i = 0; i < shootingOptions.size(); i++){
            optionsForDisplay.add(Arrays.asList(String.valueOf(i + 1), shootingOptions.get(i)));
            System.out.println("Shoot required, choose direction.");
            System.out.printf("%s - Diagonally to the %s\n", optionsForDisplay.get(i).get(0), optionsForDisplay.get(i).get(1));
        }
        int[] leftShootCoords = pawnIsWhite ? new int[]{-2, -2} : new int[]{2, -2};
        int[] rightShootCoords = pawnIsWhite ? new int[]{-2, 2} : new int[]{2, 2};
        int[] backLeftShootCoords = pawnIsWhite ? new int[]{2, -2} : new int[]{-2, -2};
        int[] backRightShootCoords = pawnIsWhite ? new int[]{2, 2} : new int[]{-2, 2};

        String direction;

        String userInput = sc.next();


        while (Integer.parseInt(userInput) > optionsForDisplay.size() || Integer.parseInt(userInput) <= 0){
            System.out.println("Invalid choose, try again");
            userInput = sc.next();
        }

        direction = optionsForDisplay.get(Integer.parseInt(userInput) - 1).get(1);

        int[] coords = new int[0];


        switch (direction) {
            case "left" -> {
                coords = leftShootCoords;
                gameBoard[selectedPawn.getPositionY() + (pawnIsWhite ? -1 : 1)][selectedPawn.getPositionX() - 1] = null;
            }
            case "right" -> {
                coords = rightShootCoords;
                gameBoard[selectedPawn.getPositionY() + (pawnIsWhite ? -1 : 1)][selectedPawn.getPositionX() + 1] = null;
            }
            case "back-left" -> {
                coords = backLeftShootCoords;
                gameBoard[selectedPawn.getPositionY() + (pawnIsWhite ? 1 : -1)][selectedPawn.getPositionX() - 1] = null;
            }
            case "back-right" -> {
                coords = backRightShootCoords;
                gameBoard[selectedPawn.getPositionY() + (pawnIsWhite ? 1 : -1)][selectedPawn.getPositionX() + 1] = null;
            }

        }
        return coords;
    }

}
