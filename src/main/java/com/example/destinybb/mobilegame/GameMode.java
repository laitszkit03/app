package com.example.destinybb.mobilegame;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class GameMode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button SinglePlayer = (Button) findViewById(R.id.button_1P);
        Button MultiPlayer = (Button) findViewById(R.id.button_2P);

        SinglePlayer.setOnClickListener(new Button.OnClickListener() {

                                            @Override

                                            public void onClick(View v) {

                                                Intent intent = new Intent(getApplicationContext(), ChooseType.class);
                                                startActivity(intent);
                                                GameMode.this.finish();
                                            }
                                        }

        );

        MultiPlayer.setOnClickListener(new Button.OnClickListener() {

                                           @Override

                                           public void onClick(View v) {

                                               Intent intent = new Intent(getApplicationContext(), Bluetooth.class);
                                               startActivity(intent);
                                               GameMode.this.finish();
                                           }
                                       }

        );


    }

}
