package com.codecool;

import com.codecool.board.Board;
import com.codecool.game.Game;
import com.codecool.pawn.Pawn;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Board board = new Board(7);
        Pawn[][] gameBoard = board.initBoard();
        Game.printBoard(gameBoard);

    }
}
