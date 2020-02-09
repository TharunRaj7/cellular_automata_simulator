package ca.controller;

import ca.exceptions.FileNotValidException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class InitialStateHandler {
    private String folderName;
    private GridStatus gridStatus;

    public static final String GRID_PREFIX = "grid";
    public static final String LIST_OF_LOCATIONS_PREFIX = "list";
    public static final String RANDOM_PREFIX = "rand";


    public InitialStateHandler() {
        gridStatus = new GridStatus(-1, -1, new ArrayList<>());
        folderName = null;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public void readCellStates(String nodeName, int stateLowerBound, int stateUpperBound) throws Exception {
        String type = nodeName.split("_")[0];
        switch (type) {
            case GRID_PREFIX:
                readFromGridFormat(nodeName);
                break;
            case LIST_OF_LOCATIONS_PREFIX:
                readFromListFormat(nodeName);
                break;
            case RANDOM_PREFIX:
                createRandomStates(nodeName, stateLowerBound, stateUpperBound);
                break;
            default:
                throw new Exception("Cannot initialize states due to the invalid '" + SimulationConfig.INITIAL_STATES_FILE_TAG + "' tag");
        }
    }

    private void createRandomStates(String filename, int stateLowerBound, int stateUpperBound) {
        Scanner scanner = initializeGridStatus(filename);
        Random random = new Random();

        if (stateUpperBound == 0 && stateLowerBound == 0) {
            System.out.println("Warning: all cell states are 0 because stateBound has not been set. ");
        }

        while (scanner.hasNext()) {
            int row = Integer.parseInt(scanner.next().substring(1));
            String colString = scanner.next();
            int col = Integer.parseInt(colString.substring(0, colString.length() - 1));
            int state = random.nextInt(stateUpperBound - stateLowerBound + 1) + stateLowerBound;

            gridStatus.getInitialState().set(row * gridStatus.getNumOfCol() + col, state);
        }

    }

    private void readFromListFormat(String filename) {
        Scanner scanner = initializeGridStatus(filename);

        while (scanner.hasNext()) {
            int row = Integer.parseInt(scanner.next().substring(1));
            String colString = scanner.next();
            int col = Integer.parseInt(colString.substring(0, colString.length() - 1));
            int state = scanner.nextInt();

            gridStatus.getInitialState().set(row * gridStatus.getNumOfCol() + col, state);
        }
    }

    private Scanner initializeGridStatus(String filename) {
        String s = fileInput(filename);
        Scanner scanner = new Scanner(s);

        gridStatus.setNumOfRow(scanner.nextInt());
        gridStatus.setNumOfCol(scanner.nextInt());
        gridStatus.initializeEmptyStates();

        return scanner;
    }

    private void readFromGridFormat(String filename) throws FileNotValidException, RuntimeException {
        String s = fileInput(filename);

        List<Integer> cellStates = gridStatus.getInitialState();
        int rowNum = gridStatus.getNumOfRow();
        int colNum = gridStatus.getNumOfCol();

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

        gridStatus.setNumOfRow(rowNum);
        gridStatus.setNumOfCol(colNum);
        gridStatus.setInitialState(cellStates);
    }

    private String fileInput(String filename) throws RuntimeException{
        if (folderName == null) {
            throw new RuntimeException("FolderName has not been set yet!");
        }

        String warningMessage = "IO Exception occurs during TXT file read. " +
                "Check your folder name and filename.";

        filename = "." + File.separatorChar + "data" + File.separatorChar + folderName + File.separatorChar + filename;
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

    public int getNumOfCol() {
        return gridStatus.getNumOfCol();
    }

    public int getNumOfRow() {
        return gridStatus.getNumOfRow();
    }

    public List<Integer> getInitialState() {
        return gridStatus.getInitialState();
    }
}
