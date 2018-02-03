package server;

import java.util.ArrayList;

public class Player {

    //String playerColor; //???
    private int foodCards; //Number of food cards this player possesses
    private int animalCards; //Number of animal cards this player possesses
    private int shelterCards; //Number of shelter cards this player has
    private int habitatsEarned; //Number of habitats this player has earned so far
    private int keeperCards; //Number of keeper cards this player possesses
    private String selection; //Initial resource selection
    private String luckySelection; //Resource selected with lucky token
    private boolean luckyDay; //Whether or not it is this player's "lucky day", that is whether or not they possess the lucky token.
    private int playerID; //Player number used for ID purposes


    public Player(int id){
        this.playerID = id; //Assign player ID to the ID passed in by server
        this.foodCards = 2; //The rules say each player starts with 2 food cards
        this.animalCards = 1; //The rules say each player starts with 1 animal card
        this.shelterCards = 0; //No player starts with shelter cards
        this.habitatsEarned = 0; //No player starts with habitats
        this.keeperCards = 0;  //No player starts with keeper cards
        this.luckyDay = false; this.luckySelection = null;  //No player starts with Lucky Day

    }

}
