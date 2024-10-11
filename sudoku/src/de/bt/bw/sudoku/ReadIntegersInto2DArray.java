package de.bt.bw.sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * ChatGPT code
 */

public class ReadIntegersInto2DArray {

    public static void main(String[] args) {
        String filename = "numbers.txt"; // Path to your input file
        int[][] array = readFileTo2DArray(filename);
        
        // Print the 2D array
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    System.out.print(array[i][j] + " ");
                }
                System.out.println();
            }
        }
    }

    private static int[][] readFileTo2DArray(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));
            int rows = 0;
            int columns = 0;

            // First, we read the file to determine the number of rows and columns
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.trim().isEmpty()) {
                    rows++;
                    String[] numbers = line.split("\\s+");
                    columns = Math.max(columns, numbers.length);
                }
            }
            scanner.close();

            // Create a 2D array with the determined size
            int[][] array = new int[rows][columns];

            // Now read the file again to populate the array
            scanner = new Scanner(new File(filename));
            int currentRow = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.trim().isEmpty()) {
                    String[] numbers = line.split("\\s+");
                    for (int j = 0; j < numbers.length; j++) {
                        array[currentRow][j] = Integer.parseInt(numbers[j]);
                    }
                    currentRow++;
                }
            }

            scanner.close();
            return array;

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            return null;
        } catch (NumberFormatException e) {
            System.out.println("Error parsing integer: " + e.getMessage());
            return null;
        }
    }
}
