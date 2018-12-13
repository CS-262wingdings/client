package edu.calvin.cs262.wingdings.pigeonpoll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * The type Pass screen 1.
 */
public class PassScreen1 extends AppCompatActivity {

    private Game game;

    TextView nameDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_screen1);
        game = Game.getInstance();
        nameDisplay = findViewById(R.id.nameShow);

        nameDisplay.setText(game.getCurrentPlayer());
    }

    /**
     * Confirm player.
     *
     * @param view the view
     */
    public void confirmPlayer(View view) {
        if (!game.isLastPlayer()) {
            Intent i = new Intent(this, QuestionAnswerScreen.class);
            startActivity(i);
            finish();
        } else {
            Intent i = new Intent(this, LastPlayerScreen.class);
            startActivity(i);
            finish();
        }
    }
}
