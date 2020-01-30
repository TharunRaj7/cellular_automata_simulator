package ca.controller;

import ca.SimulationType;
import javafx.scene.paint.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
 * @author Cady Zhou
 * @version 1.1
 * @since 1.1
 */
public class SimulationConfig {
    String colorPattern = "#\\d{6}";

    int gridWidth = -1;
    int gridHeight = -1;

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
            default:
                throw new Exception("Simulation Type not defined!");
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

        for (Character c: s.toCharArray()) {
            if (Character.isDigit(c)) {
                cellStates.add(Character.getNumericValue(c));
            } else if (!c.equals(' ') && (int)c != 10 && (int)c != 13) {  // TODO: check whether 10 and 13 are universal
                throw new IllegalArgumentException("Initial State TXT has non-digit characters: " + (int) c);
            }
        }
    }

    private void assignColors(Element element) {
        if (element.hasAttributes()) {
            NamedNodeMap nodeMap = element.getAttributes();
            for (int i = 0; i < nodeMap.getLength(); i++) {
                String colorCode = nodeMap.item(i).getNodeValue();
                if (!isValidColor(colorCode)) {
                    throw new IllegalArgumentException("The " + i + "th color is invalid!");
                } else {
                    colors.add(Color.web(colorCode));
                }
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
        return gridHeight;
    }

    public int getGridWidth() {
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
}
