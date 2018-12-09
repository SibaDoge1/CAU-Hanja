package com.example.melon.cauhanja;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.melon.cauhanja.Manager.HistoryManager;

import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class UserStatActivity extends AppCompatActivity {

    private ArrayList<Map<String, String>> historyArray;
    private TextView count1;
    private TextView rate1;
    private TextView count2;
    private TextView rate2;
    private TextView count3;
    private TextView rate3;
    private CheckBox cb1;
    private CheckBox cb2;
    private CheckBox cb3;
    private RelativeLayout loading;
    private Timer loadTimer;
    private TimerTask loadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_stat);
        count1 = findViewById(R.id.text_count1);
        rate1 = findViewById(R.id.text_rate1);
        count2 = findViewById(R.id.text_count2);
        rate2 = findViewById(R.id.text_rate2);
        count3 = findViewById(R.id.text_count3);
        rate3 = findViewById(R.id.text_rate3);
        cb1 = findViewById(R.id.checkBox1);
        cb2 = findViewById(R.id.checkBox2);
        cb3 = findViewById(R.id.checkBox3);
        loading = findViewById(R.id.loading);
        setLoading();

    }

    public void setLoading() {
        HistoryManager.downHistory(this);
        loadTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (HistoryManager.isDownloaded()) {
                            historyArray = HistoryManager.getHistoryArray();
                            onDownload();
                            loadTimer.cancel();
                        }
                    }
                });
            }
        };

        loadTimer = new Timer();
        loadTimer.schedule(loadTask, 0, 500);
    }

    public void onDownload() {
        int[] counts = {0, 0, 0}; //한자, 독해, 어휘
        int[] wrongCounts = {0, 0, 0};

        for (int i = 0; i < historyArray.size(); i++) {
            switch (historyArray.get(i).get("type")) {
                case "한자":
                    counts[0] += Integer.parseInt(historyArray.get(i).get("count"));
                    wrongCounts[0] += Integer.parseInt(historyArray.get(i).get("wrong_count"));
                    break;
                case "독해":
                    counts[1] += Integer.parseInt(historyArray.get(i).get("count"));
                    wrongCounts[1] += Integer.parseInt(historyArray.get(i).get("wrong_count"));
                    break;
                case "어휘":
                    counts[2] += Integer.parseInt(historyArray.get(i).get("count"));
                    wrongCounts[2] += Integer.parseInt(historyArray.get(i).get("wrong_count"));
                    break;
            }
        }
        count1.setText(wrongCounts[0] + " / " + counts[0]);
        count2.setText(wrongCounts[1] + " / " + counts[1]);
        count3.setText(wrongCounts[2] + " / " + counts[2]);
        if (counts[0] != 0)
            rate1.setText(((float) wrongCounts[0] / (float) counts[0]) * 100 + " %");
        if (counts[1] != 0)
            rate2.setText(((float) wrongCounts[1] / (float) counts[1]) * 100 + " %");
        if (counts[2] != 0)
            rate3.setText(((float) wrongCounts[2] / (float) counts[2]) * 100 + " %");
        loading.setVisibility(View.GONE);
    }

    public void onClickHistory(View v) {
        ArrayList<String> typeFilter = new ArrayList<String>();
        if (cb1.isChecked()) typeFilter.add("한자");
        if (cb2.isChecked()) typeFilter.add("독해");
        if (cb3.isChecked()) typeFilter.add("어휘");
        if (typeFilter.size() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserStatActivity.this);
            AlertDialog dialog = builder.setMessage("한 개 이상의 항목을 선택하세요").setNegativeButton("끄기", null).create();
            dialog.show();
            return;
        }
        Intent intent = new Intent(this, TestHistoryActivity.class);
        intent.putExtra("typeFilter", typeFilter);
        startActivity(intent);
    }

    public void onClickQuit(View v) {
        finish();
    }
}



