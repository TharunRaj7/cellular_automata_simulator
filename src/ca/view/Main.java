
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

import java.io.File;
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

    private SimulationConfig simulationConfig;
    private GridPaneHandler gridPaneHandler;
    private Grid grid;
    private GridPane myGrid;
    private Controller controller;
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
//        XMLReader retrieveFile = new XMLReader();
//        retrieveFile.getFile(stage);
//        readVariablesFromXML(retrieveFile);

        readVariablesFromXML();
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

//    private void readVariablesFromXML(XMLReader retrieveFile) {
    private void readVariablesFromXML() {
        simulationConfig = new SimulationConfig(new File("data\\GameOfLife\\GameOfLife1.xml"));
//        simulationConfig = new SimulationConfig(retrieveFile.getXMLfile());
        simulationConfig.readFile();
        controller = new Controller();
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
        grid = new Grid(simulationConfig.getRowNum(), simulationConfig.getColNum(), simulationConfig.getCellStates());
        myGrid = gridPaneHandler.createGrid(simulationConfig.getColNum(), simulationConfig.getRowNum(), simulationConfig.getGridWidth(), simulationConfig.getGridHeight());
        root.getChildren().add(myGrid);
        final TextField num = new TextField();
        Styler styler = new Styler();
        Button startButton = styler.createButton("StartCommand", event -> controller.startAnimation());
        Button stopButton = styler.createButton("StopCommand", event -> controller.pauseAnimation());
        Button reloadFileButton = styler.createButton("ReloadCommand", event -> controller.reStartAnimation());
        Button stepButton = styler.createButton("StepCommand", event -> controller.runOneStep());
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
        myGrid = gridPaneHandler.createGrid(simulationConfig.getColNum(), simulationConfig.getRowNum(), simulationConfig.getGridWidth(), simulationConfig.getGridHeight());

    }


    public static void main(String[] args) {
        launch(args);
    }
}
