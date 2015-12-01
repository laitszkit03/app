package com.example.destinybb.mobilegame;

/**
 * Created by DestinyBB on 2015/11/30.
 */
import android.content.Context;
import android.media.MediaPlayer;
public class Video {
    private static MediaPlayer mp = null;

    /**
     * Stop old song and start new one
     */
    public static void play(Context context, int resource) {
        stop(context);
        mp = MediaPlayer.create(context, resource);
        mp.setLooping(true);
        mp.start();
    }

    /**
     * Stop the music
     */
    public static void stop(Context context) {
        if (mp != null) {
            mp.stop();
            mp.reset();
            mp.release();
            mp = null;
        }
    }
}