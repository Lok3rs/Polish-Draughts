package com.codecool.inputService;

import com.codecool.pawn.Pawn;
import com.codecool.printer.Printer;

import java.util.Scanner;

public class InputService {
    final Scanner sc = new Scanner(System.in);

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
                case "3" -> moveDirection = "back-left";
                case "4" -> moveDirection = "back-right";
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
}
