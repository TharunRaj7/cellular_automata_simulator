package cellsociety;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class createGame {

    private static final Paint BACKGROUND = Color.AZURE;
    private static final int SIZE = 600;

    private static Group root = new Group();
    private static File XMLfile;

    public static Scene setupSimulation() {
        // create an grid and add to root
       // root.getChildren().add(GridPane.createGrid());
        // create a slider and add to root
        Scene scene = new Scene(root, SIZE, SIZE, BACKGROUND);
        return scene;
    }

    public static File getFile(Stage stage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File XMLfile = fileChooser.showOpenDialog(stage);
        return XMLfile;
    }

}
