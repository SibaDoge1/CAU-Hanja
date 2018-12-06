package com.example.melon.cauhanja;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserStatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_stat);


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                      /*
                        for(int i=0; i < jsonResponse.length(); i++){
                            JSONObject entity = jsonResponse.getJSONObject("0");
                        }*/
                        JSONObject entity = jsonResponse.getJSONObject("0");
                        TextView hi = findViewById(R.id.text_type1);
                        hi.setText(entity.getString("last_solved"));
                    } else {
                        Log.d("Tag", "clicked2");
                        AlertDialog.Builder builder = new AlertDialog.Builder(UserStatActivity.this);
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        };

        HistoryRequest historyRequest = new HistoryRequest("Kim", responseListener);
        RequestQueue queue = Volley.newRequestQueue(UserStatActivity.this);
        queue.add(historyRequest);
    }

    public void onClickHistory(View v) {
        Intent intent = new Intent(this, TestHistoryActivity.class);
        startActivity(intent);
    }

    public void onClickQuit(View v) {
        finish();
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
