package ca.simulations;

import ca.helpers.NeighboringType;
import ca.helpers.NeighborsHelper;
import ca.model.Cell;
import ca.model.CellShape;
import ca.model.Grids.Grid;
import ca.model.Grids.GridBase;
//import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;

public abstract class Simulation {
    GridBase grid;
    NeighborsHelper neighborsHelper;
    NeighboringType type;

    /**
     * constructor to copy the grid of a previously initialized simulation
     * @param grid
     */
    public Simulation(GridBase grid) {
        neighborsHelper = new NeighborsHelper();
        this.type = NeighboringType.ALL;
        this.grid = grid;
    }

    /**
     * default constructor to create a Simulation object
     * @param rowNum
     * @param colNum
     * @param initialStates
     * @param shape
     */
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

    /**
     * Returns all the neighbours with a specified state
     * @param r
     * @param c
     * @param state
     * @return
     */
    public List<Cell> getNeighboringCellsWithState(int r, int c, int state){
        List<Cell> ret = new ArrayList<>();
        List<Cell> neighbors = grid.getNeighborsByIndex(r, c, neighborsHelper.getNSEWRow(), neighborsHelper.getNSEWCol());
        for (Cell item : neighbors){
            if (item.getState() == state) {
                ret.add(item);
            }
        }
        return ret;
    }

    /**
     * gets all the cells on the grid with a specific state
     * @param state
     * @return a list of cells
     */
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

    /**
     * returns the number of cells with a specific state
     * @param state
     * @return
     */
    public int cellStateTotal(int state) {
        List<Cell> allCells = getCellOfState(state);
        return allCells.size();
    }

    /**
     * Method that is invoked on each step of the
     */
    public void runOneStep() {
        for (int r = 0; r < getNumOfRows(); r++) {
            for (int c = 0; c < getNumOfCols(); c++) {
                if (grid.getCell(r, c) == null) {
                    grid.setDefaultCell(r, c);
                }
            }
        }

        Grid gridNextGen = new Grid((Grid) grid);
        for (int r = 0; r < grid.getNumOfRows(); r++) {
            for (int c = 0; c < grid.getNumOfColumns(); c++) {
                gridNextGen.setCellState(r, c, determineCellState(r, c));
            }
        }
        grid = additionalActions(gridNextGen);
    }

    /**
     * getter method for the GridBase object
     * @return a GridBase object
     */
    public GridBase getGrid() {
        return grid;
    }

    /**
     * @return number of rows
     */
    public int getNumOfRows() {
        return grid.getNumOfRows();
    }

    /**
     * @return number of columns
     */
    public int getNumOfCols() {
        return grid.getNumOfColumns();
    }

    /**
     * set the number of rows
     * @param numOfRow
     */
    public void setNumOfRows(int numOfRow) {
        grid.setNumOfRows(numOfRow);
    }

    /**
     * set the number of columns
     * @param numOfCol
     */
    public void setNumOfCols(int numOfCol) {
        grid.setNumOfColumns(numOfCol);
    }

    /**
     * function to work on a grid before returning it (for additional features)
     * @param gridNextGen
     * @return a Grid
     */
    protected Grid additionalActions(Grid gridNextGen){
        return gridNextGen;
    }

    /**
     * abstract method that implements the logic to update the cells on each step. Each concrete simulation implementation
     * would override this function with its own update logic.
     * @param r
     * @param c
     * @return
     */
    protected abstract int determineCellState(int r, int c);
}
