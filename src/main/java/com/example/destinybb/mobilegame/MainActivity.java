package com.example.destinybb.mobilegame;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.TextView;
import android.app.Activity;
import android.net.Uri;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.app.ActionBar;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView test;
    public static boolean musicOpen;
    public static String KEY_MUSIC = "";
    public static  boolean music;
    public static String KEY_VIDEO = "";
    private boolean video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        musicOpen=Prefs.getMusic(this);
        music = getIntent().getBooleanExtra(KEY_MUSIC, false);
        video = getIntent().getBooleanExtra(KEY_VIDEO, false);




        test= (TextView)findViewById(R.id.textView);

        View aboutButton = this.findViewById(R.id.button3);
        aboutButton.setOnClickListener(this);
        View exitButton = this.findViewById(R.id.button6);
        exitButton.setOnClickListener(this);
        View webButton = this.findViewById(R.id.button4);
        webButton.setOnClickListener(this);

        View galleryButton = this.findViewById(R.id.button5);
        galleryButton.setOnClickListener(this);
    }


    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button3:
                Intent i = new Intent(this, About.class);
                startActivity(i);
                break;

            case R.id.StartButton:

                break;
            case R.id.button4:

                Uri uri = Uri.parse("http://yahoo.com.hk");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

                break;
            case R.id.button5:
                Intent it = new Intent(this,ImageGallery.class);
                startActivity(it);


                break;

            case R.id.button6:
                finish();
                break;


            // More buttons go here (if any) ...
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings) {
            startActivity(new Intent(this, Prefs.class));

            return true;
        }


        return super.onOptionsItemSelected(item);
    }



    public void StartButton(View v){

      Intent intent = new Intent(this, GameMode.class);
        startActivity(intent);
        this.finish();
    }
    protected void onResume() {
        super.onResume();
        Music.play(this, R.raw.main);
        musicOpen=Prefs.getMusic(this);
        if(Prefs.getMusic(this)==false){
            Music.stop(this);
        }
    }

    protected void onPause() {
        super.onPause();
        Music.stop(this);
    }

}
