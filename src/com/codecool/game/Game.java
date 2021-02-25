package com.codecool.game;

import com.codecool.board.Board;
import com.codecool.pawn.Pawn;


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
}
