package server;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * @author Ethan Uzarowski ethanmuz@gmail.com github.com/ethanmuz
 */
public class Game extends Application {

    //Game variables:
    private ArrayList<Player> players; //ArrayList that contains all current Players
    private Player currentPlayer; //Integer representing the Player who's turn it is

    //Server GUI variables:
    Stage stage;
    Scene scene;
    private GridPane gui; //GridPane that will hold the GUI labels/button
    private ArrayList<Label> playerLabels; //Holds Player labels
    private Button startButton; //Button that starts the game

    //Server networking variables:
    private final int PORT = 54329;
    private ServerSocket serverSocket;

    /**
     * Game constructor
     */
    public Game(String[] args) throws IOException {
        serverSocket = new ServerSocket(PORT);
        Application.launch(args); //Launch GUI
    }

    public Game() throws IOException {
        //Empty constructor, this has to be here
    }

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
     * Initializes variables needed for GUI
     */
    public void init(){
        gui = new GridPane();
        playerLabels = new ArrayList<>();
        playerLabels.add(new Label("Player 0")); playerLabels.get(0).setTextFill(Color.web("#ff0000")); //Fill the ArrayList with labels in lieu of possible players
        playerLabels.add(new Label("Player 1")); playerLabels.get(1).setTextFill(Color.web("#0000ff"));
        playerLabels.add(new Label("Player 2")); playerLabels.get(2).setTextFill(Color.web("#ffff00"));
        playerLabels.add(new Label("Player 3")); playerLabels.get(3).setTextFill(Color.web("#00ff00"));
        startButton = new Button("Start Game");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                startButton.setText("fds");
            }
        });
        for (Label l : playerLabels)
            gui.add(l,0,playerLabels.indexOf(l)); //Add Labels to GUI GridPane
        gui.add(startButton,0,4); //Add start button to GUI GridPane
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                startButton.setText("Game will start");
            }
        });
    }

    /**
     * Starts the GUI for the server
     *
     * @param s The stage that will be shown when starting the GUI
     */
    public void start(Stage s){
        scene = new Scene(gui);
        stage = s;
        stage.setScene(scene);
        stage.show();
    }

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
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Updates the Label for this Player on the GUI
     *
     * @param p the Player whose Label is being updated
     */
    private void updatePlayerLabel(Player p){
        playerLabels.get(p.getID()).setText(p.getName() + " " + p.getID());
    }

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
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Clears the Label for this Player on the GUI
     *
     * @param p the Player whose Label is being cleared
     */
    private void clearPlayerLabel(Player p){
        playerLabels.get(p.getID()).setText("");
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
            if (p != currentPlayer) { //Check to make sure the original roller isn't rolling again
                roll = rollResource(); //TODO: Get roll from player
                if (goldenRoll.equals(roll)) switch (goldenRoll) {
                    case "f":  //If they both rolled food, give them both a food card
                        currentPlayer.changeResources(1, 0, 0, 0);
                        p.changeResources(1, 0, 0, 0);
                        break;
                    case "a":  //If they both rolled animal, give them both an animal card
                        currentPlayer.changeResources(0, 1, 0, 0);
                        p.changeResources(0, 1, 0, 0);
                        break;
                    case "s":  //If they both rolled shelter, give them both a shelter card
                        currentPlayer.changeResources(0, 0, 1, 0);
                        p.changeResources(0, 0, 1, 0);
                        break;
                }
            }
    }

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

    /**
     * Adds players who connect to the game
     */
    public void addPlayers() throws IOException {
        while (players.size()<4){
            Socket client = serverSocket.accept();
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String name = in.readLine();
            Player p = new Player(name, players.size(), client, in, out);
            addPlayer(p);
        }
    }
}