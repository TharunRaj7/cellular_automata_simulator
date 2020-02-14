package ca.helpers;

/**
 * This class deals with different types of neighbors. The basic
 * idea is to return row and col delta for each neighbor configuration.
 * More methods can be easily added to this class for more neighbor
 * configurations. This neighborsHelper is also compatible with
 * {@link ca.model.Grids.IrregularGrid}
 *
 * Note, the getters always start from the cell above, and rotate
 * clockwise.
 *
 * @author Cady Zhou
 * @version 1.1
 * @since 1.1
 */

import ca.model.CellShape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NeighborsHelper {
    private CellShape shape;
    private String noShapeMessage = "The shape is not defined. " +
            "Thus neighbor indices cannot be determined.";

    /**
     * Creates a new instance of NeighborsHelper
     */
    public NeighborsHelper(){
       this(CellShape.SQUARE);
    }

    /**
     * Creates a new instance of NeighborsHelper with the known {@link CellShape}
     * @param shape     A {@link CellShape} that is the shape of the cell
     */
    public NeighborsHelper(CellShape shape) {
        this.shape = shape;
    }

    /**
     * Gets the row delta value of four neighbors directly above, below,
     * left and right to the cell
     * @return                      a list of row delta for NSEW neighbors
     * @throws RuntimeException     if the shape is unknown or undefined
     */
    public List<Integer> getNSEWRow() throws RuntimeException {
        switch (shape) {
            case SQUARE:
                return new ArrayList<>(Arrays.asList(-1, 0, 1, 0));
            default:
                throw new RuntimeException(noShapeMessage);
        }
    }


    /**
     * Gets the column delta value of four neighbors directly above, below,
     * left and right to the cell
     * @return                      a list of col delta for NSEW neighbors
     * @throws RuntimeException     if the shape is unknown or undefined
     */
    public List<Integer> getNSEWCol() throws RuntimeException {
        switch (shape) {
            case SQUARE:
                return new ArrayList<>(Arrays.asList(0, 1, 0, -1));
            default:
                throw new RuntimeException(noShapeMessage);
        }
    }

    /**
     * Gets the row delta value of all eight neighbors that surround the
     * cell
     * @return                      a list of row delta for all neighbors
     * @throws RuntimeException     if the shape is unknown or undefined
     */
    public List<Integer> getAllRow() throws RuntimeException{
        switch (shape) {
            case SQUARE:
                return new ArrayList<>(Arrays.asList(-1, -1, 0, 1, 1, 1, 0, -1));
            default:
                throw new RuntimeException(noShapeMessage);
        }
    }

    /**
     * Gets the column delta value of all eight neighbors that surround the
     * cell
     * @return                      a list of col delta for all neighbors
     * @throws RuntimeException     if the shape is unknown or undefined
     */
    public List<Integer> getAllCol() throws RuntimeException{
        switch (shape) {
            case SQUARE:
                return new ArrayList<>(Arrays.asList(0, 1, 1, 1, 0, -1, -1, -1));
            default:
                throw new RuntimeException(noShapeMessage);
        }
    }

}
