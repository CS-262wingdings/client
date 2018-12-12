package edu.calvin.cs262.wingdings.pigeonpoll;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.SortedList;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public class YouWin extends AppCompatActivity {

    LinearLayout allNames;
    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_win);

        game = Game.getInstance();
        allNames = findViewById(R.id.winners);

        ArrayList<Integer> firstPlace = new ArrayList<Integer>();
        ArrayList<Integer> secondPlace = new ArrayList<Integer>();
        ArrayList<Integer> thirdPlace = new ArrayList<Integer>();

        Integer score1 = 0;
        Integer score2 = 0;
        Integer score3 = 0;

        for(int i = 0; i < game.names.size(); i++){
            int playerScore = game.getPoints(i);
            if(playerScore > score3){
                if(playerScore > score2) {
                    if (playerScore > score1) {
                        score3 = score2;
                        score2 = score1;
                        score1 = game.getPoints(i);

                        thirdPlace = secondPlace;
                        secondPlace = firstPlace;
                        firstPlace = new ArrayList<Integer>();
                        firstPlace.add(i);
                    }else if(playerScore == score1){
                        firstPlace.add(i);
                    }else{
                        score3 = score2;
                        score2 = game.getPoints(i);

                        thirdPlace = secondPlace;
                        secondPlace = new ArrayList<Integer>();;
                        secondPlace.add(i);
                    }
                }
                else if(playerScore == score2){
                    secondPlace.add(i);
                }
                else{
                    score3 = game.getPoints(i);
                    thirdPlace = new ArrayList<Integer>();;
                    thirdPlace.add(i);
                }
            }else if(playerScore == score3 || score3 == 0){
                thirdPlace.add(i);
            }
        }

        TextView FirstPlace = new TextView(this);
        FirstPlace.setText("First Place (" + score1 + " points)");
        allNames.addView(FirstPlace);
        for(int i = 0; i < firstPlace.size(); i++){
            TextView newName = new TextView(this);
            newName.setText(game.names.get(firstPlace.get(i)));
            newName.setTextSize(29);
            newName.setTypeface(null, Typeface.BOLD);
            newName.setTextColor(Color.BLACK);
            allNames.addView(newName);
        }

        if(secondPlace != null) {
            TextView SecondPlace = new TextView(this);
            SecondPlace.setText("Second Place (" + score2 + " points)");
            allNames.addView(SecondPlace);
            for (int i = 0; i < secondPlace.size(); i++) {
                TextView newName = new TextView(this);
                newName.setText(game.names.get(secondPlace.get(i)));
                newName.setTextSize(27);
                newName.setTypeface(null, Typeface.BOLD);
                newName.setTextColor(Color.BLACK);
                allNames.addView(newName);
            }
        }

        if(thirdPlace != null) {
            TextView ThirdPlace = new TextView(this);
            ThirdPlace.setText("Third Place (" + score3 + " points)");
            allNames.addView(ThirdPlace);
            for (int i = 0; i < thirdPlace.size(); i++) {
                TextView newName = new TextView(this);
                newName.setText(game.names.get(thirdPlace.get(i)));
                newName.setTextSize(25);
                newName.setTypeface(null, Typeface.BOLD);
                newName.setTextColor(Color.BLACK);
                allNames.addView(newName);
            }
        }
    }

    public void newRound(View view){
        game.rounds += 1;
        Intent i = new Intent(this, QuestionScreen.class);
        startActivity(i);
        finish();
    }

    public void confirm(View view) {
        Intent i = new Intent(this, MainMenu.class);
        startActivity(i);
        finish();
    }
}
