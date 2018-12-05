package com.example.melon.cauhanja;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class ExamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);


    }

    public void OnClickTest(View v){
        goToResult();
    }

    protected void goToResult() {
        Intent intent = new Intent(this, TestResultActivity.class);
        intent.putExtra("elapsedTime", "10:00");
        intent.putExtra("questionCnt", 30);
        intent.putExtra("rightAnsCnt", 15);
        startActivity(intent);
        finish();

    }
}
