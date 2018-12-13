package edu.calvin.cs262.wingdings.pigeonpoll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * The type Results screen.
 */
public class ResultsScreen extends AppCompatActivity {

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_screen);

        this.game = Game.getInstance();

        TextView pointsGotten = findViewById(R.id.points_gotten);

        String pointText = (game.votePoints == 1) ? " Point" : " Points" ;
        pointsGotten.setText("+" + game.votePoints + pointText);

        LinearLayout roundNamesHolder = findViewById(R.id.round_names_holder);
        //LinearLayout roundScoreHolder = findViewById(R.id.round_points_holder);
        LinearLayout overallNamesHolder = findViewById(R.id.overall_names_holder);
        LinearLayout overallScoreHolder = findViewById(R.id.overall_points_holder);

        for (int i = 0 ; i < game.names.size(); i++) {

            TextView nameView = new TextView(this);
            nameView.setText(game.names.get(i));
            overallNamesHolder.addView(nameView);

            TextView nameView2 = new TextView(this);
            nameView2.setText(game.names.get(i) + " got " + game.getRoundPoints(i) + " vote");
            if(game.getRoundPoints(i) != 1){
                nameView2.setText(nameView2.getText() + "s");
            }
            roundNamesHolder.addView(nameView2);

            ProgressBar p1 = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
            p1.setMax(game.names.size());
            p1.setProgress(game.getRoundPoints(i));
            roundNamesHolder.addView(p1);

//            TextView roundScore = new TextView(this);
//            roundScore.setText("" + game.getRoundPoints(i));
//            roundScoreHolder.addView(roundScore);

            TextView overallScore = new TextView(this);
            overallScore.setText("" + game.getPoints(i));
            overallScoreHolder.addView(overallScore);
        }

        game.endTurn();

        if (game.currentPlayer == 0) {
            game.endRound();
        }

        Button nextButton = findViewById(R.id.result_done_button);

        if (game.isGameOver()) {
            nextButton.setText("Finish Game");
        }

    }

    /**
     * Next round.
     *
     * @param view the view
     */
    public void nextRound(View view) {
        if (game.isGameOver()) {
            Intent i = new Intent(this, YouWin.class);
            startActivity(i);
            finish();
        } else {
            Intent i = new Intent(this, QuestionScreen.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
    }
}
