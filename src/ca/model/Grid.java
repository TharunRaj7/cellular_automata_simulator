package ca.model;

/**
 * Despite the {@code CellShape}, it is always important to define
 * system of coordinate for that shape.
 */

import java.util.*;
import java.util.List;

public class Grid extends GridBase{
    private int numOfColumns;
    private int numOfRows;

    /**
     * Copies another Grid object
     * @param grid
     */
    public Grid (Grid grid){
        super(grid);
        this.numOfRows = grid.getNumOfRows();
        this.numOfColumns = grid.getNumOfColumns();
    }

    /**
     * Constructs the grid data structure.
     * @param numOfRows
     * @param numOfColumns
     * @param initialStates
     */
    public Grid(int numOfRows, int numOfColumns,  List<Integer> initialStates) {
        this(numOfRows, numOfColumns, initialStates, CellShape.SQUARE);
    }

    public Grid(int numOfRows, int numOfColumns,  List<Integer> initialStates, CellShape shape) {
        super(initialStates, shape);
        this.numOfRows = numOfRows;
        this.numOfColumns = numOfColumns;
    }

    @Override
    protected boolean inBound(int r, int c) {
        return (0 <= r) && (r < numOfRows) && (0 <= c) && (c < numOfColumns);
    }

    /**
     * Returns the state of cell specified by r and c. Returns 0 by default if the given r and/or c are not valid
     * @param r
     * @param c
     * @return the state of a cell.
     */

    /**
     * Returns all the cells on the grid in order.
     * @return
     */
    public List<Cell> getAllCells() {
        List<Cell> ret = new ArrayList<>();
        for (int r = 0; r < numOfRows; r++) {
            for (int c = 0; c < numOfColumns; c++) {
                ret.add(gridMap.get(new Pair(r, c)));
            }
        }
        return ret;
    }


    /**
     * Populates the Hashmap with cells and initializes their states based on a List of initial states.
     * @param initialStates
     */
    @Override
    protected void createGridModel(List<Integer>initialStates) {
        int rowLooper = 0;
        int colLooper = 0;

        for (int i = 0; i < numOfColumns*numOfRows; i++) {
            Pair temp = new Pair(rowLooper, colLooper);
            Cell tempCell = new Cell(initialStates.get(i));
            gridMap.putIfAbsent(temp, tempCell);

            if (colLooper == numOfColumns - 1){
                colLooper = 0;
                rowLooper++;
                continue;
            }
            colLooper++;
        }
    }

    /**
     * getter method for the number of columns
     * @return numOfColumns
     */
    public int getNumOfColumns() {
        return numOfColumns;
    }

    /**
     * getter method for the number of rows
     * @return numOfRows
     */
    public int getNumOfRows() {
        return numOfRows;
    }

}