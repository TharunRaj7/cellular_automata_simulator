package ca.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ResourceBundle;

/**
 * This method and class creates a button that when clicked, executes a certain method based on the handler. It also
 * creates sliders change the number of columns and number of rows of the grid.
 */

public class Styler {

    public final static int BUTTON_WIDTH = 85;
    public final static int BUTTON_HEIGHT = 10;
    public final static int BUTTON_OFFSET = 5;
    public final static int TEXTFIELD_WIDTH = 85;
    public final static int TEXTFIELD_HEIGHT = 10;
    public final static int TEXTFIELD_LOCATION = 4;
    public final static int SLIDER_WIDTH = 200;
    public final static int SLIDER_HEIGHT = 10;
    public final static int SLIDER_OFFSET = 30;
    public final static int SLIDER_MIN = 4;
    public final static int SLIDER_MAX = 50;

    public Slider rowSlider;
    public Slider colSlider;

    /**
     * Creates a UI button that is shown on the scene and when is clicked, executes an action.
     * @param name - the label on the button
     * @param handler - the method or action that is called once the button is clicked
     * @param gridHeight - used for button placement
     * @param numButton - used for button placement
     * @param myResources - used to convert the text that is stored in the resources folder to be displayed
     * @return - a button
     */
    public Button createButton(String name, EventHandler<ActionEvent> handler, int gridHeight, int numButton, ResourceBundle myResources){
        Button button = new Button();
        button.setText(myResources.getString(name));
        button.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setLayoutX(numButton * (BUTTON_WIDTH + BUTTON_OFFSET));
        button.setLayoutY(gridHeight + BUTTON_OFFSET);
        button.setOnAction(handler);
        return button;
    }

    /**
     * Creates a text field that i shown in the scene and takes in an input to change a parameter
     * @param fillerText - the placeholder text in the textfield
     * @param num - used for textfield placement
     * @param gridHeight - used for textfield placement
     * @param myResources a slider that relates to the number of rows in a grid so that it can be added to the root of a scene
     * @return - a textfield
     */
    public TextField styleTextField(String fillerText, TextField num, int gridHeight, ResourceBundle myResources){
        num.setPromptText(myResources.getString(fillerText));
        num.setPrefSize(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
        num.setLayoutX(TEXTFIELD_LOCATION * (BUTTON_WIDTH + BUTTON_OFFSET));
        num.setLayoutY(gridHeight + BUTTON_OFFSET);
        return num;
    }

    /**
     * Creates a slider that when adjusted, updates the number or rows or columns in the grid.
     * @param presetVal - the number of rows or columns that are already on the grid
     * @param gridHeight - used for slider placement
     * @param slideNum - used for slider placement
     * @param myResources a slider that relates to the number of rows in a grid so that it can be added to the root of a scene
     * @param label - slider label
     * @return
     */
    public GridPane createSlider(int presetVal, int gridHeight, int slideNum, ResourceBundle myResources, String label){
        Slider slider = new Slider(SLIDER_MIN, SLIDER_MAX, presetVal);
        if( slideNum == 1){
            rowSlider = slider;
        } else{
            colSlider = slider;
        }
        slider.valueProperty().addListener((obs, oldval, newVal) ->
                slider.setValue(newVal.intValue()));
        slider.setPrefSize(SLIDER_WIDTH, SLIDER_HEIGHT);
        GridPane grid = createSliderLayout(slider, myResources, label);
        grid.setLayoutY(gridHeight + BUTTON_HEIGHT + SLIDER_OFFSET * slideNum);
        return grid;
    }

    private GridPane createSliderLayout(Slider slider, ResourceBundle myResources, String label){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(70);
        Label sliderLabel = new Label(myResources.getString(label));
        Label currentVal = new Label(Double.toString(slider.getValue()));
        GridPane.setConstraints(sliderLabel, 0, 1);
        grid.getChildren().add(sliderLabel);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                currentVal.setText(String.format("%.2f", new_val));
            }
        });
        GridPane.setConstraints(slider, 1, 1);
        grid.getChildren().add(slider);
        GridPane.setConstraints(currentVal, 2, 1);
        grid.getChildren().add(currentVal);
        return grid;
    }

    /**
     *
     * @return a slider that relates to the number of rows in a grid so that it can be added to the root of a scene
     */
    public Slider getRowSlider(){
        return rowSlider;
    }

    /**
     *
     * @return a slider that relates to the number of columns in a grid so that it can be added to the root of a scene
     */
    public Slider getColSlider(){
        return colSlider;
    }

}
