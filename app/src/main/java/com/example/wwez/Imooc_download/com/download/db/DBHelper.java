package com.example.wwez.Imooc_download.com.download.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "download.db";
    private static DBHelper sHelper;

    private static final int VERSION = 1;

    private static final String SQL_CREATE = "create table thread_info(_id integer primary key autoincrement," +
            "thread_id integer, url text, start long, end long, finished long" +
            ")";

    private static final String SQL_DROP = "drop table if exists thread_info";

    private DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public synchronized static DBHelper getInstance(Context context) {
        if(sHelper == null) {
            sHelper = new DBHelper(context);
        }
        return sHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DROP);
        sqLiteDatabase.execSQL(SQL_CREATE);
    }
}
