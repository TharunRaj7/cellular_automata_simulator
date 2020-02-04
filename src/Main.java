import ca.controller.Controller;
import ca.view.GraphHandler;
import ca.view.SimulationView;
import ca.view.Styler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.chart.Chart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.util.ResourceBundle;

/**
 * This class extends Application and is the Main class of the simulation.
 * The stage is created and the scene is called to be made.
 * The step function updates the cells every time the step button is clicked.
 */

public class Main extends Application {
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final String TITLE = "ca.model.Simulation";

    public static final int SIZE = 600;
    public static final Paint BACKGROUND = Color.AZURE;
    public static final String RESOURCE = "ca/resources";
    public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCE + ".";

    private SimulationView simulationView;
    private GraphHandler graphHandler;
    private Timeline animation;
    private Stage stage;
    private ResourceBundle myResources;
    private Group root;

    /**
     * This method creates a new instance of the file reader as well as the scene creation.
     * Here, the file is retrieved and the simulation is begun.
     * Additionally, the animation is started and a game loop is associated with the scene.
     * @param stage
     */
    @Override
    public void start (Stage stage) {
        simulationView= new SimulationView();
        this.stage = stage;

        Scene scene = setupSimulation();
        stage.setScene(scene);
        stage.setTitle(TITLE);
        stage.show();

        startAnimation();
    }

    /**
     * This method sets up the simulation and creates all the buttons for the simulation. It calls on the Grid class
     * in the model to create the grid.
     * @return Scene
     */
    private Scene setupSimulation() {
        root = new Group();
        TextField num = new TextField();
        Styler styler = new Styler();
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "English");

        GridPane gridPane = simulationView.getCurrentGridPane();
        Controller controller = simulationView.getController();
        Chart lineChart = graphHandler.createGraph(System.currentTimeMillis());

        int buttonHeight = simulationView.getButtonHeight();

        styler.styleTextField("FillerCommand", num,
                buttonHeight, myResources);
        Button startButton = styler.createButton("StartCommand", event -> controller.startAnimation(),
                buttonHeight, 0, myResources);
        Button stopButton = styler.createButton("StopCommand", event -> controller.pauseAnimation(),
                buttonHeight, 1, myResources);
        Button reloadFileButton = styler.createButton("ReloadCommand", event -> reloadFile(),
                buttonHeight, 2, myResources);
        Button stepButton = styler.createButton("StepCommand", event -> step(),
                buttonHeight, 3, myResources);
        Button submitButton = styler.createButton("SubmitCommand", event -> controller.setAnimationSpeed(Double.parseDouble(num.getText())),
                buttonHeight, 5, myResources);

        root.getChildren().addAll(gridPane, startButton, stopButton, reloadFileButton, stepButton, submitButton, num, lineChart);
        return new Scene(root, SIZE, SIZE, BACKGROUND);
    }

    private void reloadFile() {
        simulationView = new SimulationView();
        Scene scene = setupSimulation();
        stage.setScene(scene);
        simulationView.getController().setTimeline(animation);
    }

    /**
     * In this method, we will need to call the updateCells method in the other part of the code.
     * This method is executed every time the step button on the user interface is clicked.
     */
    public void step () {
        System.out.println(animation.getRate());
        root.getChildren().remove(simulationView.getCurrentGridPane());
        simulationView.getSimulation().runOneStep();
        graphHandler.updateGraph(System.currentTimeMillis());
        root.getChildren().addAll(simulationView.getCurrentGridPane());
    }

    public void startAnimation() {
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), event -> step());
        animation = new Timeline();
        animation.setRate(SECOND_DELAY);
        simulationView.getController().setTimeline(animation);
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
