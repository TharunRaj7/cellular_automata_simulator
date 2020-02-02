package ca.model;


import java.awt.*;
import java.util.*;
import java.util.List;

public class Grid {

    private int numOfColumns;
    private int numOfRows;

    Map<Pair, Cell> gridMap;

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
     * @return an arrayList of Pair objects
     */
    public ArrayList<Cell> getNSEWNeighbors(int r, int c) {
        ArrayList<Cell> ret = new ArrayList<>();
        int [] rowIndices = {r-1, r+1};
        int [] colIndices = {c+1, c-1}; //east first, then west

        for (int i : rowIndices){
            Pair temp = getPair(i, c);  // TODO: talk about the better way
            if (temp != null){
                ret.add(gridMap.get(temp));
            }
        }

        for (int i : colIndices){
            Pair temp = getPair(r, i);
            if (temp != null){
                ret.add(gridMap.get(temp));
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
    public ArrayList<Cell> getAllNeighbors (int r, int c){
        int [] rowIndices = {r-1, r-1, r, r+1, r+1, r+1, r, r-1};
        int [] colIndices = {c, c+1, c+1, c+1, c, c-1, c-1, c-1};
        ArrayList<Cell> ret = new ArrayList<>();

        for (int i = 0; i < 8; i++){
            Pair temp = getPair(rowIndices[i], colIndices[i]);
            if (temp != null){
                ret.add(gridMap.get(temp));
            }
        }
        return ret;
    }


    /**
     * Helper method to identify and return the pair object given the row and column
     * @param r
     * @param c
     * @return a Pair object
     */
    private Pair getPair(int r, int c) {
        for (Pair pair : gridMap.keySet()){
            if (pair.checkPair(r,c)){
                return pair;
            }
        }
        return null;
    }


    /**
     * Returns a specified cell
     * @param row
     * @param col
     * @return
     */
    public Cell getCell(int row, int col) {
        Pair temp = getPair(row, col);
        return gridMap.get(temp);
    }

    /**
     * Returns all the cells on the grid in order.
     * @return
     */
    public List<Cell> getAllCells() {
        List<Cell> ret = new ArrayList<>();

        for (int r = 0; r < numOfRows; r++) {
            for (int c = 0; c < numOfColumns; c++) {
                ret.add(gridMap.get(getPair(r, c)));
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
     * Returns the state of cell specified by r and c. Returns 0 by default if the given r and/or c are not valid
     * @param r
     * @param c
     * @return the state of a cell.
     */
    public int getCellState (int r, int c){
        Pair temp = getPair(r, c);
        if (temp != null){
            return gridMap.get(temp).getState();
        }
        return 0;
    }

    /**
     *Updates the grid for the given row,col pair and the new state
     * @param r
     * @param c
     * @param state
     */
    public void setCellState(int r, int c, int state) {
        Pair temp = getPair(r, c);
        gridMap.get(temp).setState(state);
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


    public static void main(String[] args) {
        //testing for Grid Class
        List<Integer> temp = new ArrayList<>();
        for(int i = 0; i < 9; i++) {
            temp.add(i);
        }
        Grid test = new Grid(3,3, temp);
        test.setCellState(1,2, 454);

        for (Pair pair : test.gridMap.keySet()){
            System.out.println(pair);
            System.out.println("State = " + test.gridMap.get(pair).getState());
        }

        ArrayList<Cell> tester = test.getAllNeighbors(2,2);
        for (Cell cell : tester){
            System.out.println(cell.getState());
        }


    }
}