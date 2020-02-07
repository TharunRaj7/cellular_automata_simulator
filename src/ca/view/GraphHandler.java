package ca.view;

import ca.model.Grid;
import ca.simulations.Simulation;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class handles the graph that plots all the states and the number of rectangles containing those states at the
 * current time.
 */
public class GraphHandler {

    private Simulation simulation;
    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    private Map<Integer, ArrayList<Integer>> stateQuantity;

    int stepCounter = 0;

    /**
     * Here, we create a linechart and take a list of the states and their quantities. We use this to create several
     * series for a linechart which are then stored so that we can update them later.
     *
     * @param  - takes in the rows and columns and grid
     * @return LineChart, the most recent linechart
     */
    public LineChart<Number, Number> createGraph(int c, int r, Grid grid) {
        stepCounter++;
        createList(grid, c, r, stepCounter);
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        for (int i = 0; i < stateQuantity.size(); i++) {
            XYChart.Series series = new XYChart.Series();
            for (int j = 0; i < stateQuantity.get(i).size(); j++) {
                series.getData().add(new XYChart.Data(j, stateQuantity.get(i).get(j)));
            }
            lineChart.getData().add(series);
        }
        return lineChart;
    }

    /**
     * This method creates a map where the keys are the states and the values are arraylists where the index of each entry
     * in the array list is the step and the integer stored is the quantity of a certain state at that step.
     * @param grid
     * @param c
     * @param r
     * @param step
     */
    private void createList(Grid grid, int c, int r, int step) {
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                int cellState = grid.getCellState(i, j);
                if (!stateQuantity.containsKey(cellState)) {
                    ArrayList<Integer> stateTotals = new ArrayList<>();
                    stateTotals.set(step, simulation.cellStateTotal(cellState));
                    stateQuantity.put(cellState, stateTotals);
                } else {
                    stateQuantity.get(cellState).set(step, simulation.cellStateTotal(cellState);
                }
            }
        }

    }
}
