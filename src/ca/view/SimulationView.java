package ca.view;

import ca.controller.Controller;
import ca.controller.SimulationConfig;
import ca.controller.SimulationType;
import ca.helpers.GraphHandler;
import ca.helpers.GridPaneHandler;
import ca.model.Grids.Grid;
import ca.simulations.*;
import javafx.scene.chart.Chart;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class SimulationView {

    private SimulationConfig simulationConfig;
    private GridPaneHandler gridPaneHandler;
    private GraphHandler graphHandler;
    private Controller controller;
    private Simulation simulation;

    /**
     * Calls the simulation to read the XML to obtain all necessary information
     */
    public SimulationView() {
        readVariablesFromXML();
    }

    private void attemptOpenXML() {
        try {
            simulationConfig = new SimulationConfig(getXMLfile(new Stage()));
//            simulationConfig = new SimulationConfig(new File("data\\GameOfLife\\GameOfLife1.xml"), 1, 1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            attemptOpenXML();
        }
    }

    private void readVariablesFromXML() {
       attemptOpenXML();
       controller = new Controller();
       gridPaneHandler = new GridPaneHandler(simulationConfig);

       try {
           createSimulationInstance(simulationConfig.getSimulationType(), new Grid(simulationConfig.getRowNum(),
                    simulationConfig.getColNum(),
                    simulationConfig.getCellStates()));
       } catch (NullPointerException e) {
           System.out.println(e.getMessage());
           attemptOpenXML();
       }
        graphHandler = new GraphHandler(simulation);
        controller.setSimulation(simulation);
    }

    private void createSimulationInstance(SimulationType simulationType, Grid grid) throws NullPointerException {
        switch (simulationType) {
            case GameOfLife:
                simulation = new GameOfLife(grid);
                break;
            case Segregation:
                simulation = new Segregation(grid, simulationConfig.getOtherParameters());
                break;
            case Percolation:
                simulation = new Percolation(grid);
                break;
            case Fire:
                simulation = new SpreadingOfFire(grid, simulationConfig.getOtherParameters());
                break;
            case WaTorWorld:
                simulation = new WaTorWorld(grid, simulationConfig.getOtherParameters());
                break;
            case LangtonLoop:
                simulation = new LangtonLoop(grid);
                break;
            default:
                simulation = null;
                throw new NullPointerException("This simulation type does not exist!");
        }
    }

    /**
     * Calls on the helper class ```ca.helpers.GridPaneHandler.java``` to create a new gridpane, which effectively gets
     * the current gridpane. This call requires several calls to both Simulation and SimulationConfig.
     * @return Gridpane that will be displayed and added to the scene
     */
    public GridPane getCurrentGridPane() {
        return gridPaneHandler.createGrid(simulation.getNumOfCols(),
                simulation.getNumOfRows(),
                simulationConfig.getGridWidth(),
                simulationConfig.getGridHeight(),
                simulation.getGrid());
    }

    /**
     * Calls on the helper class ```ca.helpers.GraphHandler.java``` to create a new chart, which effectively gets the current
     * chart. This call requires several calls to simulationConfig.
     * @return Chart that will be displayed and added to the scene
     */
    public Chart getCurrentLineChart(){
        return graphHandler.createGraph(simulationConfig.getColNum(),
                simulationConfig.getRowNum(),
                simulationConfig.getGridWidth());
    }

    /**
     * Using values from the slideers in the UI, this method updates the number of rows and columns in the grid.
     * @param newRowNum - read in from the row slider
     * @param newColNum - read in from the column slider
     */
    public void updateGridSize(int newRowNum, int newColNum){
        simulation.setNumOfCols(newColNum);
        simulation.setNumOfRows(newRowNum);
    }

    /**
     * Gets controller so that it can be used in Main.java
     * @return the Controller
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Gets the grid height from the XML file
     * @return an integer value for the Grid height
     */
    public int getButtonHeight() {
        return simulationConfig.getGridHeight();
    }

    /**
     * Gets the grid height from the XML file
     * @return an integer value for the Grid height
     */
    public int getGridHeight() {return simulationConfig.getGridHeight();}

    /**
     * Gets the number of rows from the XML file
     * @return an integer value for the number of rows
     */
    public int getNumRows() {return simulationConfig.getRowNum();}

    /**
     * Gets the number of columns from the XML file
     * @return an integer value for the number of columns
     */
    public int getNumCols() {return simulationConfig.getColNum();}

    /**
     * creates an instance of simulation that can be used in Main.java
     * @return the Simulation
     */
    public Simulation getSimulation() {
        return simulation;
    }

    private File getXMLfile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"));
        return fileChooser.showOpenDialog(stage);
    }
}
