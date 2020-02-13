package ca.model;

import java.awt.*;


public class Pair {
    private int row;
    private int col;

    /**
     * Constructor of the pair which holds 2 integers
     * @param row
     * @param col
     */
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

    /**
     * getter method for the row value
     * @return
     */
    public int getRow() {
        return row;
    }

    /**
     * getter method for the column value
     * @return
     */
    public int getCol() {
        return col;
    }

    /**
     * setter method for the row value
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * setter method for the column
     * @param col
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * overrided toString method to print a pair object
     * @return
     */
    @Override
    public String toString() {
        return "Pair{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }

    /**
     * Overrided method for the hashcode value
     * @return
     */
    @Override
    public int hashCode() {
        return row * Integer.MAX_VALUE + col;
    }
}
