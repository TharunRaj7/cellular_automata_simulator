package visuals;

/**
 * This method and class creates a button that when clicked, executes the step function.
 */

public class Button {

    public static javafx.scene.control.Button createButton(){
        int buttonLocation = createGridPane.returnSize();
        javafx.scene.control.Button myButton = new javafx.scene.control.Button("Step");
        myButton.setLayoutX(buttonLocation - myButton.getWidth());
        myButton.setLayoutY(buttonLocation + 5 + myButton.getHeight());
        myButton.setOnAction(e -> Main.step());
        return myButton;
    }
}
