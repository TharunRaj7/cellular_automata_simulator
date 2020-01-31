package ca.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.beans.Statement;
import java.lang.reflect.Method;

/**
 * This method and class creates a button that when clicked, executes the step function.
 */

public class MyButton {

    public Button createButton(String name, EventHandler<ActionEvent> handler){
        Button button = new Button(name);
        button.setOnAction(handler);
        return button;
    }


}
