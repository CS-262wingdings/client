package wingdings.cs262.calvin.edu.pidgeonpoll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
        game.addName(nameInput.getText().toString());
        nameInput.setText("");
    }

    public void done(View view) {
        Intent i = new Intent(this, questionScreen.class);
        startActivity(i);
    }
}
