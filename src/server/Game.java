package server;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.*;

/**
 * @author Ethan Uzarowski ethanmuz@gmail.com github.com/ethanmuz
 */
public class Game extends Application {

    private ArrayList<Player> players; //ArrayList that contains all current Players
    private int currentPlayer; //Integer representing the Player who's turn it is

    /**
     * Chooses a random Blue Skies card and applies it to the given Player
     *
     * @param player Indicates the Player that these cards will be affecting
     */
    public void BlueSkies(Player player) {
        Random random = new Random();
        int card = random.nextInt(9);
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

    public void start(Stage i){

    }

    /**
     * Chooses a random Stormy Weather card and applies it to the given Player
     *
     * @param player Indicates the Player that these cards will be affecting
     */
    public void StormyWeather(Player player) {
        Random random = new Random();
        int card = random.nextInt(7);
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

    /**
     * Activates a Player's Lucky Day, while also removing it from any other Player who may have it
     *
     * @param player
     * @param selection
     */
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
}