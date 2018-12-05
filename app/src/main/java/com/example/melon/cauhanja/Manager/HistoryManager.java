package com.example.melon.cauhanja.Manager;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

public class HistoryManager {
    private Context mContext;
    ArrayList<ArrayList<String>> wordList;

    private static HistoryManager instance;

    private HistoryManager(Context context){
        mContext = context;
        AssetManager am = mContext.getResources().getAssets();
        InputStream is =  null;

        wordList = new ArrayList<>();

        try {
            is = am.open("hanjaword.txt");
            BufferedReader bufrd = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            int level = 0;
            wordList.add(new ArrayList<String>());
            String line = bufrd.readLine();
            while ((line = bufrd.readLine()) != null) {
                String str[] = line.split("\t");
                if(str.length == 1){
                    level++;
                    wordList.add(new ArrayList<String>());
                }
                else{
                    wordList.get(level).add(line);
                }
            }
            bufrd.close();
        }catch (Exception e){
        }
    }


    public static HistoryManager getInstance(Context context) {
        if(instance == null){
            instance = new HistoryManager(context);
        }
        return instance;
    }

    public ArrayList<String> getWordList(int level){
        return wordList.get(level);
    }
}
