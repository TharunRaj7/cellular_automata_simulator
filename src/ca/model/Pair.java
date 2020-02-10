package ca.model;

import java.awt.*;

public class Pair {
    private int row;
    private int col;

    public Pair(int row, int col){
        this.row = row;
        this.col = col;
    }


    /**
     * Checks whether the given parameters match the attributes of the pair this method is being invoked on.
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
       if (obj instanceof Pair) {
           return (((Pair) obj).getRow() == row) && (((Pair) obj).getCol() == col);
       }
       return false;
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

    @Override
    public int hashCode() {
        return row * Integer.MAX_VALUE + col;
    }
}
