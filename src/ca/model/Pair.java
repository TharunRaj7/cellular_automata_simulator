package ca.model;

public class Pair {
    int row;
    int col;

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

    @Override
    public String toString() {
        return "Pair{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }
}
