package com.example.melon.cauhanja;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class WordListActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        final String[] items = {"金\t성 김", "한자2", "한자3"};

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.wordlist_item, items){
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
                String str[] = items[position].split("\t");
                if(str.length > 1) {
                    Intent intent = new Intent(getApplicationContext(), WordActivity.class);
                    intent.putExtra("Hanja", str[0]);
                    intent.putExtra("Mean",str[1]);
                    startActivity(intent);
                }
            }
        });
    }
}
