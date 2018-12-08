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
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HistoryRequest extends StringRequest {
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