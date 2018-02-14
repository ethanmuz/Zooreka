package server;

import java.util.*;

public class Game {

    private ArrayList<Player> players; //ArrayList that contains all current Players

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

    public void luckyDay(Player player, String selection) {
        for (Player p : players) {
            if (p == player) {
                p.setLuckyDay(true);
                p.setLuckySelection(selection);
            } else {
                p.setLuckyDay(false);
                p.setLuckySelection(null);
            }
        }
    }
}
