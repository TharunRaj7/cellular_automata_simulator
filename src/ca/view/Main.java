
package ca.view;

import ca.controller.Controller;
import ca.controller.SimulationConfig;
import ca.model.Grid;
import ca.model.Simulation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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

    private SimulationConfig simulationConfig;
    private GridPaneHandler gridPaneHandler;
    private Grid grid;
    private GridPane myGrid;
    private Button startButton;
    private Button stopButton;
    private Button reloadFileButton;
    private Button stepButton;
    private Button submitButton;
    private Controller controller;
    private Simulation simulation;
    private Stage stage;

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

        simulationConfig = new SimulationConfig(retrieveFile.getXMLfile());
        controller = new Controller();

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

    /**
     * This method sets up the simulation and creates all the buttons for the simulation. It calls on the Grid class
     * in the model to create the grid.
     * @param size the size of the scene
     * @return Scene
     */
    public Scene setupSimulation(int size) {
        Group root = new Group();
        grid = new Grid(simulationConfig.getColNum(), simulationConfig.getRowNum(), simulationConfig.getCellStates());
        myGrid = gridPaneHandler.createGrid(simulationConfig.getColNum(), simulationConfig.getRowNum(), simulationConfig.getGridWidth(), simulationConfig.getGridHeight());
        root.getChildren().add(myGrid);
        MyButton button = new MyButton();
        startButton = button.createButton("StartCommand", event -> controller.startAnimation());
        stopButton = button.createButton("StopCommand", event -> controller.pauseAnimation());
        reloadFileButton = button.createButton("ReloadCommand", event -> start(stage));
        stepButton = button.createButton("StepCommand", event -> controller.runOneStep());
        final TextField num = new TextField();
        num.setPromptText("FillerCommand");
        submitButton = button.createButton("SubmitCommand", event -> controller.setAnimationSpeed(Double.valueOf(num.getText())));
        root.getChildren().add(startButton);
        root.getChildren().add(stopButton);
        root.getChildren().add(reloadFileButton);
        root.getChildren().add(stepButton);
        root.getChildren().add(submitButton);
        root.getChildren().add(num);
        Scene scene = new Scene(root, size, size, BACKGROUND);
        return scene;
    }

    /**
     * In this method, we will need to call the updateCells method in the other part of the code.
     * This method is executed every time the step button on the user interface is clicked.
     */
    public void step () {
       grid.updateGrid();
       myGrid = gridPaneHandler.createGrid(simulationConfig.getColNum(), simulationConfig.getRowNum(), simulationConfig.getGridWidth(), simulationConfig.getGridHeight());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
