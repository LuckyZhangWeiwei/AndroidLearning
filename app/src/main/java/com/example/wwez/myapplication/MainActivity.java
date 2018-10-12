package com.example.wwez.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.wwez.Broadcast.BoardcastActivity;
import com.example.wwez.Broadcast.Order_UnOrder_Broadcast_Activity;
import com.example.wwez.Broadcast.SMS_Listener_Activity;
import com.example.wwez.ListView.ListViewMainActivity;
import com.example.wwez.dropdownmenu.DropdownMenu_MainActivity;
import com.example.wwez.qqSliderMenu.SliderMenuMainActivity;
import com.example.wwez.tab.Tab01_MainActivity;
import com.example.wwez.tab.Tab02_MainActivity;
import com.example.wwez.tab.Tab03_MainActivity;
import com.example.wwez.tab.Tab04_MainActivity;
import com.example.wwez.viewPagerIndicator.ViewPagerIndicator_MainActivity;
import com.example.wwez.webview.Webview_MainActivity;
//import com.example.wwez.RecyclerView.MainActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String tag = MainActivity.class.getSimpleName();
    private Button btn_zanting;
    private Button btn_stopState;
    private Button dtn_duankai;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btn10;
    private Button btn11;
    private Button btn12;
    private Button btn13;
    private Button btn14;
    private Button btn15;
    private Button btn16;
    private Button btn17;
    private Button btn18;
    private Button btn19;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.Btn1);
        btn1.setOnClickListener(this);
        btn2 = findViewById(R.id.Btn2);
        btn2.setOnClickListener(this);
        btn3 = findViewById(R.id.Btn3);
        btn3.setOnClickListener(this);
        btn4 = findViewById(R.id.Btn4);
        btn4.setOnClickListener(this);
        btn5 = findViewById(R.id.Btn5);
        btn5.setOnClickListener(this);
        btn6 = findViewById(R.id.Btn6);
        btn6.setOnClickListener(this);
        btn7 = findViewById(R.id.Btn7);
        btn7.setOnClickListener(this);
        btn8 = findViewById(R.id.Btn8);
        btn8.setOnClickListener(this);
        btn9 = findViewById(R.id.Btn9);
        btn9.setOnClickListener(this);
        btn10 = findViewById(R.id.Btn10);
        btn10.setOnClickListener(this);
        btn11 = findViewById(R.id.Btn11);
        btn11.setOnClickListener(this);
        btn12 = findViewById(R.id.Btn12);
        btn12.setOnClickListener(this);
        btn13 = findViewById(R.id.Btn13);
        btn13.setOnClickListener(this);
        btn14 = findViewById(R.id.Btn14);
        btn14.setOnClickListener(this);
        btn15 = findViewById(R.id.Btn15);
        btn15.setOnClickListener(this);
        btn16 = findViewById(R.id.Btn16);
        btn16.setOnClickListener(this);
        btn17 = findViewById(R.id.Btn17);
        btn17.setOnClickListener(this);
        btn18 = findViewById(R.id.Btn18);
        btn18.setOnClickListener(this);
        btn19 = findViewById(R.id.Btn19);
        btn19.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.Btn1:
                 Intent intent = new Intent(this,Activity01.class);
                 startActivity(intent);
                 break;
             case R.id.Btn2:
                 Intent intent2 = new Intent(this,Activity02.class);
                 startActivity(intent2);
                 break;
             case R.id.Btn3:
                 Intent intent3 = new Intent(this,Activity03.class);
                 startActivity(intent3);
                 break;
             case R.id.Btn4:
                 Intent intent4 = new Intent(this,Avtivity_8_5.class);
                 startActivity(intent4);
                 break;
             case R.id.Btn5:
                 Intent intent5 = new Intent(this,AsyncTask_Activity.class);
                 startActivity(intent5);
                 break;
             case R.id.Btn6:
                 Intent intent6 = new Intent(this,GetHtmlFromNet_Activity.class);
                 startActivity(intent6);
                 break;
             case R.id.Btn7:
                 Intent intent7 = new Intent(this,HttpPost_Activity.class);
                 startActivity(intent7);
                 break;
             case R.id.Btn8:
                 Intent intent8 = new Intent(this,FileUploadActivity.class);
                 startActivity(intent8);
                 break;
             case R.id.Btn9:
                 Intent intent9 = new Intent(this,BoardcastActivity.class);
                 startActivity(intent9);
                 break;
             case R.id.Btn10:
                 Intent intent10 = new Intent(this,Order_UnOrder_Broadcast_Activity.class);
                 startActivity(intent10);
                 break;
             case R.id.Btn11:
                 Intent intent11 = new Intent(this,SMS_Listener_Activity.class);
                 startActivity(intent11);
                 break;
             case R.id.Btn12:
                 Intent intent12 = new Intent(this,FragmentActivity10.class);
                 startActivity(intent12);
                 break;
             case R.id.Btn13:
                 Intent intent13 = new Intent(this,com.example.wwez.RecyclerView.Main2Activity.class);
                 startActivity(intent13);
                 break;
             case R.id.Btn14:
                 Intent intent14 = new Intent(this,ListViewMainActivity.class);
                 startActivity(intent14);
                 break;
             case R.id.Btn15:
                 Intent intent15 =new Intent(this, ViewPagerIndicator_MainActivity.class);
                 startActivity(intent15);
                 break;
             case R.id.Btn16:
                 Intent intent16 =new Intent(this, SliderMenuMainActivity.class);
                 startActivity(intent16);
                 break;
             case R.id.Btn17:
                 Intent intent17 =new Intent(this, DropdownMenu_MainActivity.class);
                 startActivity(intent17);
                 break;
             case R.id.Btn18:
                 Intent intent18 =new Intent(this, Tab04_MainActivity.class);
                 startActivity(intent18);
                 break;
             case R.id.Btn19:
                 Intent intent19 =new Intent(this, Webview_MainActivity.class);
                 startActivity(intent19);
                 break;
         }
    }
}
