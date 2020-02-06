package ca.controller;

import ca.model.Pair;
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
 * To use this class, a {@link File} needs to be passed in. If this
 * {@@code File} is not specified in the constructor, it is required
 * to manually set this {@code File} by calling {@link #readFile(File),
 * or with {@link #setFile(File)} and call {@link #readFile()} to start
 * reading before getting any parameters.
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
    private String colorPattern = "#......";

    private int gridWidth = -1;
    private int gridHeight = -1;

    private int rowNum = -1;
    private int colNum = -1;

    private File file;
    private SimulationType simulationType;
    private Document doc;
    private List<Integer> cellStates;
    private List<Color> colors;
    private String folderName;
    private List<String> otherParameters;

    /**
     * Create a new instance without configuration file
     */
    public SimulationConfig() {
        cellStates = new ArrayList<>();
        colors = new ArrayList<>();
        otherParameters = new ArrayList<>();
    }

    /**
     * Create a new instance with known configuration file
     * @param file          configuration XML file
     * @throws Exception    when the input file does not fulfill
     *                      the requirement
     * @see #setFile(File)
     * @see #readFile
     */
    public SimulationConfig(File file) throws Exception {
        this();
        setFile(file);
        readFile();
    }

    /**
     * Pass in simulation configuration file
     * @param file          configuration XML file
     * @throws Exception    when the input is not a XML file
     */
    public void setFile(File file) throws Exception {
        String fileName = file.getName();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, file.getName().length());
        if (!fileExtension.equals("xml")) {
            this.file = null;
            throw new Exception("This is not an XML File!");
        }

        this.file = file;
    }

    /**
     * Read with pre-assigned XML information
     * @throws Exception    when the input file is not set
     *                      or does not fulfill requirement
     * @see #readFile(File)
     */
    public void readFile() throws Exception {
        if (file == null) {
            throw new NullPointerException("Set the XML configuration file first!");
        }

        readFile(file);
    }

    /**
     * Read all required information for simulations
     * @param file          the XML configuration file
     * @throws Exception    When the input XML file does not fulfill
     *                      the required format
     * @see #setFile(File)
     * @see #getElementFromNode(String)
     */
    public void readFile(File file) throws Exception {
        setFile(file);

        doc = generateDocument();

        try {
            Element elementOthers = getElementFromNode("parameters");
            readOtherParameters(elementOthers);
        } catch (Exception e) {
            System.out.println("Warning: " + e.getMessage());
        }

        Element elementGeneral = getElementFromNode("general");
        folderName = getNodeContent(elementGeneral, "inFolder");
        assignSimulationType(elementGeneral);
        Element elementInitialStates = getElementFromNode("initialStates");
        assignGridStates(elementInitialStates);
        Element elementColors = getElementFromNode("colors");
        assignColors(elementColors);
    }

    private Document generateDocument() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);

        if (!checkFileValidity(doc)) {
            throw new Exception("Invalid Simulation Configuration File! (Root node is not 'simulation')");
        }
        doc.getDocumentElement().normalize();
        return doc;
    }

    private boolean checkFileValidity(Document doc) {
        return doc.getDocumentElement().getNodeName().equals("simulation");
    }

    private Element getElementFromNode(String tagName) throws Exception {
        Node node = doc.getElementsByTagName(tagName).item(0);
        if (node == null || node.getNodeType() != Node.ELEMENT_NODE) {
            throw new Exception("XML file node '" + tagName + "' is not found!");
        }
        return (Element) node;
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
                throw new Exception("Simulation Type not defined!");
        }
    }

    //TODO: start from here
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


    private String getNodeContent(Element element, String tagName) throws Exception {
        if (element.getElementsByTagName(tagName).item(0) == null) {
            throw new Exception(tagName + " does not exist underneath " + element.getNodeName());
        }
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

    private void readOtherParameters(Element element) {
        NodeList nodeList = element.getElementsByTagName("*");
        for (int i = 0; i < nodeList.getLength(); i++) {
            otherParameters.add(nodeList.item(i).getTextContent());
        }
    }


    private boolean isValidColor(String colorCode) {
        return colorCode.matches(colorPattern);
    }


    public int getGridHeight() {
        if (gridHeight == -1) {
            errorLogger.warning("gridHeight has not been changed since initialization.");
        }
        return gridHeight;
    }

    public int getGridWidth() {
        if (gridWidth == -1) {
            errorLogger.warning("gridWidth has not been changed since initialization.");
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
            errorLogger.warning("colNum has not been changed since initialization.");
        }
        return colNum;
    }

    public int getRowNum() {
        if (rowNum == -1) {
            errorLogger.warning("rowNum has not been changed since initialization.");
        }
        return rowNum;
    }

    public List<String> getOtherParameters() {
        return otherParameters;
    }
}
