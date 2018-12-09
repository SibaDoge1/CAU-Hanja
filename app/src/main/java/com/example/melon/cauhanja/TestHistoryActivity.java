package com.example.melon.cauhanja;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestHistoryActivity extends AppCompatActivity {
    private ListView listView;
    private JSONObject historyObject;
    private ArrayList<Map<String, String>> historyArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_history);

        Intent intent = getIntent();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Tag", "down Success");
                try {
                    historyObject = new JSONObject(response);
                    JSONtoArray();
                } catch (Exception e) {
                    Log.d("Tag", e.getMessage() + e.getStackTrace());
                    e.printStackTrace();
                }
            }
        };

        HistoryRequest historyRequest = new HistoryRequest(MenuActivity.getMemberID(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(historyRequest);
/*
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.testhistory_item , historyArray){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = convertview;

                if(v == null){
                    viewHolder = new ViewHolder();
                    v = inflater.inflate(R.layout.list_row, null);
                    viewHolder.tv_title = (TextView)v.findViewById(R.id.tv_title);
                    viewHolder.iv_image = (ImageView)v.findViewById(R.id.iv_image);
                    viewHolder.btn_button = (Button)v.findViewById(R.id.btn_button);
                    viewHolder.cb_box = (CheckBox)v.findViewById(R.id.cb_box);

                    v.setTag(viewHolder);

                }else {
                    viewHolder = (ViewHolder)v.getTag();
                }

                viewHolder.tv_title.setText(getItem(position).title);

                viewHolder.iv_image.setOnClickListener(buttonClickListener);
                viewHolder.iv_image.setTag(position);

                viewHolder.btn_button.setText(getItem(position).button);
                viewHolder.btn_button.setOnClickListener(buttonClickListener);
                viewHolder.btn_button.setTag(position);

                viewHolder.cb_box.setTag(position);
                viewHolder.cb_box.setOnClickListener(buttonClickListener);

                return v;
            }
        };

        listView = (ListView) findViewById(R.id.history_list);
        listView.setAdapter(adapter);

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
        });
        */
    }

    public void JSONtoArray() {
        try {
            boolean success = historyObject.getBoolean("success");
            if (success) {
                Log.d("Tag", "success");
                int[] counts = {0, 0, 0}; //한자, 독해, 어휘
                int[] wrongCounts = {0, 0, 0};

                for (int i = 0; i < historyObject.length() - 1; i++) {
                    JSONObject entity = historyObject.getJSONObject(Integer.toString(i));
                    Map<String, String> map = new HashMap<>();
                    map.put("q_id", entity.getString("q_id"));
                    map.put("count", entity.getString("count"));
                    map.put("wrong_count", entity.getString("wrong_count"));
                    map.put("error_rate", entity.getString("error_rate"));
                    map.put("last_solved", entity.getString("last_solved"));
                    map.put("type", entity.getString("type"));
                    map.put("content", entity.getString("content"));
                    map.put("answer", entity.getString("answer"));
                    map.put("question", entity.getString("question"));
                    historyArray.add(map);
                }
            } else {
                Log.d("Tag", "failed");
            }
        } catch (Exception e) {
            Log.d("Tag", e.getMessage() + e.getStackTrace());
            e.printStackTrace();
        }
    }
}
