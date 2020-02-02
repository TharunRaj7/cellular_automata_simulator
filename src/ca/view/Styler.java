package ca.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.beans.Statement;
import java.lang.reflect.Method;

/**
 * This method and class creates a button that when clicked, executes a certain method based on the handler.
 */

public class Styler {

    public Button createButton(String name, EventHandler<ActionEvent> handler){
        Button button = new Button(name);
        button.setOnAction(handler);
        return button;
    }


}
