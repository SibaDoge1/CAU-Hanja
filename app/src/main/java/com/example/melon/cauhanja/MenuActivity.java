package com.example.melon.cauhanja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MenuActivity extends AppCompatActivity {

    private static String memberID;
    private static String memnerName;

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

    public static String getMemberID(){
        return memberID;
    }
    public void onClickExam(View v){
        Intent intent = new Intent(this, PopupActivity.class);
        intent.putExtra("Popup_Mode",1);
        intent.putExtra("Member_ID",memberID);
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

    public void onClickQuit(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
