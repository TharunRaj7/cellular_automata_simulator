simulation
====

This project implements a cellular automata simulator.

Names: Hannah Taubenfeld, Tharun Mani Raj, Cady Zhou

### Timeline

Start Date: 1/26/20

Finish Date: 2/2/20

Hours Spent: 7+ each

### Primary Roles

 * Hannah Taubenfeld
     - Implement ```ca.view.Main.java```, ```ca.view.GridPaneHandler.java```, ```ca.view.Styler.java```, ```ca.view.XMLReader.java```
     - Implement Segregation and Percolation ca.model.Simulation classes

 * Tharun Mani Raj
     - Implement ```ca.model.Cell.java```, ```ca.model.Grid.java```, and ```ca.model.Pair.java```
     - Implement WaTorWorld and Fire ca.model.Simulation classes

 * Cady Zhou
      - Implement ```ca.controller.Controller.java```, ```ca.controller.SimulationType.java``` and ```ca.controller.SimulationConfig.java```
     - Implement Game of Life ca.model.Simulation classes


### Resources Used

- https://www2.cs.duke.edu/courses/spring20/compsci308/
- https://coursework.cs.duke.edu/compsci308_2020spring/lab_browser


### Running the Program

Main class:

- Sets stage
- Reads in XML file from file chooser
- Sets scene
- Calls Grid class to create a grid
- Creates a GridPane and fills it will colors
- Creates/adds all necessary buttons 
- Calls step function which updates the grid from Grid class then removes the gridpane and creates a new one

Data files needed: 

* GameOfLife
    - GameOfLife1.xml
    - gol1.txt

* Segregation
    - Segregation1.xml
    - seg1.txt

* Percolation
    - Percolation1.xml
    - perc1.txt

* WaTorWorld
    - WaTorWorld1.xml
    - wtw1.txt
    
* Spreading Of Fire
    - SpreadingOfFire.xml
    - sof1.txt

Features implemented:

- File chooser to select XML file at the beginning of the simulation
- Gridpane created with different colored cells that update at every step
- Start button to start the simulation
- Stop button to pause the simulation 
- Reload File button to select a new XML file 
- Step button to perform only one step of the simulation
- Textfile and submit button to speed up the step of the simulation

### Notes/Assumptions

Assumptions or Simplifications:

- There will always be an XML file with a corresponding text file to read in the initial states
- The numbers or states used in the text file correspond to those specified in the simulation code
- The grid size will never exceed that of the scene size 

Interesting data files:

- We used a simulation template that we all followed

Known Bugs:

- A bug appears when you run the simulation and the file chooser appears, however, the simulation still 
runs and uses the XML file chosen

Extra credit:

- n/a

### Impressions

- It can be difficult when working together when it comes to merging all the code and fixing bugs. 
Although at the beginning as we all worked on different parts and then merged together it was easy, once you 
go back and make smaller changes you have to be careful of merge conflicts. Otherwise, teammates end up with
different code which can lead to even more issues. 
- Once one simulation is up and running, it is easy to debug and create other simulations. By following a similar format
and just changing the rules for how a state is determined, the rest is already all figured out. 

