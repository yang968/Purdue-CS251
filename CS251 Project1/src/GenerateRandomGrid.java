/**
 * GenerateRandomGrid
 *
 * Given the rows, columns and probability of block cell, generate a random grid.
 *
 * @author Linda Huang, huang654@purdue.edu
 * @date June 23, 2016
 */

import java.util.*;

public class GenerateRandomGrid {
    public static int[][] generateGrid (int rows, int columns, double probability) {
        Random random = new Random();

        int[][] grid = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                double a = random.nextDouble();
                if (a < probability)
                    grid[i][j] = 0;
                else {
                    int b = random.nextInt(3);
                    grid[i][j] = b + 1;
                }
            }
        }
        return grid;
    }

    public static void main(String[] args) {
        int rows = Integer.parseInt(args[0]);
        int columns = Integer.parseInt(args[1]);
        double probability = Double.parseDouble(args[2]);
        int[][] grid = generateGrid(rows, columns, probability);

        System.out.println(rows + " " + columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.printf("%d ",grid[i][j]);
            }
            System.out.println();
        }
    }
}
