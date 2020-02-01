package ca.model;

import ca.controller.SimulationConfig;
import ca.model.Grid;

import java.io.File;

public abstract class Simulation {
    Grid gameGrid;

    public Simulation(String simulationType, Grid grid) {
        File fileName = new File(simulationType);
        gameGrid = grid;
    }

    public abstract void runOneStep();

}
