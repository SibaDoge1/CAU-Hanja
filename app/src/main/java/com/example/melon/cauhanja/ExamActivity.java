package com.example.melon.cauhanja;


import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.melon.cauhanja.Manager.Exam;
import com.example.melon.cauhanja.Manager.ExamManager;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ExamActivity extends AppCompatActivity {

    private String memberID;
    private int level;

    private int questionIndex;
    private int timeSec;
    private int selectButtonNum;

    private Timer timer;
    private TimerTask timerTask;
    private Timer loadTimer;
    private TimerTask loadTask;


    private AlertDialog dialog;

    private TextView numView;
    private TextView timeView;
    private TextView questionView;
    private TextView contentView;

    private RadioButton ex1;
    private RadioButton ex2;
    private RadioButton ex3;
    private RadioButton ex4;
    private RadioButton ex5;


    ExamManager examManager;
    ArrayList<Exam> examList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        memberID = getIntent().getStringExtra("Member_ID");
        level = getIntent().getIntExtra("Level",0);

        numView = (TextView) findViewById(R.id.exam_questionNumber);
        timeView = (TextView) findViewById(R.id.exam_time);
        questionView = (TextView) findViewById(R.id.exam_type);
        contentView = (TextView) findViewById(R.id.exam_question);

        ex1 = (RadioButton) findViewById(R.id.exam_answer1);
        ex2 = (RadioButton) findViewById(R.id.exam_answer2);
        ex3 = (RadioButton) findViewById(R.id.exam_answer3);
        ex4 = (RadioButton) findViewById(R.id.exam_answer4);
        ex5 = (RadioButton) findViewById(R.id.exam_answer5);

        examManager = ExamManager.getInstance(this);
        examManager.makeExam("테스트용");

        questionIndex = 0;

        setLoading();
    }

    protected void goToResult() {
        Intent intent = new Intent(this, TestResultActivity.class);
        intent.putExtra("elapsedTime", timeView.getText().toString());
        intent.putExtra("questionCnt", 30);
        intent.putExtra("rightAnsCnt", 15);
        startActivity(intent);

        loadTimer.cancel();
        timer.cancel();
        finish();
    }

    public void setQuestion() {
        numView.setText((questionIndex + 1) + "번");
        questionView.setText(examList.get(questionIndex).getQuestion());
        contentView.setText(examList.get(questionIndex).getContent());

        ex1.setText(examList.get(questionIndex).getExample(0));
        ex2.setText(examList.get(questionIndex).getExample(1));
        ex3.setText(examList.get(questionIndex).getExample(2));
        ex4.setText(examList.get(questionIndex).getExample(3));
        ex5.setText(examList.get(questionIndex).getExample(4));
    }

    public void setLoading() {
        loadTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (examManager.isDone()) {
                            examList = examManager.getExamList();
                            setQuestion();
                            setTimer();
                            loadTimer.cancel();
                        }
                    }
                });
            }
        };

        loadTimer = new Timer();
        loadTimer.schedule(loadTask, 0, 500);
    }

    public void setTimer() {
        timeSec = 0;

        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timeSec++;
                        setTimerView();
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
    }

    public void setTimerView() {
        int time_min = timeSec / 60;
        int time_sec = timeSec % 60;

        timeView.setText(String.format("%02d : %02d", time_min, time_sec));
    }

    public void clearButtion() {
        ex1.setChecked(false);
        ex2.setChecked(false);
        ex3.setChecked(false);
        ex4.setChecked(false);
        ex5.setChecked(false);
        selectButtonNum = 0;
    }

    public void sendResult(){

    }

    public void OnClickTest(View v) {
        goToResult();
    }

    public void onClickRadioButton(View v) {
        RadioButton b = (RadioButton)v;
        switch (b.getId()){
            case R.id.exam_answer1:
                selectButtonNum = 1;
                break;
            case R.id.exam_answer2:
                selectButtonNum = 2;
                break;
            case R.id.exam_answer3:
                selectButtonNum = 3;
                break;
            case R.id.exam_answer4:
                selectButtonNum = 4;
                break;
            case R.id.exam_answer5:
                selectButtonNum = 5;
                break;
        }
    }

    public void onClickNext(View v) {
        if (questionIndex + 1 < examList.size()) {
            if(selectButtonNum != 0) {
                questionIndex++;
                clearButtion();
                setQuestion();
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                dialog = builder.setMessage("답을 체크해주세요.").setNegativeButton("OK", null).create();
                dialog.show();
            }
        } else {
            goToResult();
        }
    }

}
