package cellsociety;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class createGame {

    private static final Paint BACKGROUND = Color.AZURE;
    private static final int SIZE = 600;
    private static final int XML_file_dimension = 10;

    private static Group root = new Group();

    public static Scene setupSimulation() {
        //create a button
        root.getChildren().add(createGridPane.createGrid(XML_file_dimension));
        Scene scene = new Scene(root, SIZE, SIZE, BACKGROUND);
        return scene;
    }
}
