package edu.calvin.cs262.wingdings.pigeonpoll;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * The type Main menu.
 */
public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


    }

    /**
     * Start game.
     *
     * @param view the view
     */
    public void StartGame(View view) {
        startActivity(new Intent(this,How2Play.class));
    }


    /**
     * Customize game.
     *
     * @param view the view
     */
    public void customizeGame(View view) {
        startActivity(new Intent(this,GameCustomization.class));
    }
}
