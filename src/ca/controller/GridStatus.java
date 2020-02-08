package ca.controller;

import java.util.ArrayList;
import java.util.List;

public class GridStatus {
    private int numOfRow;
    private int numOfCol;
    private List<Integer> initialState;

    public GridStatus() {
        this(0,0, new ArrayList<>());
    }

    public GridStatus(int row, int col, List<Integer> initialState) {
        this.numOfRow = row;
        this.numOfCol = col;
        this.initialState = initialState;
    }

    public int getNumOfCol() {
        return numOfCol;
    }

    public int getNumOfRow() {
        return numOfRow;
    }

    public List<Integer> getInitialState() {
        return initialState;
    }

    public void setNumOfRow(int numOfRow) {
        this.numOfRow = numOfRow;
    }

    public void setNumOfCol(int numOfCol) {
        this.numOfCol = numOfCol;
    }

    public void setInitialState(List<Integer> initialState) {
        this.initialState = initialState;
    }
}
