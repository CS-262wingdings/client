package wingdings.cs262.calvin.edu.pidgeonpoll;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    public ArrayList<String> names;
    public HashMap<String, Integer> points;     //Total points in the game
    public HashMap<String, Integer> roundPoints;    //Points for this round
    public int firstPlayer;     //Stores whose round it is
    public int currentPlayer;       //Storing who is asking the question
    public int roundsPlayed;
    public int rounds;      //Total rounds

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
        roundPoints = new HashMap<String, Integer>();
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

}
