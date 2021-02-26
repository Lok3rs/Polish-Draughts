package com.codecool.game;

import com.codecool.board.Board;
import com.codecool.pawn.Pawn;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Game {

    Scanner sc = new Scanner(System.in);

    public static void printBoard(Pawn[][] gameBoard){

        char[] alphabet = "ABCDEFGHIJKLMNOPRSTWYZ".toCharArray();
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
    }

    private int askForBoardSize(){
        System.out.print("Choose game board size (8 - 20): ");
        int boardSize = 0;
        while (boardSize < 8 || boardSize > 20) {
            String userInput = sc.next();
            try{
                boardSize = Integer.parseInt(userInput);
            } catch (NumberFormatException e){
                System.out.print("Size needs to be an integer 8 - 20, try again: "
                );
            }
            System.out.print(
                    (boardSize < 8 || boardSize > 20) ? "Invalid number, try again: " : ""
            );
        }

        return boardSize;
    }



}
