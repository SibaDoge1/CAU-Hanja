package com.example.melon.cauhanja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class UserStatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_stat);
    }

    public void onClickHistory(View v){
        Intent intent = new Intent(this, TestHistoryActivity.class);
        startActivity(intent);
    }

    public void onClickQuit(View v){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}
