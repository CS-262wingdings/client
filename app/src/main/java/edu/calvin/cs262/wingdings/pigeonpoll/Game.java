package edu.calvin.cs262.wingdings.pigeonpoll;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Game {

    // Stores the names of all players in clockwise order
    ArrayList<String> names;

    // Stores the overall points for each player, where the key is each player's
    // name in "names", and the values are the points for that player
    private HashMap<String, Integer> points;

    // Stores how many votes each player has received this round, mapping the player's
    // name to the votes.
    private HashMap<String, Integer> votesPerPlayer;

    // Keeps track of who asked the question by storing the index of that player
    int firstPlayer;

    // Keeps track of who has the phone currently by storing the index of that player
    int currentPlayer;

    // Stores the number of completed rounds
    int roundsPlayed;

    // Stores how many rounds the game will last:
    // A round is a cycle of every player asking a question
    int rounds;

    // Stores the value of the rounds' points so we can show them in the results screen
    int votePoints;

    // Stores this Game's instance
    private static Game instance;

    //Stores the current question
    String currentQuestion;

    Game(int rounds) {
        names = new ArrayList<String>();
        points = new HashMap<String, Integer>();
        votesPerPlayer = new HashMap<String, Integer>();
        firstPlayer = 0;
        currentPlayer = 0;
        roundsPlayed = 0;
        this.rounds = rounds;
    }

    public static void resetGame() {
        instance = new Game(1);
    }

    void addName(String name) {
        names.add(name);
        points.put(name, 0);
    }

    public void endTurn() {
        votesPerPlayer = new HashMap<String, Integer>();
        firstPlayer = currentPlayer;
    }

    public void endRound()
    {
        roundsPlayed++;
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

    public void voteForQuestion(String playerName) {
        if(votesPerPlayer.containsKey(playerName)) {
            votesPerPlayer.put(playerName, votesPerPlayer.get(playerName) + 1);
        }else{
            votesPerPlayer.put(playerName,1);
        }
        currentPlayer = (currentPlayer + 1 + names.size()) % names.size();
    }

    public void voteForPlayer(String playerName) {
        votePoints = (votesPerPlayer.containsKey(playerName)) ? votesPerPlayer.get(playerName) : 0 ;

        if (points.containsKey(getCurrentPlayer())) {
            points.put(getCurrentPlayer(), points.get(getCurrentPlayer()) + votePoints);
        } else {
            points.put(getCurrentPlayer(), votePoints);
        }

        if(votePoints == 0){
            for(int i = 0; i < names.size(); i ++){
                if(names.get(i) != getCurrentPlayer()){
                    if (points.containsKey(i)) {
                        points.put(names.get(i), points.get(names.get(i)) + 1);
                    } else {
                        points.put(names.get(i), 1);
                    }
                }
            }
        }
    }

    String getCurrentPlayer() {
        return names.get(currentPlayer);
    }

    public void setQuestion(String q){
        currentQuestion = q;
    }

    public boolean isLastPlayer() {
        return ((currentPlayer + names.size() + 1) % names.size() == firstPlayer);
    }

    public int getPoints(int playerNum) {
        return points.get(names.get(playerNum));
    }

    public int getRoundPoints(int playerNum) {
        return (votesPerPlayer.get(names.get(playerNum)) == null) ? 0 : votesPerPlayer.get(names.get(playerNum));
    }
}
