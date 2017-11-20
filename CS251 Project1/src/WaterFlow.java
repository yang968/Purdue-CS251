/**
 * WaterFlow
 * Water flow problem which start every empty cell in the first row.
 *
 * @author Seungho Yang
 * @CS login ID: yang968
 * @PSO section: 10
 * @date 9/16/2016
 */

import javax.swing.*;
import java.util.*;

public class WaterFlow {
    public int rows; //number of rows of the grid
    public int columns; //number of columns of grid
    public int[][] delayTimeGrid; //time of water in the grid, and 0 means the water is blocked
    public int[][] reachTimeGrid; //time of water flow reach that point
    public boolean[][] earliestPathGrid; //a boolean grid that identify the shortest path. For visualization purpose
    public List<Cell> earliestPath; //The earliest flow path
    public WaterFlowVisualization visualization;
    public Scanner s = new Scanner(System.in);
    public boolean visual = true;

    //TODO: add variables that you need
    private Queue<Cell> currentQueue;
    private Queue<Cell> q1;
    private Queue<Cell> q2;
    private Queue<Cell> q3;
    int time = -1;
    // TEST

    /* The default constructor 
     * Read Input from terminal, do not modify it
     * */
    public WaterFlow() {
        //get inputs
        rows = s.nextInt();
        columns = s.nextInt();
        delayTimeGrid = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                delayTimeGrid[i][j] = s.nextInt();
            }
        }

        //TODO: initial the variables
        reachTimeGrid = new int[rows][columns];
        earliestPathGrid = new boolean[rows][columns];
        earliestPath = new Stack<Cell>();

        q1 = new Queue<Cell>();
        q2 = new Queue<Cell>();
        q3 = new Queue<Cell>();
        currentQueue = new Queue<Cell>();

        // Update first row of reachTimeGrid to either 0 or -1 and add cells to respective queues
        // while other cells below to -1
        int c, r;
        for (c = 0; c < columns; c++) {
            for (r = 0; r < rows; r++) {
                if (r == 0) {
                    if (delayTimeGrid[0][c] > 0) {
                        reachTimeGrid[0][c] = 0;
                        updateQueue(0, c);
                    }
                    else reachTimeGrid[r][c] = -1;
                }
                else {
                    reachTimeGrid[r][c] = -1;
                }
            }
        }
    }

    /**
     * Update the water flow once.
     *
     * @return Null
     */
    public void flow() {
        // Don't Change this part, it's visualize Part
        if (visual) try {
            Thread.sleep(100);
            //count++;
            //if (count == 5) Thread.sleep(20000);
            visualization.repaint();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //TODO: Start your implementation of progress here
        time++;
        while (!currentQueue.isEmpty()) {
            Cell c = currentQueue.dequeue();
            if (rows == 1) break;
            if (columns == 1) {
                updateGrid(c.row + 1, c.column);
                break;
            }
            if (c.row == 0) {
                updateGrid(c.row + 1, c.column);
            }
            else if (c.row <= rows - 2) {
                if (c.column == 0) {
                    updateGrid(c.row + 1, c.column);
                    updateGrid(c.row, c.column + 1);
                }
                else if (c.column == columns-1) {
                    updateGrid(c.row + 1, c.column);
                    updateGrid(c.row, c.column - 1);
                }
                else {
                    updateGrid(c.row, c.column - 1);
                    updateGrid(c.row + 1, c.column);
                    updateGrid(c.row, c.column + 1);
                }
            }
            else if (c.row == rows - 1) {
                if (c.column == 0) {
                    updateGrid(c.row, c.column + 1);
                }
                else if (c.column == columns-1) {
                    updateGrid(c.row, c.column - 1);
                }
                else {
                    updateGrid(c.row, c.column - 1);
                    updateGrid(c.row, c.column + 1);
                }
            }
        }
        // Assign nodes by having the queue calling the method point to the nodes that
        // the queues in the argument points to.
        currentQueue.transferNodes(q1);
        q1 = new Queue<Cell>();
        q1.transferNodes(q2);
        q2 = new Queue<Cell>();
        q2.transferNodes(q3);
        q3 = new Queue<Cell>();
    }


    /**
     * Calculate the waterflow until it ends.
     */
    public void determineFlow() {
        //TODO: Fill in the condition of the while
        while (!currentQueue.isEmpty() || !q1.isEmpty() || !q2.isEmpty() || !q3.isEmpty()) {
            this.flow();
        }
    }

    /**
     * Create the Visualization of the Waterflow
     */
    public void visualize() {
        visualization = new WaterFlowVisualization(this);

        JFrame frame = new JFrame("Water Flow Visualization");
        frame.add(visualization);
        visualization.init();
        visualization.start();
        frame.setSize(visualization.getSize());
        frame.setVisible(true);
    }

    /**
     * Find one shortest path and update the shortestGrid.
     *
     * 
     */
    public void earliestFlowPath() {
       //TODO: implement the earliest path method
        int[] sumOfGridsLastRow = new int[columns];

        // Find the cell in the last row with the lowest flow value when water exits the grid
        int c, min = Integer.MAX_VALUE;
        int columnIndex = -1;
        for (c = columns-1; c > -1; c--) {
            if (reachTimeGrid[rows-1][c] != -1) {
                sumOfGridsLastRow[c] = delayTimeGrid[rows-1][c] + reachTimeGrid[rows-1][c];
                if (sumOfGridsLastRow[c] <= min) {
                    min = sumOfGridsLastRow[c];
                    columnIndex = c;
                }
            }
        }
        // Return nothing if there were no water flowing to the last row
        if (columnIndex == -1) {
            return;
        }
        // Add the cell with the lowest value to the earliestPath
        int currentRow = rows - 1;
        Cell lastGrid = new Cell(currentRow, columnIndex);
        earliestPath.add(lastGrid);
        earliestPathGrid[currentRow][columnIndex] = true;

        // Add Cells that are forming the earliestPath
        while (currentRow > 0) {
            if (columnIndex == 0) {
                if (sumOfGrids(currentRow-1,columnIndex) == reachTimeGrid[currentRow][columnIndex]) {
                    earliestPath.add(0, new Cell(currentRow - 1, columnIndex));
                    currentRow--;
                }
                else if (sumOfGrids(currentRow,columnIndex + 1) == reachTimeGrid[currentRow][columnIndex]) {
                    earliestPath.add(0, new Cell(currentRow, columnIndex + 1));
                    columnIndex++;
                }
            }
            else if (columnIndex == columns - 1) {
                if (sumOfGrids(currentRow,columnIndex - 1) == reachTimeGrid[currentRow][columnIndex]) {
                    earliestPath.add(0, new Cell(currentRow, columnIndex - 1));
                    columnIndex--;
                }
                else if (sumOfGrids(currentRow-1,columnIndex) == reachTimeGrid[currentRow][columnIndex]) {
                    earliestPath.add(0, new Cell(currentRow - 1, columnIndex));
                    currentRow--;
                }
            }
            else {
                if (sumOfGrids(currentRow,columnIndex - 1) == reachTimeGrid[currentRow][columnIndex]) {
                    earliestPath.add(0, new Cell(currentRow, columnIndex - 1));
                    columnIndex--;
                }
                else if (sumOfGrids(currentRow-1,columnIndex) == reachTimeGrid[currentRow][columnIndex]) {
                    earliestPath.add(0, new Cell(currentRow - 1, columnIndex));
                    currentRow--;
                }
                else if (sumOfGrids(currentRow,columnIndex + 1) == reachTimeGrid[currentRow][columnIndex]) {
                    earliestPath.add(0, new Cell(currentRow, columnIndex + 1));
                    columnIndex++;
                }
            }
            earliestPathGrid[currentRow][columnIndex] = true;
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int[][] getDelayTimeGrid() {
        return delayTimeGrid;
    }

    public int[][] getReachTimeGrid() {
        return reachTimeGrid;
    }

    public boolean[][] getEarliestPathGrid() {
        return earliestPathGrid;
    }

    /**
     * Enqueue the cell of delayTimeGrid based on the value of delayTimeGrid[row][column]
     * @param row
     * @param column
     */
    public void updateQueue(int row, int column) {
        if (delayTimeGrid[row][column] == 1) {
            q1.enqueue(new Cell(row, column));
        }
        if (delayTimeGrid[row][column] == 2) {
            q2.enqueue(new Cell(row, column));
        }
        if (delayTimeGrid[row][column] == 3) {
            q3.enqueue(new Cell(row, column));
        }
    }

    /**
     * Update reachTimeGrid table at specific row and column
     * if delayTimeGrid[row][column] does not equal 0 and reachTimeGrid[row][column] == -1 (not updated)
     *  then reachTimeGrid[row][column] = time of the current flow
     * @param row
     * @param column
     */
    public void updateGrid(int row, int column) {
        if (delayTimeGrid[row][column] != 0 && reachTimeGrid[row][column] == -1) {
            reachTimeGrid[row][column] = time;
            updateQueue(row, column);
        }
    }

    /**
     * Returns sum of the values at specific row & column of reachTimeGrid and delayTimeGrid
     * @param row
     * @param column
     * @return Value of the Sum
     */
    public int sumOfGrids(int row, int column) {
        return reachTimeGrid[row][column] + delayTimeGrid[row][column];
    }
}

