
package ca.view;

import ca.controller.SimulationConfig;
import ca.model.Grid;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.util.Duration;

/**
 * This class extends Application and is the Main class of the simulation.
 * The stage is created and the scene is called to be made.
 * The step function updates the cells every time the step button is clicked.
 */

public class Main extends Application {

    private static final String TITLE = "ca.model.Simulation";
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final int SIZE = 600;
    private static final Paint BACKGROUND = Color.AZURE;
    private static final int XML_file_dimension = 10;

    private int XML_dimension;

    private SimulationConfig simulationConfig;
    private Grid grid;
    private MyButton startMyButton;

    /**
     * This method creates a new instance of the file reader as well as the scene creation.
     * Here, the file is retrieved and the simulation is begun.
     * Additionally, the animation is started and a game loop is associated with the scene.
     * @param stage
     */
    @Override
    public void start (Stage stage) {
        getXML retrieveFile = new getXML();
        retrieveFile.getFile(stage);
        Scene myGameScene = setupSimulation(SIZE);
        stage.setScene(myGameScene);
        stage.setTitle(TITLE);
        stage.show();
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();   
    }

    /**
     * Right now, this method uses a magic number for the XML_file_dimension which is the
     * dimension of the grid. This value will need to be read in from the XML file.
     * @param size the size of the scene
     * @return Scene
     */
    public Scene setupSimulation(int size) {
        Group root = new Group();
        grid = new Grid(simulationConfig.getGridWidth(), simulationConfig.getGridHeight());
        root.getChildren().add(grid.getGrid());

        startMyButton = new MyButton();
        root.getChildren().add(new Button());
        Scene scene = new Scene(root, size, size, BACKGROUND);
        return scene;
    }


    /**
     * In this method, we will need to call the updateCells method in the other part of the code.
     * This method is executed every time the step button on the user interface is clicked.
     */
    public static void step () {

        System.out.println("hello");
    }


        myGrid.setGridLinesVisible(true);
    fillGrid(myGrid, dimension);
        myGrid.setLayoutX(GRID_OFFSET);
        myGrid.setLayoutY(GRID_OFFSET);
}
