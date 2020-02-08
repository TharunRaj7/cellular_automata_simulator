package ca.simulations;

import ca.helpers.NeighboringType;
import ca.helpers.NeighborsHelper;
import ca.model.Cell;
import ca.model.CellShape;
import ca.model.Grid;
//import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;

public abstract class Simulation {
    Grid grid;
    NeighborsHelper neighborsHelper;
    NeighboringType type;

    //TODO: change the cocnstructor usage to separate view and model
    public Simulation(Grid grid) {
        this.grid = grid;
        neighborsHelper = new NeighborsHelper();
        this.type = NeighboringType.ALL;
    }

    public Simulation(int rowNum, int colNum, List<Integer> initialStates, CellShape shape) {
        this.grid = new Grid(rowNum, colNum, initialStates, shape);
        neighborsHelper = new NeighborsHelper(shape);
        this.type = NeighboringType.ALL;
    }

    /**
     * This method tells the number of neighbors that have a
     * specific state. It can be either all eight neighbors or
     * only NSWE neighbors by passing in different {@code mode}.
     * @param r         an int of the row position of this cell
     * @param c         an int of the col position of this cell
     * @param type      the number of neighbors chosen, ("EIGHT"/"NSWE")
     * @param state     an int representing the state to find
     * @return          the number of neighbors have {@code state}
     */
    //TODO: change method headings
    public int getNeighborStateNumber(int r, int c, NeighboringType type, int state) {
       List<Cell> neighbors = getNeighboringCellsOfState(r, c, type);
        int num = 0;
        for (Cell cell: neighbors) {
            if (cell.getState() == state) {
                num++;
            }
        }
        return num;
    }

    // TODO: change method heads in other classes
    private List<Cell> getNeighboringCellsOfState(int r, int c, NeighboringType type) {
        List<Cell> neighbors;
        try {
            switch (type) {
                case ALL:
                    neighbors = grid.getNeighborsByIndex(r, c, neighborsHelper.getAllRow(),
                            neighborsHelper.getAllCol());
                    break;
                case NSEW:
                    neighbors = grid.getNeighborsByIndex(r, c, neighborsHelper.getNSEWRow(),
                            neighborsHelper.getNSEWCol());
                    break;
                default:
                    neighbors = new ArrayList<>();
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return neighbors;
    }

    // TODO: check to see why need cell access
    public List<Cell> getCellOfState(int state) {
        List<Cell> cells = grid.getAllCells();
        List<Cell> ret = new ArrayList<>();

        for (Cell cell: cells) {
            if (cell.getState() == state) {
                ret.add(cell);
            }
        }
        return ret;
    }

    public int cellStateTotal(int state) {
        List<Cell> allCells = getCellOfState(state);
        return allCells.size();
    }

    public void runOneStep() {
        Grid gridNextGen = new Grid(grid);
        for (int r = 0; r < grid.getNumOfRows(); r++) {
            for (int c = 0; c < grid.getNumOfColumns(); c++) {
                gridNextGen.setCellState(r, c, determineCellState(r, c));
            }
        }
        grid = additionalActions(gridNextGen);
    }

    // TODO: delete this and check for dependency
    public Grid getGrid() {
        return grid;
    }

    protected Grid additionalActions(Grid gridNextGen){
        return gridNextGen;
    }
    protected abstract int determineCellState(int r, int c);
}
