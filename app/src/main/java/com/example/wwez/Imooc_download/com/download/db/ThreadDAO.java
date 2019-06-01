package com.example.wwez.Imooc_download.com.download.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.wwez.Imooc_download.com.download.entry.ThreadInfo;

import java.util.ArrayList;
import java.util.List;

public class ThreadDAO implements IThreadDAO {
    private DBHelper mHelper;

    public ThreadDAO(Context context) {
        mHelper = DBHelper.getInstance(context);
    }

    @Override
    public synchronized void insertThread(ThreadInfo threadInfo) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("insert into thread_info(thread_id, url, start, end, finished) values(?, ?, ?, ?, ?)",
            new Object[]{
                    threadInfo.getId(),
                    threadInfo.getUrl(),
                    threadInfo.getStart(),
                    threadInfo.getEnd(),
                    threadInfo.getFinished()
            });
        db.close();
    }

    @Override
    public synchronized void deleteThread(String url) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("delete from thread_info where url = ?",
            new Object[]{
                    url
            });
        db.close();
    }

    @Override
    public synchronized void updateThread(String url, int thread_id, long finished) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("update thread_info set finished = ? where url = ? and thread_id = ?",
            new Object[]{
                finished,
                url,
                thread_id
            });
        db.close();
    }

    @Override
    public List<ThreadInfo> getThreads(String url) {
        List<ThreadInfo> list = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url = ?", new String[]{
           url
        });
        while (cursor.moveToNext()) {
            ThreadInfo threadinfo = new ThreadInfo();
            threadinfo.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            threadinfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            threadinfo.setStart(cursor.getLong(cursor.getColumnIndex("start")));
            threadinfo.setEnd(cursor.getLong(cursor.getColumnIndex("end")));
            threadinfo.setFinished(cursor.getLong(cursor.getColumnIndex("finished")));
            list.add(threadinfo);
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public boolean isExists(String url, int thread_id) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url = ? and thread_id = ?", new String[]{
                url,
                String.valueOf(thread_id)
        });
        boolean result = cursor.moveToNext();
        cursor.close();
        db.close();
        return result;
    }
}
