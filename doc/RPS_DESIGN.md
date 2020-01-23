### Name: Hannah Taubenfeld, Tharun Raj Mani Raj, Cady Zhou
### NetID: hbt3, tm272, zz160

## RPS Design
- Have a player class, a game class, and another class responsible for display. Pass players into the controller to generate game results, and pass the result to the display  

- Have multiple players/single player play with a bot. For player and a bot, we can write a method to auto-generate a result in the player class, OR have another bot class extends from the player class

- Have a "weapon" class, and other classes inherit from it OR have one weapon class, but pass in parameters for different weapons

#### Weapon implementation
*The first option*  
**Pros:** Make one change in the parent class to change all behaviors, use OOP   
**Cons:** Have too many classes when you have a lot of weapons

*The second option*   
**Pros:** Easier to change, easier to create a new weapon, many public methods  
**Cons:** Disorganized, cannot change behavior for individual weapon

Detailed description: 
- A abstract class ```Weapon.java``` with two sets of weapons that it wins or loses against, getters and setters for sets, a toString function to print its attributes for debugging purpose
- Several different weapon classes inherit the abstract class
- When the game is played, draw a random/cheated/calculated weapon for the bot
- Asks the player to input his/her choice 
- A controller that determine who wins 

