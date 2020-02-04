package ca.simulations;

import ca.model.Grid;

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
    int DEAD_CELL = 0;
    int LIVE_CELL = 1;

    public GameOfLife(Grid grid) {
        super(grid);
    }

    @Override
    protected int determineCellState(int r, int c) {
        if (grid.getCellState(r, c) == LIVE_CELL) {
            int liveNeighbors = getNeighborStateNumber(r, c, mode, LIVE_CELL);
            return (liveNeighbors == 2 || liveNeighbors == 3) ? LIVE_CELL : DEAD_CELL;
        } else if (getNeighborStateNumber(r, c, mode, LIVE_CELL) == 3) {
            return LIVE_CELL;
        }

        return DEAD_CELL;
    }
}
