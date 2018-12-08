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

    private ExamManager(Context context){
        mContext = context;

        examList = new ArrayList<>();
        questionNumberList = new ArrayList<>();

        category = new HashMap();

        AssetManager am = mContext.getResources().getAssets();
        InputStream is =  null;

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
                    if (success) {
                        Exam e = new Exam();

                        e.setId(Integer.parseInt(jsonResponse.getString("id")));
                        e.setLevel(Integer.parseInt(jsonResponse.getString("level")));
                        e.setAnswer(Integer.parseInt(jsonResponse.getString("answer")));
                        e.setErrorRate(Integer.parseInt(jsonResponse.getString("errorRate")));

                        e.setContent(jsonResponse.getString("content"));

                        String c = jsonResponse.getString("category");
                        int subc = Integer.parseInt(jsonResponse.getString("subcategory"));

                        e.setType(getType(c) + subc);
                        e.setQuestion(category.get(e.getType()));

                        examList.add(e);
                        if(examList.size() == questionNumberList.size()){
                            isDone = true;
                        }

                    }
                    else {
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

    public void addQuestion(int id){
        getQuesiontRequest gqs = new getQuesiontRequest(String.valueOf(id), responseListener);
        RequestQueue queue = Volley.newRequestQueue(mContext.getApplicationContext());
        queue.add(gqs);
    }

    public int getType(String category){
        int type = 0;
        switch (category){
            case "독해" :
                type = 100;
                break;
            case "어휘" :
                type = 200;
                break;
            case "한자" :
                type = 300;
                break;
        }
        return type;
    }

    public void makeExam(String userID){
        examList = new ArrayList<>();
        questionNumberList = new ArrayList<>();

        isDone = false;

        // 테스트용 10문제
        Random r = new Random();
        for(int inx = 0; inx < 10; inx ++){
            questionNumberList.add(inx + 1);
        }
        //

        for(int inx = 0; inx < questionNumberList.size(); inx ++){
            addQuestion(questionNumberList.get(inx));
        }
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
class getQuesiontRequest extends StringRequest {
    final static private String URL = "https://jeffjks.cafe24.com/getQuestion.php";
    private Map<String, String> parameters;

    public getQuesiontRequest(String qid, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null); // 해당 정보를 POST 방식으로 URL에 전송
        parameters = new HashMap<>();
        parameters.put("questionID", qid);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
