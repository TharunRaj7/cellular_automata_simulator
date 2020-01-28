package cellsociety;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class createGame {

    private static final Paint BACKGROUND = Color.AZURE;
    private static final int SIZE = 600;
    private static final int XML_file_dimension = 10;


    private static Group root = new Group();
    private static File XMLfile;

    public static Scene setupSimulation() {
        // Slider slider = new Slider(1, 10, 1);
        //root.getChildren().add(slider);
        root.getChildren().add(GridPane.createGrid(XML_file_dimension));
        Scene scene = new Scene(root, SIZE, SIZE, BACKGROUND);
        return scene;
    }

    public static File getFile(Stage stage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"));
        XMLfile = fileChooser.showOpenDialog(stage);
        return XMLfile;
    }

}
