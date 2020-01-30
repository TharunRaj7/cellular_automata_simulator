import java.util.ArrayList;
import java.util.HashMap;

public abstract class Grid {
    HashMap<Pair, Cell> gridMap;
    private int height;
    private int width;


    public Grid(int height, int width) {
        //make the grid
        gridMap = new HashMap<>();
    }
    public ArrayList<Cell> getNSEWNeigbours(){
        return new ArrayList<>();
    }
    public ArrayList<Cell> getAllNeighbours(){
        return new ArrayList<>();
    }

    public void updateGrid(int row, int col, State state){

    }
}