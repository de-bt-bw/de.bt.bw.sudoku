package de.bt.bw.sudoku;

import java.util.Scanner;

/**
 * ChatGPT Code
 */
public class RepeatString {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read the string
        System.out.print("Enter a string: ");
        String s = scanner.nextLine();

        // Read the integer
        System.out.print("Enter an integer: ");
        int n = scanner.nextInt();

        // Print the string n times
        for (int i = 0; i < n; i++) {
            System.out.println(s);
        }

        scanner.close();
    }
}
