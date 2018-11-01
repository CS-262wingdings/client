package wingdings.cs262.calvin.edu.pigeonpoll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LastPlayerScreen extends AppCompatActivity {

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_player_screen);
        this.game = Game.getInstance();
        TextView topText = findViewById(R.id.top_text);
        topText.setText(game.getCurrentPlayer() + ", who do you think got the most votes?");

        LinearLayout nameHolder = (LinearLayout)findViewById(R.id.name_voting_list);
        for (int i = 0; i < game.names.size(); i++) {
            Button b = makeButton(game.names.get(i));
            nameHolder.addView(b);
        }


    }

    private Button makeButton(final String name) {
        Button b = new Button(this);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.voteForPlayer(name);
                viewResults();
            }
        });
        b.setText(name);
        return b;
    }

    private void viewResults() {
        Intent i = new Intent(this, ResultsScreen.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}
