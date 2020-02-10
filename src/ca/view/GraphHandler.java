package ca.view;

import ca.controller.SimulationConfig;
import ca.model.Grid;
import ca.simulations.Simulation;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class handles the graph that plots all the states and the number of rectangles containing those states at the
 * current time.
 */
public class GraphHandler {

    public static final int LINECHART_WIDTH = 300;
    public static final int LINECHART_HEIGHT = 250;
    public static final int LINECHART_OFFSET = 10;

    private Simulation simulation;
    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    public Map<Integer, ArrayList<Integer>> stateQuantity = new HashMap<>();

    int stepCounter = 0;

    public GraphHandler(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Here, we create a linechart and take a list of the states and their quantities. The chart lines are recreated at
     * every step since we have the information stored.
     *
     * @param  - takes in the rows and columns and grid
     * @return LineChart, the most recent linechart
     */
    public LineChart<Number, Number> createGraph(int c, int r, int gridWidth) {
        createList(simulation.getGrid(), c, r, stepCounter);
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        for (int i = 0; i < stateQuantity.size(); i++) {
            XYChart.Series series = new XYChart.Series();
            series.setName("State:" + stateQuantity.keySet().toArray()[i]);
            for(int j = 0; j< stepCounter; j++){
                series.getData().add(new XYChart.Data(j, stateQuantity.get(i).get(j)));
            }
            lineChart.getData().add(series);
            lineChart.setCreateSymbols(false);
        }
        stepCounter++;
        lineChart.setPrefSize(LINECHART_WIDTH, LINECHART_HEIGHT);
        lineChart.setLayoutX(gridWidth + LINECHART_OFFSET);
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
                    stateTotals.add(step, simulation.cellStateTotal(cellState));
                    stateQuantity.put(cellState, stateTotals);
                } else {
                    stateQuantity.get(cellState).add(step, simulation.cellStateTotal(cellState));
                }
            }
        }

    }
}
