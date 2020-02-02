package ca.view;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

/**
 * This class opens a file chooser at the start of the simulation.
 * A file chooser is initiated and presented and the only acceptable type of file is of
 * the XML format.
 */
public class XMLReader {

    private File XMLfile;

    public File getFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"));
        XMLfile = fileChooser.showOpenDialog(stage);
        return XMLfile;
    }

    /**
     * Here, the file that is chosen in the above method is returned. In this way, the backend is able to retrieve
     * the file and read it.
     * @return File
     */
    public File getXMLfile(){
        return XMLfile;
    }
}
