package ca.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GridBase {
    protected Map<Pair, Cell> gridMap;
    protected CellShape shape;

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

    // TODO: why using this method?
    public Cell getCell(int r, int c) {
        return gridMap.get(new Pair(r, c));
    }
    public int getCellState(int r, int c) {
        return getCell(r, c).getState();
    }

    public void setCellState(int r, int c, int state) {
        gridMap.get(new Pair(r, c)).setState(state);
    }

    public CellShape getShape() {
        return shape;
    }


    protected abstract boolean inBound(int r, int c);
    protected abstract void createGridModel(List<Integer> initialStates);
}
