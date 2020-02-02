package ca.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * This method and class creates a button that when clicked, executes a certain method based on the handler.
 */

public class Styler {

    private static int BUTTON_WIDTH = 75;
    private static int BUTTON_HEIGHT = 10;
    private static int BUTTON_OFFSET = 5;
    private static int TEXTFIELD_LOCATION = 4;

    public Button createButton(String name, EventHandler<ActionEvent> handler, int gridHeight, int numButton){
        Button button = new Button(name);
        button.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setLayoutX(numButton * (BUTTON_WIDTH + BUTTON_OFFSET));
        button.setLayoutY(gridHeight + BUTTON_OFFSET);
        button.setOnAction(handler);
        return button;
    }

    public TextField setLocation(TextField num, int gridHeight){
        num.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        num.setLayoutX(TEXTFIELD_LOCATION * (BUTTON_WIDTH + BUTTON_OFFSET));
        num.setLayoutY(gridHeight + BUTTON_OFFSET);
        return num;
    }


}
