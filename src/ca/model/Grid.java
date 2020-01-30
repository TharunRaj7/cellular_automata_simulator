package ca.model;

import ca.model.Cell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.HashMap;

public class Grid {
    private int column;
    private int row;

    private int cellWidth;
    private int cellHeight;

    HashMap<Pair, Cell> gridMap;
    GridPane grid;


    public Grid(int row, int column, ArrayList<Integer> initialStates) {
        //make the grid
        gridMap = new HashMap<>();
        grid = new GridPane();
    }

    public ArrayList<Cell> getNSEWNeigbours(){
        return new ArrayList<>();
    }
    public ArrayList<Cell> getAllNeighbours(){
        return new ArrayList<>();
    }

    public void updateGrid(int row, int col, int state){
    }

    /**
     * Here, the grid dimension or size is read in from the XML file.
     * The corresponding rows and columns are generated and added to the grid.
     * @param dimension
     * @return GridPane
     */

    private void createGrid() {
        for(int i = 0; i < row; i++) {
            RowConstraints rowConstraints = new RowConstraints(cellWidth);
            grid.getRowConstraints().add(rowConstraints);
        }

        for(int j = 0; j < column; j++) {
            ColumnConstraints column = new ColumnConstraints(cellHeight);
            grid.getColumnConstraints().add(column);
        }

        // TODO: fill in the states
    }

    public GridPane getGrid() {
        return grid;
    }
}