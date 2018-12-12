package edu.calvin.cs262.wingdings.pigeonpoll;

//import java.awt.Button;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//import javax.swing.text.View;
//import javax.xml.ws.Response;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
//import jdk.nashorn.internal.codegen.CompilerConstants.Call;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//import sun.rmi.runtime.Log;

public class DownloadQuestion extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private QuestionManager qm;
    private int sortOption = 0;
    private boolean questionsReceived;
    private ArrayList<Question> downloadedQuestions;
    private LinearLayout layoutParent;

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

        layoutParent = (LinearLayout)findViewById(R.id.download_question_holder);
    }

    private void generateQuestions() {
        layoutParent.removeAllViewsInLayout();

        questionsReceived = false;
        downloadedQuestions = new ArrayList<>();

        qm.downloadQuestions(DownloadQuestion.this);
    }

    private Button makeButton(final Question q) {

        final Button b = new Button(this);

        b.setText(q.text);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.setEnabled(false);
                Toast.makeText(getApplicationContext(), b.getText() + ": Question Downloaded!", Toast.LENGTH_SHORT).show();
                qm.addQuestionLocally(q);

                // PUT request with index q.
                Call<Items> call = QuestionClient.getInstance().getService().updateDownloads(q.id);

                call.enqueue(new Callback<Items>() {
                    @Override
                    public void onResponse(Call<Items> call, Response<Items> response) {
                        if (response.isSuccessful()) {
                        }
                        // TODO handle error
                    }

                    @Override
                    public void onFailure(Call<Items> call, Throwable t) {
                        Log.d("Error", t.getMessage());
                    }
                });
            }
        });
        if (qm.isQuestionDownloaded(q)) {
            b.setEnabled(false);
        }
//
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );


        b.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.border));

        params.setMargins(15, 10, 15, 10);

        b.setLayoutParams(params);


        return b;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selected = (String) parent.getItemAtPosition(position);
        if (selected.equals("Date")) {
            sortOption = 0;
        } else {
            sortOption = 1;
        }

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                generateQuestions();
            }
        });
        t.run();
        if (sortOption == 0) {
            Comparator<Question> comp = new Comparator<Question>() {
                @Override
                public int compare(Question o1, Question o2) {
                    return o1.timeStamp.compareTo(o1.timeStamp);
                }
            };
            Collections.sort(downloadedQuestions, comp);
        } else {
            Comparator<Question> comp = new Comparator<Question>() {
                @Override
                public int compare(Question o1, Question o2) {
                    return o1.downloads - (o2.downloads);
                }
            };
            Collections.sort(downloadedQuestions, comp);
        }

        for (Question q : downloadedQuestions) {
            Button b = makeButton(q);
            layoutParent.addView(b);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void returnList(ArrayList<Question> returnList) {
        downloadedQuestions = returnList;
        questionsReceived = true;

        if (sortOption == 0) {
            Comparator<Question> comp = new Comparator<Question>() {
                @Override
                public int compare(Question o1, Question o2) {
                    return o1.timeStamp.compareTo(o1.timeStamp);
                }
            };
            Collections.sort(downloadedQuestions, comp);
        } else {
            Comparator<Question> comp = new Comparator<Question>() {
                @Override
                public int compare(Question o1, Question o2) {
                    return o1.downloads - (o2.downloads);
                }
            };
            Collections.sort(downloadedQuestions, comp);
        }

        for(Question q : downloadedQuestions) {
            Button b = makeButton(q);
            layoutParent.addView(b);
        }
    }
}
