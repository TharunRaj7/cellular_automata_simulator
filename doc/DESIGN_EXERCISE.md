### Name: Hannah Taubenfeld, Tharun Raj Mani Raj, Cady Zhou
### NetID: hbt3, tm272, zz160

## Code Review
We reviewed one game code and discussed how we can refractor it.

## High Level Design of ca.model.Simulation 
- Talked about our understanding of ca.model.Simulation 
- Configuration: set up rules for states, set up the UI, determine time of simulation 
- ca.model.Simulation: iterate through the grids and apply rules, do the number of steps you wanna take 
- Visualization: grid, visually show states and changes in every step   
- Data Structure of grids: HashMap<ca.model.Pair<Integer, Integer>, State>, where ca.model.Pair is the coordinate, and also a method to check if the ca.model.Pair is valid
- ca.model.Cell know about its neighbor: by using ca.model.Pair +1/-1 row and col
- ca.model.Simulation details: BFS with one queue 
- Relationship between a cell and simulation: ca.model.Simulation gets the state of the cell, and updates states of cells around it 
- What is the grid: a class that contains cells
- ca.model.Grid behavior: setup initial states of cells, change state of cells, read the rule
- Who needs to know about it: only the main class who runs the game 
- Info about simulation in the configuration file: rule, type of simulation, steps, size of grid, initial states
- How is UI updated: change color of specific cells that are changed 

## CRC 
![crc_1](crc_1.jpg)
![crc_2](crc_2.jpg)
![crc_3](crc_3.jpg)
![crc_4](crc_4.jpg)



