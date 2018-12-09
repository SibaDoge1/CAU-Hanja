package com.example.melon.cauhanja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.view.ViewGroup;;

import com.example.melon.cauhanja.Manager.WordManager;

import java.util.ArrayList;

public class TestResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        String elapsedTime;
        int questionCnt;
        int rightAnsCnt;

        Intent intent = getIntent();
        elapsedTime = intent.getStringExtra("elapsedTime");
        questionCnt = intent.getIntExtra("questionCnt", 0);
        rightAnsCnt = intent.getIntExtra("rightAnsCnt", 0);

        TextView time = findViewById(R.id.elapsed_time);
        TextView count = findViewById(R.id.count);
        TextView rate = findViewById(R.id.rate);

        time.setText(elapsedTime);
        count.setText(rightAnsCnt + " / " + questionCnt);
        float correctRate = ((float)rightAnsCnt/(float)questionCnt)*100;
        rate.setText(correctRate + " %");
    }

    public void OnClickQuit(View v){
        finish();
    }

    public void OnClickHistory(View v){
        Intent intent = new Intent(this, TestHistoryActivity.class);
        ArrayList<String> typeFilter = new ArrayList<String>();
        typeFilter.add("한자");
        typeFilter.add("독해");
        typeFilter.add("어휘");
        startActivity(intent);
        finish();
    }
}
