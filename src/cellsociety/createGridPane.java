package cellsociety;


import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class createGridPane {

    private static final int GRID_WIDTH = 40;

    private static GridPane myGrid = new GridPane();
    private static int length = 0;

    public static GridPane createGrid(int dimension) {
        length = dimension;
        for(int i = 0; i < dimension; i++) {
            ColumnConstraints column = new ColumnConstraints(GRID_WIDTH);
            myGrid.getColumnConstraints().add(column);
        }
        for(int i = 0; i < dimension; i++) {
            RowConstraints row = new RowConstraints(GRID_WIDTH);
            myGrid.getRowConstraints().add(row);
        }
        myGrid.setGridLinesVisible(true);
        fillGridPane.fillGrid(myGrid);
        myGrid.setLayoutX(5);
        myGrid.setLayoutY(5);
        return myGrid;
    }

    public static int returnSize(){
        return length*GRID_WIDTH;
    }
}
