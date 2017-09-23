package com.example.fanjinwei_firstpriject.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 范晋炜 on 2017/8/19 0019.
 * com.example.fanjinwei_firstpriject.sql
 * MySQLiteOpenHelper
 */


public class MySQLiteOpenHelper extends SQLiteOpenHelper{
    public MySQLiteOpenHelper(Context context) {
        super(context, "channel management.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table channel(name varchar(20),selected Integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
