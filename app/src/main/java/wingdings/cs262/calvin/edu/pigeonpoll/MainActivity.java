package wingdings.cs262.calvin.edu.pigeonpoll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = Game.getInstance();
    }

    public void nameEnter(View view) {
        EditText nameInput = findViewById(R.id.name_input);
        String input = nameInput.getText().toString();
        if (!(input.equals(""))) {
            if (input.length() > 35) {
                Toast.makeText(this, "Your name must be less than 36 characters", Toast.LENGTH_SHORT).show();
            } else {
                game.addName(input);
                nameInput.setText("");
            }
        }
    }

    public void done(View view) {
        if (game.names.size() > 2) {
            Intent i = new Intent(this, QuestionScreen.class);
            startActivity(i);
        } else {
            Toast.makeText(this, "You cannot play with less than 3 players", Toast.LENGTH_SHORT).show();
        }

    }
}
