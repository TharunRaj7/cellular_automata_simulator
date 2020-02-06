package ca.controller;

import ca.exceptions.FileNotValidException;
import javafx.scene.paint.Color;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
 *     <li>Simulation Name/Type</li>
 *     <li>Grid Size</li>
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
 * Note, {@link #cellStates} is read as a list, and thus ignores the
 * validity of dimensions. {@link #rowNum} and {@link #colNum} are given
 * based on TXT file input, and hence should be checked whether fulfill
 * the game design.
 *
 * @author Cady Zhou
 * @version 1.2
 * @since 1.1
 */
public class SimulationConfig {
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
    public void setFile(File file) throws FileNotValidException {
        String fileName = file.getName();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, file.getName().length());
        if (!fileExtension.equals("xml")) {
            this.file = null;
            throw new FileNotValidException("This is not an XML File!");
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
            throw new FileNotValidException("Set the XML configuration file first!");
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
    public void readFile(File file) throws RuntimeException, FileNotValidException,
            IOException, SAXException, ParserConfigurationException {
        setFile(file);

        doc = generateDocument();

        try {
            Element elementOthers = getElementFromNode("parameters");
            readOtherParameters(elementOthers);
        } catch (FileNotValidException e) {
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

    private Document generateDocument() throws ParserConfigurationException, IOException, SAXException, FileNotValidException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);

        if (!checkFileValidity(doc)) {
            throw new FileNotValidException("Invalid Simulation Configuration File! (Root node is not 'simulation')");
        }
        doc.getDocumentElement().normalize();
        return doc;
    }

    private boolean checkFileValidity(Document doc) {
        return doc.getDocumentElement().getNodeName().equals("simulation");
    }

    private Element getElementFromNode(String tagName) throws FileNotValidException {
        Node node = doc.getElementsByTagName(tagName).item(0);
        if (node == null || node.getNodeType() != Node.ELEMENT_NODE) {
            throw new FileNotValidException("Warning: XML file node '" + tagName + "' is not found!");
        }
        return (Element) node;
    }

    private void assignSimulationType(Element element) throws RuntimeException, FileNotValidException {
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
                throw new RuntimeException("Simulation Type not defined!");
        }
    }

    private void assignGridStates(Element element) throws FileNotValidException {
        // grid size
        String width = getNodeContent(element, "gridWidth");
        String height = getNodeContent(element, "gridHeight");

        gridWidth = convertToNumber(width);
        gridHeight = convertToNumber(height);

        // initial states
        readCellStates(getNodeContent(element, "cellConfig"));
    }

    private int convertToNumber(String s) {
        int res = -1;
        try {
            res = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.println("Cannot convert " + s + " to a numerical number: " + e.getMessage());
        }

        return res;
    }


    private String getNodeContent(Element element, String tagName) throws FileNotValidException {
        if (element.getElementsByTagName(tagName).item(0) == null) {
            throw new FileNotValidException(tagName + " does not exist underneath " + element.getNodeName());
        }
        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }


    private String fileInput(String filename) {
        String warningMessage = "IO Exception occurs during TXT file read. " +
                "Check your folder name and filename.";
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
            System.out.println(warningMessage);
        } finally {
            try {
                assert in != null;
                in.close();
            } catch (IOException e) {
                System.out.println(warningMessage);
            }
        }
        return s;
    }

    private void readCellStates(String filename) throws FileNotValidException {
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
                throw new FileNotValidException("Initial State TXT has non-digit characters: " + (int) c);
            }

            if (!endOfFirstLine) {
                colNum ++;
            }
        }

        rowNum += 2; // because it starts at -1, and the last line does not have line feed
        colNum = (colNum + 1) / 2;  // the last number of a row does not have a space follow it
    }

    private void assignColors(Element element) throws RuntimeException {
        NodeList nodeList = element.getElementsByTagName("*");
        for (int i = 0; i < nodeList.getLength(); i++) {
            String colorCode = nodeList.item(i).getTextContent();
            if (!isValidColor(colorCode)) {
                System.out.println("The " + i + "th color is invalid! (" + colorCode + ")");
                throw new RuntimeException("The " + i + "th color is invalid! (" + colorCode + ")");
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
        String colorPattern = "#......";
        return colorCode.matches(colorPattern);
    }

    public int getGridHeight() {
        if (gridHeight == -1) {
            System.out.println("Warning: gridHeight has not been changed since initialization.");
        }
        return gridHeight;
    }

    public int getGridWidth() {
        if (gridWidth == -1) {
            System.out.println("Warning: gridWidth has not been changed since initialization.");
        }
        return gridWidth;
    }

    public List<Color> getColors() {
        if (colors.size() == 0) {
            System.out.println("Warning: no valid color exists.");
        }
        return colors;
    }

    public List<Integer> getCellStates() {
        if (cellStates.size() == 0) {
            System.out.println("Warning: no initial cell states exist.");
        }
        return cellStates;
    }

    public SimulationType getSimulationType() {
        if (simulationType == null) {
            System.out.println("Warning: simulationType has not been set.");
        }
        return simulationType;
    }

    public int getColNum() {
        if (colNum == -1) {
            System.out.println("Warning: column number has not been changed since initialization.");
        }
        return colNum;
    }

    public int getRowNum() {
        if (rowNum == -1) {
            System.out.println("Warning: rowNum has not been changed since initialization.");
        }
        return rowNum;
    }

    public List<String> getOtherParameters() {
        if (otherParameters.size() == 0) {
            System.out.println("Warning: there are no other parameters");
        }
        return otherParameters;
    }
}
