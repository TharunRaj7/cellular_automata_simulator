package ca.simulations;

import ca.model.Grids.Grid;

import java.util.*;

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
    public static final int VACANT_CELL = 0;
    public static final int AGENT_1 = 1;
    public static final int AGENT_2 = 2;
    public static final int PLACE_HOLDER = 3;

    private double percent;
    private int vacantNumber;
    private List<Integer> needRelocation;

    /**
     * Calculates the number of vacant cells and initializes a an arraylist of current agents that need to be relocated.
     * Reads in the percent value from the XML file.
     * @param grid
     * @param additionalParameters
     */
    public Segregation(Grid grid, List<String> additionalParameters) {
        super(grid);
        vacantNumber = calcVacantNumber();
        needRelocation = new ArrayList<>();

        try {
            percent = Double.parseDouble(additionalParameters.get(0));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private int calcVacantNumber() {
        for (int i = 0; i < grid.getNumOfRows(); i++) {
            for (int j = 0; j < grid.getNumOfColumns(); j++) {
                if (grid.getCellState(i, j) == VACANT_CELL) {
                    vacantNumber ++;
                }
            }
        }
        return vacantNumber;
    }

    @Override
    protected Grid additionalActions(Grid gridNextGen) {
        for (int i = 0; i < gridNextGen.getNumOfRows(); i++) {
            for (int j = 0; j < gridNextGen.getNumOfColumns(); j++) {
                if (gridNextGen.getCellState(i, j) == PLACE_HOLDER) {
                    if (needRelocation.size() > 0) {
                        int random = new Random().nextInt(needRelocation.size());
                        gridNextGen.setCellState(i, j, needRelocation.get(random));
                        needRelocation.remove(random);
                    } else {
                        gridNextGen.setCellState(i, j, VACANT_CELL);
                    }
                }
            }
        }
        return gridNextGen;
    }

    @Override
    protected int determineCellState(int r, int c) {
        int currentState = grid.getCellState(r, c);
        {
            if (currentState == VACANT_CELL) {
                return PLACE_HOLDER;
            }

            int satisfiedAgent = getNumberOfNeighbors(r, c, type, currentState);
            int nonVacantAgent = getNumberOfNeighbors(r, c, type, AGENT_1) + getNumberOfNeighbors(r, c, type, AGENT_2);
            if ((double) satisfiedAgent / nonVacantAgent < percent && needRelocation.size() < vacantNumber) {
                needRelocation.add(currentState);
                return VACANT_CELL;
            }
            return currentState;
        }
    }
}
