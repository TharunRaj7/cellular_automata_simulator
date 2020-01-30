package cellsociety;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * This class creates the instance of the game, or simulation.
 * Here, a root is created and the elements are added to the root.
 */
public class createGame {

    private static final Paint BACKGROUND = Color.AZURE;
    private static final int XML_file_dimension = 10;
    private int XML_dimension;
    /**
     * Right now, this method uses a magic number for the XML_file_dimension which is the
     * dimension of the grid. This value will need to be read in from the XML file.
     * @param size
     * @return Scene
     */
    public Scene setupSimulation(int size) {
        Group root = new Group();
        root.getChildren().add(createGridPane.createGrid(XML_file_dimension));
        root.getChildren().add(Button.createButton(size));
        Scene scene = new Scene(root, size, size, BACKGROUND);
        return scene;
    }

    /**
     * Must finish by getting the XML dimension from the SimulationConfig
     * @return int
     */
    private int getGridDimension(){
        return XML_dimension;
    }


}
