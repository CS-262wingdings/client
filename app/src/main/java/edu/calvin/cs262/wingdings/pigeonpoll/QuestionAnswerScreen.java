package edu.calvin.cs262.wingdings.pigeonpoll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QuestionAnswerScreen extends AppCompatActivity {

    private String question;
    private TextView questionLabel;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer_screen);

        //question = getIntent().getStringExtra("question");
        //question = (!question.equals("custom")) ? question: "Who best fits the asked question?";
        game = Game.getInstance();
        if(game.currentQuestion == null) {
            question = "Who best fits the asked question?";
        }else {
            question = game.currentQuestion;
        }

        questionLabel = (TextView)findViewById(R.id.question_label);
        questionLabel.setText(question);

        LinearLayout nameHolder = (LinearLayout)findViewById(R.id.name_list);
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
                game.voteForQuestion(name);
                nextTurn();
            }
        });
        b.setText(name);
        return b;
    }

    public void nextTurn(){
        Intent i = new Intent(this, PassScreen1.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}
