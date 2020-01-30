package ca.view;


import ca.controller.SimulationConfig;
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

public class createGridPane {

    private static final int GRID_WIDTH = 40;
    private static final int GRID_OFFSET = 5;
    private static int length;



    /**
     * The grid is filled based on the colors received from the XML file.
     * @param myGrid
     * @param dimension
     */
    private void fillGrid(GridPane myGrid, int dimension){
        List<Color> colors = new ArrayList<>(getCellColors());
        for(int i = 0; i< dimension; i++){
            for(int j = 0; j< dimension; j++){
                Color cellColor = colors.get((dimension*i)+j);
                myGrid.add(new Rectangle(GRID_WIDTH,GRID_WIDTH, cellColor), i, j);
            }
        }
    }

    /**
     * From the SimulationConfig, we grab the list of colors that correspond with the initial states.
     * @return the list of colors
     */
    private List<javafx.scene.paint.Color> getCellColors() {
        SimulationConfig colors = new SimulationConfig();
        return colors.getColors();
    }

    /**
     * In order to place the button correctly in the scene, the grid's size is returned and then later called by the
     * Button class. In this way, the button does not overlap the grid.
     * @return the size of the grid
     */
    public static int returnSize(){
        return length*GRID_WIDTH;
    }


}
