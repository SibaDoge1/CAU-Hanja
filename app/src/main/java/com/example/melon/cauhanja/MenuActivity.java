package com.example.melon.cauhanja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    private int memberNumber;

    private TextView menu_Member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent;
        intent = getIntent();

        memberNumber = intent.getIntExtra("Member_Number",0);

        menu_Member = (TextView)findViewById(R.id.menu_member);

        menu_Member.setText("회원번호 : "+memberNumber);
    }
}
