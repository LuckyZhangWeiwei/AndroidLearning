package com.example.wwez.IndexList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;

import com.example.wwez.myapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IndexList_MainActivity extends AppCompatActivity {

    private ArrayList<String> mItems;
    private IndexableListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_list__main);

        mItems = new ArrayList<>();
        mItems.add("Diary of a Wimpy Kid 6: Cabin Fever");
        mItems.add("Steve Jobs");
        mItems.add("Inheritance (The Inheritance Cycle)");
        mItems.add("11/22/63: A Novel");
        mItems.add("The Hunger Games");
        mItems.add("The LEGO Ideas Book");
        mItems.add("Explosive Eighteen: A Stephanie Plum Novel");
        mItems.add("Catching Fire (The Second Book of the Hunger Games)");
        mItems.add("Elder Scrolls V: Skyrim: Prima Official Game Guide");
        mItems.add("Death Comes to Pemberley");
        mItems.add("Diary of a Wimpy Kid 6: Cabin Fever");
        mItems.add("Steve Jobs");
        mItems.add("Inheritance (The Inheritance Cycle)");
        mItems.add("11/22/63: A Novel");
        mItems.add("The Hunger Games");
        mItems.add("The LEGO Ideas Book");
        mItems.add("Explosive Eighteen: A Stephanie Plum Novel");
        mItems.add("Catching Fire (The Second Book of the Hunger Games)");
        mItems.add("Elder Scrolls V: Skyrim: Prima Official Game Guide");
        mItems.add("做作");
        mItems.add("wokao");
        Collections.sort(mItems);

        ContentAdapter adapter = new ContentAdapter(this, android.R.layout.simple_list_item_1, mItems);

        mListView = findViewById(R.id.listview);
        mListView.setAdapter(adapter);
        mListView.setFastScrollEnabled(true); // 设置快速滑动
    }

    class ContentAdapter extends ArrayAdapter<String> implements SectionIndexer {
        private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        public ContentAdapter(Context context, int textViewResourceId, List<String> objects) {
                super(context, textViewResourceId, objects);
        }

        @Override
        public Object[] getSections() {
            String[] sections = new String[mSections.length()];
            for(int i=0; i<mSections.length();i++){
                sections[i] = String.valueOf(mSections.charAt(i));
            }
            return sections;
        }

        @Override
        public int getPositionForSection(int sectionIndex) {  // sectionIndex 索引的序列号
            for(int i = sectionIndex; i>=0; i--) {  //从当前的section 往前查, 一直到遇到第一个对应item为止， 否者不进行定位
                for(int j = 0; j < getCount(); j++) {  //getcount 获得列表所有的数据条数
                    if(i == 0){ //查询数字
                        for(int k=0; k<=9;k++) {
                            if(StringMatcher.match(
                                    String.valueOf(getItem(j).charAt(0)),
                                    String.valueOf(k))
                                    ){
                                return j;
                            }
                        }
                    } else { //查询字母
                        if(StringMatcher.match(
                                String.valueOf(getItem(j).charAt(0)),
                                String.valueOf(mSections.charAt(i)))
                                ){
                            return j;
                        }
                    }
                }
            }
            return 0;
        }

        @Override
        public int getSectionForPosition(int position) {
            return 0;
        }
    }
}
