package edu.calvin.cs262.wingdings.pigeonpoll;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_questions);

        qm = QuestionManager.getInstance(getApplicationContext());

        layoutParent = (LinearLayout)findViewById(R.id.manage_question_holder);

        showQuestions();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void showQuestions() {
        layoutParent.removeAllViewsInLayout();

        for (Question q : qm.getAllQuestions()) {
            LinearLayout questionLayout = new LinearLayout(this);
            questionLayout.setOrientation(LinearLayout.HORIZONTAL);

            Button b = makeButton(q);

            Button closeButton = makeCloseButton(questionLayout, q);

            questionLayout.addView(closeButton);
            questionLayout.addView(b);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT);
//            params.weight = 0.0f;
//            params.gravity = Gravity.CENTER_VERTICAL;

            questionLayout.setLayoutParams(params);
            questionLayout.setPadding(1, 10, 15, 10);

            closeButton.setHeight(5);
            closeButton.setWidth(5);

            layoutParent.addView(questionLayout);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private Button makeCloseButton(final LinearLayout nameView, final Question q) {
        final Button closeButton = new Button(this);

        if (qm.isQuestionEnabled(q)) {
            //closeButton.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.checkmark));

        } else {

        }
        //closeButton.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.delete));
        closeButton.setText("delete");
        closeButton.setTextColor(Color.WHITE);
        closeButton.setMinHeight(1);
        closeButton.setMinWidth(1);
        closeButton.setBackgroundColor(Color.rgb(240,86,88));
        closeButton.setTextSize(15);
        //}

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (!qm.isQuestionEnabled(q)) {
                    layoutParent.removeView(nameView);
                    qm.deleteQuestion(q);
                    showQuestions();
                //}
            }
        });


        return closeButton;
    }

    private Button makeButton(final Question q) {

        final Button b = new Button(this);

        b.setText(q.text);
        b.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.border));

        if (qm.isQuestionEnabled(q)) {
            b.setTextColor(Color.BLACK);
        } else {
            b.setTextColor(Color.argb(60, 0, 0, 0));
        }

        b.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                if (qm.isQuestionEnabled(q)) {
                    Toast.makeText(getApplicationContext(), "Question Disabled. It will not be suggested during games.", Toast.LENGTH_SHORT).show();
                    qm.disableQuestion(q);
                } else {
                    Toast.makeText(getApplicationContext(), "Question Enabled. It will now become part of the game.", Toast.LENGTH_SHORT).show();
                    qm.enableQuestion(q);
                }
                showQuestions();
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(15, 5, 15, 25);



        b.setLayoutParams(params);

        return b;
    }
}
