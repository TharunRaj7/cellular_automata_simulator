package ca.controller;

/**
 * This class handles possible user inputs, which include both
 * button-click reactions and keyboard inputs. Both
 * {@link javafx.animation.Animation} and
 * {@link ca.simulations.Simulation} needs to be passed in.
 *
 * @author Cady Zhou
 * @version 1.1
 * @since 1.1
 * @see javafx.animation.Animation
 */

import ca.simulations.Simulation;
import javafx.animation.Animation;
import javafx.scene.input.KeyCode;

public class Controller {
    Animation animation;
    Simulation simulation;

    /**
     * Creates an empty instance of this {@code Controller}
     */
    public Controller() {

    }
    /**
     * Creates a new instance of this {@code Controller}
     * @param animation an {@link Animation} created in the project's main
     */
    public Controller(Animation animation) {
        this.animation = animation;
    }

    /**
     * Passes in the {@link Animation} instance used in the project's main
     * @param timeline  an {@link Animation} created in the project's main
     */
    public void setTimeline(Animation timeline) {
        this.animation = timeline;
    }

    /**
     * Passes in the {@link Simulation} instance that is running
     * @param simulation    a {@link Simulation} instance that is running
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Reacts to users's inputs
     * @param keyCode   a {@link KeyCode} that user inputs
     */
    public void onHandleKeyInput(KeyCode keyCode) {
        switch (keyCode) {
            // TODO: fill in the keyCode options
            default:
                break;
        }
    }

    /**
     * Sets the speed of the simulation
     * @param speed     a double of animation speed, the original value is 0.016
     */
    public void setAnimationSpeed(double speed) {
        animation.setRate(speed);
    }

    /**
     * Pauses the simulation
     */
    public void pauseAnimation() {
        animation.pause();
    }

    /**
     * Starts the simulation from where it ends
     */
    public void startAnimation() {
        animation.play();
    }
}
