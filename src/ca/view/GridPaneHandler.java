package ca.view;


import ca.controller.SimulationConfig;
import ca.model.Grid;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * This Class creates the gridpane that is shown as a visual for the simulation.
 * The class is responsible for creating the grid outline as well as filling each cell in the
 * grid with the correct color based on the initial cell states.
 */

public class GridPaneHandler {

    private static List<Color> cellColors = new ArrayList<Color>();
    private int cellWidth;
    private int cellHeight;
    private Grid grid;

    /**
     * Creates the initial gridpane based on the number of columns, rows and width/height of the grid.
     * Calls other methods to fill the grid.
     * @param c - columns in grid
     * @param r - rows in grid
     * @param width - full grid width
     * @param height - full grid height
     * @return GridPane - returns the Gridpane to the scene
     */
    public GridPane createGrid(int c, int r, int width, int height){
        cellWidth = width / c;
        cellHeight = height / r;
        GridPane myGrid = new GridPane();
        for(int i =0; i < c; i++){
            ColumnConstraints column = new ColumnConstraints(cellWidth);
            myGrid.getColumnConstraints().add(column);
        }
        for(int j =0; j < r; j++){
            RowConstraints row = new RowConstraints(cellHeight);
            myGrid.getRowConstraints().add(row);
        }
        myGrid.setGridLinesVisible(true);
        getCellColors();
        fillGrid(myGrid, c, r, cellWidth, cellHeight);
        return myGrid;
    }

    /**
     * Based on the cell states from the Grid class, the grid is filled with the corresponding colors.
     * @param myGrid
     * @param c
     * @param r
     * @param cellWidth
     * @param cellHeight
     */
    private void fillGrid(GridPane myGrid, int c, int r, int cellWidth, int cellHeight){
        for(int i = 0; i< c; i++){
            for(int j = 0; j< r; j++){
                int cellState = grid.getCellState(r, c);
                Color cellColor = cellColors.get(cellState);
                myGrid.add(new Rectangle(cellWidth, cellHeight, cellColor), i, j);
            }
        }
    }

    /**
     * From the SimulationConfig, we grab the list of colors that correspond with the initial states and create
     * an array where the index is the state, and the item in the list is the corresponding color.
     */
    private void getCellColors() {
        SimulationConfig getStateColors = new SimulationConfig();
        List<Integer> states = getStateColors.getCellStates();
        List<Color> colors = getStateColors.getColors();
        for( int i = 0; i < colors.size();i++){
            int index = i;
            if( !cellColors.contains(colors.get(i))){
                cellColors.add(states.get(i), colors.get(i));
            }
        }
    }
}
