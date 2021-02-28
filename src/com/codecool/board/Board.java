package com.codecool.board;


import com.codecool.pawn.Pawn;

public class Board {

    int size;

    public Board(int size){
        this.size = size;
    }

    public static Pawn[][] initBoard(int size){
        Pawn[][] gameBoard = new Pawn[size][size];
        int lastIndex = gameBoard.length - 1;
        int beforeLastIndex = gameBoard.length - 2;

        for (int rowIndex = 0; rowIndex < gameBoard.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < gameBoard.length; columnIndex++){
                if (rowIndex == 0) {
                    gameBoard[rowIndex][columnIndex] = columnIndex % 2 == 0 ? new Pawn(rowIndex, columnIndex, false) : null;
                }
                else if (rowIndex == 1) {
                    gameBoard[rowIndex][columnIndex] = columnIndex % 2 == 1 ? new Pawn(rowIndex, columnIndex, false) : null;
                }
                else if (rowIndex == lastIndex) {
                    gameBoard[rowIndex][columnIndex] = columnIndex % 2 == 0 ? new Pawn(rowIndex, columnIndex, true) : null;
                }
                else if (rowIndex == beforeLastIndex ) {
                    gameBoard[rowIndex][columnIndex] = columnIndex % 2 == 1 ? new Pawn(rowIndex, columnIndex, true) : null;
                }
            }
        }
        return gameBoard;
    }
}
