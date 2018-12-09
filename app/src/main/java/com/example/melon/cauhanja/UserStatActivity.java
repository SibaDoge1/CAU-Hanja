package com.example.melon.cauhanja;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class UserStatActivity extends AppCompatActivity {

    private TextView count1;
    private TextView rate1;
    private TextView count2;
    private TextView rate2;
    private TextView count3;
    private TextView rate3;
    private CheckBox cb1;
    private CheckBox cb2;
    private CheckBox cb3;
    private JSONObject historyObject;
    private String userId;

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

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Tag", "down Success");
                try {
                    historyObject = new JSONObject(response);
                    onDownload();
                } catch (Exception e) {
                    Log.d("Tag", e.getMessage() + e.getStackTrace());
                    e.printStackTrace();
                }
            }
        };

        HistoryRequest historyRequest = new HistoryRequest(MenuActivity.getMemberID(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(historyRequest);

    }

    public void onDownload() {
        try {
            boolean success = historyObject.getBoolean("success");
            if (success) {
                Log.d("Tag", "success");
                int[] counts = {0, 0, 0}; //한자, 독해, 어휘
                int[] wrongCounts = {0, 0, 0};

                for (int i = 0; i < historyObject.length() - 1; i++) {
                    JSONObject entity = historyObject.getJSONObject(Integer.toString(i));
                    switch (entity.getString("type")) {
                        case "한자":
                            counts[0] += entity.getInt("count");
                            wrongCounts[0] += entity.getInt("wrong_count");
                            break;
                        case "독해":
                            counts[1] += entity.getInt("count");
                            wrongCounts[1] += entity.getInt("wrong_count");
                            break;
                        case "어휘":
                            counts[2] += entity.getInt("count");
                            wrongCounts[2] += entity.getInt("wrong_count");
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
            } else {
                Log.d("Tag", "failed");
            }
        } catch (Exception e) {
            Log.d("Tag", e.getMessage() + e.getStackTrace());
            e.printStackTrace();
        }
    }

    public void onClickHistory(View v) {
        int checkData = 0;
        if (cb1.isChecked()) checkData += 1;
        if (cb2.isChecked()) checkData += 2;
        if (cb3.isChecked()) checkData += 4;
        if (checkData == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserStatActivity.this);
            AlertDialog dialog = builder.setMessage("한 개 이상의 항목을 선택하세요").setNegativeButton("끄기", null).create();
            dialog.show();
            return;
        }
        Intent intent = new Intent(this, TestHistoryActivity.class);
        intent.putExtra("checkData", checkData);
        startActivity(intent);
    }

    public void onClickQuit(View v) {
        finish();
    }
}



