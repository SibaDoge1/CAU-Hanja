package com.example.melon.cauhanja;

import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class ExamActivity extends AppCompatActivity {

    private Timer timer;
    private TimerTask timerTask;
    private int timeSec;

    private TextView timeView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        timeView = (TextView)findViewById(R.id.exam_time);
        setTimer();
    }

    public void OnClickTest(View v){
        goToResult();
    }

    protected void goToResult() {
        Intent intent = new Intent(this, TestResultActivity.class);
        intent.putExtra("elapsedTime", timeView.getText().toString());
        intent.putExtra("questionCnt", 30);
        intent.putExtra("rightAnsCnt", 15);
        startActivity(intent);
        finish();

    }

    public void setTimer(){
        timeSec = 0;

        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timeSec ++;
                        setTimerView();
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask,0,1000);
    }

    public void setTimerView() {
        int time_min = timeSec / 60;
        int time_sec = timeSec % 60;

        timeView.setText(String.format("%02d : %02d",time_min,time_sec));
    }
}
