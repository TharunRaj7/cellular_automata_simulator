package test;

import ca.view.SimulationView;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class GeneralTest extends Application {

    public void start (Stage stage) {
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(300.0, 50.0,
                450.0, 150.0,
                300.0, 250.0,
                150.0, 150.0);

        GridPane gridPane = new GridPane();
        gridPane.add(new Polygon(), 0, 0);
        gridPane.add(new Polygon(), 0, 1);
        gridPane.add(polygon, 1, 0);

        Group root = new Group();
//        root.getChildren().addAll(gridPane);
        root.getChildren().addAll(polygon);
        Scene scene = new Scene(root, 600, 300);

        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
