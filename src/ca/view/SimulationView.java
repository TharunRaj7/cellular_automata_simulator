package ca.view;

import ca.controller.Controller;
import ca.controller.SimulationConfig;
import ca.controller.SimulationType;
import ca.model.Grid;
import ca.simulations.*;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class SimulationView {

    private SimulationConfig simulationConfig;
    private GridPaneHandler gridPaneHandler;
    private Controller controller;
    private Simulation simulation;

    public SimulationView() {
        readVariablesFromXML();
    }

    private void readVariablesFromXML() {
        //simulationConfig = new SimulationConfig(getXMLfile(new Stage()));
        simulationConfig = new SimulationConfig(new File("data\\WaTorWorld\\WaTorWorld1.xml"));
        //simulationConfig = new SimulationConfig(new File("data\\Segregation\\Segregation1.xml"));
        simulationConfig.readFile();

        controller = new Controller();
        gridPaneHandler = new GridPaneHandler(simulationConfig);
        try {
            createSimulationInstance(simulationConfig.getSimulationType(), new Grid(simulationConfig.getRowNum(),
                    simulationConfig.getColNum(),
                    simulationConfig.getCellStates()));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
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
            case WaTorWorld:
                simulation = new WaTorWorld(grid, simulationConfig.getOtherParameters());
                break;
            default:
                simulation = null;
                throw new NullPointerException("This simulation type does not exist!");
        }
        // TODO: add other two simulations
    }

    public GridPane getCurrentGridPane() {
        return gridPaneHandler.createGrid(simulationConfig.getColNum(),
                simulationConfig.getRowNum(),
                simulationConfig.getGridWidth(),
                simulationConfig.getGridHeight(),
                simulation.getGrid());
    }

    public Controller getController() {
        return controller;
    }

    public int getButtonHeight() {
        return simulationConfig.getGridHeight();
    }

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
