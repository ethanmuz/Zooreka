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
  4. Team Up: all players roll the resource dice, and each player who matches the original roller earns one of that resource for them and the original roller
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
