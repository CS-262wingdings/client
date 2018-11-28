package edu.calvin.cs262.wingdings.pigeonpoll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class YouWin extends AppCompatActivity {

    TextView nameSlot;
    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_win);

        game = Game.getInstance();
        nameSlot = findViewById(R.id.nameShow2);
        String nameString = "Nobody";

        int highestPoints = 0;
        for(int i = 0; i < game.names.size(); i++) {
            int newPoints = game.getPoints(i);
            if(highestPoints < newPoints){
                nameString = game.names.get(i);
            }else if(newPoints != 0 && newPoints == highestPoints){
                nameString = nameString + " + " + game.names.get(i);
            }
        }
        nameSlot.setText(nameString);

    }

    public void confirm(View view) {
        Intent i = new Intent(this, MainMenu.class);
        startActivity(i);
        finish();
    }
}
