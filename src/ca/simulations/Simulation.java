package ca.simulations;

import ca.model.Cell;
import ca.model.Grid;

import java.util.ArrayList;

public abstract class Simulation {
    Grid grid;

    public Simulation(Grid grid) {
        this.grid = grid;
    }

    /**
     * This method tells the number of neighbors that have a
     * specific state. It can be either all eight neighbors or
     * only NSWE neighbors by passing in different {@code mode}.
     * @param r         an int of the row position of this cell
     * @param c         an int of the col position of this cell
     * @param mode      the number of neighbors chosen, ("EIGHT"/"NSWE")
     * @param state     an int representing the state to find
     * @return          the number of neighbors have {@code state}
     */
    public int getNeighborStateNumber(int r, int c, String mode, int state) {
        ArrayList<Cell> neighbors;
        switch (mode) {
            case "EIGHT":
                neighbors = grid.getAllNeighbors(r, c);
                break;
            case "NSWE":
                neighbors = grid.getNSEWNeigbors(r, c);
                break;
            default:
                neighbors = new ArrayList<>();
        }

        int num = 0;
        for (Cell cell: neighbors) {
            if (cell.getState() == state) {
                num++;
            }
        }

        return num;
    }

    public abstract void runOneStep();
}
