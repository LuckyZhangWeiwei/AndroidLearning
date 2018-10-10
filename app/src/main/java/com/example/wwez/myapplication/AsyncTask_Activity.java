package com.example.wwez.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AsyncTask_Activity extends AppCompatActivity {

    private TextView tv;
    MyAsyncTask task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_);
        tv = findViewById(R.id.asyncactivity_tv);
        task = new MyAsyncTask();
        task.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        task.cancel(true);
    }

    class MyAsyncTask extends AsyncTask<Void, Integer, String> {
        //被主线程执行，在doInBackground之前执行
        @Override
        protected void onPreExecute() {

        }
        //子线程执行，用来处理耗时操作
        @Override
        protected String doInBackground(Void... voids) {
            for(Integer i=1;i<=100;i++){
                try {
                    Thread.sleep(1000);
                    publishProgress(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return "已完成";
        }

        //被主线程执行，在doInBackground之后
        @Override
        protected void onPostExecute(String result) {
          tv.setText(result);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            tv.setText(values[0].toString()+"%");
        }
    }
}



