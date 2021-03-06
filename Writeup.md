# Zooreka

#### January 30, 2017

Zooreka is a board game for kids, created by a popular board game company called Cranium. My goal is to write a program in Java that allows one to play this game, with up to 4 players, over the network.

I'm assuming that many people don't know what Zooreka is, and if they do, they most likely don't know the rules. The following are images of the rule book (courtesy of manual.guru):

![Page 1 of Zooreka Manual](https://i.imgur.com/q5zNQBQ.png "Page 1 of Zooreka Manual")
![Page 2 of Zooreka Manual](https://i.imgur.com/zMYychI.png "Page 2 of Zooreka Manual")

These are the basic rules of how to play the board game. However, playing on the computer would require a little bit of flexibility in these rules, so the final implementation of the game into my program may be a little different than you're used to at home. The following will be a basic list of rules I plan to follow when creating this game:

- The game will set up everything needed for the players, including board, tip cards, virtual dice, movers, resource cards, habitats, lucky tokens, selection tokens, Blue Skies cards, and Stormy Weather cards
- For simplicity, the game will assign players colors
- Each player will receive two food cards and one animal card
- For simplicity, the game will decide who rolls first
- Before a roll, each player may choose to move their selection tokens to a resource (either the food, animal, or shelter) option
- Each turn, the player whose turn it is will roll two dice: one for the number of spaces to move, and one resource that will be given to any player whose selection token matches that of the resource dice roll
- There are six possible spaces to land on the board:
  1. Lucky Day: gives the player whose turn it is an extra selection token to increase resource yield
  2. Blue Skies: the player draws a Blue Skies card, which usually helps their situation in the game
  3. Stormy Weather: the player draws a Stormy Weather card, which usually hurts their situation in the game
  4. Team Up: all players roll the resource die, and each player who matches the original roller earns one of that resource for them and the original roller
  5. Roll Again: The player rolls again
  6. Trading Post: The player may exchange resources for other resources (forward or backward) in the ratios they see on the tip cards:
     - 1 Animal card = 3 Food cards
     - 1 Shelter card = 2 Animal cards
     - 1 Habitat = 1 Shelter card + 2 Animal cards + 4 Food cards
- Once a player has 4 habitats, they win the game

Those are the basic rules of the game.

Tomorrow I will outline how I expect to implement all this into a Java program.

#### January 31, 2017

Yesterday, I outlined the basic rules for the board game Zooreka. Today, I plan on outlining how I will implement these rules into my Java game. Because I realize I originally forgot to provide some photos of the Zooreka game for reference, here are some (I apologize for the quality, these are not my images):

![Picture of Zooreka game](http://3.bp.blogspot.com/-gj3DHDc4TVE/UT_uGeRMyqI/AAAAAAAAFPE/rJQaelMk_Oc/s1600/IMG_3238.JPG "Picture of Zooreka game")
![Picture of Zooreka game](https://i.ebayimg.com/thumbs/images/g/H~sAAOSwWxNYp4kN/s-l225.jpg "Picture of Zooreka game")
![Picture of Zooreka game](https://guideimg.alibaba.com/images/shop/85/10/29/1/cranium-zooreka-board-game-build-your-ultimate-zoo-for-kids-100-complete_1812981.JPG "Picture of Zooreka game")

I plan on keeping most of the game activities on the server, while the player actions and GUI are on the client's side. In the server class, most of the player data will be saved in a Player class.

There can be up to 4 players in the game of Zooreka. The Player class will include the following data: player color (String), number of food cards (int), animal cards (int), shelter cards (int), habitats (int), and Trading Post cards (int; more on these later), which resource their selection token(s) are on (String ArrayList), whether or not they are in possession of the lucky token (boolean), mover (Mover Object) probably (I haven't decided exactly how I am going to do this), and a player ID number so that I can keep track of turn order, which will also be used as the player index in a Players array that I will use for the game.

In addition to the player data, the server will handle rolling of the dice. The game has 2 dice; one for resources and one for the number of spaces to move. The six-sided die needed for number of spaces to move is standard, but the resources die is a little different. There are three sides that have the food icon, two sides that have the animal icon, and one side that has the shelter icon. These dice should be fairly easy to implement.

Tomorrow I will detail a couple more elements of the game that need to be planned out before I begin writing code.

#### February 1, 2018

Yesterday, I described a little into detail about how I plan to implement some elements of the Zooreka game into my Java program. I introduced the fields that I plan on including in my Player class, and how the dice rolling will work in the game.

I mentioned earlier that I plan on keeping most of the game data server-side, so today I want to document a little more information which parts of the game will be server-side and which we be client-side. I also want to go into detail on how they will communicate with each other.

The game server will also handle distribution of Blue Skies and Stormy Weather cards. These are cards that affect your game in its current state, as in "gain 3 food cards" or "if you have more than 3 animal cards, lose 2". I plan to just have a BlueSkies and StormyWeather class that have ArrayLists with all card possibilities, so that when a player lands on one of these spaces, a random card will be drawn and the change will affect the user.

The server will also be responsible for handling the TeamUp events. The TeamUp occurs when someone lands on the TeamUp space. When this happens, every player rolls the resource dice, and whoever rolls the same as the original roller, earns one of that resource for themself and the original roller. I am going to have a method for TeamUp that handles all of this on the server side.

Those are all the points I wanted to touch on that would be controlled by the server.

However, there are some elements of the game that I plan to dedicate to the client side of the program.

For example, the game's GUI code will all be client-side. I plan to use JavaFX to display all the visual elements for the game. Just like the real-life game, the spaces for the movers will be on the outside, and all the real-time game information (e.g. resource cards, habitats, tip card, etc.) will all be in the middle of the view. I will be using a model-view-controller design pattern, with the view and controller being client-side, while the server keeps the game model.

The last aspect of the program I want to highlight my plans for is how the client and server will communicate with each other.

The server will be the central location for all game activities, as I mentioned earlier. The clients will send strings through a PrintWriter to the server, which will use a BufferedReader to read them, then change the model based on the change. The server will then distribute a change through a PrintWriter to all the players, who then can update their view based on the move that was made.

The following is an example of the communication protocol I plan on implementing into the game:

Server ––> All Players: "1 roll" [Player 1's turn]

Player 1 ––> Server: "1 roll" [Player 1 rolls dice]

*Server rolls dice: 4 and food; Server moves Player 1 four spaces and gives everyone whose selection token is on food a food resource card*

Server ––> All Players: "1 roll 4 food[24]" [Player 1 rolls a 4 and food resource, so Players 2 and 4 each get a food card]

*Player 1 lands on Blue Skies: Server draws Blue Skies card and applies it to Player 1: "Gain 2 food cards"*

Server ––> All Players: "1 BS[2f]"

*Clients add 2 food cards to Player 1's resources according to Blue Skies card*

Player 1 ––> Server: "end" [Player 1 ends turn]

*Server changes currentPlayer to 2*

Server ––> All Players: "2 roll" [Player 2's turn]

This is how the clients and server in my game will communicate.

I am pretty sure I outlined everything so far that I want to before I begin writing code. If there is anything more I want to add, you will see it in the future writeups. Also, if there is anything I plan on editing that has already been written, I will edit it and leave a comment at the bottom of that day's writeup, noting what was changed, and when and why it was modified.

Tomorrow, unless I think of more planning that I need to do, I will probably begin to write the code for the game, starting with file structure setup and then probably writing the Player class on the Server side.

#### February 2, 2018

I have outlined the basic rules and gameplay of the family board game "Zooreka", and I explained how I intend to turn this game into a Java program. It is time to start writing code to put this into effect.

For this program, I will be using IntelliJ IDEA to develop. I began by creating a new project. I already had a Zooreka project folder, because that's where I saved my Writeup.md that I keep a daily log for so I can have this information in the GitHub repo.

![Create new Project](https://ethanmuz.files.wordpress.com/2018/02/s1.png "Create new Project")

It's time to set up my file structure for the server and client. Initially, I am going to just create a 'server' and 'client' folder inside my 'src' folder so that I can keep the classes I am working on separate. 

![Initial file structure](https://ethanmuz.files.wordpress.com/2018/02/s.png "Initial file structure")

I then began with the class that I planned to start with, the Player class on the server side. This will hold most of the game data for gameplay. Most of the game is built upon ownership of resources and habitats, and also the earning/trading of these items. Dynamic player data will all be handled in this class, so it is an important class to write before writing the shell for modifying the data.

![Created Player class](https://ethanmuz.files.wordpress.com/2018/02/s3.png "Created Player class")

Now I am going to fill my Player class up with all the instance variables needed for the game (that I know of so far, of course). These have pretty much all been mentioned before in my writeup when I discussed my plan for the Player class (which is the reason for a plan in the first place I guess).

![Declared some instance variables](https://ethanmuz.files.wordpress.com/2018/02/s4.png "Declared some instance variables")

So while creating these fields, I realized that I haven't decided how am I going to implement the player's mover objects on the board. I am going to make a temporary decision that it will be part of the GUI, and that the client will just tell the server what space they land on, without the server concerning itself with which space the player is inhabiting on the board.

I also realize I never discussed the "trading post cards" that I mentioned earlier (a.k.a "Keeper Cards", hence the variable 'keeperCards' in my Player class). A player gains one of these cards from a Blue Skies draw, it is the only card that is obtained from Blue Skies or Stormy Weather (or any card period if you discredit resource cards). This card essentially lets you visit the Trading Post after anyone's turn, where you'd normally have to land on a Trading Post space in order to do any trading. This is why I have a 'keeperCards' variable for an instance variable. I plan to have this be a button on the GUI for anyone who is in possession of one Keeper Card or more, so they can activate it during anyone's turn, and then they can do trading immediately after that person's turn is over.

Also, I currently have 'playerColor' as a String, but in the future I may have to make it a Color, so that I can do easy setting to the player's mover for the GUI. That may not even be something I have to do though, if I just hardcode the color of the movers and just assign it to a player based on the Player's ID number. So playerColor may not even be necessary later on.

Now, it is time to write the constructor for Player. This should not be difficult, because we know almost all the starting values except playerID (which will be assigned when the client joins the server), and their resource selections. It would be awkward to have the players decide which resource selection they want in the server lobby, so I may just force all the players to choose right before the first dice roll of the game.

The rules of the game state that a player starts out with 2 food cards, an animal card, and an initial selection token. I will set these variables accordingly, and the rest of the variables will be empty. For example, 'shelterCards', 'habitatsEarned', and 'keeperCards' will equal 0, while luckySelection will be null, and luckyDay will be false.

Here I built the constructor, and did some other things too, like comment the instance variables, and make them private too:

![Built constructor](https://ethanmuz.files.wordpress.com/2018/02/s5.png "Built constructor")

I am going to now write the methods for the player class. The first method I want to write is the getter for the Player ID, a.k.a a function that returns the 
player's ID.

```java
public int getID(){
    return this.playerID; //Returns this Player's ID number
}
```

Simple function that supplies the Player's ID. Now I am going to add the getters for the resource cards, habitats, keeper cards, resource selection, and lucky day token and selection:

```java
public int getFood() {
    return this.foodCards; //Returns number of food cards this Player has
}

public int getAnimalCards() {
    return this.animalCards; //Returns number of animal cards this Player has
}

public int getShelterCards() {
    return this.shelterCards; //Returns number of shelter cards this Player has
}

public int getHabitats() {
    return this.habitatsEarned; //Returns number of habitats this Player has
}

public int getKeeperCards() {
    return this.keeperCards; //Returns number of keeper cards this Player has
}

public String getSelection() {
    return this.selection; //Returns the resource this Player has selected (as a String "food", "animal", or "shelter")
}

public boolean isLuckyDay() {
    return this.luckyDay; //Returns whether or not it is this Player's lucky day
}

public String getLuckySelection() {
    return this.luckySelection; //Returns the lucky resource this Player has selected (if one; as a String "food", "animal", or "shelter")
}
```

Now all my getters are coded, and I'm ready to begin writing my setters. However, I am not going to write the setters for the resources and habitats as you'd expect. I am going to write a changeResources method that takes int parameters for food, animal, and shelter cards and habitats. The reason for this is I want to change the amount of resources a player has, I usually want to modify more than one type of resource. This occurs in trading, and in many Blue Skies and Stormy Weather cards. Here is what it is going to look like:

```java
/**
 * Changes the number of resources this player has, based on parameters
 * Does not let a player have a negative number of resource cards/habitats, naturally
 * 
 * @param food Number of food cards to be added (-subtracted) from this Player
 * @param animal Number of animal cards to be added (-subtracted) from this Player
 * @param shelter Number of shelter cards to be added (-subtracted) from this Player
 * @param habitats Number of habitats to be added (-subtracted) from this Player
 */
public void changeResources(int food, int animal, int shelter, int habitats){
    if(((this.getFood() + food) >= 0)
    && ((this.getAnimalCards() + animal) >= 0)      //Checks to see if this move is valid (i.e. doesn't let the player
    && ((this.getShelterCards() + shelter) >= 0)    //have negative values)
    && ((this.getHabitats() + habitats) >= 0) )
    {
        this.foodCards += food;             //Applies the change to all resources
        this.animalCards += animal;
        this.shelterCards += shelter;
        this.habitatsEarned += habitats;
    }
 }
```

This method is much better than having individual setters for each value. Now I am going to write the setters––that's right, plural––for keeperCards. These will only ever increase or decrease in increments of 1, I am just going to have an addKeeperCard() method that adds one keeper card, and a subtractKeeperCard() method that subtracts one keeper card, if the Player has one. Here is the code:

```java
public void addKeeperCard(){
    this.keeperCards++;
}

//Subtracts one keeper card, if it can
public void subtractKeeperCard(){
    if (this.getKeeperCards() > 0)  //Checks to see if you can even subtract one
        this.keeperCards--;
}
```

Then the rest of the setters should be standard:

```java
//Sets selection
public void setSelection(String s){
    this.selection = s;
}

//Sets luckySelection
public void setLuckySelection(String l){
    this.luckySelection = l;
}

//Sets luckyDay
public void setLuckyDay(boolean l){
    this.luckyDay = l;
}

//Sets player ID; only use when player leaves game lobby
public void setPlayerID(int id){
    this.playerID = id;
}
```

That should be the conclusion of the Player class...for right now. There might be some things to add later on.

My current plan for tomorrow is to start writing the server GUI, and if I have time, some of the game actions that the client commands. But the Joywave concert at RIT is tomorrow, so we'll see how much time I have.

#### February 13, 2018

I know I haven't worked on this in a while, I've been pretty bogged down with schoolwork recently. Now I'm back, so I should be able to get a lot of this project done in the near future.

Today I want to get as much of the server done as possible. Unlike the Player class, I am going to write the methods and instance variables as I go for each feature the Server will have. The Player class has structure that was defined already in the game's rules, and it was mostly just going to hold Player data with minimal moving parts. Since the Server is going to be running with more complex methods, it is better to add the fields and methods as I go because they are going to change as I implement more features.

To start, I am going to create the Game class in my project structure. Obviously, the Game class will go in the server package, as so:

![Added Server to project structure](https://ethanmuz.files.wordpress.com/2018/02/s7.png "Added Server to project structure")

The first features I want to implement are Blue Skies and Stormy Weather. Each of these can have their own methods, that take a Player and randomly assign resource cards, based on what "card" was drawn, to their instance of the Player class.

As written, the Blue Skies method does exactly this; here is the code:

```java
public void BlueSkies(Player player){
    Random random = new Random(); int card = random.nextInt(9);
    switch (card) {
        case 0:
            player.addKeeperCard(); //Player draws a 'keeper' Blue Skies card
            break;
        case 1:
            player.changeResources(0, 0, 1, 0); //Player's Blue Skies card gives them one shelter card
            break;
        case 2:
            player.changeResources(2, 2, 0, 0); //Player's Blue Skies card gives them two animal cards and two food cards
            break;
        case 3:
            player.changeResources(1, 2, 0, 0); //Player's Blue Skies card gives them two animal cards and one food card
            break;
        case 4:
            player.changeResources(0, 2, 0, 0); //Player's Blue Skies card gives them two animal cards
            break;
        case 5:
            player.changeResources(3, 1, 0, 0); //Player's Blue Skies card gives them one animal card and three food cards
            break;
        case 6:
            player.changeResources(2, 1, 0, 0); //Player's Blue Skies card gives them one animal card and two food cards
            break;
        case 7:
            player.changeResources(1, 1, 0, 0); //Player's Blue Skies card gives them one animal card and one food card
            break;
        case 8:
            player.changeResources(3, 0, 0, 0); //Player's Blue Skies card gives them three food cards
            break;
    }
}
```

I want to do the same thing with the Stormy Weather, however I obviously do not want the Player to be able to have negative resource values. If you recall, we fixed this in the changeResources method so that this was impossible. This way, I can do the same thing as Blue Skies method except make the parameter values negative, which subtracts resources from the Player, as long as they will not have negative values. This is the same way the Stormy Weather cards are implemented into the board game as well.

Here is the code for Stormy Weather method:

```java
public void StormyWeather(Player player){
    Random random = new Random(); int card = random.nextInt(7);
    switch (card) {
        case 0:
            player.changeResources(-1, -2, 0, 0); //Player's Stormy Weather card takes away two animal cards and one food card
            break;
        case 1:
            player.changeResources(0, -2, 0, 0); //Player's Stormy Weather card takes away two animal cards
            break;
        case 2:
            player.changeResources(-2, -1, 0, 0); //Player's Stormy Weather card takes away one animal card and two food cards
            break;
        case 3:
            player.changeResources(-1, -1, 0, 0); //Player's Stormy Weather card takes away one animal card and one food card
            break;
        case 4:
            player.changeResources(-3, 0, 0, 0); //Player's Stormy Weather card takes away three food cards
            break;
        case 5:
            player.changeResources(-2, 0, 0, 0); //Player's Stormy Weather card takes away two food cards
            break;
        case 6:
            player.changeResources(-1, 0, 0, 0); //Player's Stormy Weather card takes away one food card
            break;
    }
}
```

Now that the Blue Skies and Stormy Weather methods are written, I want to write the Lucky Day method. The way this will work is that the Player will pick a resource selection for their Lucky Token, then the method I write will accept the Player and selection as parameters. The method will remove the Lucky Token from any other player who has it (if anyone has it) and give the Player the lucky token, along with applying their lucky selection. Keep in mind this will only happen if the Player does not currently have Lucky Day.

To do this, I will have to create the ArrayList of Players in the Game class, so I can iterate it in the luckyDay method. I added this as an instance variable (so far the only one):

```java
private ArrayList<Player> players; //ArrayList that contains all current Players
```

Here is the luckyDay method's code:

```java
public void luckyDay(Player player, String selection) {
    for (Player p : players) {
        if (p == player) {
            p.setLuckyDay(true); //Gives current Player 'Lucky Day'
            p.setLuckySelection(selection); //Sets the Player's Lucky Day selection
            } else {
            p.setLuckyDay(false);       //Removes any other Player's Lucky Day
            p.setLuckySelection(null);  //and sets their selection to null
        }
    }
}
```

That's all I wanted to get done tonight. Tomorrow I should work on a couple more game features, and I might start implementing the protocol that the server and client will use to communicate with each other.

#### February 17, 2018

Today I want to implement a feature or two into the Game class, and then maybe set up a communication protocol between the server and client.

The Game class is still missing a method for the TeamUp aspect of the game, so first I am going to try to add that. As mentioned earlier, TeamUp is an event in the game where every player rolls the resource die, and every player whose roll matches that of the player whose turn it is, earns one of that resource for them and the player whose turn it is. This is going to be a little awkward to implement at first, because this relies on some game features that I don't have yet. Initially, I have to create a new instance variable that keeps track of whose turn it is. I added the follow field to my Game class:

```java
private int currentPlayer; //Integer representing the Player whose turn it is
```

Also, I need to methods to the server that rolls the dice. The reason that the dice rolling is server-side is that it will be quicker in-game to just have the server roll the dice and distribute the number/resource to all players, rather than have a player roll the dice, and send it back to the server, then have the server distribute the roll to the rest of the players.

The dice roll methods will be super simple. The number die method will simply return a random number 1-6 (inclusive). Here is that code:

```java
/**
 * Rolls a virtual dice (1-6) and returns the value
 *
 * @return dice roll
 */
public int rollDie(){
    Random random = new Random();
    int roll = random.nextInt(6) + 1; //Random number 1-6 (inclusive)
    return roll;
}
```

The resource die will be a little different. There are still six sides, but you have a 1/2 chance of rolling a food card, a 1/3 chance of rolling an animal card, and a 1/6 chance of rolling a shelter card. Because of this, the outcomes must be adjusted accordingly. I will have a random number 1-6, so 1-3 will result in food card, 4-5 will result in animal card, and 6 will result in a shelter card. Here is how that is implemented into code:

```java
/**
 * Rolls a virtual dice (1-6) and returns the resource based on roll
 *
 * @return resource roll
 */
public String rollResource(){
    Random random = new Random();
    int roll = random.nextInt(6) + 1; //Random number 1-6 (inclusive)
    if (roll <= 3)  //If roll is 1, 2, or 3
        return "f"; //Return food card
    else if (roll <= 5) //If roll is 4 or 5
        return "a";     //Return animal card
    else            //If roll is 6
        return "s"; //Return shelter card
}
```

Now that the dice rolls are implemented, I should be able to start the TeamUp method. Here is the pseudocode for how I want to write it:

```
TeamUp:
    Current player roll
    For every other player:
        Roll
        If roll = current player roll
            Add 1 resource to current player
            Add 1 resource to player
```

Now here is what my java code ended up being:

```java
/**
 * Performs the Team Up action in the game
 */
public void teamUp(){
    String goldenRoll; //Roll that original player rolls
    String roll; //Roll that current TeamUp roller rolled

    goldenRoll = rollResource(); //TODO: Get current player's roll

    for (Player p : players)
        if (p != players.get(currentPlayer)) { //Check to make sure the original roller isn't rolling again
            roll = rollResource(); //TODO: Get roll from player
            if (goldenRoll.equals(roll)) switch (goldenRoll) {
                case "f":  //If they both rolled food, give them both a food card
                    players.get(currentPlayer).changeResources(1, 0, 0, 0);
                    p.changeResources(1, 0, 0, 0);
                    break;
                case "a":  //If they both rolled animal, give them both an animal card
                    players.get(currentPlayer).changeResources(0, 1, 0, 0);
                    p.changeResources(0, 1, 0, 0);
                    break;
                case "s":  //If they both rolled shelter, give them both a shelter card
                    players.get(currentPlayer).changeResources(0, 0, 1, 0);
                    p.changeResources(0, 0, 1, 0);
                    break;
            }
        }
}
```

This concludes the work for today, I have most of the Team Up part of the game coded.

#### February 24-25, 2018

My plan for tonight's hackathon is to begin with a GUI for the server. I want to have the game server to have a GUI while the game is running so that the computer hosting the server can see current players and have control over when the game starts.

I plan for the Server GUI to be very simple. There will just be 4 spots for information on the players currently playing, and then a "start game" button right below. The player information would be the player's name, ID number, and color they are in the game. Previously, I didn't plan on the Players having names, so this is something I have to add to the Player class. I did the following to add the name variable to the Player class:

- Add 'name' instance variable
- Add 'name' variable to constructor parameters, add set name field to that value
- Add getName() method to return the name

I don't need a setName(), because the Player's name should never change throughout the game.

Now it is time to start adding the JavaFX code into the Game class. First, I will make sure that the Game class extends Application. When this happens, I have to add a start(Stage) method to the Game class as well.

Next, I am going to create my variables that will be used by the Game's GUI:

```java
//Server GUI variables:
private Stage GUIstage; //The stage being used for the GUI
private Scene scene; //The scene being used to hold the GUI GridPane
private GridPane gui; //GridPane that will hold the GUI labels/button
private ArrayList<Label> playerLabels; //Holds Player labels
private Button startButton; //Button that starts the game
```

Now it is time to begin coding what is in the start(Stage) method for the Game application. This method should do the following commands:

- Initialize the GridPane 'gui' that will be holding all the GUI elements
- Initialize ArrayList 'playerLabels' that holds all the Labels for Player data in the GUI
- Fill the playerLabels ArrayList with filler Labels
- Initialize the start button
- Add all Player Labels and start Button to the GUI GridPane
- Start the GUI application

When I implement these into the start(Stage) method, here is the code:

```java
/**
 * Starts the GUI for the server
 *
 * @param stage The stage that will be shown when starting the GUI
 */
public void start(Stage stage){
    gui = new GridPane();
    playerLabels = new ArrayList<>();
    playerLabels.add(new Label("")); //Fill the ArrayList with labels in lieu of possible players
    playerLabels.add(new Label(""));
    playerLabels.add(new Label(""));
    playerLabels.add(new Label(""));
    startButton = new Button("Start Game");
    for (Label l : playerLabels)
        gui.add(l,0,playerLabels.indexOf(l)); //Add Labels to GUI GridPane
    gui.add(startButton,0,4); //Add start button to GUI GridPane

    //Start GUI
    scene = new Scene(gui);
    GUIstage.setScene(scene);
    GUIstage.show();
}
```

I would like to test if this works so I need to do some things to make sure I am able to test it. I need to make a new class with a main method so I can create a new Game object that will run the server's GUI. This class will be called Server, and it's only purpose will be to create a new Game object to run the server:

```java
public class Server {
    public static void main(String[] args) {
        Game game = new Game(); //Start game
    }
}
```

Now it is time to run the game for the first time. I will run the Server's main method so that it creates a new Game object, which will then start the GUI.

This did not work. I got an error:

```
Application launch must not be called more than once
```

This probably means that my constructor is running more than once, which doesn't make a lot of sense since I just wrote it and it isn't being called more than once. I might have to pass the command-line arguments through this process, since I'm pretty sure the launch method has them as an argument most of the time. I'll try this and see where we're at.

Now when I run it, here is the error I get:

```
java.lang.NoSuchMethodException: server.Game.<init>()
```

Usually, my GUIs have init() methods, which I'm guessing is the reason for this error. It is smart of me to have most of my initialization code in the start method, in the init method instead, anyway. For these reasons, I made the init() method, here is the code now:

```java
/**
 * Initializes variables needed for GUI
 */
public void init(){
    gui = new GridPane();
    playerLabels = new ArrayList<>();
    playerLabels.add(new Label("")); //Fill the ArrayList with labels in lieu of possible players
    playerLabels.add(new Label(""));
    playerLabels.add(new Label(""));
    playerLabels.add(new Label(""));
    startButton = new Button("Start Game");
    for (Label l : playerLabels)
        gui.add(l,0,playerLabels.indexOf(l)); //Add Labels to GUI GridPane
    gui.add(startButton,0,4); //Add start button to GUI GridPane
}
```

After running the program again, I get the same error. This is weird this time because I definitely have an init() method. After searching Google, I see that this can be fixed by including a constructor with no parameters in your class. I guess I will try this by adding the following constructor to my code:

```
public Game(){
    //Empty constructor, this has to be here
}
```

After I added that empty constructor, I no longer get errors! In fact, I now see what I wanted the whole time: A working GUI. Here is what the GUI looks like when I run the program:

![Initial GUI](https://ethanmuz.files.wordpress.com/2018/02/s8.png "Initial GUI")

This is exactly what it was supposed to look like, too. 4 Spots for Player labels, and one start button.

I know that you can't see any text for the labels, so I can add text for them. I also want to change the color of the text for each player so it matches their Player color. I have no idea how to do this, but a Google search yields the method I need to call on the label to change the text color:

```java
setTextFill(Color.web("hex value"));
```

Once I add the colors to the labels, here is what the code looks like in the init() method:

```java
playerLabels.add(new Label("Player 0")); playerLabels.get(0).setTextFill(Color.web("#ff0000")); //Fill the ArrayList with labels in lieu of possible players
playerLabels.add(new Label("Player 1")); playerLabels.get(1).setTextFill(Color.web("#0000ff"));
playerLabels.add(new Label("Player 2")); playerLabels.get(2).setTextFill(Color.web("#ffff00"));
playerLabels.add(new Label("Player 3")); playerLabels.get(3).setTextFill(Color.web("#00ff00"));
```

Now when I run the program, here is the output:

![GUI with text and colors now for Labels](https://ethanmuz.files.wordpress.com/2018/02/s9.png "GUI with text and colors now for Labels")

There are a couple more things I want to do before I put the coding on pause for a moment. I want to add methods for what should happen when Players join and leave the server.

When a Player joins the server, I want it to create a new Player object and add it to the players ArrayList. Also, I want it to take the Player's name and ID and change the appropriate Label to that information. Then, it will re-add the Label to the gui GridPane. After this, it will set the scene and show the stage again, to update the server's view.

When I was writing this code, I realized that when I changed the information on the Player's Label, it was a super long line with functions called upon functions, which breaks the Law of Demeter. Instead, I created a new function for that command to make it easier to understand when reading the addPlayer method.

Here is the code for the addPlayer and updatePlayerLabel methods:

```java
/**
 * Adds Player to game
 *
 * @param p The Player being added
 */
public void addPlayer(Player p){
    players.add(p); //Adds this Player to players
    updatePlayerLabel(p); //Updates the GUI's Label for this Player
    gui.add(playerLabels.get(p.getID()),0, p.getID()); //Re-adds this label to the gui GridPane

    //Start GUI
    GUIstage.setScene(scene);
    GUIstage.show();
}

/**
 * Updates the Label for this Player on the GUI
 *
 * @param p the Player whose Label is being updated
 */
private void updatePlayerLabel(Player p){
    playerLabels.get(p.getID()).setText(p.getName() + " " + p.getID());
}
```

Now I work on the removePlayer method. This should be very similar to the addPlayer method, however the Player is just being removed instead of added (duh).

I also created a clearPlayerLabel method to remove the text from that Player's label on the GUI, while also trying to follow the Law of Demeter. Here is the code for the removePlayer and clearPlayerLabel methods:

```java
/**
 * Removes Player to game
 *
 * @param p The Player being removed
 */
public void removePlayer(Player p){
    players.remove(p); //Removes this Player from players
    clearPlayerLabel(p); //Updates the GUI's Label for this Player
    gui.add(playerLabels.get(p.getID()),0, p.getID()); //Re-adds this label to the gui GridPane

    //Start GUI
    GUIstage.setScene(scene);
    GUIstage.show();
}

/**
 * Clears the Label for this Player on the GUI
 *
 * @param p the Player whose Label is being cleared
 */
private void clearPlayerLabel(Player p){
    playerLabels.get(p.getID()).setText("");
}
```

Now that these methods are written, there is one last thing I want to do. I noticed that my currentPlayer instance variable for the Game class is an int, which represents the Player's ID, however I think it would be better as a Player object, that holds the Player whose turn it is. So I am going to change that to a Player object. To do this, I also change the TeamUp method so that

```
players.get(currentPlayer)
```

is now just

```
currentPlayer
```

which is a lot better from a Software Engineering standpoint.

I am now going to take a break from this hackathon; if I don't get anything else done, then I'll just pick up where I left off next time.

#### March 2, 2018

Today, I want to implement the trading post into the game. Basically there will just be methods that change the resources that a player has based on what they want to trade.

Here is the code for the trading post trades, very simple:

```java
    /**
     * Player trades three food cards for an animal card
     *
     * @param player The player who is trading
     */
    public void buyAnimal(Player player){
        player.changeResources(-3,1,0,0);
    }

    /**
     * Player trades one animal card for an three food cards
     *
     * @param player The player who is trading
     */
    public void sellAnimal(Player player){
        player.changeResources(3,-1,0,0);
    }

    /**
     * Player trades two animal cards for a shelter card
     *
     * @param player The player who is trading
     */
    public void buyShelter(Player player){
        player.changeResources(0,-2,1,0);
    }

    /**
     * Player trades a shelter card for 2 animals card
     *
     * @param player The player who is trading
     */
    public void sellShelter(Player player){
        player.changeResources(0,2,-1,0);
    }

    /**
     * Player trades four food cards, two animal cards, and a shelter card for a habitat
     *
     * @param player The player who is trading
     */
    public void buyHabitat(Player player){
        player.changeResources(-4,-2,-1,1);
    }

    /**
     * Player trades a habitat for our food cards, two animal cards, and a shelter card
     *
     * @param player The player who is trading
     */
    public void sellHabitat(Player player){
        player.changeResources(4,2,1,-1);
    }
```

Now, I finally want to begin to implement the protocol for communication between the server and client. The initial plan is to send Strings over a socket using a PrintWriter and BufferedReader. The protocol used will be the following:

- "[player number] roll": player rolls
- "[player number] roll [number dice] [resource dice] [players who got the resource]": result of player roll
- "[player number] [spot landed on]": spot the player landed on
- "[player number] BS [number of food cards] [number of animal cards] [number of shelter cards]": player lands on Blue Skies with number of food, animal, and shelter cards to gain
- "[player number] SW [number of food cards] [number of animal cards] [number of shelter cards]": player lands on Stormy Weather with number of food, animal, and shelter cards to lose
- "[player number] TP [selling] [buying]": player trading at trading post
- "[player number] LD [lucky day selection]": player lands on lucky day and makes selection
- "[player number] TU [resource roll]": player lands on TeamUp and rolls resource die, or if there is already a TeamUp in progress, the player just sends their roll
- "[player number] KC": player used keeper card
- "[player number] ST [selected resource] [selected lucky day resource]": player changes selection token

Those are all the possible messages needed to be sent from client to server and vice versa (that I can think of right now; any more needed will be added to this list).

Now, I want to start to incorporate the Sockets and ServerSockets into my game. I don't remember everything about networking in Java, so I'm using [Oracle's Server Socket documentation](https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html) for right now.

To begin, I created a ServerSocket for the Game class:

```java
//Server networking variables:
private final int PORT = 61783;
private ServerSocket serverSocket = new ServerSocket(PORT);
```

This will be continued later.

Tomorrow:::::

Today, I want to be able to accept new connections and then assign a PrintWriter and BufferedReader to this connection, and this will be a new Player. To do this, I first have to add a PrintWriter and BufferedReader as fields for the Player class.
