/**
 * Given a sudoku, solve it. 
 * For every space, enter 0.
 * Some test cases : 
 * 
Test case 1: --> 3 0 6 5 0 8 4 0 0 5 2 0 0 0 0 0 0 0 0 8 7 0 0 0 0 3 1 0 0 3 0 1 0 0 8 0 9 0 0 8 6 3 0 0 5 0 5 0 0 9 0 6 0 0 1 3 0 0 0 0 2 5 0 0 0 0 0 0 0 0 7 4 0 0 5 2 0 6 3 0 0

Test case 2: --> 3 0 5 8 7 0 0 0 0 0 0 6 0 2 9 0 0 5 4 2 9 0 5 0 6 0 0 0 6 0 7 0 0 9 0 2 0 0 0 0 0 0 0 0 0 9 0 3 0 0 5 0 4 0 0 0 7 0 8 0 1 6 3 1 0 0 5 6 0 2 0 0 0 0 0 0 3 2 7 0 8

Test case 3: --> 0 0 2 0 0 5 4 0 0 0 0 0 8 0 7 3 2 0 5 0 0 0 0 0 0 8 9 0 2 0 0 1 3 5 7 8 0 0 0 0 0 0 0 0 0 7 4 8 9 5 0 0 3 0 8 3 0 0 0 0 0 0 1 0 9 5 3 0 1 0 0 0 0 0 6 5 0 0 7 0 0 

Test case 4: --> 5 0 9 4 0 0 0 0 0 0 0 0 0 3 8 0 4 0 1 0 0 0 6 0 0 9 0 0 0 4 0 0 2 0 1 9 0 0 3 0 0 0 5 0 0 9 5 0 1 0 0 6 0 0 0 8 0 0 4 0 0 0 7 0 9 0 7 1 0 0 0 0 0 0 0 0 0 5 9 0 1

Test case 5: --> 0 8 0 0 0 0 0 0 0 7 4 9 8 0 0 0 0 0 3 5 0 0 7 4 0 0 0 0 3 0 0 0 0 9 0 5 0 0 0 1 0 2 0 0 0 9 0 2 0 0 0 0 1 0 0 0 0 7 6 0 0 9 4 0 0 0 0 0 1 8 5 7 0 0 0 0 0 0 0 3 0 

Test case 6: --> 0 9 0 0 0 5 0 0 3 2 0 0 8 0 0 6 5 0 0 0 8 0 1 0 0 0 0 0 0 0 0 7 0 5 0 0 0 3 0 0 4 0 0 6 0 0 0 4 0 8 0 0 0 0 0 0 0 0 9 0 2 0 0 0 2 3 0 0 8 0 0 1 7 0 0 3 0 0 0 8 0 
 */
package puzzles;

import java.util.ArrayList;
import java.util.Scanner;

