package ca.simulations;

import ca.model.Cell;
import ca.model.Grid;

import java.util.ArrayList;
import java.util.List;

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
                neighbors = grid.getNSEWNeighbors(r, c);
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

    public List<Cell> getCellOfState(int state) {
        List<Cell> cells = new ArrayList<>();
        for (int r = 0; r < grid.getNumOfRows(); r++) {
            for (int c = 0; c < grid.getNumOfColumns(); c++) {
                if (grid.getCellState(r, c) == state) {
                    cells.add(grid.getCell(r, c));
                }
            }
        }

        return cells;
    }

    public abstract void runOneStep();
}
