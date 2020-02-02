package ca.simulations;


/**
 * This class is the implementation of Segregation. The rules
 * of Segregation, according to <a href="https://www2.cs.duke.edu/courses/spring20/compsci308/assign/02_simulation/nifty/mccown-schelling-model-segregation/"
 * this page </a>, which are:
 * <ul>
 *  <li>Any agent cell with x% or greater similar neighbors is satisfied.</li>
 *  <li>Any agent cell with less than x% similar neighbors is unsatisfied.</li>
 *  <li>Any unsatisfied agent can more to any vacant cell.</li>
 * </ul>
 *
 * To run the simulation, call {@link #runOneStep()} inside the
 * animation loop.
 */
public class WaTorWorld extends Simulation {

    public WaTorWorld (){
        //empty constructor
    }
    @Override
    public void runOneStep() {
        System.out.println("test");
    }
}
