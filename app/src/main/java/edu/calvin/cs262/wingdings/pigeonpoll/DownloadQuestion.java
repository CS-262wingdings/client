package edu.calvin.cs262.wingdings.pigeonpoll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class DownloadQuestion extends AppCompatActivity {

    private Game game = Game.getInstance();
    private QuestionManager qm = QuestionManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_question);

        ArrayList<Question> onlineQuestions = qm.fakeGetOnlineQuestions();

        LinearLayout layout = findViewById(R.id.download_question_holder);

        for(Question q : onlineQuestions) {
            Button b = makeButton(q);
            layout.addView(b);
        }

    }

    private Button makeButton(final Question q) {

        final Button b = new Button(this);

        b.setText(q.text);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.setEnabled(false);
                Toast.makeText(getApplicationContext(), b.getText() + ": question downloaded", Toast.LENGTH_LONG).show();
                qm.addQuestion(b.getText().toString(), true);
            }
        });
        if (qm.isQuestionDownloaded(q)) {
            b.setEnabled(false);
        }
        b.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.border));

        return b;
    }
}
