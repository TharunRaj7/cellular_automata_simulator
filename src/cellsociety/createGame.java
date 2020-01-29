package cellsociety;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class createGame {

    private static final Paint BACKGROUND = Color.AZURE;
    public static int XML_file_dimension = 10;

    private static Group root = new Group();

    public static Scene setupSimulation(int size) {
        root.getChildren().add(createGridPane.createGrid(XML_file_dimension));
        root.getChildren().add(Button.createButton(size));
        Scene scene = new Scene(root, size, size, BACKGROUND);
        return scene;
    }
}
