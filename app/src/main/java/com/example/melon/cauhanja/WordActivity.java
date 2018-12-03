package com.example.melon.cauhanja;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.melon.cauhanja.Manager.WordManager;

import java.util.ArrayList;

public class WordActivity extends AppCompatActivity {

    private TextView hanja;
    private TextView mean;
    private int level;
    private int index;
    private ArrayList<String> wordList;
    private WordManager wordManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        hanja = (TextView)findViewById(R.id.word_hanja);
        mean = (TextView)findViewById(R.id.word_Mean);

        Intent intent;
        intent = getIntent();

        hanja.setText(intent.getStringExtra("Hanja"));
        mean.setText(intent.getStringExtra("Mean"));

        level = intent.getIntExtra("Level",0);
        index = intent.getIntExtra("Index",0);

        wordManager = WordManager.getInstance(this);
        wordList = wordManager.getWordList(level);
    }

    public void onClickNext(View v){
        if(index != wordList.size()) {
            index++;
        }

        String str[] = wordList.get(index).split("\t");

        hanja.setText(str[0]);
        mean.setText(str[1]);
    }

    public void onClickPrev(View v){
        if(index != 0) {
            index--;
        }

        String str[] = wordList.get(index).split("\t");

        hanja.setText(str[0]);
        mean.setText(str[1]);
    }

    public void onClickGoList(View v){
        finish();
    }
}
