package ca.model;

public class Cell {
    private int state;
    private CellShape shape;

    public Cell(int state){
        this(state, CellShape.SQUARE);
    }

    public Cell(int state, CellShape shape) {
        this.state = state;
        this.shape = shape;
    }

    public Cell(Cell cell) {
        this.state = cell.state;
        this.shape = cell.shape;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public CellShape getShape() {
        return shape;
    }
}


