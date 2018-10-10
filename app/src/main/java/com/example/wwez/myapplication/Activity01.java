package com.example.wwez.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Activity01 extends AppCompatActivity {

    private ListView lv_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_01);
        lv_list = (ListView) findViewById(R.id.lv_list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getData());
        lv_list.setAdapter(adapter);
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String txt = adapter.getItem(position);
                Toast.makeText(Activity01.this, txt, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String[] getData() {
        return new String[]{
                "张三",
                "李四",
                "王五",
                "张三",
                "李四",
                "王五",
                "张三",
                "李四",
                "王五",
                "张三",
                "李四",
                "王五",
                "张三",
                "李四",
                "王五",
                "张三",
                "李四",
                "王五",
                "张三",
                "李四",
                "王五",
                "张三",
                "李四",
                "王五","张三",
                "李四",
                "王五",
                "张三",
                "李四",
                "王五",
                "张三",
                "李四",
                "王五",
                "张三",
                "李四",
                "王五",
                "张三",
                "李四",
                "王五",
                "张三",
                "李四",
                "王五",
                "张三",
                "李四",
                "王五",
                "张三",
                "李四",
                "王五"
        };
    }
}
