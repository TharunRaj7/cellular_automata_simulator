package ca.model;

public class Pair {
    private int row;
    private int col;

    public Pair(int row, int col){
        this.row = row;
        this.col = col;
    }

    /**
     * Checks whether the given parameters match the attributes of the pair this method is being invoked on.
     * @param row
     * @param col
     * @return true or false
     */
    public boolean checkPair (int row, int col){
        return (this.row == row && this.col == col);
    }

    public int getRow() {
        return row;
    }


    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }
}
