package com.example.melon.cauhanja.Manager;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.melon.cauhanja.MenuActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HistoryManager {
    private static ArrayList<Map<String, String>> historyArray = new ArrayList<Map<String, String>>();
    private static HistoryManager instance;
    private static boolean isDownloaded = false;
    private static Context mContext;

    public static void downHistory(Context context) {
        mContext = context;
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Tag", "down Success");
                try {
                    historyArray = JSONtoArray(new JSONObject(response));
                    isDownloaded = true;
                } catch (Exception e) {
                }
            }
        };

        HistoryRequest historyRequest = new HistoryRequest(MenuActivity.getMemberID(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(historyRequest);
    }

    public static boolean isDownloaded(){
        return isDownloaded;
    }
    public static ArrayList<Map<String, String>> getHistoryArray() {
        return historyArray;
    }

    public static ArrayList<Map<String, String>> JSONtoArray(JSONObject json) {
        ArrayList<Map<String, String>> array = new ArrayList<Map<String, String>>();
        try {
            boolean success = json.getBoolean("success");
            if (success) {
                Log.d("Tag", "success");
                int[] counts = {0, 0, 0}; //한자, 독해, 어휘
                int[] wrongCounts = {0, 0, 0};

                for (int i = 0; i < json.length() - 1; i++) {
                    JSONObject entity = json.getJSONObject(Integer.toString(i));
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
                    array.add(map);
                }
            } else {
                Log.d("Tag", "failed");
            }
        } catch (Exception e) {
            Log.d("Tag", e.getMessage() + e.getStackTrace());
            e.printStackTrace();
        }
        return array;
    }

}

class HistoryRequest extends StringRequest {
    final static private String URL = "https://jeffjks.cafe24.com/getSolveHistory.php";
    private Map<String, String> parameters;

    public HistoryRequest(String id, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null); // 해당 정보를 POST 방식으로 URL에 전송
        Log.d("Tag", "LoginRequest");
        parameters = new HashMap<>();
        parameters.put("memberID", id);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }

}