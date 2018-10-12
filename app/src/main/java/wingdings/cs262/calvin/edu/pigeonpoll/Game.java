package wingdings.cs262.calvin.edu.pigeonpoll;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    // Stores the names of all players in clockwise order
    public ArrayList<String> names;

    // Stores the overall points for each player, where the key is each player's
    // name in "names", and the values are the points for that player
    public HashMap<String, Integer> points;

    // Stores how many votes each player has received this round, mapping the player's
    // name to the votes.
    public HashMap<String, Integer> votesPerPlayer;

    // Keeps track of who asked the question by storing the index of that player
    public int firstPlayer;

    // Keeps track of who has the phone currently by storing the index of that player
    public int currentPlayer;

    // Stores the number of completed rounds
    public int roundsPlayed;

    // Stores how many rounds the game will last:
    // A round is a cycle of every player asking a question
    public int rounds;

    // Stores this Game's instance
    private static Game instance;


    public Game(int rounds) {
        names = new ArrayList<String>();
        points = new HashMap<String, Integer>();
        firstPlayer = 0;
        currentPlayer = 0;
        roundsPlayed = 0;
        this.rounds = rounds;
    }

    public int getPlayerNumber() {
        return names.size();
    }

    public void addName(String name) {
        names.add(name);
        points.put(name, 0);
    }

    public void startRound() {
        votesPerPlayer = new HashMap<String, Integer>();
    }

    public void endTurn() {
        currentPlayer = (currentPlayer + 1) % names.size();
    }

    public boolean isGameOver() {
        return (roundsPlayed >= rounds);
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game(1);
        }
        return instance;
    }

    public String getCurrentPlayer() {
        return names.get(currentPlayer);
    }

}
