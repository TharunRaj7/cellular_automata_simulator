package ca.simulations;

import ca.model.Grids.GridBase;
import java.util.List;
import java.util.Random;

/**
 * This class is the implementation of Spreading of Fire. The rules
 * of Game of Life, according to <a href="https://www2.cs.duke.edu/courses/spring20/compsci308/assign/02_simulation/nifty/shiflet-fire/"
 * this page </a>, which are:
 * <ul>
 *  <li>Any burning cell becomes an empty cell at the next step.</li>
 *  <li>Any empty cells remain empty at the next step.</li>
 *  <li>To determine if a tree will be burning in the next step, you use the probCatch function which determines
 *  the probability of tree in a cell catching fire if a tree in a neighboring cell is on fire.</li>
 * </ul>
 *
 * To run the simulation, call {@link #runOneStep()} inside the
 * animation loop.
 */
public class SpreadingOfFire extends Simulation{
    int EMPTY_CELL = 0;
    int TREE_CELL = 1;
    int BURNING_CELL = 2;
    int rangeMin = 0;
    int rangeMax = 1;

    private double percent;


    public SpreadingOfFire(GridBase grid, List<String> additionalParameters) {
        super(grid);
        try {
            percent = Double.parseDouble(additionalParameters.get(0));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected int determineCellState(int r, int c) {
        if (grid.getCellState(r, c) == BURNING_CELL) {
            return EMPTY_CELL;
        } else if(grid.getCellState(r, c) == TREE_CELL && getNeighborStateNumber(r, c, type, BURNING_CELL) >= 1){
            Random randNum = new Random();
            double randomValue = rangeMin + (rangeMax - rangeMin) * randNum.nextDouble();
            System.out.println(randomValue);
            return (randomValue <= percent) ? BURNING_CELL : TREE_CELL;
        } else if(grid.getCellState(r, c) == TREE_CELL && getNeighborStateNumber(r, c, type, BURNING_CELL) < 1){
            return TREE_CELL;
        }
        return EMPTY_CELL;
    }
}
