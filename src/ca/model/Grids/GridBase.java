package ca.model.Grids;

import ca.model.Cell;
import ca.model.CellShape;
import ca.model.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GridBase {
    protected Map<Pair, Cell> gridMap;
    protected CellShape shape;
    public static final int DEFAULT_STATE = 0;

    /**
     * The constructor to copy a GridBase object.
     * @param gridBase
     */
    public GridBase(GridBase gridBase) {
        this.gridMap = new HashMap<>(gridBase.gridMap);
        for (Pair p: gridMap.keySet()) {
            gridMap.put(p, new Cell(gridMap.get(p)));
        }
        this.shape = gridBase.shape;
    }

    /**
     * Be cautions that this constructor does not call
     * {@link #createGridModel(List)} as {@code numOfRow}
     * and {@code numOfCol} have not been set as the stage.
     * For all inheritance, {@link #createGridModel(List)}
     * needs to be explicitly called in the constructor.
     *
     * @param shape
     */
    public GridBase(CellShape shape) {
        this.gridMap = new HashMap<>();
        this.shape = shape;
    }

    /**
     * Returns all the neighbors surrounding a cell given the row and column indices
     * @param r
     * @param c
     * @param rowIndices
     * @param colIndices
     * @return a list of cells
     * @throws RuntimeException
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
     * returns a pair given a cell
     * @param cell
     * @return
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
     * returns a cell at a specific location on the grid.
     * @param r
     * @param c
     * @return
     */
    public Cell getCell(int r, int c) {
        return gridMap.get(new Pair(r, c));
    }

    /**
     * returns the cell of a specified state
     * @param r
     * @param c
     * @return
     */
    public int getCellState(int r, int c) {
        return getCell(r, c).getState();
    }

    /**
     * Add a default set at r,c
     * @param r
     * @param c
     */
    public void setDefaultCell(int r, int c) {
        gridMap.put(new Pair(r, c), new Cell(DEFAULT_STATE));
    }

    /**
     * Set the state of specified cell
     * @param r
     * @param c
     * @param state
     */
    public void setCellState(int r, int c, int state) {
        gridMap.get(new Pair(r, c)).setState(state);
    }

    /**
     * getter method for the shape for the cell
     * @return
     */
    public CellShape getShape() {
        return shape;
    }

    /**
     * Checks whether a coordinate pair is contained within the bounds of the grid
     * @param r
     * @param c
     * @return a boolean value
     */
    protected abstract boolean inBound(int r, int c);

    /**
     * abstract method to build the grid model
     * @param initialStates
     */
    protected abstract void createGridModel(List<Integer> initialStates);

    /**
     * gets all the cells from the grid
     * @return a list of cells
     */
    public abstract List<Cell> getAllCells();

    /**
     * getter method for the number of rows
     * @return
     */
    public abstract int getNumOfRows();

    /**
     * getter method for the number of columns
     * @return
     */
    public abstract int getNumOfColumns();

    /**
     * setter method for the number of rows
     * @param numOfRows
     */
    public abstract void setNumOfRows(int numOfRows);

    /**
     * setter method for the number of colums
     * @param numOfColumns
     */
    public abstract void setNumOfColumns(int numOfColumns);
}
