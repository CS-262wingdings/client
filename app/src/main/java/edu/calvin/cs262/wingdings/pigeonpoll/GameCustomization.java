package edu.calvin.cs262.wingdings.pigeonpoll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GameCustomization extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_customization);
    }

    /**
     * Goto customize.
     *
     * @param view the view
     */
    public void gotoCustomize(View view) {
        startActivity(new Intent(this,CustomQuestion.class));
    }

    /**
     * Download questions.
     *
     * @param view the view
     */
    public void downloadQuestions(View view) {startActivity(new Intent(this,DownloadQuestion.class));}

    /**
     * Manage questions.
     *
     * @param view the view
     */
    public void manageQuestions(View view) {startActivity(new Intent(this,ManageQuestions.class));}
}
