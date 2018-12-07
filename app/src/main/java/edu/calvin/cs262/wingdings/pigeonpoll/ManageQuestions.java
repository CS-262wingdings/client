package edu.calvin.cs262.wingdings.pigeonpoll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageQuestions extends AppCompatActivity {

    private QuestionManager qm;
    private LinearLayout layoutParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_questions);

        qm = QuestionManager.getInstance(getApplicationContext());

        layoutParent = (LinearLayout)findViewById(R.id.manage_question_holder);

        showQuestions();
    }

    private void showQuestions() {
        layoutParent.removeAllViewsInLayout();

        for (Question q : qm.getAllQuestions()) {
            LinearLayout questionLayout = new LinearLayout(this);
            questionLayout.setOrientation(LinearLayout.HORIZONTAL);

            Button b = makeButton(q);

            Button closeButton = makeCloseButton(questionLayout, q);

            questionLayout.addView(b);
            questionLayout.addView(closeButton);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 0.0f;
            params.gravity = Gravity.CENTER_VERTICAL;

            questionLayout.setLayoutParams(params);
            questionLayout.setPadding(15, 25, 15, 25);

            layoutParent.addView(questionLayout);
        }
    }

    private Button makeCloseButton(final LinearLayout nameView, final Question q) {
        final Button closeButton = new Button(this);

        if (qm.isQuestionEnabled(q)) {
            closeButton.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.check));
        } else {
            closeButton.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.delete));
        }

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!qm.isQuestionEnabled(q)) {
                    layoutParent.removeView(nameView);
                    qm.deleteQuestion(q);
                    showQuestions();
                }
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 0.0f;
        params.gravity = Gravity.CENTER_VERTICAL;

        closeButton.setWidth(50);
        closeButton.setHeight(50);

        return closeButton;
    }

    private Button makeButton(final Question q) {

        final Button b = new Button(this);

        b.setText(q.text);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qm.isQuestionEnabled(q)) {
                    qm.disableQuestion(q);
                } else {
                    qm.enableQuestion(q);
                }
                showQuestions();
            }
        });

        b.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.border));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1.0f;

        b.setLayoutParams(params);

        return b;
    }
}
