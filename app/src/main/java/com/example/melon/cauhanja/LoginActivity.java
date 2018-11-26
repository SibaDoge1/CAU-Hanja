package com.example.melon.cauhanja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText login_id;
    private EditText login_pass;

    private int memberNumber; // DB로 부터 받아오는 회원 id (키)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_id = (EditText) findViewById(R.id.signup_id);
        login_pass = (EditText) findViewById(R.id.login_password);
        memberNumber = 0;
    }

    public void onClickLogin(View v){
        String id = login_id.getText().toString();
        String pass = login_pass.getText().toString();

        Intent intent = new Intent(this,MenuActivity.class);
        intent.getIntExtra("Member_Number",memberNumber);
        startActivity(intent);
    }

    public void onClickSignUp(View v){
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }

    public void ondClickButton3(View v){
        //TODO Button3 click event
    }
}
