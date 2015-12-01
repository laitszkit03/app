package com.example.destinybb.sql;

/**
 * Created by kurt on 25/11/2015.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import java.security.SecureRandom;
import java.util.Random;


public class MyDataBase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "Quiz.db";
    private static final int DATABASE_VERSION = 1;


    public MyDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        // you can use an alternate constructor to specify a database location
        // (such as a folder on the sd card)
        // you must ensure that this folder is available and you have permission
        // to write to it
        //super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);

    }



    public Cursor getQuestions() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"0 _id", "question", "ans_a", "ans_b", "ans_c", "ans_d", "real_ans"};
        String sqlTables = "questions";

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);

        c.moveToFirst();
        return c;

    }

    public String[] randomQuestion() {
        Cursor tempQuery = this.getQuestions();

        int total = tempQuery.getCount();
        total = total - 1;
        System.out.println(total);
        int randomIndex = randInt(0, total);
        tempQuery.moveToPosition(randomIndex);
        String[] questArray = new String[6];

        questArray[0] = tempQuery.getString(tempQuery.getColumnIndex("question"));
        questArray[1] = tempQuery.getString(tempQuery.getColumnIndex("ans_a"));
        questArray[2] = tempQuery.getString(tempQuery.getColumnIndex("ans_b"));
        questArray[3] = tempQuery.getString(tempQuery.getColumnIndex("ans_c"));
        questArray[4] = tempQuery.getString(tempQuery.getColumnIndex("ans_d"));
        questArray[5] = tempQuery.getString(tempQuery.getColumnIndex("real_ans"));


        return questArray;


    }

    public String[] randomQuestion(int index) {
        Cursor tempQuery = this.getQuestions();

        int total = tempQuery.getCount();
        tempQuery.moveToPosition(index);
        String[] questArray = new String[6];

        questArray[0] = tempQuery.getString(tempQuery.getColumnIndex("question"));
        questArray[1] = tempQuery.getString(tempQuery.getColumnIndex("ans_a"));
        questArray[2] = tempQuery.getString(tempQuery.getColumnIndex("ans_b"));
        questArray[3] = tempQuery.getString(tempQuery.getColumnIndex("ans_c"));
        questArray[4] = tempQuery.getString(tempQuery.getColumnIndex("ans_d"));
        questArray[5] = tempQuery.getString(tempQuery.getColumnIndex("real_ans"));


        return questArray;


    }


    public static int randInt(int min, int max) {

        // NOTE: This will (intentionally) not run as written so that folks
        // copy-pasting have to think about how to initialize their
        // Random instance.  Initialization of the Random instance is outside
        // the main scope of the question, but some decent options are to have
        // a field that is initialized once and then re-used as needed or to
        // use ThreadLocalRandom (if using at least Java 1.7).
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public int[] randIntArray(int count) {
        SecureRandom rnd = new SecureRandom();
        int[] array = new int[count];
        Cursor tempQuery = this.getQuestions();

        int total = tempQuery.getCount();

        outer: for(int i=0;i<total;i++){
            array[i] = rnd.nextInt(total);
            for (int j=0;j<i;j++){
                if(array[i]==array[j]){
                    i--;
                    continue outer;
                }
            }


        }
        return array;
    }

    public void close() {
        super.close();// may be you should add this

    }
}

