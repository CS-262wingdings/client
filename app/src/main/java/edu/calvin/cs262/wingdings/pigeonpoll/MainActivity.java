package edu.calvin.cs262.wingdings.pigeonpoll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private Game game;
    private LinearLayout nameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = Game.getInstance();
        nameList = (LinearLayout)findViewById(R.id.name_list);
    }

    public void nameEnter(View view) {
        EditText nameInput = findViewById(R.id.name_input);
        String input = nameInput.getText().toString();
        if (!(input.equals(""))) {
            if (input.length() > 35) {
                Toast.makeText(this, "Your name must be less than 36 characters", Toast.LENGTH_SHORT).show();
            } else {
                game.addName(input);

                LinearLayout nameLayout = new LinearLayout(this);
                nameLayout.setOrientation(LinearLayout.HORIZONTAL);

                TextView nameView = new TextView(this);
                nameView.setText(input);
                nameView.setTextSize(24);

                RadioButton closeButton = makeCloseButton(nameLayout, input);

                nameLayout.addView(closeButton);
                nameLayout.addView(nameView);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.weight = 0.0f;
                params.gravity = Gravity.CENTER_HORIZONTAL;

                nameLayout.setLayoutParams(params);
                nameLayout.setPadding(15, 25, 0, 25);

                nameList.addView(nameLayout);

                nameInput.setText("");
            }
        }
    }

    public void done(View view) {
        if (game.names.size() > 2) {
            Intent i = new Intent(this, QuestionScreen.class);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(this, "You cannot play with less than 3 players", Toast.LENGTH_SHORT).show();
        }
    }

    private RadioButton makeCloseButton(final LinearLayout nameView, final String name) {
        final RadioButton closeButton = new RadioButton(this);

        closeButton.setButtonDrawable(R.drawable.delete);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "Button clicked.");
                nameList.removeView(nameView);
                game.names.remove(name);
            }
        });
        return closeButton;
    }

    @Override
    public void onBackPressed() {
    }
}
