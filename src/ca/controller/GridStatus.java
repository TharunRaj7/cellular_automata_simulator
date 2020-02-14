package ca.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds the status of a grid, which includes
 * {@link #numOfRow}, {@link #numOfCol}, and {@link #initialState}
 * of the grid.
 *
 * @author Cady Zhou
 * @version 1.1
 * @since 1.1
 */
public class GridStatus {
    private int numOfRow;
    private int numOfCol;
    private List<Integer> initialState;

    /**
     * Creates a new instance of GridStatus with default values
     */
    public GridStatus() {
        this(0,0, new ArrayList<>());
    }

    /**
     * Creates a new instance with passsed in initial values
     * @param row               an int as number of rows
     * @param col               an int as number of cols
     * @param initialState      a list of integer representing the
     *                          initial states
     */
    public GridStatus(int row, int col, List<Integer> initialState) {
        this.numOfRow = row;
        this.numOfCol = col;
        this.initialState = initialState;
    }

    /**
     * Fills the {@link #initialState} with 0
     */
    public void initializeEmptyStates() {
        for (int i = 0; i < numOfCol * numOfRow; i++) {
            initialState.add(0);
        }
    }

    /**
     * Gets the number of col of this grid
     * @return  an int as the number of col
     */
    public int getNumOfCol() {
        return numOfCol;
    }

    /**
     * Gets the number of row of this grid
     * @return  an int as the number of row
     */
    public int getNumOfRow() {
        return numOfRow;
    }

    /**
     * Gets the initial states of this grid
     * @return  a list of integer representing the initial states
     */
    public List<Integer> getInitialState() {
        return initialState;
    }

    /**
     * Sets the number of row
     * @param numOfRow  an int as the number of row
     */
    public void setNumOfRow(int numOfRow) {
        this.numOfRow = numOfRow;
    }

    /**
     * Sets the number of col
     * @param numOfCol  an int as the number of col
     */
    public void setNumOfCol(int numOfCol) {
        this.numOfCol = numOfCol;
    }

    /**
     * Sets the initial states
     * @param initialState  a list of integer representing the initial states
     */
    public void setInitialState(List<Integer> initialState) {
        this.initialState = initialState;
    }
}
