package ca.controller;

/**
 * This class handles user inputs.
 */

import ca.model.Simulation;
import javafx.animation.Animation;
import javafx.application.Application;
import javafx.scene.input.KeyCode;

public class Controller {
    Animation animation;
    Simulation simulation;

    public Controller() {
    }

    public Controller(Animation animation) {
        this.animation = animation;
    }

    public void setTimeline(Animation timeline) {
        this.animation = timeline;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public void onHandleKeyInput(KeyCode keyCode) {
        switch (keyCode) {
            //TODO: fill in the keyCode options
            default:
                break;
        }
    }


    public void setAnimationSpeed(double speed) {
        animation.setRate(speed);
    }

    public void pauseAnimation() {
        animation.pause();
    }

    public void startAnimation() {
        animation.play();
    }

    public void reStartAnimation() {
        animation.playFromStart();
    }

    public void runOneStep() {
        simulation.runOneStep();
    }

}
