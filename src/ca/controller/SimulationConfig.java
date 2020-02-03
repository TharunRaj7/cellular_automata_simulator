package ca.controller;

import javafx.scene.paint.Color;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class reads in necessary simulation information, including
 * <ul>
 *     <li>ca.model.Simulation Name/Type</li>
 *     <li>ca.model.Grid Size</li>
 *     <li>Initial cell states</li>
 *     <li>Colors corresponding to each state</li>
 * </ul>
 * from a xml file. An example xml file can be found under
 * {@code data.SimulationTemplate.xml}.
 *
 * Note, {@link #rowNum} and {@link #colNum} are given based
 * on TXT file input, and hence should be checked whether fulfill
 * the game design.
 *
 * @author Cady Zhou
 * @version 1.1
 * @since 1.1
 */
public class SimulationConfig {
    String colorPattern = "#......";
    Logger logger;

    int gridWidth = -1;
    int gridHeight = -1;

    int rowNum = -1;
    int colNum = -1;

    File file;
    SimulationType simulationType;
    Document doc;
    List<Integer> cellStates;
    List<Color> colors;
    String folderName;

    /**
     * Create a new instance without configuration file
     */
    public SimulationConfig() {
        cellStates = new ArrayList<>();
        colors = new ArrayList<>();
        logger = Logger.getLogger(SimulationConfig.class.getName());
        logger.setLevel(Level.WARNING);
    }

    /**
     * Create a new instance with known configuration file
     * @param file  configuration xml file
     */
    public SimulationConfig(File file) {
        this();
        this.file = file;
    }

    /**
     * Read all required information for simulations
     * @param file  the xml configuration file
     */
    public void readFile(File file) {
        this.file = file;
        try {
            doc = generateDocument();

            Element elementGeneral = getElementFromNode("general");
            folderName = getNodeContent(elementGeneral, "inFolder");
            assignSimulationType(elementGeneral);
            Element elementInitialStates = getElementFromNode("initialStates");
            assignGridStates(elementInitialStates);
            Element elementColors = getElementFromNode("colors");
            assignColors(elementColors);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Document generateDocument() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);

        if (!checkFileValidity(doc)) {
            throw new Exception("Invalid ca.model.Simulation Configuration File! (Root node is not 'simulation')");
        }
        doc.getDocumentElement().normalize();
        return doc;
    }

    private boolean checkFileValidity(Document doc) {
        return doc.getDocumentElement().getNodeName().equals("simulation");
    }

    private Element getElementFromNode(String tagName) throws Exception {
        Node node = doc.getElementsByTagName(tagName).item(0);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            return (Element) node;
        } else {
            throw new Exception("Invalid xml file node: " + tagName + "!");
        }
    }

    private void assignSimulationType(Element element) throws Exception {
        String type = getNodeContent(element, "type");
        switch (type) {
            case "GameOfLife":
                simulationType = SimulationType.GameOfLife;
                break;
            case "Percolation":
                simulationType = SimulationType.Percolation;
                break;
            case "Segregation":
                simulationType = SimulationType.Segregation;
                break;
            default:
                throw new Exception("ca.model.Simulation Type not defined!");
        }
    }

    private void assignGridStates(Element element) throws Exception {
        // grid size
        String width = getNodeContent(element, "gridWidth");
        String height = getNodeContent(element, "gridHeight");
        try {
            gridWidth = Integer.parseInt(width);
            gridHeight = Integer.parseInt(height);
        } catch (NumberFormatException | NullPointerException e ) {
            e.printStackTrace();
        }

        // initial states
        try {
            readCellStates(getNodeContent(element, "cellConfig"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String getNodeContent(Element element, String tagName) {
        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }


    private String fileInput(String filename) {
        FileInputStream in = null;
        String s = null;
        try {
            File inputFile = new File(filename);
            in = new FileInputStream(inputFile);
            byte[] bt = new byte[(int) inputFile.length()];
            in.read(bt);
            s = new String(bt);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert in != null;
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return s;
    }

    private void readCellStates(String filename) throws Exception {
        if (folderName == null) {
            throw new Exception("The configuration file does not contain outer folder name!");
        }
        filename = "." + File.separatorChar + "data" + File.separatorChar + folderName + File.separatorChar + filename;
        String s = fileInput(filename);

        boolean endOfFirstLine = false;
        for (Character c: s.toCharArray()) {
            if (Character.isDigit(c)) {
                cellStates.add(Character.getNumericValue(c));
            } else if ((int)c == 10) {  // line
                rowNum++;
                endOfFirstLine = true;
            } else if (!c.equals(' ') && (int)c != 13) {  // TODO: check whether 10 and 13 are universal
                throw new IllegalArgumentException("Initial State TXT has non-digit characters: " + (int) c);
            }

            if (!endOfFirstLine) {
                colNum ++;
            }
        }

        rowNum += 2; // because it starts at -1, and the last line does not have line feed
        colNum = (colNum + 1) / 2;  // the last number of a row does not have a space follow it
    }

    private void assignColors(Element element) {
        NodeList nodeList = element.getElementsByTagName("*");
        for (int i = 0; i < nodeList.getLength(); i++) {
            String colorCode = nodeList.item(i).getTextContent();
            if (!isValidColor(colorCode)) {
                throw new IllegalArgumentException("The " + i + "th color is invalid! (" + colorCode + ")");
            } else {
                Color c = Color.web(colorCode);
                colors.add(c);
            }
        }
    }


    private boolean isValidColor(String colorCode) {
        return colorCode.matches(colorPattern);
    }

    /**
     * Read with pre-assigned xml information
     */
    public void readFile() {
        try {
            if (file == null) {
                throw new NullPointerException("Set the xml configuration file first!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        readFile(file);
    }

    public int getGridHeight() {
        if (gridHeight == -1) {
            logger.warning("gridHeight has not been changed since initialization.");
        }
        return gridHeight;
    }

    public int getGridWidth() {
        if (gridWidth == -1) {
            logger.warning("gridWidth has not been changed since initialization.");
        }
        return gridWidth;
    }

    public List<Color> getColors() {

        return colors;
    }

    public List<Integer> getCellStates() {
        return cellStates;
    }

    public SimulationType getSimulationType() {
        return simulationType;
    }

    public int getColNum() {
        if (colNum == -1) {
            logger.warning("colNum has not been changed since initialization.");
        }
        return colNum;
    }

    public int getRowNum() {
        if (rowNum == -1) {
            logger.warning("rowNum has not been changed since initialization.");
        }
        return rowNum;
    }

}
