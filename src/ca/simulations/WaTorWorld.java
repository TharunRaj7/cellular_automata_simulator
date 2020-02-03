package ca.simulations;


import ca.model.Cell;
import ca.model.Grid;
import ca.model.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * This class is the implementation of WatorWorld. The rules
 * of WaTorWorld, according to <a href=https://www2.cs.duke.edu/courses/spring20/compsci308/assign/02_simulation/nifty/scott-wator-world/WatorWorld.htm"
 * this page </a>, which are:
 * <ul>
 *  <li>A Fish moves to any adjacent cell(NSEW only) by random on each turn. If a fish moves a specified number of times,
 *  it breeds a new fish into a random empty adjacent cell if there are any.</li>
 *  <li>A shark eats a fish at random if there are fishes in adjacent cells. If there aren't any fishes in adjacent
 *  cells, the shark moves in the same manner as the fish to an empty adjacent cell.</li>
 *  <li> If a shark moves a specified number of times it breeds a new fish into a random empty
 *  adjacent cell if there are any.</li>
 * </ul>
 *
 * To run the simulation, call {@link #runOneStep()} inside the
 * animation loop.
 *
 * Note: the same fish/shark might get accessed again on the same step if they move to cells that will get called later.
 * To fix this, maybe have a list of cells that objects moved into and don't access them if they are called on future
 * access calls.
 */
public class WaTorWorld extends Simulation {
    // 3 types of states on cells
    private final int EMPTY = 0;
    private final int FISH = 1;
    private final int SHARK = 2;
    private Map<Cell, Pair> turnsMade;

    private int numOfTurnsFish;
    private int numOfTurnsShark;

    public WaTorWorld (Grid grid, int numOfTurnsFish, int numOfTurnsShark){
        super(grid);
        this.numOfTurnsFish = numOfTurnsFish;
        this.numOfTurnsShark = numOfTurnsShark;
        turnsMade = new HashMap<>();
        populateTurnsMade();    //Sets up the data structure required to keep track of the number of turns
    }

    /**
     * Design to keep track of different objects and their number of steps:
     * - Implement a hashmap with cells being the key and a pair object containing the type of object:
     * (EMPTY = 0, FISH = 1, SHARK = 2) and the second value in the pair is the number of steps the object has taken.
     */
    private void populateTurnsMade() {
        List<Cell> allCells = grid.getAllCells();
        for (Cell cell : allCells){
            if (cell.getState() == FISH){
                Pair temp = new Pair(FISH, 0);
                turnsMade.putIfAbsent(cell, temp);
            }
            else if (cell.getState() == SHARK){
                Pair temp = new Pair(SHARK, 0);
                turnsMade.putIfAbsent(cell, temp);
            }else {
                Pair temp = new Pair(EMPTY, 0);
                turnsMade.putIfAbsent(cell, temp);
            }

        }
    }

    /**
     * Logic to run at each step of the simulation
     */
    @Override
    public void runOneStep() {
        for (int r = 0; r < grid.getNumOfRows(); r++) {
            for (int c = 0; c < grid.getNumOfColumns(); c++) {
                if (grid.getCellState(r,c) == FISH){
                    runFishHandler(r,c);
                }
                else if (grid.getCellState(r,c) == SHARK){
                    runSharkHandler(r,c);
                }
            }
        }
    }

    private void runFishHandler(int r, int c) {
        moveAndBreedCombo(r,c, FISH);
    }


    private void runSharkHandler(int r, int c) {
        // Logic to eat fish
        int adjacentFishCells = getNeighborStateNumber(r, c, "NSEW", FISH);
        if (adjacentFishCells > 0){
            List<Cell> fishCells = getNeighboringCellsOfState(r,c, FISH);
            Cell currentCell = grid.getCell(r,c);
            eatingProtocol(currentCell, fishCells);
        }else{
            moveAndBreedCombo(r,c,SHARK);
        }
    }

    private void moveAndBreedCombo(int r, int c, int type) {
        int adjacentEmptyCells = getNeighborStateNumber(r,c,"NSEW", 0);
        int numOfTurns = (type == FISH) ? FISH : SHARK;
        if (adjacentEmptyCells > 0){
            List<Cell> emptyCells = getNeighboringCellsOfState(r,c,0);
            Cell newCell = movementProtocol(grid.getCell(r,c) , emptyCells); //returns the new cell the fish moved to

            if (turnsMade.get(newCell).getCol() >= numOfTurns){
                Pair temp = grid.getPairGivenCell(newCell);
                emptyCells = getNeighboringCellsOfState(temp.getRow(), temp.getCol(), 0);
                breedingProtocol(newCell, emptyCells);
            }
        }
    }

    private void eatingProtocol(Cell currentCell, List<Cell> fishCells) {
        Random rand = new Random();
        Cell fishToEat = fishCells.get(rand.nextInt(fishCells.size())); //Move to a random empty cell

        //Destroy fish
        fishToEat.setState(EMPTY);
        this.turnsMade.get(fishToEat).setRow(EMPTY); this.turnsMade.get(fishToEat).setCol(0);
    }

    private Cell movementProtocol(Cell currentCell, List<Cell> emptyCells) {
        int objectType = turnsMade.get(currentCell).getRow();
        int turnsMade = this.turnsMade.get(currentCell).getCol();
        Random rand = new Random();
        Cell newPosition = emptyCells.get(rand.nextInt(emptyCells.size())); //Move to a random empty cell

        // Changing states to reflect movement
        currentCell.setState(EMPTY);
        newPosition.setState(objectType);

        // Updating turnsMade to match shifts in position
        this.turnsMade.get(currentCell).setRow(EMPTY); this.turnsMade.get(currentCell).setCol(0);
        this.turnsMade.get(newPosition).setRow(objectType);  this.turnsMade.get(newPosition).setCol(++turnsMade);
        return newPosition;
    }

    private void breedingProtocol(Cell cell, List<Cell> emptyCells) {
        int objectType = turnsMade.get(cell).getRow();
        Random rand = new Random();
        Cell newPosition = emptyCells.get(rand.nextInt(emptyCells.size())); //Move to a random empty cell

        // Changing states to reflect breeding
        newPosition.setState(objectType);

        // Updating turnsMade to match new object that was bred
        this.turnsMade.get(cell).setCol(0); // reset turnsMade to 0
        this.turnsMade.get(newPosition).setRow(objectType); this.turnsMade.get(newPosition).setCol(0);


    }


}
