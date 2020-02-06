package test;

import ca.controller.SimulationConfig;

import java.io.File;

public class TestXMLRead {
    public static void main(String[] args) {
        SimulationConfig simulationConfig = null;
        try {
//            simulationConfig = new SimulationConfig(new File("data\\Test\\WrongFormat.txt"));

            simulationConfig = new SimulationConfig();
            simulationConfig.setFile(new File("data\\Test\\MissingNode.xml"));
            simulationConfig.readFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(simulationConfig.getColors());
    }
}
