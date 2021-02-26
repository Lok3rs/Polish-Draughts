package com.codecool.game;

import com.codecool.board.Board;
import com.codecool.pawn.Pawn;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Game {

    final Scanner sc = new Scanner(System.in);
    final static char[]  alphabet = "ABCDEFGHIJKLMNOPRSTWYZ".toCharArray();

    public static void printBoard(Pawn[][] gameBoard){

        String dashes = new String(new char[gameBoard.length * 4]).replace("\0", "-");
        List<Integer> columnNumbers = IntStream.rangeClosed(1, gameBoard.length)
                .boxed().collect(Collectors.toList());
        for (int number : columnNumbers) {
            if (number == 1) {
                System.out.printf("    %s", number);
            } else if (number == gameBoard.length) {
                System.out.printf("   %s\n", number);
            } else {
                System.out.printf("   %s", number);
            }
        }

        for (int rowIndex = 0; rowIndex < gameBoard.length; rowIndex++){
            System.out.printf("%s  ", alphabet[rowIndex]);
            for (int elementIndex = 0; elementIndex < gameBoard[rowIndex].length; elementIndex++) {

                if (elementIndex == gameBoard[rowIndex].length - 1) {
                    System.out.print(
                            gameBoard[rowIndex][elementIndex] == null ? "   " :
                                    gameBoard[rowIndex][elementIndex].getIsWhite() ? " W " : " B "
                    );
                } else {
                    System.out.print(
                            gameBoard[rowIndex][elementIndex] == null ? "   |" :
                                    gameBoard[rowIndex][elementIndex].getIsWhite() ? " W |" : " B |"
                    );
                }
            }

            System.out.println(rowIndex == gameBoard.length - 1 ? "\n" : "\n  " + dashes);
        }
    }

    public void startGame(){
        int size = askForBoardSize();
        Pawn[][] gameBoard = Board.initBoard(size);
        printBoard(gameBoard);
        Pawn pawn = choosePawnForMove(gameBoard, true);
    }

    private int askForBoardSize(){
        System.out.print("Choose game board size (8 - 20): ");
        int boardSize = 0;
        while (boardSize < 8 || boardSize > 20) {
            String userInput = sc.next();
            try{
                boardSize = Integer.parseInt(userInput);
            } catch (NumberFormatException e){
                System.out.print("Size needs to be an integer 8 - 20, try again: ");
            }
            System.out.print(
                    (boardSize < 8 || boardSize > 20) ? "Invalid number, try again: " : ""
            );
        }

        return boardSize;
    }

    private Pawn choosePawnForMove(Pawn[][] gameBoard, boolean whitesTurn){
        int rowIndex = getRowIndex(gameBoard);
        int columnIndex = getColumnIndex(gameBoard);

        System.out.println(validatePawnSelection(gameBoard, rowIndex, columnIndex, whitesTurn));

        return gameBoard[rowIndex][columnIndex];
    }


    private int getRowIndex(Pawn[][] gameBoard){
        System.out.print("Provide row character: ");
        String rowChar = sc.next().toUpperCase();
        int rowIndex = new String(alphabet).indexOf(rowChar);
        while (rowIndex == -1 || rowIndex >= gameBoard.length){
            System.out.print("Invalid row character, try again: ");
            rowChar = sc.next().toUpperCase();
            rowIndex = new String(alphabet).indexOf(rowChar);
            System.out.println(rowIndex);
        }
        return rowIndex;
    }

    private int getColumnIndex(Pawn[][] gameBoard){
        int columnIdentifier = 0;
        System.out.print("Provide column number: ");
        while (columnIdentifier == 0 || columnIdentifier > gameBoard.length){
            String columnIndexStr = sc.next();
            try{
                columnIdentifier = Integer.parseInt(columnIndexStr);
            }
            catch (NumberFormatException e){
                System.out.print("Column index needs to be a number, try again: ");
            }
            System.out.print(
                    columnIdentifier == 0 || columnIdentifier > gameBoard.length ? "Index can't be 0 and can't be bigger than " + gameBoard.length : ""
            );
        }
        return columnIdentifier - 1;
    }


    private boolean validatePawnSelection(Pawn[][] gameBoard, int rowIndex, int columnIndex, boolean playerIsWhite){
        Pawn selectedField = gameBoard[rowIndex][columnIndex];
        return selectedField != null && selectedField.getIsWhite() == playerIsWhite;
    }



}
