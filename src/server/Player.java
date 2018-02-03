package server;

import java.util.ArrayList;

public class Player {

    //String playerColor; //???
    int foodCards; //Number of food cards this player possesses
    int animalCards; //Number of animal cards this player possesses
    int shelterCards; //Number of shelter cards this player has
    int habitatsEarned; //Number of habitats this player has earned so far
    int keeperCards; //Number of keeper cards this player possesses
    String selection; //Initial resource selection
    String luckySelection; //Resource selected with lucky token
    boolean luckyDay; //Whether or not it is this player's "lucky day", that is whether or not they possess the lucky token.
    int playerID; //Player number used for ID purposes

}
