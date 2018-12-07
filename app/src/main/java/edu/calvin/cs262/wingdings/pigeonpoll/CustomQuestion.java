package edu.calvin.cs262.wingdings.pigeonpoll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class CustomQuestion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_question);
    }

    public void newQuestion(View view) {
        EditText entry = findViewById(R.id.entryField);
        if (!entry.getText().toString().equals("") && !entry.getText().toString().equals("")) {
            CheckBox online = findViewById(R.id.uploadOption);      //TODO: make the online submit
            QuestionManager qm = QuestionManager.getInstance(getApplicationContext());
            qm.addQuestion(entry.getText().toString(), true);
            entry.setText("");
        }
    }
}
