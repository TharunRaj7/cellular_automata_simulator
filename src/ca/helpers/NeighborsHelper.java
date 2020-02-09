package ca.helpers;

/**
 * The getters always start from the cell above, and rotate
 * clockwise.
 */

import ca.model.CellShape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NeighborsHelper {
    private CellShape shape;
    private String noShapeMessage = "The shape is not defined. Thus neighbor indices cannot be determined.";

    public NeighborsHelper(){
       this(CellShape.SQUARE);
    }

    public NeighborsHelper(CellShape shape) {
        this.shape = shape;
    }

    public List<Integer> getNSEWRow() throws RuntimeException {
        switch (shape) {
            case SQUARE:
                return new ArrayList<>(Arrays.asList(-1, 0, 1, 0));
            default:
                throw new RuntimeException(noShapeMessage);
        }
    }

    public List<Integer> getNSEWCol() throws RuntimeException {
        switch (shape) {
            case SQUARE:
                return new ArrayList<>(Arrays.asList(0, 1, 0, -1));
            default:
                throw new RuntimeException(noShapeMessage);
        }
    }

    public List<Integer> getAllRow() throws RuntimeException{
        switch (shape) {
            case SQUARE:
                return new ArrayList<>(Arrays.asList(-1, -1, 0, 1, 1, 1, 0, -1));
            default:
                throw new RuntimeException(noShapeMessage);
        }
    }

    public List<Integer> getAllCol() throws RuntimeException{
        switch (shape) {
            case SQUARE:
                return new ArrayList<>(Arrays.asList(0, 1, 1, 1, 0, -1, -1, -1));
            default:
                throw new RuntimeException(noShapeMessage);
        }
    }

}
