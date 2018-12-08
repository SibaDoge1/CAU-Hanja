package com.example.melon.cauhanja;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class PopupActivity extends Activity {

    private ListView listView;
    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        mode = getIntent().getIntExtra("Popup_Mode",0);

        String[] items = {"8급","7급","6급","5급","준4급","4급","준3급","3급","2급","1급"};

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
        listView = (ListView) findViewById(R.id.popup_List);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent;
                switch (mode) {
                    case 1:
                        intent = new Intent(getApplicationContext(), ExamActivity.class);
                        intent.putExtra("Level", position);
                        startActivity(intent);
                        finish();
                        break;
                    case 2:
                        intent = new Intent(getApplicationContext(), WordListActivity.class);
                        intent.putExtra("Level", position);
                        startActivity(intent);
                        finish();
                        break;
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }
}
