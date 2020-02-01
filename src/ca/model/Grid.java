package ca.model;


import java.util.*;

public class Grid {
    private int numOfColumns;
    private int numOfRows;

    Map<Pair, Cell> gridMap;

    public Grid(int numOfRows, int numOfColumns,  List<Integer> initialStates) {
        this.gridMap = new LinkedHashMap<>();
        this.numOfRows = numOfRows;
        this.numOfColumns = numOfColumns;
        createGridModel(initialStates);
    }


    public ArrayList<Cell> getNSEWNeigbors (int r, int c) {
        ArrayList<Cell> ret = new ArrayList<>();
        int [] rowIndices = {r-1, r+1};
        int [] colIndices = {c+1, c-1}; //east first, then west

        for (int i : rowIndices){
            Pair temp = getPair(i, c);
            if (temp != null){
                ret.add(gridMap.get(temp));
            }
        }

        for (int i : colIndices){
            Pair temp = getPair(r, i);
            if (temp != null){
                ret.add(gridMap.get(temp));
            }
        }
        return ret;
    }

    private Pair getPair(int r, int c) {
        for (Pair pair : gridMap.keySet()){
            if (pair.checkPair(r,c)){
                return pair;
            }
        }
        return null;
    }


    public ArrayList<Cell> getAllNeighbors (int r, int c){
        return new ArrayList<>();
    }

        /**
         *
         * @param row
         * @param col
         * @param state
         */
    public void updateGrid ( int row, int col, int state){
        for (Pair pair : gridMap.keySet()){
            if (pair.checkPair(row, col)){
                gridMap.get(pair).setState(state);
            }
        }
    }


    private void createGridModel(List<Integer>initialStates) {
        int rowLooper = 0;
        int colLooper = 0;
        for (int i = 0; i < numOfColumns*numOfRows; i++) {
            Pair temp = new Pair(rowLooper, colLooper);
            Cell tempCell = new Cell(initialStates.get(i));
            System.out.println(initialStates.get(i));
            gridMap.putIfAbsent(temp, tempCell);

            if (colLooper == numOfColumns - 1){
                colLooper = 0;
                rowLooper++;
                continue;
            }
            colLooper++;
        }
    }

    public int getCellState (int r, int c){return 0;}

    public static void main(String[] args) {
        //testing for Grid Class
        List<Integer> temp = new ArrayList<>();
        for(int i = 0; i < 9; i++) {
            temp.add(i);
        }
        Grid test = new Grid(3,3, temp);
        test.updateGrid(1,2, 454);

        for (Pair pair : test.gridMap.keySet()){
            System.out.println(pair);
            System.out.println("State = " + test.gridMap.get(pair).getState());
        }

        ArrayList<Cell> tester = test.getNSEWNeigbors(2,2);
        for (Cell cell : tester){
            System.out.println(cell.getState());
        }


    }
}