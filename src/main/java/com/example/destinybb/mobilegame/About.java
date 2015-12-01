package com.example.destinybb.mobilegame;

/**
 * Created by DestinyBB on 2015/11/29.
 */
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.VideoView;
public class About extends Activity {
    public static String KEY_VIDEO = "";
    private boolean video2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EditText edit = (EditText) findViewById(R.id.editText1);
        setContentView(R.layout.about);
        /*if(Prefs.getVideo(this)==true) {
            VideoView video = (VideoView) findViewById(R.id.video);
            video.setVideoPath("/data/ok.3gp");
            video.start();

        }*/



    }
}
