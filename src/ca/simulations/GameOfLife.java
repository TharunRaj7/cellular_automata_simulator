package ca.simulations;

import ca.model.Grid;
import ca.model.Simulation;

/**
 * This class is the implementation of Game of Life. The rules
 * of Game of Life, according to <a href="https://en.wikipedia.org/wiki/Conway's_Game_of_Life"
 * this Wikipedia page </a>, which are:
 * <ul>
 *  <li>Any live cell with two or three neighbors survives.</li>
 *  <li>Any dead cell with three live neighbors becomes a live
 *  cell.</li>
 *  <li>All other live cells die in the next generation.
 *  Similarly, all other dead cells stay dead.</li>
 * </ul>
 *
 * To run the simulation, call {@link #runOneStep()} inside the
 * animation loop.
 */
public class GameOfLife extends Simulation {
    public GameOfLife(Grid grid) {
        super(grid);
    }

    @Override
    public void runOneStep() {

    }
}
