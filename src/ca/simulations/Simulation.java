package ca.simulations;

import ca.helpers.NeighboringType;
import ca.helpers.NeighborsHelper;
import ca.model.Cell;
import ca.model.CellShape;
import ca.model.Grids.Grid;
import ca.model.Grids.GridBase;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the abstract class for all simulations. The core
 * method {@link #runOneStep()} should be called by the
 * Main in a loop.
 *
 * Currently {@link #runOneStep()} is implemented so that
 * child classes only need to implement {@link #determineCellState(int, int)}
 * to update the entire gird. However, it is also acceptable
 * to override {@link #runOneStep()} if needed. Note, programmers shoudl
 * be especially careful with the synchronization issue of {@link #runOneStep()}
 * if overriding is necessary.
 *
 * @author Cady Zhou
 * @version 1.1
 * @since 1.1
 */
public abstract class Simulation {
    GridBase grid;
    NeighborsHelper neighborsHelper;
    NeighboringType type;

    /**
     * Creates an instance of simulation with a grid
     * @param grid      A grid instance for the simulation
     */
    public Simulation(GridBase grid) {
        neighborsHelper = new NeighborsHelper();
        this.type = NeighboringType.ALL;
        this.grid = grid;
    }

    /**
     * Creates a simulation with initial states of the grid
     * @param rowNum            an int as the number of rows of the grid
     * @param colNum            an int as the number of cols of the grid
     * @param initialStates     a list of integer as the cells' initial states
     * @param shape             the shape of teh grid
     */
    public Simulation(int rowNum, int colNum, List<Integer> initialStates, CellShape shape) {
        this.grid = new Grid(rowNum, colNum, initialStates, shape);
        neighborsHelper = new NeighborsHelper(shape);
        this.type = NeighboringType.ALL;
    }

    /**
     * Gets the number of neighbors that have a specific state with any
     * valid neighboring type.
     * @param r         an int of the row position of this cell
     * @param c         an int of the col position of this cell
     * @param type      the {@link NeighboringType}
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
     * Gets the neighbors that have a specific state with any
     * valid neighboring type.
     * @param r         an int of the row position of this cell
     * @param c         an int of the col position of this cell
     * @param state     an int representing the state to find
     * @return          neighbors of {@code state}
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
     * Gets all cells on the grid with a specific state
     * @param state             an int representing the state to find
     * @return                  a list of cells with that state
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
     * Gets the number of cells with a specific state
     * @param state     an int representing the state to find
     * @return          the number of cells with that state
     */
    public int cellStateTotal(int state) {
        List<Cell> allCells = getCellOfState(state);
        return allCells.size();
    }

    /**
     * Runs one step of this simulation
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
     * Gets this GridBase object
     * @return  this GridBase object
     */
    public GridBase getGrid() {
        return grid;
    }

    /**
     * Gets the number of rows of this grid
     * @return  an int as the number of rows of this grid
     */
    public int getNumOfRows() {
        return grid.getNumOfRows();
    }

    /**
     * Gets the number of cols of this grid
     * @return  an int as the number of cols of this grid
     */
    public int getNumOfCols() {
        return grid.getNumOfColumns();
    }

    /**
     * Sets the number of rows of this grid
     * @param numOfRow   an int as the number of rows of this grid
     */
    public void setNumOfRows(int numOfRow) {
        grid.setNumOfRows(numOfRow);
    }

    /**
     * Sets the number of cols of this grid
     * @param numOfCol   an int as the number of cols of this grid
     */
    public void setNumOfCols(int numOfCol) {
        grid.setNumOfColumns(numOfCol);
    }

    /**
     * Does additional actiosn on a grid in one step
     * @param gridNextGen   a grid for the next generation
     * @return              a Grid after processing
     */
    protected Grid additionalActions(Grid gridNextGen){
        return gridNextGen;
    }

    /**
     * Gets the cell state on next generation. Each concrete simulation implementation
     * would override this function with its own update logic.
     * @param r     an int of the row index
     * @param c     an int of the col index
     * @return      the state of the cell in the next generation   
     */
    protected abstract int determineCellState(int r, int c);
}
