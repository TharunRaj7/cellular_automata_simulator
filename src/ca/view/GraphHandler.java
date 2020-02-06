//package ca.view;
//
//import ca.model.Grid;
//import javafx.scene.chart.LineChart;
//import javafx.scene.chart.NumberAxis;
//import javafx.scene.chart.XYChart;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * This class handles the graph that plots all the states and the number of rectangles containing those states at the
// * current time.
// */
//public class GraphHandler {
//
//    private Grid grid;
//    final NumberAxis xAxis = new NumberAxis();
//    final NumberAxis yAxis = new NumberAxis();
//    private LineChart<Number,Number> lineChart;
//    private List<XYChart.Series> seriesList = new ArrayList<XYChart.Series>();
//
//    public GraphHandler(Grid grid) {
//        this.grid = grid;
//    }
//
//    /**
//     * Here, we create a linechart and take a list of the states and their quantities. We use this to create several
//     * series for a linechart which are then stored so that we can update them later.
//     * TODO: create a method in Grid at every step that creates an ArrayList<Integer> where the index is the state and the integer is the number of states at that step.
//     *
//     * @param startTime - takes the time when the graph is being created
//     * @return LineChart, the most recent linechart
//     */
//    public LineChart<Number, Number> createGraph(long startTime){
//        long elapsedTime = System.currentTimeMillis() - startTime;
//        lineChart = new LineChart<Number, Number>(xAxis, yAxis);
//        List<Integer> stateQuantity = grid.getStateNumbers();
//        for(int i = 0; i < stateQuantity.size(); i++){
//            XYChart.Series series = new XYChart.Series();
//            series.getData().add(new XYChart.Data(elapsedTime, stateQuantity.get(i)));
//            seriesList.add(series);
//            lineChart.getData().add(series);
//        }
//        return lineChart;
// }
//
//    /**
//     * Updates the linechart to add the values at that given step
//     * @param nextStepTime - the next current time interval
//     */
//    public void updateGraph(long nextStepTime){
//        long elapsedTime = System.currentTimeMillis() - nextStepTime;
//        List<Integer> stateQuantity = grid.getStateNumbers();
//        for(int i = 0; i < stateQuantity.size(); i++){
//            seriesList.get(i).getData().add(new XYChart.Data(elapsedTime, stateQuantity.get(i)));
//        }
//    }
//}
