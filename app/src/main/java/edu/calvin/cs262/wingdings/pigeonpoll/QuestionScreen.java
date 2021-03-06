package edu.calvin.cs262.wingdings.pigeonpoll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * The type Question screen.
 */
public class QuestionScreen extends AppCompatActivity {

    private static int NUM_OPTIONS = 3;

    private Game game;
    private QuestionManager qManager;

    private Question[] currentQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_screen);

        game = Game.getInstance();
        qManager = QuestionManager.getInstance(getApplicationContext());

        TextView questionPrompt = findViewById(R.id.prompt);
        questionPrompt.setText(game.getCurrentPlayer() + ", Ask a question");

        getQuestions();

        for (int i = 0; i < NUM_OPTIONS; i++) {
            Button questionOption = makeButton(currentQuestions[i]);
            ((LinearLayout)findViewById(R.id.question_layout)).addView(questionOption);
        }
    }

    /**
     * My question entered.
     *
     * @param view the view
     */
    public void myQuestionEntered(View view) {
        Intent i = new Intent(this, QuestionAnswerScreen.class);
        //i.putExtra("question", "custom");
        game.setQuestion(null);
        startActivity(i);
        finish();
    }


    private Button makeButton(final Question question) {
        Button b = new Button(this);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qManager.askQuestion(question);

                Intent i = new Intent(getApplicationContext(), QuestionAnswerScreen.class);
                //i.putExtra("question", question.text);
                game.setQuestion(question.text);
                startActivity(i);
                finish();
            }
        });
        b.setText(question.text);

        b.setPadding(0, 25, 0 ,25);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        b.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.border));
        params.setMargins(15, 5, 15, 25);


        b.setLayoutParams(params);
        return b;
    }

    private void getQuestions() {
        if (currentQuestions == null) {
            currentQuestions = new Question[3];
        }
        for (int i = 0; i < NUM_OPTIONS; i++) {
            currentQuestions[i] = qManager.getRandomQuestion();
            Log.d("QuestionTest: ", currentQuestions[i].text);
        }
    }

    @Override
    public void onBackPressed() {
    }
}
