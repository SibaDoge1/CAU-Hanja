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

public class WordActivity extends AppCompatActivity {

    private TextView hanja;
    private TextView mean;

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
    }
}
