package ca.model;


import java.util.*;
import java.util.List;

public class Grid {
    private int numOfColumns;
    private int numOfRows;
    private Map<Pair, Cell> gridMap;


    /**
     * Constructs the grid data structure.
     * @param numOfRows
     * @param numOfColumns
     * @param initialStates
     */
    public Grid(int numOfRows, int numOfColumns,  List<Integer> initialStates) {
        this.gridMap = new LinkedHashMap<>();
        this.numOfRows = numOfRows;
        this.numOfColumns = numOfColumns;
        createGridModel(initialStates);
    }

    /**
     * Copies another Grid object
     * @param grid
     */
    public Grid (Grid grid){
        this.numOfRows = grid.getNumOfRows();
        this.numOfColumns = grid.getNumOfColumns();
        this.gridMap = grid.gridMap;
    }
    /**
     * Gets the neighboring cells (North, South, East, West of the specified cell)
     * @param r
     * @param c
     * @return an arrayList of Cell objects
     */
    public List<Cell> getNSEWNeighbors(int r, int c) {
        List<Cell> ret = new ArrayList<>();
        int[] rowIndices = {r, r, r-1, r+1};
        int[] colIndices = {c+1, c-1, c, c}; //east first, then west
        return loopThroughNeighbors(rowIndices, colIndices, ret);
    }

    private boolean inBound(int r, int c) {
        return (0 <= r) && (r < numOfRows) && (0 <= c) && (c < numOfColumns);
    }

    private List<Cell> loopThroughNeighbors(int[] rowIndices, int[] colIndices, List<Cell> ret) {
        for (int i = 0; i < rowIndices.length; i++) {
            if (inBound(rowIndices[i], colIndices[i])) {
                ret.add(gridMap.get(new Pair(rowIndices[i], colIndices[i])));
            }
        }
        return ret;
    }


    /**
     * Returns all the neighbors surrouding a given cell (Up to 8).
     * @param r
     * @param c
     * @return an arrayList of all the neighboring cells.
     */
    public List<Cell> getAllNeighbors (int r, int c) {
        List<Cell> ret = new ArrayList<>();
        int[] rowIndices = {r-1, r-1, r, r+1, r+1, r+1, r, r-1};
        int[] colIndices = {c, c+1, c+1, c+1, c, c-1, c-1, c-1};
        return loopThroughNeighbors(rowIndices, colIndices, ret);
    }

    /**
     * returns a pair given a cell
     * @param cell
     * @return
     */
    public Pair getPairGivenCell(Cell cell){
        for (Pair pair : gridMap.keySet()){
            if (gridMap.get(pair) == cell){
                return pair;
            }
        }
        return null;
    }


    /**
     * Returns the state of cell specified by r and c. Returns 0 by default if the given r and/or c are not valid
     * @param r
     * @param c
     * @return the state of a cell.
     */

    public Cell getCell(int r, int c) {
       return gridMap.get(new Pair(r, c));
    }

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
    private void createGridModel(List<Integer>initialStates) {
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

    public int getCellState(int r, int c) {
        return getCell(r, c).getState();
    }

    public void setCellState(int r, int c, int state) {
        gridMap.get(new Pair(r, c)).setState(state);
    }
}