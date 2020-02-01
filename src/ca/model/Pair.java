package ca.model;

public class Pair {
    int row;
    int col;

    public Pair(int row, int col){
        this.row = row;
        this.col = col;
    }

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
