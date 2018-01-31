
reka
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
