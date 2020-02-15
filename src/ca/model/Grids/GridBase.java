package ca.model.Grids;

import ca.model.Cell;
import ca.model.CellShape;
import ca.model.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the abstract class for all {@code Grid} implementations.
 * This class contains the cell shape and cells' states of the grid.
 *
 * Note, number(s) of row and number(s) of col are two factors that
 * should be implemented for all {@code Grid}, but it is acceptable to
 * have more than one number of row/col.
 *
 * @author Cady Zhou
 * @author Tharun Raj
 * @version 1.1
 * @since 1.1
 */

public abstract class GridBase {
    public static final int DEFAULT_STATE = 0;

    protected Map<Pair, Cell> gridMap;
    protected CellShape shape;

    /**
     * Creates an instance of GridBase with given shape.
     *
     * Be cautions that this constructor does not call
     * {@link #createGridModel(List)} as {@code numOfRow} and
     * {@code numOfCol} have not been set as the stage. For all
     * inheritance, {@link #createGridModel(List)} needs to be
     * explicitly called in the constructor.
     *
     * @param shape     a {@link CellShape} that indicates the type
     *                  of this grid
     */
    public GridBase(CellShape shape) {
        this.gridMap = new HashMap<>();
        this.shape = shape;
    }

    /**
     * Creates a new {@code GridBase} from an existing {@code GridBase}.
     * @param gridBase      an existing GridBase
     */
    public GridBase(GridBase gridBase) {
        this.gridMap = new HashMap<>(gridBase.gridMap);
        for (Pair p: gridMap.keySet()) {
            gridMap.put(p, new Cell(gridMap.get(p)));
        }
        this.shape = gridBase.shape;
    }

    /**
     * Returns all the neighbors surrounding a cell given the row
     * and column indices
     * @param r                     the row index of this cell
     * @param c                     the col index of this cell
     * @param rowIndices            a list of row delta for possible
     *                              neighbors
     * @param colIndices            a list of column delta for possible
     *                              neighbors
     * @return                      a list of neighboring cells
     * @throws RuntimeException     if the size of {@code rowIndices} and
     *                              {@code colIndices} do not match
     */
    public List<Cell> getNeighborsByIndex(int r, int c, List<Integer> rowIndices, List<Integer> colIndices) throws RuntimeException{
        if (rowIndices.size() != colIndices.size()) {
            throw new RuntimeException("row and col sizes are not aligned for getNeighbors!");
        }

        List<Cell> ret = new ArrayList<>();
        for (int i = 0; i < rowIndices.size(); i++) {
            if (inBound(rowIndices.get(i) + r, colIndices.get(i) + c)) {
                ret.add(gridMap.get(new Pair(rowIndices.get(i) + r, colIndices.get(i) + c)));
            }
        }
        return ret;
    }

    /**
     * Gets the coordinate of a given cell
     * @param cell      a Cell whose coordinate to be investigated
     * @return          a {@link Pair} representing the cell's coordinate
     */
    public Pair getPairGivenCell(Cell cell){
        for (Pair pair : gridMap.keySet()){
            if (gridMap.get(pair) == cell){
                return pair;
            }
        }
        return null;
    }

    /**
     * Gets a cell at a specific location on the grid.
     * @param r     the row index of the cell
     * @param c     the col index of the cell
     * @return      a {@link Cell} at this location
     */
    public Cell getCell(int r, int c) {
        return gridMap.get(new Pair(r, c));
    }

    /**
     * Gets the state of a cell
     * @param r     the row index of this cell
     * @param c     the col index of this cell
     * @return      the state of this cell
     */
    public int getCellState(int r, int c) {
        return getCell(r, c).getState();
    }

    /**
     * Add a cell of default state at a specific location
     * @param r     the row index of this cell
     * @param c     the col index of this cell
     */
    public void setDefaultCell(int r, int c) {
        gridMap.put(new Pair(r, c), new Cell(DEFAULT_STATE));
    }

    /**
     * Set the state of this specified cell
     * @param r         the row index of this cell
     * @param c         the col index of this cell
     * @param state     the state of this cell
     */
    public void setCellState(int r, int c, int state) {
        gridMap.get(new Pair(r, c)).setState(state);
    }

    /**
     * Gets the shape of cells in this grid
     * @return  a {@link CellShape} of this cell
     */
    public CellShape getShape() {
        return shape;
    }

    /**
     * Checks whether a coordinate pair is contained within the bounds of the grid
     * @param r     an int of the row index
     * @param c     an int of the col index
     * @return      if the location is valid
     */
    protected abstract boolean inBound(int r, int c);

    /**
     * Initializes a gird instance with initial states of cells
     * @param initialStates     a list of initial states of cells
     */
    protected abstract void createGridModel(List<Integer> initialStates);

    /**
     * Gets all the cells from the grid
     * @return  a list of cells
     */
    public abstract List<Cell> getAllCells();

    /**
     * Gets the number of rows of this grid
     * @return  an int as the number of rows of this grid
     */
    public abstract int getNumOfRows();

    /**
     * Gets the number of cols of this grid
     * @return  an int as the number of cols of this grid
     */
    public abstract int getNumOfColumns();

    /**
     * Sets the number of rows of this grid
     * @param numOfRows   an int as the number of rows of this grid
     */
    public abstract void setNumOfRows(int numOfRows);

    /**
     * Sets the number of cols of this grid
     * @param numOfColumns   an int as the number of rows of this grid
     */
    public abstract void setNumOfColumns(int numOfColumns);
}
