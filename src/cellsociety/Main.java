
package cellsociety;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.util.Duration;



public class Main extends Application {

    private static final String TITLE = "Simulation";
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final int SIZE = 600;
    Step myStepFunction = new Step() {
        @Override
        public void step(double elapsedTime) {
            this.step(elapsedTime);
        }
    };
    private Scene myGameScene;

    @Override
    public void start (Stage stage) {
        getXML.getFile(stage);
        myGameScene = createGame.setupSimulation(SIZE);
        stage.setScene(myGameScene);
        stage.setTitle(TITLE);
        stage.show();
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    public static void step () {
        //call update grid in the backend
            //executes based on button clicked
        System.out.println("hello");
    }

}
