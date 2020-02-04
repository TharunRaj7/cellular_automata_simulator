package ca.simulations;

import ca.model.Cell;
import ca.model.Grid;

import java.util.List;
import java.util.Random;

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

    public Segregation(Grid grid) {
        super(grid);
    }


    /**
     * The logic that is run at each step of the code.
     * Takes the number of neighbors and the number of neighbors that are the same. If the percent calculated is
     * greater than or equal to the desired percent, the agent remains. Otherwise, it is moved to a vacant cell.
     */
    @Override
    public void runOneStep() {
        for (int r = 0; r < grid.getNumOfRows(); r++) {
            for (int c = 0; c < grid.getNumOfColumns(); c++) {
                int sameNeighbors = getNeighborStateNumber(r, c, mode, grid.getCellState(r, c));
                int numNeighbors = grid.getAllNeighbors(r,c).size();
                int isSatisfied = sameNeighbors/numNeighbors;
                if ((grid.getCellState(r,c) != VACANT_CELL) && (isSatisfied <= percent)){
                    moveToVacant(r,c, grid.getCellState(r, c));
                }
            }
        }
    }

    /**
     * Takes all of the current vacant cells and moves the unsatisfied agent to one of them.
     * @param r
     * @param c
     * @param agent
     */
    public void moveToVacant(int r, int c, int agent){
        List<Cell> vacantCells = getCellOfState(VACANT_CELL);
        Cell currentCell = grid.getCell(r,c);
        Random rand = new Random();
        Cell newPosition = vacantCells.get(rand.nextInt(vacantCells.size()));
        currentCell.setState(VACANT_CELL);
        newPosition.setState(agent);
    }

    @Override
    int determineCellState(int r, int c){
        return VACANT_CELL;
    }
}
