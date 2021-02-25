package com.codecool.board;


import com.codecool.pawn.Pawn;

public class Board {

    int size;

    public Board(int size){
        this.size = size;
    }

    public Pawn[][] initBoard(){
        Pawn[][] gameBoard = new Pawn[size][size];
        return fillBoard(gameBoard);
    }

    public Pawn[][] fillBoard(Pawn[][] gameBoard) {
        int lastIndex = gameBoard.length - 1;
        int beforeLastIndex = gameBoard.length - 2;

        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++){
                if (i == 0) { gameBoard[i][j] = j % 2 == 0 ? new Pawn(i, j, false) : null; }
                else if (i == 1) { gameBoard[i][j] = j % 2 == 1 ? new Pawn(i, j, false) : null; }
                else if (i == lastIndex) { gameBoard[i][j] = j % 2 == 0 ? new Pawn(i, j, true) : null; }
                else if (i == beforeLastIndex ) { gameBoard[i][j] = j % 2 == 1 ? new Pawn(i, j, true) : null; }
                }
            }
        return gameBoard;
    }
}
