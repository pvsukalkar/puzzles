/**
 * Markov takes out his Snakes and Ladders game and stares at the board, and wonders: If he had absolute control on the die (singular), and could get it to generate any number (in the range ) he desired, what would be the least number of rolls of the die in which he'd be able to reach the destination square (Square Number ) after having started at the base square (Square Number )?

Rules

Markov has total control over the die and the face which shows up every time he tosses it. You need to help him figure out the minimum number of moves in which he can reach the target square (100) after starting at the base (Square 1).

A die roll which would cause the player to land up at a square greater than 100, goes wasted, and the player remains at his original square. Such as a case when the player is at Square Number 99, rolls the die, and ends up with a 5.

If a person reaches a square which is the base of a ladder, (s)he has to climb up that ladder, and he cannot come down on it. If a person reaches a square which has the mouth of the snake, (s)he has to go down the snake and come out through the tail - there is no way to climb down a ladder or to go up through a snake.

Constraints

The board is always of the size  and Squares are always numbered  to .

 
 

Square number 1 and 100 will not be the starting point of a ladder or a snake. 
No square will have more than one of the starting or ending point of a snake or ladder (e.g. snake 56 to 44 and ladder 44 to 97 is not possible because 44 has both ending of a snake and a starting of a ladder)

Input Format

The first line contains the number of tests, T. T testcases follow.

For each testcase, the first line contain N(Number of ladders) and after that N line follow. Each of the N line contain 2 integer representing the starting point and the ending point of a ladder respectively.

The next line contain integer M(Number of snakes) and after that M line follow. Each of the M line contain 2 integer representing the starting point and the ending point of a snake respectively.

Output Format

For each of the T test cases, output one integer, each in a new line, which is the least number of moves (or rolls of the die) in which the player can reach the target square (Square Number 100) after starting at the base (Square Number 1). This corresponds to the ideal sequence of faces which show up when the die is rolled. 
If there is no solution, print -1.

Sample Input

2
3
32 62
42 68
12 98
7
95 13
97 25
93 37
79 27
75 19
49 47
67 17
4
8 52
6 80
26 42
2 72
9
51 19
39 11
37 29
81 3
59 5
79 23
53 7
43 33
77 21 
Sample Output

3
5
 */

package puzzles;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class SnakeLadderProblem {
    static int arr[]; 
    static int boardSize = 0;
    static final int MAX_ROLL_ON_DICE = 6; 
    public static void main(String[] args) {
        System.out.println("Enter target square . Example for a normal snake and ladder board, target would be 100");
        Scanner input = new Scanner(System.in);
        boardSize = input.nextInt();
        arr = new int[boardSize];
        System.out.println("Total how many snakes and ladders");
        int snakesAndLadders = input.nextInt();
        HashMap<Integer, Integer>snakes = new HashMap<>();
        HashMap<Integer, Integer>ladders = new HashMap<>();
        for (int i = 0; i < snakesAndLadders; i++) {
            int a = input.nextInt();
            int b = input.nextInt();
            if (a < b) {  //it's a ladder
                ladders.put(a, b);
            }
            else if (a > b) {
                snakes.put(a, b);
            }
            else {
                System.out.println("This is not snake or ladder"+a+", "+b);
            }
        }
        minSnakesAndLadderCount(boardSize, snakes, ladders);
        System.out.println(Arrays.toString(arr));
        System.out.println("min jumps required: "+arr[boardSize-1]);
        input.close();
    }

    private static void minSnakesAndLadderCount(int boardSize, HashMap<Integer, Integer> snakes,
            HashMap<Integer, Integer> ladders) {
        arr[0] = 0; 
        for (int i = 1; i < boardSize; i++) {
            if (arr[i] == 0) {
                arr[i] = minPreviousSix(i, snakes, ladders)+1; 
            }
        }
    }

    private static int minPreviousSix(int position, HashMap<Integer, Integer> snakes, HashMap<Integer, Integer> ladders) {
        int min = boardSize;
        if (snakes.containsKey(position)) {     //if there is snake at this position, you can't reach this position ever. so returning boardSize (which is like infinity)
            return boardSize;
        }
        for (int i = position-1; i >= position-MAX_ROLL_ON_DICE && i >= 0; i--) {
            if (min > arr[i]) {
                min = arr[i];
            }
        }
        if (ladders.containsKey(position)) {
            arr[ladders.get(position)-1] = min+1;
        }
        return min;
    }
}
