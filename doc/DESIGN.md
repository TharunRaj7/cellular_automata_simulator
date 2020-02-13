# ca.model.Simulation Design Final
### Names: Hannah Taubenfeld (hbt3), Tharun Raj Mani Raj (tm272), Cady Zhou (zz160)

## Team Roles and Responsibilities

 * Hannah Taubenfeld: My responsibility was writing the View portion of the model. This included creating all the visuals with the scene and stage. Within the scene contained the gridpane which filled with colors depending on the states as well as updated at every step. There were also several buttons that I implemented that had a range of features, and sliders that changed the number of rows and columns within the grid. Beyond that, I was also responsible for creating a graph that showed the quanitities of each state at each step. Finally, I was responsible for 2-3 simulation implementations. 

 * Tharun Raj: My reponsibility was to build the grid model with the respective methods that goes along with the grid class. I also was responsible to add the additional features such as running multiple simulations together and the functionality to change the states of individual cells at realtime. I also was responsible for 2 simulation implementations.

 * Cady Zhou: My responsibility was to write a class for the XML and TXT user input and handle possible exceptions. I also added the feature to allow different input methods. I was also responsible for planning out the project structure and addding/refactoring abstract classes whenever necessary. This part included changing the Grid class to use hashcode and refactoring it into an abstract GridBase class that allowed extension, implementing a way to get different neighbors efficiently, and wrote the abstract Simulation class. 


## Design goals

#### What Features are Easy to Add
- New UI features: ```Styler.java``` makes it easy to add new UI componenets by simply passing in necessary parameters
- Colors of the simulation: Users have the control to initialize the simulation with cells of different colors 
- New Neighbor Type: any new types of neighbors can be easily added by implementing ```NeighborsHelper.java```
- Different Grid Location Type: By extending ```GridBase.java```, a different coordinate system can be set up for other grid location types
- New Simulations: additional simulaitons can be easily implemented by extending ```Simulation.java```
- Additional simulation parameters: The design of the XML file and reading algorithm allows any number of additional parameters to be added

## High-level Design

#### Core Classes
- ```ca.controller.Controller.java```
    - Purpose: starts the animation and enables ability to pause animation and run one step of the animation as well as set the speed of the steps
    - Interaction: all of the methods in the class are used in ```ca.view.SimulationView.java``` and an instance of ```ca.simulations.Simulations.java``` is created 
- ```ca.controller.SimulationConfig.java```
    - Purpose: to read in all the necessary information from an XML file and return the needed information for other classes, it also assigns a simulation type to the XML file read in which enables the correct simulation class to be used
    - Interaction: all the information collected from reading the XML is referenced and used again in ```ca.view.SimulationView.java```
- ```ca.model.grids.Grid.java```
    - Purpose: to take all the cells and create one grid made up of those cells, also has helper methods that assist the view portion of the project to update the visuals 
    - Interaction: calls on ```ca.model.Cell.java``` and is used in almost every other class as an instance to make necessary changes
- ```ca.model.Cell.java```
    - Purpose: to initialize each cell in the grid given a state and shape and create getters and setters to be used elsewhere in the project
    - Interaction: several instances of this class are used in ```ca.model.grids.Grid.java``` to form a grid
- ```ca.simulations.Simulations.java```
    - Purpose: to establish the "rules" of each simulation which are chosen to be executed depending on which XML file is read in
    - Interaction: a simulation instance created in ```ca.view.SimulationView.java``` every time the simulation begins
- ```ca.view.Styler.java```
    - Purpose: to create UI features such as buttons, sliders, and text boxes in an effort to reduce duplicate code
    - Interaction: instance was created in ```Main.java``` and all the necessary parameters were passed in as the feature was saved in ```Main.java``` and then added to the root
- ```ca.view.SimulationView.java```
    - Purpose: controls the UI components and separates the view from the rest of the project model
    - Interaction: an instance is created in ```Main.java``` and this class uses different simulations to create a new simulation instance
- ```Main.java```
    - Purpose: to set the stage and create a scene with all the UI features and design. This starts the simulation and effectively in some way calls all the other classes to execute
    - Interaction: directly interacts with ```ca.view.SimulationView.java``` and``ca.view.Styler.java``


## Assumptions that Affect the Design

- There will always be an XML file with a corresponding text(s) file to read in the initial states
- The numbers or states used in the text file correspond to those specified in the simulation code
- The grid size will never exceed that of the scene size.
- For the segregation and spreading of fire simulation, the percent that makes an agent satisfied or probability that a tree will burn down, respectively, will be hardcoded into the XML file.
- The only shapes that the grid will be are triangles, hexagons, and squares.
- We assumed that states would be finite integers instead of a floating point.

#### Features Affected by Assumptions
- Assuming that the grid size would not exceed the scene size made it difficult to implement a grid that dynamically grew or shrank in size 
- For the segregation and spreading of fire simluations, the user cannot change the number nor update it while the simulation is running. 




## New Features HowTo

#### Easy to Add Features
- If we were to add new UI features, we could use the ```Styler.java```  class. It makes it easy to add new UI componenets by simply passing in necessary parameters. It is also possible to add new methods in this class to implement other UI components. We would be able to customize the UI experience since all the styler components are located in a centralized class.

- Colors of the simulation: the xml has a color section, which allows any number of colors to be passed in. The user can simply customize the color scheme of the simulation by changing colors passed in. It is also easy to add a new color if there is an additional state. 


- New Neighbor Type: The ```NeighboringType``` Enum contains a list of currently exsited neighboring type. The row and column delta values are coded in ```NeighborsHelper.java```. To create any new neighboring types, implement these two classes and call ```getNeighborsByIndex``` from ```GridBase.java```, which accepts row and col delta values, and returns neighbors. 


- Different Grid Location Type: By extending ```GridBase.java```, a different coordinate system can be set up for other grid location types. For example, a hexagonal grid has different numbers of column on its even and odd rows. We can implement a ```IrregularGrid.java``` to reflect this difference.

- We could easily add new simulation implementations by extending the ```Simulation.java``` class. We would have to override the determineCellState function which handles the logic to update the cells on the grid on each step of the simulation.

- Additional simulation parameters: The design of the XML file and reading algorithm allows any number of additional parameters to be added. The ```SimulationConfig.java``` reads in all additional parameters as a list. 

- The grid class (GridBase.java) is made abstract so that constructing varying concrete grids with different shapes would be easier. We could make a concrete class and override the createGridModel method to build grids with irregular shapes.

#### Other Features not yet Done
- One of the features that we could not complete on time was the irrgular grid implementation. However, we have added necessary classes to add this feature in a future build. For example, we have the irregular grid class to add this implementation. We also have the NeighborsHelper.java class and the CellShape.java enummerated class which would make adding this feature straightforward since we already have the template to do so. The grid class is also made abstract.

- One other feature that we could have added was the was the ability to save the current state of the grid on a XML file and load that file again to start running a simulation from the saved state. We coudld do this by adding a method that handles this functionality, essentially by looping through the cells on the grid and writing them to a XML file. This file could be saved in the same folder the original XML file was loaded from and add the date/time to the name of the file. An additional button could be added to save the state.

