package ca.simulations;

import ca.model.Grid;

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
                gridNextGen.updateState(r, c, determineCellState(r, c));
            }
        }
        grid = gridNextGen;
    }

    private int determineCellState(int r, int c) {
        if (grid.getCellState(r, c) == AGENT_1) {
            return checkAgents(r, c, AGENT_1);
        } else if (grid.getCellState(r, c) == AGENT_2) {
            return checkAgents(r, c, AGENT_2);
        }
        return VACANT_CELL;
    }

    /**
     * Takes the number of neighbors and the number of neighbors that are the same. If the percent calculated is
     * greater than or equal to the desired percent, the agent remains. Otherwise, it is moved to a vacant cell.
     * @param r
     * @param c
     * @param agent
     * @return
     */
    private int checkAgents(int r, int c, int agent){
        int sameNeighbors = getNeighborStateNumber(r, c, mode, agent);
        int numNeighbors = grid.getAllNeighbors(r,c).size();
        int isSatisfied = sameNeighbors/numNeighbors;
        return (isSatisfied >= percent) ? agent : moveToAnyVacant(agent, VACANT_CELL);
    }

    /**
     * This method goes through the grid, looking for the first cell that is marked vacant. If it is vacant, then
     * the state becomes the agent passed in and the state of the cell that the agent was previously at becomes
     * vacant. This is only called if the agent is not satisfied in its original cell.
     * @param agent
     * @param VACANT_CELL
     * @return
     */
    private int moveToAnyVacant(int agent, int VACANT_CELL){
        for (int r = 0; r < grid.getNumOfRows(); r++) {
            for (int c = 0; c < grid.getNumOfColumns(); c++) {
              if(grid.getCellState(r, c) == VACANT_CELL){
                  grid.setCellState(r, c, agent);
              }
            }
        }
        return VACANT_CELL;
    }
}
