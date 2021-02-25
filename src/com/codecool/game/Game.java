package com.codecool.game;

import com.codecool.board.Board;
import com.codecool.pawn.Pawn;

import java.util.InputMismatchException;
import java.util.Scanner;


public class Game {

    public static void printBoard(Pawn[][] gameBoard){

        String dashes = new String(new char[gameBoard.length * 4]).replace("\0", "-");

        for (int rowIndex = 0; rowIndex < gameBoard.length; rowIndex++){
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

            System.out.println(rowIndex == gameBoard.length - 1 ? "\n" : "\n" + dashes);
        }
    }

    public void startGame(){
        askForBoardSize();
    }

    private int askForBoardSize(){
        Scanner sc = new Scanner(System.in);
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
}
