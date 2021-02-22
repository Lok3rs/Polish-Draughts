package com.codecool.board;

import com.codecool.pawn.Pawn;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Board {
    int size;

    private int getSizeFromUser(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Provide a size of a board (4-20): ");
        size = 0;
        while (size < 4 || size > 20){

            try {
                size = sc.nextInt();

                String message = (size < 4 || size > 20) ?
                        String.format("Number %d is not in range 4-20, try again: ", size) :
                        String.format("Your board size is settled to %d", size);
                System.out.print(message);
            }
            catch(InputMismatchException e){
                System.out.print("Size has to be a number, try again: ");
                sc.next();
            }
        }
        return size;
    }

    public void setSize(){
        this.size = getSizeFromUser();
    }

    private enum Cell {Pawn, None};

    private Pawn[][] initBoard(){
        int pawnsNumber = this.size * 2;

        Pawn[][] board = new Pawn[this.size][this.size];

        for(int i = 0; i < this.size; i++){

            if(i == 0 || i == this.size - 1){
                board[i] = (this.size % 2 == 0) ? new String[]{("x", null) * this.size / 2} :{};
            }
        }

    }

}
