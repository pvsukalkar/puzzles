/**
 * Given rate of interest, number of years, and monthly amount calculate the SIP amount that will be received after period is over.
 */
package puzzles;

import java.util.Scanner;

public class SIPCalculator {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter montly amount");
        long P = input.nextInt();
        System.out.println("Enter number of years");
        int N = input.nextInt();
        System.out.println("Enter rate");
        int R = input.nextInt();
        System.out.println(calculateSIP(P, N, R));
        input.close();
    }
    
    private static long calculateSIP(long P, int N, int R) {
        long A = 0; 
        for (int i = 1; i <= N*12; i++) {
            A += P*Math.pow((double)(1+R/1200.0), i); 
            System.out.println(A);
        }
        return A;
    }
}
