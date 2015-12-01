package com.example.destinybb.mobilegame;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.app.Activity;
import android.view.Window;
import android.widget.*;
import android.content.Intent;

import com.example.destinybb.sql.MyDataBase;

/**
 * Created by DestinyBB on 2015/11/26.
 */
public class ChooseType extends Activity {
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        // 取消元件的應用程式標題
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.type_layout);
        MyDataBase db = new MyDataBase(this);
        final int[] tempArray = db.randIntArray(10);
        db.close();


        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(ChooseType.this, GamePlay.class);
                Bundle bundle = new Bundle();
                intent.putExtra("type", button.getText().toString());
                intent.putExtra("questArray", tempArray);
                intent.putExtras(bundle);
                startActivity(intent);
                ChooseType.this.finish();

            }
        });
        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ChooseType.this, GamePlay.class);
                Bundle bundle = new Bundle();
                intent.putExtra("type", button2.getText().toString());
                intent.putExtra("questArray", tempArray);
                intent.putExtras(bundle);
                startActivity(intent);
                ChooseType.this.finish();

            }
        });
    }

}