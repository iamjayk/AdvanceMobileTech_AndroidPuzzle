package com.example.build.blockpuzzle;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jc317897 on 5/01/2017.
 */
public class DBHelper extends SQLiteOpenHelper implements Serializable {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "game.db";
    static final String TABLE_NAME = "users";
    static final String USERNAME_COL = "username";
    static final String SCORE_COL = "score";

    public DBHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "create table " + TABLE_NAME + " ("
                + USERNAME_COL + " text primary key,"
                + SCORE_COL + " integer)";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertPlayer(String usr) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME_COL, usr);
        values.put(SCORE_COL, 0);

        db.insert(TABLE_NAME, null, values);
        return true;
    }

    public Cursor getPlayer(String usr) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlstr = "select * from " + TABLE_NAME + " where "
                + USERNAME_COL + " = " + "'" + usr + "'";
        Cursor cursor = db.rawQuery(sqlstr, null);
        return cursor;
    }

    public boolean updatePlayer(String usr, int score){
        SQLiteDatabase db = this.getWritableDatabase();
        if(getPlayer(usr) == null){
            return false;
        }
        ContentValues values = new ContentValues();
        values.put(SCORE_COL, score);
        db.update(TABLE_NAME, values, USERNAME_COL + " = ?", new String[]{usr});
        return true;
    }

    public ArrayList<String> getAllPlayerNames()
    {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " + TABLE_NAME, null );
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            array_list.add(cursor.getString(cursor.getColumnIndex(USERNAME_COL)));
            cursor.moveToNext();
        }
        cursor.close();
        return array_list;
    }

    public ArrayList<String> getAllScores()

    {
        ArrayList<String> array_list = new ArrayList<String>();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " + TABLE_NAME, null );
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            array_list.add(cursor.getString(cursor.getColumnIndex(SCORE_COL)));
            cursor.moveToNext();
        }
        cursor.close();
        return array_list;

    }

}//end of DBHelper

