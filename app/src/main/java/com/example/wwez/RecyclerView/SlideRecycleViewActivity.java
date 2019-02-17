package com.example.wwez.RecyclerView;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.wwez.RecyclerView.adapter.InventoryAdapter;
import com.example.wwez.RecyclerView.bean.Inventory;
import com.example.wwez.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//com.example.wwez.RecyclerView.SlideRecycleViewActivity
public class SlideRecycleViewActivity extends AppCompatActivity {

    private SlideRecyclerView recycler_view_list;
    private List<Inventory> mInventories;
    private InventoryAdapter mInventoryAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_recycle_view);
        recycler_view_list = findViewById(R.id.recycler_view_list);
        recycler_view_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_inset));
        recycler_view_list.addItemDecoration(itemDecoration);
        mInventories = new ArrayList<>();
        Inventory inventory;
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            inventory = new Inventory();
            inventory.setItemDesc("测试数据" + i);
            inventory.setQuantity(random.nextInt(100000));
            inventory.setItemCode("0120816");
            inventory.setDate("20180219");
            inventory.setVolume(random.nextFloat());
            mInventories.add(inventory);
        }
        mInventoryAdapter = new InventoryAdapter(this, mInventories);
        recycler_view_list.setAdapter(mInventoryAdapter);
        mInventoryAdapter.setOnDeleteClickListener(new InventoryAdapter.OnDeleteClickLister() {
            @Override
            public void onDeleteClick(View view, int position) {
                mInventories.remove(position);
                mInventoryAdapter.notifyDataSetChanged();
                recycler_view_list.closeMenu();
            }
        });
    }
}
