/**
 * Print all permutations of a given string.
 */
package puzzles;

import java.util.Scanner;

public class Permutation {

    public static void main(String[] args) {
        System.out.println("Enter String");
        Scanner input = new Scanner(System.in);
        String str = input.nextLine();
        permute("", str);
        input.close();
    }

    private static void permute(String prefix, String suffix) {
        int len = suffix.length();
        if (len == 0) {
            System.out.println(prefix);
        }
        else {
            for (int i = 0; i < len; i++) {
                permute(prefix+suffix.charAt(i), suffix.substring(0,i)+suffix.substring(i+1,len));
            }
        }
    }
}
