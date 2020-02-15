package ca.simulations;

import ca.model.Grids.Grid;

/**
 * This class is the implementation of Percolation. The rules
 * of Percolation, according to <a href="https://www2.cs.duke.edu/courses/spring20/compsci308/assign/02_simulation/PercolationCA.pdf"
 * this page </a>, which are:
 * <ul>
 *  <li>Any blocked cell remains blocked indefinitely.</li>
 *  <li>Any percolated cell remains percolated indefinitely.</li>
 *  <li>Any open cell becomes percolated if any of its neighbors are percolated, otherwise it remains open.</li>
 * </ul>
 *
 * To run the simulation, call {@link #runOneStep()} inside the
 * animation loop.
 */
public class Percolation extends Simulation {
    int OPEN = 0;
    int BLOCKED = 1;
    int PERCOLATED = 2;

    /**
     * Creates an instance of grid that can be updated.
     * @param grid
     */
    public Percolation(Grid grid) {
        super(grid);
    }

    @Override
    protected int determineCellState(int r, int c) {
       if (grid.getCellState(r, c) == PERCOLATED) {
            return PERCOLATED;
       } else if(grid.getCellState(r, c) == OPEN){
            int percolatedNeighbors = getNumberOfNeighbors(r, c, type, PERCOLATED);
            return (percolatedNeighbors >= 1) ? PERCOLATED : OPEN;
       }
       return BLOCKED;
    }
}
