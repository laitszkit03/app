package com.example.destinybb.mobilegame;

/**
 * Created by DestinyBB on 2015/11/30.
 */


        import android.content.Context;
        import android.os.Bundle;
        import android.preference.PreferenceActivity;
        import android.preference.PreferenceManager;

public class Prefs extends PreferenceActivity {

    private static final String OPT_MUSIC = "music";
    private static final boolean OPT_MUSIC_DEF = true;

    private static final String OPT_VIDEO = "video";
    private static final boolean OPT_VIDEO_DEF = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

    public static boolean getMusic(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(OPT_MUSIC, OPT_MUSIC_DEF);
    }


    public static boolean getVideo(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(OPT_VIDEO, OPT_VIDEO_DEF);
    }
}

