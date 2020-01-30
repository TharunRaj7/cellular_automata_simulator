package ca.model;

import javafx.scene.Node;

public class Cell {
    int state;
    Node node;

    public Cell(int state, Node node){
        this.state = state;
        this.node = node;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}


