package com.tomaskostadinov.instantmail.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tomaskostadinov.instantmail.models.Letter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomas on 11.06.2016
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mails";
    private static final String TABLE_NAME = "mails";
    private static final int VERSION = 1;
    private static final String KEY_ID = "id";
    private static final String KEY_TIME = "time";
    private static final String KEY_COUNT = "count";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_TIME + " INTEGER, " + KEY_COUNT + " INTEGER )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addLetter(Letter letter){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_COUNT, letter.getCount());
        values.put(KEY_TIME, letter.getTime());

        db.insert(TABLE_NAME, null, values);
        db.close();

    }

    public List<Letter> getLetters(){
        List<Letter> letters = new ArrayList<Letter>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM" + TABLE_NAME +  " ORDER BY " + KEY_TIME, null);

        if(cursor.moveToFirst()){
            do {
                Letter l = new Letter();
                l.setCount(Integer.parseInt(cursor.getString(2)));
                l.setTime(Integer.parseInt(cursor.getString(1)));
                letters.add(l);
            } while (cursor.moveToNext());
        }

        return letters;
    }
}
