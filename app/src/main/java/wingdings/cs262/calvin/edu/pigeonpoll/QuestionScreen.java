package wingdings.cs262.calvin.edu.pigeonpoll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuestionScreen extends AppCompatActivity {

    private TextView questionPrompt;
    private Button button1, button2, button3;

    private Game game;
    private QuestionManager qManager;

    private Question[] currentQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_screen);

        game = Game.getInstance();
        qManager = QuestionManager.getInstance();

        questionPrompt = findViewById(R.id.prompt);
        questionPrompt.setText(game.getCurrentPlayer() + ", enter your question:");

        getQuestions();

        button1 = findViewById(R.id.question_1_button);
        button1.setText(currentQuestions[0].text);

        button2 = findViewById(R.id.question_2_button);
        button2.setText(currentQuestions[1].text);

        button3 = findViewById(R.id.question_3_button);
        button3.setText(currentQuestions[2].text);
    }

    public void myQuestionEntered(View view) {
    }


    public void questionSelected(View view) {
    }

    private void getQuestions() {
        if (currentQuestions == null) {
            currentQuestions = new Question[3];
        }
        for (int i = 0; i < 3; i++) {
            currentQuestions[i] = qManager.getRandomQuestion();
            Log.d("QuestionTest: ", currentQuestions[i].text);
        }
    }
}
