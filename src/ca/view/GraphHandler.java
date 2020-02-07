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
    private LineChart<Number, Number> lineChart;
    private List<XYChart.Series> seriesList = new ArrayList<XYChart.Series>();
    private Map<Integer, ArrayList<Integer>> stateQuantity;

    /**
     * Here, we create a linechart and take a list of the states and their quantities. We use this to create several
     * series for a linechart which are then stored so that we can update them later.
     *
     * @param step - takes the time when the graph is being created
     * @return LineChart, the most recent linechart
     */
    public LineChart<Number, Number> createGraph(int step, int c, int r, Grid grid) {
        createList(grid, c, r, step);
        lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        for (int i = 0; i < stateQuantity.size(); i++) {
            XYChart.Series series = new XYChart.Series();
            for (int j = 0; i < stateQuantity.get(i).size(); j++) {
                series.getData().add(new XYChart.Data(j, stateQuantity.get(i).get(j)));
            }
            seriesList.add(series);
            lineChart.getData().add(series);
        }
        return lineChart;
    }

    // two things needed here: how to keep track of what step it is, is the logic right for map?
    private void createList(Grid grid, int c, int r, int step) {
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                int cellState = grid.getCellState(i, j);
                if (!stateQuantity.containsKey(cellState)) {
                    ArrayList<Integer> stateTotals = new ArrayList<>();
                    stateTotals.get(step) = simulation.cellStateTotal(cellState);
                    stateQuantity.put(cellState, stateTotals);
                } else {
                    stateQuantity.get(cellState).get(step) = simulation.cellStateTotal(cellState);
                }
            }
        }

    }
}