public class SudokuSolver {
    static int countOfKnowns = 0;
    private static int debug = 0;
    static ArrayList<Integer> visited = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        solveSudoku();
        // test();
    }

    private static void solveSudoku() {
        Scanner input = new Scanner(System.in);
        // System.out.println("Enter size of sudoku. If it's nxn sudoku. Enter n. n can't be greater than 10");
        int n = 9;
        if (n > 10) {
            System.out.println("What did I just tell you? n can't be greater than 10");
            System.exit(0);
        }
        System.out.println("Enter current sudoku entries. If entry is blank, input 0");
        ArrayList<Integer>[][] sudoku = new ArrayList[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sudoku[i][j] = new ArrayList<>();
                int temp = input.nextInt();
                if (temp > 0 && temp < 10) {
                    sudoku[i][j].add(temp);
                    countOfKnowns++;
                }
            }
        }
        printBoard(sudoku);
        solveSudokuByRepetition(sudoku);
        int count = 0;
        while (countOfKnowns < 81) {
            //printBoard(sudoku);
            sudoku = assumptionMethod(sudoku);         //use only for hard sudoku problems. easy and medium will be solved by repetition
            count++;
        }
        printBoard(sudoku);
        input.close();
    }

    private static void solveSudokuByRepetition(ArrayList<Integer>[][] sudoku) {
        int prevCount = 0;
        while (prevCount < countOfKnowns) {
            prevCount = countOfKnowns;
            fillPossibleEntries(sudoku);
            removeImprobablePeers(sudoku);
        }
    }

    private static ArrayList<Integer>[][] assumptionMethod(ArrayList<Integer>[][] sudoku) {
        print("Entered assumption method");
        int n = sudoku.length;
        int k = -1, l = -1, len = 10;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (sudoku[i][j].size() >1 && len > sudoku[i][j].size() && !visited.contains(i*10+j)) {
                    len = sudoku[i][j].size();
                    k = i;
                    l = j;
                }
            }
        }
        print("k--> "+k+" l--> "+l);
        ArrayList<Integer>[][] sudokuCopy = null;
        for (int i = 0; i < sudoku[k][l].size(); i++) {
            int m = sudoku[k][l].get(i);
            int originalCountOfknowns = countOfKnowns;
            sudokuCopy = getCopyOfMatrix(sudoku);
            sudokuCopy = replaceContentsAtIndex(sudokuCopy, k, l, m);
            solveSudokuByRepetition(sudokuCopy);
            if (isValid(sudokuCopy)) {
                sudoku = getCopyOfMatrix(sudokuCopy);
                countOfKnowns++;
                break;
            }
            else {
                countOfKnowns = originalCountOfknowns;
                sudokuCopy = getCopyOfMatrix(sudoku);
            }
        }
        visited.add(k*10+l);
        return sudoku;
    }

    private static void print(String message) {
        if (debug  == 1) {
            System.out.println(message);
        }
    }

    private static boolean isValid(ArrayList<Integer>[][] sudokuCopy) {
        int n = sudokuCopy.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (sudokuCopy[i][j].size() == 1) {
                    int m = sudokuCopy[i][j].get(0);
                    //check rows and columns
                    for (int k = 0; k < n; k++) {
                        if (sudokuCopy[k][j].size() == 1 && (k != i) && sudokuCopy[k][j].get(0) == m) {
                            print("not valid for : i-->"+i+" j-->"+j+"  k-->"+k+" j-->"+j );
                            return false;
                        }
                        if (sudokuCopy[i][k].size() == 1 && (k != j) && sudokuCopy[i][k].get(0) == m) {
                            print("not valid for : i-->"+i+" j-->"+j+"  j-->"+j+" k-->"+k );
                            return false;
                        }
                    }
                    //check box
                    int t = i/3; 
                    int s = j/3; 
                    for (int k = 3*t; k < 3*t+3; k++) {
                        for (int l = 3*s; l < 3*s+3; l++) {
                            if (sudokuCopy[k][l].size() == 1 && !(k == i && l == j) && sudokuCopy[k][l].get(0) == m) {
                                print("not valid for : i-->"+i+" j-->"+j+"  k-->"+k+" l-->"+l );
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private static ArrayList<Integer>[][] replaceContentsAtIndex(ArrayList<Integer>[][] sudokuCopy, int k, int l,
            int m) {
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(m);
        sudokuCopy[k][l] = temp;
        return sudokuCopy;
    }

    private static ArrayList<Integer>[][] getCopyOfMatrix(ArrayList<Integer>[][] matrix) {
        int size = matrix.length;
        ArrayList<Integer> newMatrix[][] = new ArrayList[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newMatrix[i][j] = (ArrayList<Integer>) matrix[i][j].clone();
            }
        }
        return newMatrix;
    }

    public static void test() {
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(2);
        temp.add(3);
        ArrayList<Integer> temp3 = (ArrayList<Integer>) temp.clone();
        temp3.add(5);
        print("temp: " + temp.toString());
        print("temp3: " + temp3.toString());
    }

    private static void removeImprobablePeers(ArrayList<Integer>[][] sudoku) {
        removeImprobablePeersFromBox(sudoku);
        removeImprobablePeersFromColumn(sudoku);
        removeImprobablePeersFromRow(sudoku);
    }

    public static void fillPossibleEntries(ArrayList<Integer>[][] sudoku) {
        int n = sudoku.length;
        // remove existing peers
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (sudoku[i][j].size() != 1) {
                    ArrayList<Integer> allPossibilities = removeExistingPeers(sudoku, n, i, j);
                    if (sudoku[i][j].size() == 0) {
                        sudoku[i][j].addAll(allPossibilities);
                    }
                }
            }
        }
        //System.out.println();
        //printBoard(sudoku);

    }

    public static void removeImprobablePeersFromRow(ArrayList<Integer>[][] sudoku) {
        int n = sudoku.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (sudoku[i][j].size() > 1) {
                    ArrayList<Integer> originalPossibilities = (ArrayList<Integer>) sudoku[i][j].clone();
                    // remove probable elements from same row
                    for (int k = 0; k < n; k++) {
                        if (sudoku[i][k].size() >= 1 && k != j) {
                            originalPossibilities.removeAll(sudoku[i][k]); // remove known element from row
                        }
                    }
                    if (originalPossibilities.size() == 1) {
                        int s = originalPossibilities.get(0);
                        ArrayList<Integer> temp = new ArrayList<>();
                        for (int k = 0; k < sudoku[i][j].size(); k++) {
                            int t = sudoku[i][j].get(k);
                            if (t != s) {
                                temp.add(t);
                            }
                        }
                        sudoku[i][j].removeAll(temp);
                        countOfKnowns++;
                    }
                }
            }
        }
        //System.out.println();
        //printBoard(sudoku);
    }

    public static void removeImprobablePeersFromColumn(ArrayList<Integer>[][] sudoku) {
        int n = sudoku.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (sudoku[i][j].size() > 1) {
                    ArrayList<Integer> originalPossibilities = (ArrayList<Integer>) sudoku[i][j].clone();
                    // remove probable elements from same column
                    for (int k = 0; k < n; k++) {
                        if (sudoku[k][j].size() >= 1 && k != i) {
                            originalPossibilities.removeAll(sudoku[k][j]); // remove known element from row
                        }
                    }
                    if (originalPossibilities.size() == 1) {
                        ArrayList<Integer> temp = new ArrayList<>();
                        int s = originalPossibilities.get(0);
                        for (int k = 0; k < sudoku[i][j].size(); k++) {
                            int t = sudoku[i][j].get(k);
                            if (t != s) {
                                temp.add(t);
                            }
                        }
                        sudoku[i][j].removeAll(temp);
                        countOfKnowns++;
                    }
                }
            }
        }
        //System.out.println();
        //printBoard(sudoku);
    }

    public static void removeImprobablePeersFromBox(ArrayList<Integer>[][] sudoku) {
        int n = sudoku.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (sudoku[i][j].size() > 1) {

                    ArrayList<Integer> originalPossibilities = (ArrayList<Integer>) sudoku[i][j].clone();
                    if (i == 0 && j == 8) {
                        print("originalPossibilities: " + originalPossibilities.toString());
                        print("sudoku[" + i + "][" + j + "]: " + sudoku[i][j]);
                    }
                    // remove probable elements from same column
                    int t = i / 3;
                    int s = j / 3;
                    for (int k = 3 * t; k < 3 * t + 3; k++) {
                        for (int l = 3 * s; l < 3 * s + 3; l++) {
                            if (sudoku[k][l].size() >= 1 && !(k == i && l == j)) {
                                originalPossibilities.removeAll(sudoku[k][l]); // remove known element from box
                                if (i == 0 && j == 8) {
                                    print("originalPossibilities: " + originalPossibilities.toString());
                                    print("sudoku[" + k + "][" + l + "]: " + sudoku[k][l]);
                                }
                            }
                        }
                    }
                    if (originalPossibilities.size() == 1) {
                        int q = originalPossibilities.get(0);
                        ArrayList<Integer> temp = new ArrayList<>();
                        for (int k = 0; k < sudoku[i][j].size(); k++) {
                            int p = sudoku[i][j].get(k);
                            if (p != q) {
                                temp.add(p);
                            }
                            if (i == 0 && j == 8) {
                                print("p: " + p + " ,q: " + q);
                                print("sudoku[" + i + "][" + j + "]: " + sudoku[i][j]);
                            }
                        }
                        sudoku[i][j].removeAll(temp);
                        countOfKnowns++;
                    }
                    if (i == 0 && j == 8) {
                        print("originalPossibilities: " + originalPossibilities.toString());
                        print("sudoku[" + i + "][" + j + "]: " + sudoku[i][j]);
                    }
                }
            }
        }
        //System.out.println();
        //printBoard(sudoku);
    }

    public static ArrayList<Integer> removeExistingPeers(ArrayList<Integer>[][] sudoku, int n, int i, int j) {
        ArrayList<Integer> allPossibilities = null;
        if (sudoku[i][j].size() == 0) {
            allPossibilities = getAllPossibleEntries(n);
        } else {
            allPossibilities = sudoku[i][j];
        }
        if (i == 0 && j == 8) {
            print("before entering --> allPossiblities: " + allPossibilities);
            print("before entering --> sudoku[" + i + "][" + j + "]: " + sudoku[i][j]);
        }
        // remove elements from same row
        for (int k = 0; k < n; k++) {
            if (sudoku[i][k].size() == 1 && k != j) {
                allPossibilities.remove(sudoku[i][k].get(0)); // remove known element from row
            }
        }
        // remove elements from same column
        for (int k = 0; k < n; k++) {
            if (sudoku[k][j].size() == 1 && k != i) {
                allPossibilities.remove(sudoku[k][j].get(0)); // remove known element from row
            }
        }
        // remove elements from same box
        int t = i / 3;
        int s = j / 3;
        for (int k = 3 * t; k < 3 * t + 3; k++) {
            for (int l = 3 * s; l < 3 * s + 3; l++) {
                if (sudoku[k][l].size() == 1 && !(k == i && l == j)) {
                    allPossibilities.remove(sudoku[k][l].get(0)); // remove known element from box
                }
            }
        }
        if (allPossibilities.size() == 1) {
            countOfKnowns++;
        }
        if (i == 0 && j == 8) {
            print("before leaving --> allPossiblities: " + allPossibilities);
            print("before leaving --> sudoku[" + i + "][" + j + "]: " + sudoku[i][j]);
        }
        return allPossibilities;
    }

    public static ArrayList<Integer> getAllPossibleEntries(int n) {
        ArrayList<Integer> allPossibilities = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            allPossibilities.add(i + 1);
        }
        return allPossibilities;
    }

    public static void printBoard(ArrayList<Integer>[][] sudoku) {
        int n = sudoku.length;
        System.out.println();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(sudoku[i][j].toString() + "\t");
            }
            System.out.println();
        }
        print("Knowns: " + countOfKnowns);
    }
}
