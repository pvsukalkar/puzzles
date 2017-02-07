/**Given a number n, find all valid permutations of size n
*Ex. for n = 2 , print ()(), (())
*/
package puzzles;

import java.util.Scanner;

public class ValidParanthesisPermutation {
    public static void main(String[] args) {
        System.out.println("Enter n");
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        printPermutation(n, n, "");
        input.close();
    }
    
    private static void printPermutation(int open, int closed, String str) {
        //System.out.println("open: "+open+" closed: "+closed+" str: '"+str+"'");
        if (open == 0 && closed == 0) {
            System.out.println(str);
        }
        if (open > 0) {
            printPermutation(open-1, closed, str+"(");
        }
        if (open<closed) {
            printPermutation(open, closed-1, str+")");
        }
    }
    
    
}
