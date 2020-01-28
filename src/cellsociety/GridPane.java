package cellsociety;


import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;

import java.awt.*;


public class GridPane {

    private static final int GRID_WIDTH = 40;

    private static javafx.scene.layout.GridPane myGrid = new javafx.scene.layout.GridPane();

    public static javafx.scene.layout.GridPane createGrid(int dimension){
        for(int i = 0; i < dimension; i++) {
            ColumnConstraints column = new ColumnConstraints(GRID_WIDTH);
            myGrid.getColumnConstraints().add(column);
        }
        for(int i = 0; i < dimension; i++) {
            RowConstraints row = new RowConstraints(GRID_WIDTH);
            myGrid.getRowConstraints().add(row);
        }
        myGrid.setGridLinesVisible(true);
        myGrid.setLayoutX(5);
        myGrid.setLayoutY(5);
        return myGrid;
    }
}
