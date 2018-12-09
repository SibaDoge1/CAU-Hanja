package com.example.melon.cauhanja;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.melon.cauhanja.Manager.HistoryManager;

import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class TestHistoryActivity extends AppCompatActivity {
    private ListView listView;
    private RelativeLayout loading;
    private EditText type;
    private EditText wrong;
    private EditText rate;
    private ArrayList<Map<String, String>> historyArray;
    private ArrayList<Map<String, String>> filteredArray = new ArrayList<Map<String, String>>();
    private ArrayList<String> typeFilter;
    private boolean reDownload;
    private Timer loadTimer;
    private TimerTask loadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_history);

        Intent intent = getIntent();
        typeFilter = intent.getStringArrayListExtra("typeFilter");
        reDownload = intent.getBooleanExtra("reDownload", true);

        loading = findViewById(R.id.loading);
        type = findViewById(R.id.type);
        wrong = findViewById(R.id.wrong);
        rate = findViewById(R.id.rate);
        listView = (ListView) findViewById(R.id.history_list);

        if(reDownload) HistoryManager.downHistory(this);
        setLoading();

    }

    public void setLoading() {
        loadTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (HistoryManager.isDownloaded()) {
                            historyArray = HistoryManager.getHistoryArray();
                            loading.setVisibility(View.GONE);
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
        historyArray = doFilter(typeFilter, -1, -1);
        historyAdapter adapter = new historyAdapter(TestHistoryActivity.this, R.layout.testhistory_item, historyArray);
        listView.setAdapter(adapter);

        loading.setVisibility(View.GONE);
        /*
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                String str[] = historyArray.get(position). split("\t");
                if(str.length > 1) {
                    Intent intent = new Intent(getApplicationContext(), WordActivity.class);
                    intent.putExtra("Hanja", str[0]);
                    intent.putExtra("Mean",str[1]);
                    intent.putExtra("Index",position);
                    intent.putExtra("Level",level);
                    startActivity(intent);
                }
            }
        });*/

        Log.d("Tag", "set adapter");

    }

    public void onClickFilter(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog;
        ArrayList<String> typeFilterArray = new ArrayList<String>();
        String typeFilter = "";
        int wrongFilter = -1;
        float rateFilter = -1;

        if (type.getText().toString().length() > 0) {
            typeFilter = type.getText().toString();
            if (!typeFilter.equals("한자") && !typeFilter.equals("독해") && !typeFilter.equals("어휘")) {
                dialog = builder.setMessage("올바른 문제 유형을 적어주세요").setPositiveButton("OK", null).create();
                dialog.show();
                return;
            }
            typeFilterArray.add(typeFilter);
        }
        if (wrong.getText().toString().length() > 0)
            wrongFilter = Integer.parseInt(wrong.getText().toString());
        if (rate.getText().toString().length() > 0)
            rateFilter = (Float.parseFloat(rate.getText().toString()))/(float)100;

        filteredArray = doFilter(typeFilterArray, wrongFilter, rateFilter);
        historyAdapter adapter = new historyAdapter(TestHistoryActivity.this, R.layout.testhistory_item, filteredArray);
        listView.setAdapter(adapter);
    }

    public ArrayList<Map<String, String>> doFilter(ArrayList<String> _typeFilter, int wrongFilter, float rateFilter) {
        ArrayList<Map<String, String>> resultArray = new ArrayList<Map<String, String>>();
        boolean[] filterOption = {true, true, true};
        Log.d("tag", "safasf " + _typeFilter);
        if (_typeFilter.size() == 0) filterOption[0] = false;
        if (wrongFilter < 0) filterOption[1] = false;
        if (Float.compare(rateFilter, 0) == -1) filterOption[2] = false;

        for (int i = 0; i < historyArray.size(); i++) {
            boolean[] isOk = {false, false, false};

            if(filterOption[0]){
                for (int j = 0; j < _typeFilter.size(); j++) {
                    if (historyArray.get(i).get("type").equals(_typeFilter.get(j))) {
                        isOk[0] = true;
                        break;
                    }
                }
            }
            else isOk[0] = true;

            if (filterOption[1]) {
                if (Integer.parseInt(historyArray.get(i).get("wrong_count")) > wrongFilter)
                    isOk[1] = true;
            }
            else isOk[1] = true;

            if (filterOption[2]) {
                if (Float.compare(Float.parseFloat(historyArray.get(i).get("error_rate")), rateFilter) == 1)
                    isOk[2] = true;
            }
            else isOk[2] = true;

            if (isOk[0] &&isOk[1] && isOk[2]) {
                resultArray.add(historyArray.get(i));
            }
        }

        return resultArray;
    }
}


class historyAdapter extends ArrayAdapter<Map<String, String>> {
    private ArrayList<Map<String, String>> historyArray = new ArrayList<Map<String, String>>();
    private Context mContext;

    public historyAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Map<String, String>> objects) {
        super(context, resource, objects);
        mContext = context;
        historyArray = objects;
    }

    @Override
    public int getCount() {
        return historyArray.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.testhistory_item, parent, false);
        }

        TextView type = (TextView) convertView.findViewById(R.id.type);
        TextView id = (TextView) convertView.findViewById(R.id.id);
        TextView question = (TextView) convertView.findViewById(R.id.question);
        TextView count = (TextView) convertView.findViewById(R.id.count);
        TextView rate = (TextView) convertView.findViewById(R.id.rate);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        Map<String, String> historyItem = historyArray.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        type.setText(historyItem.get("type"));
        id.setText(historyItem.get("q_id"));
        question.setText(historyItem.get("question") + " : \n" + historyItem.get("content"));
        count.setText(historyItem.get("wrong_count") + " / " + historyItem.get("count"));
        rate.setText(Float.parseFloat(historyItem.get("error_rate"))*(float)100 + " %");
        date.setText(historyItem.get("last_solved"));

        Log.d("Tag", "entity set");
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Map<String, String> getItem(int position) {
        return historyArray.get(position);
    }
}

