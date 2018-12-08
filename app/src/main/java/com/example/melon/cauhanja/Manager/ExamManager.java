package com.example.melon.cauhanja.Manager;

import android.content.Context;

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

public class ExamManager {
    public class exam{
        int id;
        int type;
        int level;
        int answer;
        String content;
        float errorRate;
    }

    private Context mContext;
    private static ExamManager instance;
    private HashMap<Integer,String> category;
    private ArrayList<exam> examList;

    private ExamManager(Context context){
        mContext = context;

        examList = new ArrayList<>();

        category = new HashMap();

        AssetManager am = mContext.getResources().getAssets();
        InputStream is =  null;

        try {
            is = am.open("Category.txt");
            BufferedReader bufrd = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            String line = bufrd.readLine();
            while ((line = bufrd.readLine()) != null) {
                String str[] = line.split("\t");
                category.put(Integer.getInteger(str[0]),str[1]);
            }
            bufrd.close();
        }catch (Exception e){
        }
    }

    public static ExamManager getInstance(Context context) {
        if(instance == null){
            instance = new ExamManager(context);
        }
        return instance;
    }

    public void addQuestion(int id){

        Response.Listener<String> responseListener = new Response.Listener<String> () {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("examTest", "flag2");

                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        exam e = new exam();

                        e.id = Integer.parseInt(jsonResponse.getString("id"));
                        e.level = Integer.parseInt(jsonResponse.getString("level"));
                        e.answer =  Integer.parseInt(jsonResponse.getString("answer"));
                        e.content = jsonResponse.getString("content");
                        e.errorRate = Integer.parseInt(jsonResponse.getString("errorRate"));

                        String c = jsonResponse.getString("category");
                        int subc = Integer.parseInt(jsonResponse.getString("subcategory"));

                        e.type = getType(c) + subc;

                        examList.add(e);
                        Log.d("examTest", e.content);

                    }
                    else {
                        Log.d("examTest", "fail");
                    }
                }
                catch (Exception e) {
                    Log.d("examTest", e.getMessage());

                    e.printStackTrace();
                }
            }
        };
        getQuesiontRequest gqs = new getQuesiontRequest(String.valueOf(id), responseListener);
        RequestQueue queue = Volley.newRequestQueue(mContext.getApplicationContext());
        queue.add(gqs);

        Log.d("examTest", "flag1");
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
}
class getQuesiontRequest extends StringRequest {
    final static private String URL = "https://jeffjks.cafe24.com/getQuestion.php";
    private Map<String, String> parameters;

    public getQuesiontRequest(String qid, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null); // 해당 정보를 POST 방식으로 URL에 전송
        parameters = new HashMap<>();
        parameters.put("questionID", qid);
        Log.d("examTest", "getQ");

    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
