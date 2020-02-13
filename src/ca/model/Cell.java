package ca.model;

public class Cell {
    private int state;
    private CellShape shape;

    /**
     * constructor for a cell
     * @param state
     */
    public Cell(int state){
        this(state, CellShape.SQUARE);
    }

    /**
     * constructor for cells with non-rectangular shapes
     * @param state
     * @param shape
     */
    public Cell(int state, CellShape shape) {
        this.state = state;
        this.shape = shape;
    }

    /**
     * constructor that copies a given cell
     * @param cell
     */
    public Cell(Cell cell) {
        this.state = cell.state;
        this.shape = cell.shape;
    }

    /**
     * getter method for the state of the cell
     * @return
     */
    public int getState() {
        return state;
    }

    /**
     * setter method for the state of the cell
     * @param state
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * getter method for the shape of the cell
     * @return
     */
    public CellShape getShape() {
        return shape;
    }
}


