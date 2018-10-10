package com.example.wwez.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.wwez.Fragment.Fragment01.ItemFragment;
import com.example.wwez.Fragment.Fragment01.dummy.DummyContent;

/*
* listFragment 使用
* */
public class FragmentActivity09 extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment09);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Toast.makeText(this,item.content, Toast.LENGTH_SHORT).show();
    }
}
