package ca.simulations;

import ca.model.Grid;
import ca.model.Simulation;

/**
 * This class is the implementation of Segregation. The rules
 * of Segregation, according to <a href="https://www2.cs.duke.edu/courses/spring20/compsci308/assign/02_simulation/nifty/mccown-schelling-model-segregation/"
 * this page </a>, which are:
 * <ul>
 *  <li>Any agent cell with x% or greater similar neighbors is satisfied.</li>
 *  <li>Any agent cell with less than x% similar neighbors is unsatisfied.</li>
 *  <li>Any unsatisfied agent can more to any vacant cell.</li>
 * </ul>
 *
 * To run the simulation, call {@link #runOneStep()} inside the
 * animation loop.
 */

public class Segregation extends Simulation {
    int VACANT_CELL = 0;
    int AGENT_1 = 1;
    int AGENT_2 = 2;
    double percent = 0.3;

    String mode = "EIGHT";

    public Segregation(Grid grid) {
        super(grid);
    }

    @Override
    public void runOneStep() {
        Grid gridNextGen = new Grid(grid);

        for (int r = 0; r < grid.getNumOfRows(); r++) {
            for (int c = 0; c < grid.getNumOfColumns(); c++) {
                gridNextGen.setState(r, c, determineCellState(r, c));
            }
        }
        grid = gridNextGen;
    }

    private int determineCellState(int r, int c) {
        if (grid.getState(r, c) == AGENT_1) {
            return checkAgents(r, c, AGENT_1);
        } else if (grid.getState(r, c) == AGENT_2) {
            return checkAgents(r, c, AGENT_2);
        }
        return VACANT_CELL;
    }

    private int checkAgents(int r, int c, int agent){
        int sameNeighbors = getNeighborStateNumber(r, c, mode, agent);
        int numNeighbors = grid.getAllNeighbors(r,c).size();
        int isSatisfied = sameNeighbors/numNeighbors;
        //if it has a satisfying number of same neighbors remain, otherwise move to a vacant cell
        return (isSatisfied >= percent) ? agent : moveToAnyVacant(agent);
    }
}
