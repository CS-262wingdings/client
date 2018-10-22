package wingdings.cs262.calvin.edu.pigeonpoll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class QuestionAnswerScreen extends AppCompatActivity {

    private String question;
    private TextView questionLabel;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer_screen);

        question = getIntent().getStringExtra("question");
        question = (!question.equals("custom")) ? question: "Who best fits the asked question?";

        questionLabel = (TextView)findViewById(R.id.question_label);
        questionLabel.setText(question);

        game = Game.getInstance();

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
                game.vote(name);
            }
        });
        b.setText(name);
        return b;
    }

    @Override
    public void onBackPressed() {
    }
}
