package Controller;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;


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
    File file;
    SimulationType simulationType;

    public SimulationConfig() {
    }

    /**
     * Create a new instance with known configuration file
     * @param file  configuration xml file
     */
    public SimulationConfig(File file) {
        this.file = file;
    }

    public void readFile(File file) {
        this.file = file;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            if (!checkFileValidity(doc)) {
                throw new Exception("Invalid XML file! Please follow the template");
            }

            NodeList general = doc.getElementsByTagName("general");


            NodeList nodeList = doc.getElementsByTagName("student");
            for (int itr = 0; itr < nodeList.getLength(); itr++) {
                {
                    Node node = nodeList.item(itr);
                    System.out.println("\nNode Name :" + node.getNodeName());
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) node;
                        System.out.println("Student id: " + eElement.getElementsByTagName("id").item(0).getTextContent());
                        System.out.println("First Name: " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
                        System.out.println("Last Name: " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
                        System.out.println("Subject: " + eElement.getElementsByTagName("subject").item(0).getTextContent());
                        System.out.println("Marks: " + eElement.getElementsByTagName("marks").item(0).getTextContent());
                    }
                }
            }
        catch(Exception e)
            {
                e.printStackTrace();
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkFileValidity(Document doc) {
        return doc.getDocumentElement().getNodeName().equals("simulation");
    }

    /**
     *
     */
    public void readFile() {
        if (file == null) {
            throw new NullPointerException("Set the xml configuration file first!");
        }
        readFile(file);
    }

}
