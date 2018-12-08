package com.example.melon.cauhanja;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.melon.cauhanja.Manager.ExamManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private EditText login_id;
    private EditText login_pass;

    private String memberName;
    private String memberID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_id = (EditText) findViewById(R.id.signup_id);
        login_pass = (EditText) findViewById(R.id.login_password);
    }

    public void onClickLogin(View v) {
        String id = login_id.getText().toString();
        String pass = login_pass.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String> () {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        String memberID = jsonResponse.getString("memberID");
                        String memberPW = jsonResponse.getString("memberPW");
                        memberName = jsonResponse.getString("memberName");

                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        intent.putExtra("Member_Name",memberName);
                        intent.putExtra("Member_ID",memberID);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Log.d("Tag", "clicked2");
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        dialog = builder.setMessage("Failed").setNegativeButton("Retry", null).create();
                        dialog.show();
                    }
                }
                catch (Exception e) {

                    e.printStackTrace();
                }
            }
        };

        LoginRequest loginRequest = new LoginRequest(id, pass, responseListener);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(loginRequest);
    }

    public void onClickSignUp(View v){
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }

    public void ondClickButton3(View v){
        //TODO Button3 click event
        //메뉴창으로 이동 - 로그인안될때
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        intent.putExtra("Member_Name","테스트");
        intent.putExtra("Member_ID","test1");
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}

class LoginRequest extends StringRequest {
    final static private String URL = "https://jeffjks.cafe24.com/memberLogin.php";
    private Map<String, String> parameters;

    public LoginRequest(String id, String pw, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null); // 해당 정보를 POST 방식으로 URL에 전송
        Log.d("Tag", "LoginRequest");
        parameters = new HashMap<>();
        parameters.put("memberID", id);
        parameters.put("memberPW", pw);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}