package wingdings.cs262.calvin.edu.pidgeonpoll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class questionScreen extends AppCompatActivity {

    private TextView questionPrompt;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_screen);

        game = Game.getInstance();

        questionPrompt = findViewById(R.id.prompt);
        questionPrompt.setText(game.getCurrentPlayer() + ", enter your question:");
    }
}
