package com.codecool;

import com.codecool.board.Board;
import com.codecool.pawn.Pawn;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Board newBoard = new Board(8);
        System.out.println(Arrays.deepToString(newBoard.initBoard()));
        newBoard.initBoard();
        newBoard.printBoard();
    }
}
