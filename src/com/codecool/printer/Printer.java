package com.codecool.printer;

import com.codecool.pawn.Pawn;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Printer {
    public final static char[] alphabet = "ABCDEFGHIJKLMNOPRSTWYZ".toCharArray();

    public static void printBoard(Pawn[][] gameBoard) {
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

        for (int rowIndex = 0; rowIndex < gameBoard.length; rowIndex++) {
            System.out.printf("%s  ", alphabet[rowIndex]);
            for (int elementIndex = 0; elementIndex < gameBoard[rowIndex].length; elementIndex++) {

                if (elementIndex == gameBoard[rowIndex].length - 1) {
                    System.out.print(
                            gameBoard[rowIndex][elementIndex] == null ? "   " :
                                    gameBoard[rowIndex][elementIndex].getIsWhite() ? " "+"\u2B1C"+" " : " "+"\u2B1B"+" "
                    );
                } else {
                    System.out.print(
                            gameBoard[rowIndex][elementIndex] == null ? "   |" :
                                    gameBoard[rowIndex][elementIndex].getIsWhite() ? " "+"\u2B1C"+" |" : " "+"\u2B1B"+" |"
                    );
                }
            }
            System.out.println(rowIndex == gameBoard.length - 1 ? "\n" : "\n  " + dashes);
        }
    }
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
