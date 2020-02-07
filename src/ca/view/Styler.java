package ca.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.util.ResourceBundle;

/**
 * This method and class creates a button that when clicked, executes a certain method based on the handler.
 */

public class Styler {

    private static int BUTTON_WIDTH = 85;
    private static int BUTTON_HEIGHT = 10;
    private static int BUTTON_OFFSET = 5;
    private static int TEXTFIELD_WIDTH = 85;
    private static int TEXTFIELD_HEIGHT = 10;
    private static int TEXTFIELD_LOCATION = 4;
    private static int SLIDER_WIDTH = 200;
    private static int SLIDER_HEIGHT = 10;
    private static int SLIDER_OFFSET = 30;

    public Button createButton(String name, EventHandler<ActionEvent> handler, int gridHeight, int numButton, ResourceBundle myResources){
        Button button = new Button();
        button.setText(myResources.getString(name));
        button.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setLayoutX(numButton * (BUTTON_WIDTH + BUTTON_OFFSET));
        button.setLayoutY(gridHeight + BUTTON_OFFSET);
        button.setOnAction(handler);
        return button;
    }

    public TextField styleTextField(String fillerText, TextField num, int gridHeight, ResourceBundle myResources){
        num.setPromptText(myResources.getString(fillerText));
        num.setPrefSize(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
        num.setLayoutX(TEXTFIELD_LOCATION * (BUTTON_WIDTH + BUTTON_OFFSET));
        num.setLayoutY(gridHeight + BUTTON_OFFSET);
        return num;
    }

    public Slider createDiffSlider(int newNum, int gridHeight, int slideNum){
        return createSlider(newNum, gridHeight, slideNum);
    }

    private Slider createSlider(int presetVal, int gridHeight, int slideNum){
        Slider slider = new Slider(4, 30, presetVal);
        slider.valueProperty().addListener((obs, oldval, newVal) ->
                slider.setValue(newVal.intValue()));
        slider.setPrefSize(SLIDER_WIDTH, SLIDER_HEIGHT);
        slider.setLayoutY(gridHeight + BUTTON_HEIGHT + SLIDER_OFFSET * slideNum);
        return slider;
    }

}
