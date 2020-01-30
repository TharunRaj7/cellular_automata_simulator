package ca.view;

import javafx.scene.control.Button;

/**
 * This method and class creates a button that when clicked, executes the step function.
 */

public class MyButton {
    Button button;

    public MyButton(String name){
        int buttonLocation = createGridPane.returnSize();
        button = new Button(name);
        button.setLayoutX(buttonLocation - button.getWidth());
        button.setLayoutY(buttonLocation + 5 + button.getHeight());
        button.setOnAction(e -> Main.step());
    }


}
