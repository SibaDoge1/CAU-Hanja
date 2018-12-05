package com.example.melon.cauhanja;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.melon.cauhanja.Manager.HistoryManager;

import java.util.ArrayList;

public class TestHistoryActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_history);

        Intent intent = getIntent();
/*
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.testhistory_item , historyList){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        listView = (ListView) findViewById(R.id.wordList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                String str[] = wordList.get(position).split("\t");
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
}
