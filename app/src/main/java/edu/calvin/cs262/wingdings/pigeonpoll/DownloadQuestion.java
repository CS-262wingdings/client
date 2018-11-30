package edu.calvin.cs262.wingdings.pigeonpoll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DownloadQuestion extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private QuestionManager qm;
    private int sortOption = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_question);

        qm = QuestionManager.getInstance(getApplicationContext());

        Spinner options = findViewById(R.id.sort_options_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        options.setAdapter(adapter);
        options.setOnItemSelectedListener(this);

        LinearLayout layoutParent = findViewById(R.id.download_question_holder);

        generateQuestions(layoutParent);
    }

    private void generateQuestions(LinearLayout layoutParent) {
        layoutParent.removeAllViewsInLayout();

        ArrayList<Question> onlineQuestions = qm.fakeGetOnlineQuestions();

        if (sortOption == 0) {
            Comparator<Question> comp = new Comparator<Question>() {
                @Override
                public int compare(Question o1, Question o2) {
                    return o1.timeStamp.compareTo(o1.timeStamp);
                }
            };
            Collections.sort(onlineQuestions, comp);
        } else {
            Comparator<Question> comp = new Comparator<Question>() {
                @Override
                public int compare(Question o1, Question o2) {
                    return o1.downloads - (o2.downloads);
                }
            };
            Collections.sort(onlineQuestions, comp);
        }

        for(Question q : onlineQuestions) {
            Button b = makeButton(q);
            layoutParent.addView(b);
        }
    }

    private Button makeButton(final Question q) {

        final Button b = new Button(this);

        b.setText(q.text);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.setEnabled(false);
                Toast.makeText(getApplicationContext(), b.getText() + ": Question Downloaded!", Toast.LENGTH_SHORT).show();
                qm.addQuestion(b.getText().toString(), true);
            }
        });
        if (qm.isQuestionDownloaded(q)) {
            b.setEnabled(false);
        }
        b.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.border));

        return b;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selected = (String)parent.getItemAtPosition(position);
        if (selected.equals("Date")) {
            sortOption = 0;
        } else {
            sortOption = 1;
        }
        generateQuestions((LinearLayout)findViewById(R.id.download_question_holder));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
