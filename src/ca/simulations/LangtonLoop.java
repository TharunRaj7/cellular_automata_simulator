package ca.simulations;

import ca.helpers.NeighboringType;
import ca.model.Grids.GridBase;

/**
 * This class is the implementation of LangtonLoop. The rules
 * for LangtonLoop, according to <a href="http://lslwww.epfl.ch/pages/embryonics/thesis/Chapter3.html"
 * are:
 * <ul>
 * if fewer than two elements in the neighborhood are alive, the next state is dead (death by starvation);
 * if more than three elements in the neighborhood are alive, the next state is dead (death by overcrowding);
 * if exactly three elements in the neighborhood are alive, then the next state is alive (birth);
 * otherwise (i.e., if exactly two of the elements in the neighborhood are alive) the next state is equal to the current state (survival).
 *
 * To run the simulation, call {@link #runOneStep()} inside the
 * animation loop.
 */
public class LangtonLoop extends Simulation{
    private final int DEAD = 0;
    private final int ALIVE = 1;

    public LangtonLoop(GridBase grid) {
        super(grid);
    }


    @Override
    protected int determineCellState(int r, int c) {
        int neighboursAlive = getNeighborStateNumber(r,c, NeighboringType.ALL, ALIVE);
        if (neighboursAlive < 2 || neighboursAlive > 3){
            return DEAD;
        }
        else {
            return (neighboursAlive == 2) ? grid.getCellState(r,c) : ALIVE;    //return dead if 2,  if 3
        }
    }
}

/*if fewer than two elements in the neighborhood are alive, the next state is dead (death by starvation);
if more than three elements in the neighborhood are alive, the next state is dead (death by overcrowding);
if exactly three elements in the neighborhood are alive, then the next state is alive (birth);
otherwise (i.e., if exactly two of the elements in the neighborhood are alive) the next state is equal to the current state (survival).*/