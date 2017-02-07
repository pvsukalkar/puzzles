/**
 * 
 * The n-queens puzzle is the problem of placing n queens on an nÃ—n chessboard such that no two queens attack each other.
 * Given an integer n, return all distinct solutions to the n-queens puzzle.
 * For example,
There exist two distinct solutions to the 4-queens puzzle:

[
 [".Q..",  // Solution 1
  "...Q",
  "Q...",
  "..Q."],

 ["..Q.",  // Solution 2
  "Q...",
  "...Q",
  ".Q.."]
]
 */

package puzzles;

import java.util.Scanner;

public class NQueenPuzzlePermutation {
    static int count = 1;
    public static void main(String[] args) {
        System.out.println("Enter size of board");
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int board[][] = new int[n][n];
        //constructBoard(0, board, n);
        for (int i = 0; i < board[0].length; i++) {
            placeNextQueen(0, i, board);
            //board = cleanBoard(board);
        }
        //printBoard(board);
        input.close();
    }
    
    private static int[][] cleanBoard(int [][] board) {
        int size = board[0].length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = 0; 
            }
        }
        return board; 
    }
    
    private static int[][] copyMatrix(int [][]matrix) {
        int size = matrix[0].length;
        int newMatrix [][] = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newMatrix[i][j] = matrix[i][j];
            }
        }
        return newMatrix;
    }
    
    private static int[][] placeNextQueen(int row, int column, int currentBoard[][]) {
        int [][] tempBoard = copyMatrix(currentBoard);
        int n = currentBoard[0].length;
        tempBoard = placeQueen(row, column, tempBoard);
        for (int c = 0; c < n; c++) {
            if (isValidWithQueen(row+1, c, tempBoard)) {
                placeNextQueen(row+1, c, tempBoard);   //start here
            }
        }
        if (row == n-1) {
            System.out.println(count+++".");
            printBoard(tempBoard);
        }
        return currentBoard;
    }
    
    private static boolean isValidWithQueen(int row, int column, int[][] currentBoard) {
        int size = currentBoard[0].length;
        if (row < 0 || row >= size || column < 0 || column >= size || currentBoard[row][column] == -1) {
            return false;
        }
        return true;
    }
    
    private static int[][] placeQueen(int i, int j, int[][] currentBoard){
        int size = currentBoard[0].length;
        for (int k = 0; k < size  ; k++) {
            if (currentBoard[i][k] == 0) {
                currentBoard[i][k] = -1; 
            }
            if (currentBoard[k][j] == 0) {
                currentBoard[k][j] = -1; 
            }
        }

        //fill diagonals
        for (int l = 1, m = 1; l < size; l++, m++) {
            int r = i; 
            int c = j; 
            currentBoard = fillPlace(r+l, c-m, -1, currentBoard);
            currentBoard = fillPlace(r-l, c+m, -1, currentBoard);
            currentBoard = fillPlace(r+l, c+m, -1, currentBoard);
            currentBoard = fillPlace(r-l, c-m, -1, currentBoard);
        }
        currentBoard[i][j] = 1; 
//        System.out.println("Printing after filling queen");
//        printBoard(currentBoard);
        return currentBoard;
    }

    private static int[][] fillPlace(int r, int c, int value, int [][] board) {
        int size = board[0].length;
        if (r>=0 && c>=0 && r<size && c< size) {
            board[r][c] = value;
        }
        return board; 
    }
    
    private static void printBoard(int chessBoard[][]) {
        int size = chessBoard[0].length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (chessBoard[i][j] == 1) {
                    System.out.print(" Q ");
                }
                else if( chessBoard[i][j] == -1){
                    System.out.print(" * ");
                }
                else {
                    System.out.print(" o ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
