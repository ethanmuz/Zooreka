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

    /**
     * Constructor for Player
     *
     * @param id Server tells this Player what its Player ID number is
     */
    public Player(int id) {
        this.playerID = id; //Assign player ID to the ID passed in by server
        this.foodCards = 2; //The rules say each player starts with 2 food cards
        this.animalCards = 1; //The rules say each player starts with 1 animal card
        this.shelterCards = 0; //No player starts with shelter cards
        this.habitatsEarned = 0; //No player starts with habitats
        this.keeperCards = 0;  //No player starts with keeper cards
        this.luckyDay = false;
        this.luckySelection = null;  //No player starts with Lucky Day

    }

    //Returns this Player's ID number
    public int getID() {
        return this.playerID;
    }

    //Returns number of food cards this Player has
    public int getFood() {
        return this.foodCards;
    }

    //Returns number of animal cards this Player has
    public int getAnimalCards() {
        return this.animalCards;
    }

    //Returns number of shelter cards this Player has
    public int getShelterCards() {
        return this.shelterCards;
    }

    //Returns number of habitats this Player has
    public int getHabitats() {
        return this.habitatsEarned;
    }

    //Returns number of keeper cards this Player has
    public int getKeeperCards() {
        return this.keeperCards;
    }

    //Returns the resource this Player has selected (as a String "food", "animal", or "shelter")
    public String getSelection() {
        return this.selection;
    }

    //Returns whether or not it is this Player's lucky day
    public boolean isLuckyDay() {
        return this.luckyDay;
    }

    //Returns the lucky resource this Player has selected (if one; as a String "food", "animal", or "shelter")
    public String getLuckySelection() {
        return this.luckySelection;
    }

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

}
