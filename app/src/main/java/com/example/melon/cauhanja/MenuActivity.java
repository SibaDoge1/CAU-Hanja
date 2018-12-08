package com.example.melon.cauhanja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    private String memberID;
    private String memnerName;

    private TextView menu_Member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent;
        intent = getIntent();

        memberID = intent.getStringExtra("Member_ID");
        memnerName = intent.getStringExtra("Member_Name");
        menu_Member = (TextView)findViewById(R.id.menu_member);

        menu_Member.setText(memnerName + "님 환영합니다.");
    }

    public void onClickExam(View v){
        Intent intent = new Intent(this, PopupActivity.class);
        intent.putExtra("Popup_Mode",1);
        startActivity(intent);
    }

    public void onClickWord(View v){
        Intent intent = new Intent(this, PopupActivity.class);
        intent.putExtra("Popup_Mode",2);
        startActivity(intent);
    }

    public void onClickStat(View v){
        Intent intent = new Intent(this, UserStatActivity.class);
        startActivity(intent);
    }

    public void onClickResult(View v) {
        Intent intent = new Intent(this, TestResultActivity.class);
        intent.putExtra("elapsedTime", "10:00");
        intent.putExtra("questionCnt", 30);
        intent.putExtra("rightAnsCnt", 15);
        startActivity(intent);

    }
}
