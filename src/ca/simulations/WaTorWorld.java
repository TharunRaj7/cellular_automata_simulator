package ca.simulations;


import ca.model.Cell;
import ca.model.Grid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is the implementation of WatorWorld. The rules
 * of WaTorWorld, according to <a href=https://www2.cs.duke.edu/courses/spring20/compsci308/assign/02_simulation/nifty/scott-wator-world/WatorWorld.htm"
 * this page </a>, which are:
 * <ul>
 *  <li>A Fish moves to any adjacent cell(NSEW only) by random on each turn. A fish breeds a new fish into a random
 *  empty adjacent cell if there are any.</li>
 *  <li>A shark eats a fish at random if there are fishes in adjacent cells. If there aren't any fishes in adjacent
 *  cells, the shark moves in the same manner as the fish to an empty adjacent cell.</li>
 *  <li>Any unsatisfied agent can more to any vacant cell.</li>
 * </ul>
 *
 * To run the simulation, call {@link #runOneStep()} inside the
 * animation loop.
 */
public class WaTorWorld extends Simulation {
    // 2 types of objects
    private final int FISH = 1;
    private final int SHARK = 2;
    private Map<Cell, Integer> turnsMade;

    private int numOfTurnsFish;
    private int numOfTurnsShark;

    public WaTorWorld (Grid grid, int numOfTurnsFish, int numOfTurnsShark){
        super(grid);
        this.numOfTurnsFish = numOfTurnsFish;
        this.numOfTurnsShark = numOfTurnsShark;
        turnsMade = new HashMap<>();
        populateTurnsMade();    //Sets up the data structure required to keep track of the number of turns
    }

    private void populateTurnsMade() {
        List<Cell> allCells = grid.getAllCells();
        for (Cell cell : allCells){
            turnsMade.putIfAbsent(cell, 0);
        }
    }

    @Override
    public void runOneStep() {
        System.out.println("test");
    }
}
