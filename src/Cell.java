import javafx.scene.Node;

public abstract class Cell {

    State state;
    Node image;

    public Cell(State initialState, Node image){

    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}


