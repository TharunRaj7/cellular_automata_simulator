package cellsociety;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class createGame {

    private static final Paint BACKGROUND = Color.AZURE;
    private static final int SIZE = 600;

    private static Group root = new Group();


    public static Scene setupSimulation(){
        // create an grid and add to root
        root.getChildren().add(GridPane.createGrid());
        // create a slider and add to root
        Scene scene = new Scene(root, SIZE, SIZE, BACKGROUND);
        //select a file to upload
        //read the XML file

        return scene;
    }
}
