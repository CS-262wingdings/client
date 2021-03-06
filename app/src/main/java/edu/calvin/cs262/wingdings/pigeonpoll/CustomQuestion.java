package edu.calvin.cs262.wingdings.pigeonpoll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * The type Custom question.
 */
public class CustomQuestion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_question);
    }

    /**
     * New question.
     *
     * @param view the view
     */
    public void newQuestion(View view) {
        EditText entry = findViewById(R.id.entryField);
        if (!entry.getText().toString().equals("") && !entry.getText().toString().equals("")) {
            CheckBox online = findViewById(R.id.uploadOption);
            QuestionManager qm = QuestionManager.getInstance(getApplicationContext());
            qm.addQuestion(entry.getText().toString(), online.isChecked());
            entry.setText("");
        }
    }

    /**
     * Other questions.
     *
     * @param view the view
     */
    public void otherQuestions(View view) {
        Intent i = new Intent(this, ManageQuestions.class);
        startActivity(i);
    }

}
