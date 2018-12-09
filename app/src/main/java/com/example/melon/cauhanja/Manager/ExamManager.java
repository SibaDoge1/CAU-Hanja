package com.example.melon.cauhanja.Manager;

import android.content.Context;

import android.content.Intent;
import android.content.res.AssetManager;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ExamManager {


    private Context mContext;
    private static ExamManager instance;
    private HashMap<Integer,String> category;
    private ArrayList<Exam> examList;
    private ArrayList<Integer> questionNumberList;
    private Response.Listener<String> responseListener;
    private boolean isDone;
    private String level;

    private ExamManager(Context context){
        mContext = context;

        examList = new ArrayList<>();
        questionNumberList = new ArrayList<>();

        category = new HashMap();

        AssetManager am = mContext.getResources().getAssets();
        InputStream is =  null;

        level = "3";

        try {
            is = am.open("Category.txt");
            BufferedReader bufrd = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            String line = bufrd.readLine();
            while ((line = bufrd.readLine()) != null) {
                String str[] = line.split("\t");
                category.put(Integer.parseInt(str[0]),str[1]);
            }
            bufrd.close();
        }catch (Exception e){
        }

        responseListener = new Response.Listener<String> () {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success) {
                        for (int inx = 0; inx < 20; inx++) {
                            Log.i("examTest", "exam Make Flag 2 / " + inx);

                            JSONObject jo = jsonResponse.getJSONObject(String.valueOf(inx));

                            Log.i("examTest", "12345");

                            Exam e = new Exam();
                            e.setId(Integer.parseInt(jo.getString("qid")));
                            e.setLevel(Integer.parseInt(jo.getString("level")));
                            e.setAnswer(Integer.parseInt(jo.getString("answer")));
                            e.setErrorRate(Integer.parseInt(jo.getString("rate")));

                            e.setContent(jo.getString("content"));
                            e.setType(Integer.parseInt(jo.getString("type")));
                            e.setQuestion(category.get(e.getType()));

                            examList.add(e);
                        }

                        isDone = true;
                    }else{

                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static ExamManager getInstance(Context context) {
        if(instance == null){
            instance = new ExamManager(context);
        }
        return instance;
    }

    public void makeExam(String userID){
        examList = new ArrayList<>();
        questionNumberList = new ArrayList<>();

        isDone = false;

        getQuestionListRequsest gqs = new getQuestionListRequsest(userID,level, responseListener);
        RequestQueue queue = Volley.newRequestQueue(mContext.getApplicationContext());
        queue.add(gqs);
        Log.i("examTest","exam Make Flag 5");

        /*
        // 테스트용 10문제
        Random r = new Random();
        for(int inx = 0; inx < 20; inx ++){
            questionNumberList.add(r.nextInt(326) + 1);
        }
        //

        for(int inx = 0; inx < questionNumberList.size(); inx ++){
            addQuestion(questionNumberList.get(inx));
        }
        */

    }

    public boolean isDone() {
        return isDone;
    }

    public ArrayList<Exam> getExamList(){
        return examList;
    }

    public int getSize(){
        return examList.size();
    }
}


class getQuestionListRequsest extends StringRequest {
    final static private String URL = "https://jeffjks.cafe24.com/getQuestionsToShow.php";
    private Map<String, String> parameters;

    public getQuestionListRequsest(String user_id,String level, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null); // 해당 정보를 POST 방식으로 URL에 전송
        parameters = new HashMap<>();
        parameters.put("userID", user_id);
        parameters.put("problemLevel", level);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}