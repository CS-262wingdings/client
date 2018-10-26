package wingdings.cs262.calvin.edu.pigeonpoll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    public void confirmPlayer(View view) {
        if(game.currentPlayer != (game.firstPlayer - 1)% game.names.size()){
            Intent i = new Intent(this, QuestionAnswerScreen.class);
            startActivity(i);
            finish();
        }else{
            //go to last player's intent
        }
    }
}
