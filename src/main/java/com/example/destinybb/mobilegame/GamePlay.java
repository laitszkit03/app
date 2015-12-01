package com.example.destinybb.mobilegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Script;
import android.view.Window;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.destinybb.sql.MyDataBase;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.lang.reflect.Type;

/**
 * Created by DestinyBB on 2015/11/26.
 */
public class GamePlay extends Activity {
    public static String Type = "";
    private TextView Question;
    private Button btn_ansA, btn_ansB, btn_ansC, btn_ansD;
    private Context context = this;
    private static int Score;
    private static int counter;
    private static int[] btArray;
    private String answer;
    //database test
    private MyDataBase db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 取消元件的應用程式標題
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.game_layout);
        TextView Test = (TextView) findViewById(R.id.textView3);
        Test.setText(getIntent().getStringExtra(Type));
        final TextView Question = (TextView) findViewById(R.id.textView4);
        TextView ScoreBoard = (TextView) findViewById(R.id.score);

        this.btn_ansA = (Button) findViewById(R.id.button_ansA);
        this.btn_ansB = (Button) findViewById(R.id.button_ansB);
        this.btn_ansC = (Button) findViewById(R.id.button_ansC);
        this.btn_ansD = (Button) findViewById(R.id.button_ansD);

        if (MainActivity.music == true) {
            Music.play(GamePlay.this, R.raw.main);
        }

        //=====================================================================================================
        this.db = new MyDataBase(this);

        ScoreBoard.setText("Score: " + Score + "/10");

        try {
            Intent tempIntent = getIntent();
            Bundle extras = tempIntent.getExtras();
            if(extras!=null) {
                btArray = tempIntent.getIntArrayExtra("questArray");
//Type = tempIntent.getStringExtra("type");

            }

           //  System.out.println(Type);
            String[] questArray = db.randomQuestion(btArray[counter]);
            Question.setText(questArray[0]);
            btn_ansA.setText(questArray[1]);
            btn_ansB.setText(questArray[2]);
            btn_ansC.setText(questArray[3]);
            btn_ansD.setText(questArray[4]);
            answer = questArray[5];

            db.close();
        } catch (Exception ex) {
            System.out.println("something wrong");
        }

        db.close();

        btn_ansA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (counter == 9) {
                    gameOver();
                } else {
                    if (answer.equals("a")) {
                        Music.play(GamePlay.this, R.raw.click);
                        Music.mp.setLooping(false);
                        if (Prefs.getMusic(GamePlay.this) == false) {
                            Music.stop(GamePlay.this);
                        }
                        Toast.makeText(arg0.getContext(), "Correct Answer", Toast.LENGTH_SHORT).show();
                        Score++;
                    } else {
                        Music.play(GamePlay.this, R.raw.wrongclick);
                        Music.mp.setLooping(false);
                        if (Prefs.getMusic(GamePlay.this) == false) {
                            Music.stop(GamePlay.this);
                        }
                        Toast.makeText(arg0.getContext(), "Wrong Answer , The Correct Answer is " + answer, Toast.LENGTH_SHORT).show();
                    }

                    counter++;

                    String[] questArray = db.randomQuestion(btArray[counter]);
                    db.close();
                    Question.setText(questArray[0]);
                    btn_ansA.setText(questArray[1]);
                    btn_ansB.setText(questArray[2]);
                    btn_ansC.setText(questArray[3]);
                    btn_ansD.setText(questArray[4]);
                    answer = questArray[5];

                    TextView ScoreBoard = (TextView) findViewById(R.id.score);
                    ScoreBoard.setText("Score: " + Score + "/10");
                }
            }

        });

        btn_ansB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (counter == 9) {
                    gameOver();
                } else {

                    if (answer.equals("b")) {
                        Music.play(GamePlay.this, R.raw.click);
                        Music.mp.setLooping(false);
                        if (Prefs.getMusic(GamePlay.this) == false) {
                            Music.stop(GamePlay.this);
                        }
                        Toast.makeText(arg0.getContext(), "Correct Answer", Toast.LENGTH_SHORT).show();
                        Score++;
                    } else {
                        Music.play(GamePlay.this, R.raw.wrongclick);
                        Music.mp.setLooping(false);
                        if (Prefs.getMusic(GamePlay.this) == false) {
                            Music.stop(GamePlay.this);
                        }
                        Toast.makeText(arg0.getContext(), "Wrong Answer , The Correct Answer is " + answer, Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(getApplicationContext(), GamePlay.class);
                    intent.putExtra("Score", Score);

                    counter++;

                    String[] questArray = db.randomQuestion(btArray[counter]);
                    db.close();
                    Question.setText(questArray[0]);
                    btn_ansA.setText(questArray[1]);
                    btn_ansB.setText(questArray[2]);
                    btn_ansC.setText(questArray[3]);
                    btn_ansD.setText(questArray[4]);
                    answer = questArray[5];


                    TextView ScoreBoard = (TextView) findViewById(R.id.score);
                    ScoreBoard.setText("Score: " + Score + "/10");
                }
            }

        });

        btn_ansC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (counter == 9) {
                    gameOver();
                } else {
                    if (answer.equals("c")) {
                        Music.play(GamePlay.this, R.raw.click);
                        Music.mp.setLooping(false);
                        if (Prefs.getMusic(GamePlay.this) == false) {
                            Music.stop(GamePlay.this);
                        }
                        Toast.makeText(arg0.getContext(), "Correct Answer", Toast.LENGTH_SHORT).show();
                        Score++;
                    } else {
                        Music.play(GamePlay.this, R.raw.wrongclick);
                        Music.mp.setLooping(false);
                        if (Prefs.getMusic(GamePlay.this) == false) {
                            Music.stop(GamePlay.this);
                        }
                        Toast.makeText(arg0.getContext(), "Wrong Answer , The Correct Answer is " + answer, Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(getApplicationContext(), GamePlay.class);
                    intent.putExtra("Score", Score);

                    counter++;


                    String[] questArray = db.randomQuestion(btArray[counter]);
                    db.close();
                    Question.setText(questArray[0]);
                    btn_ansA.setText(questArray[1]);
                    btn_ansB.setText(questArray[2]);
                    btn_ansC.setText(questArray[3]);
                    btn_ansD.setText(questArray[4]);
                    answer = questArray[5];

                    TextView ScoreBoard = (TextView) findViewById(R.id.score);
                    ScoreBoard.setText("Score: " + Score + "/10");
                }
            }

        });

        btn_ansD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (counter == 9) {
                    gameOver();
                } else {

                    if (answer.equals("d")) {
                        Music.play(GamePlay.this, R.raw.click);
                        Music.mp.setLooping(false);
                        if (Prefs.getMusic(GamePlay.this) == false) {
                            Music.stop(GamePlay.this);
                        }
                        Toast.makeText(arg0.getContext(), "Correct Answer", Toast.LENGTH_SHORT).show();
                        Score++;
                    } else {
                        Music.play(GamePlay.this, R.raw.wrongclick);
                        Music.mp.setLooping(false);
                        if (Prefs.getMusic(GamePlay.this) == false) {
                            Music.stop(GamePlay.this);
                        }
                        Toast.makeText(arg0.getContext(), "Wrong Answer , The Correct Answer is " + answer, Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(getApplicationContext(), GamePlay.class);
                    intent.putExtra("Score", Score);

                    counter++;

                    String[] questArray = db.randomQuestion(btArray[counter]);
                    db.close();
                    Question.setText(questArray[0]);
                    btn_ansA.setText(questArray[1]);
                    btn_ansB.setText(questArray[2]);
                    btn_ansC.setText(questArray[3]);
                    btn_ansD.setText(questArray[4]);
                    answer = questArray[5];


                    TextView ScoreBoard = (TextView) findViewById(R.id.score);
                    ScoreBoard.setText("Score: " + Score + "/10");
                }
            }

        });


    }

    protected void onResume() {
        super.onResume();
        Music.play(this, R.raw.starting);
        Music.mp.setLooping(false);
        if (Prefs.getMusic(this) == false) {
            Music.stop(this);
        }
    }

    public static void clearAll() {
        Score = 0;
        counter = 0;
        btArray = null;

    }

    private void gameOver() {
        Intent intent = new Intent(getApplicationContext(), GameOver.class);
        String test = String.valueOf(Score);
        intent.putExtra("Score", test);
        startActivity(intent);
        GamePlay.this.finish();

    }


}
