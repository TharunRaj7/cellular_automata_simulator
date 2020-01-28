package cellsociety;


import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;


public class GridPane {

    private static GridPane myGrid;

    private static GridPane createGrid(){
        //create gridpane based on size read in from XML file
        GridPane myGrid = new GridPane();
        //for each column create x amount of rows
        myGrid.getColumnConstraints().add(new ColumnConstraints(100));
        return myGrid;
    }

    public static Node getGrid(){
        return myGrid;
    }
}
