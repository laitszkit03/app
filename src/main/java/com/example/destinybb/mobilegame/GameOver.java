package com.example.destinybb.mobilegame;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GameOver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textView_finalScore = (TextView) findViewById(R.id.textView_finalScore);

        Intent intent = this.getIntent();
        String finalScore = intent.getStringExtra("Score");
        textView_finalScore.setText("Final score:  " + finalScore);
        GamePlay.clearAll();
        final Button button7 = (Button) findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GameOver.this.finish();
                Intent intent = new Intent(GameOver.this, MainActivity.class);
                intent.putExtra(GamePlay.Type, button7.getText().toString());
                startActivity(intent);

            }
        });

    }



}
