package ca.model;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.*;

public class Grid {
    private int numOfColumns;
    private int numOfRows;

    Map<Pair, Cell> gridMap;

    public Grid(int numOfRows, int numOfColumns, ArrayList<Integer> initialStates) {
        HashMap<Pair, Cell> gridMap;
        GridPane grid;
    }


    public Grid( int row, int column, List<Integer > initialStates){
            //make the grid
            this.numOfRows = numOfRows;
            this.numOfColumns = numOfColumns;
            gridMap = new LinkedHashMap<>();
            createGridModel();
        }


        public ArrayList<Cell> getNSEWNeigbors ( int r, int c){
            return new ArrayList<>();
        }
        public ArrayList<Cell> getAllNeighbors ( int r, int c){
            return new ArrayList<>();
        }

        /**
         *
         * @param row
         * @param col
         * @param state
         * @param color
         */
        public void updateGrid ( int row, int col, int state, Color color){

        }


    private void createGridModel() {
        int rowLooper;
        int colLooper;
        for (int i = 0; i < numOfRows; i++) {

        }
    }
}