package ca.model;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Grid {
    private int numOfColumns;
    private int numOfRows;

    Map<Pair, Cell> gridMap;

    public Grid(int numOfRows, int numOfColumns, ArrayList<Integer> initialStates) {
        //make the grid
        this.numOfRows = numOfRows;
        this.numOfColumns = numOfColumns;
        gridMap = new LinkedHashMap<>();
        createGridModel();
    }

    private void createGridModel() {
        int rowLooper;
        int colLooper;
        for(int i = 0; i < numOfRows; i++){
            
        }
    }

    public ArrayList<Cell> getNSEWNeigbours(int row, int col){

        return new ArrayList<>();
    }
    public ArrayList<Cell> getAllNeighbours(int row, int col){
        return new ArrayList<>();
    }

    /**
     *
     * @param row
     * @param col
     * @param state
     * @param color
     */
    public void updateGrid(int row, int col, int state, Color color){

    }


    public GridPane getGrid() {
        return gridView;
    }
}