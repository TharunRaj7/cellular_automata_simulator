
package ca.view;

import ca.controller.Controller;
import ca.controller.SimulationConfig;
import ca.controller.SimulationType;
import ca.model.Grid;
import ca.simulations.GameOfLife;
import ca.simulations.Simulation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

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
    private Controller controller;
    private Stage stage;
    private GridPane gridPane;
    private Simulation simulation;
    private List<Color> colors;


    /**
     * This method creates a new instance of the file reader as well as the scene creation.
     * Here, the file is retrieved and the simulation is begun.
     * Additionally, the animation is started and a game loop is associated with the scene.
     * @param stage
     */
    @Override
    public void start (Stage stage) {
        XMLReader retrieveFile = new XMLReader();
        retrieveFile.getFile(stage);
        readVariablesFromXML(retrieveFile);

        Scene myGameScene = setupSimulation(SIZE);
        stage.setScene(myGameScene);
        stage.setTitle(TITLE);
        stage.show();

        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), event -> step());
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void readVariablesFromXML(XMLReader retrieveFile) {
        simulationConfig = new SimulationConfig(retrieveFile.getXMLfile());
        controller = new Controller();
        grid = new Grid(simulationConfig.getGridWidth(), simulationConfig.getGridHeight(), simulationConfig.getCellStates());
        gridPane = new GridPane();
        fillGrid();
        colors = simulationConfig.getColors();
        createSimulationInstance(simulationConfig.getSimulationType());
    }

    private void createSimulationInstance(SimulationType simulationType) {
        switch (simulationType) {
            case GameOfLife:
                simulation = new GameOfLife(grid);
                break;
            default:
                simulation = null;
        }
    }

    /**
     * This method sets up the simulation and creates all the buttons for the simulation. It calls on the Grid class
     * in the model to create the grid.
     * @param size the size of the scene
     * @return Scene
     */
    public Scene setupSimulation(int size) {
        Group root = new Group();
        Styler styler = new Styler();

        Button startButton = styler.createButton("StartCommand", event -> controller.startAnimation());
        Button stopButton = styler.createButton("StopCommand", event -> controller.pauseAnimation());
        Button reloadFileButton = styler.createButton("ReloadCommand", event -> start(stage));
        Button stepButton = styler.createButton("StepCommand", event -> controller.runOneStep());
        TextField num = new TextField();
        num.setPromptText("FillerCommand");
        Button submitButton = styler.createButton("SubmitCommand", event -> controller.setAnimationSpeed(Double.valueOf(num.getText())));

        root.getChildren().addAll(gridPane, startButton, stopButton, reloadFileButton, stepButton, submitButton, num);
        return new Scene(root, size, size, BACKGROUND);
    }

    /**
     * In this method, we will need to call the updateCells method in the other part of the code.
     * This method is executed every time the step button on the user interface is clicked.
     */
    public void step () {
        simulation.runOneStep();
    }


    /**
     * The grid is filled based on the colors received from the XML file.
     */
    private void fillGrid(){
        for(int i = 0; i< grid.getNumOfRows(); i++){
            for(int j = 0; j< grid.getNumOfColumns(); j++){
                Rectangle rectangle = new Rectangle();
                rectangle.setFill(colors.get(grid.getCellState(i, j)));
                gridPane.add(rectangle, j, i);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
