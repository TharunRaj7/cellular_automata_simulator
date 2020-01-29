package cellsociety;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class getXML {

    private static File XMLfile;

    public static File getFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"));
        XMLfile = fileChooser.showOpenDialog(stage);
        return XMLfile;
    }

    public static File returnXML(){
        //need to send the XMLfile to the backend
        return XMLfile;
    }
}
