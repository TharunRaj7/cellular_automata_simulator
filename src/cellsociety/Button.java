package cellsociety;

public class Button {

    private static int buttonLocation;

    public static javafx.scene.control.Button createButton(int size){
        buttonLocation = createGridPane.returnSize();
        javafx.scene.control.Button myButton = new javafx.scene.control.Button("Step");
        myButton.setLayoutX(buttonLocation - myButton.getWidth());
        myButton.setLayoutY(buttonLocation + 5 + myButton.getHeight());
        myButton.setOnAction(e -> Main.step());
        return myButton;
    }

    // how to use the instance?
}
