package com.example.melon.cauhanja;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private EditText signup_id;
    private EditText signup_pass;
    private EditText signup_passConf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signup_id = (EditText)findViewById(R.id.signup_id);
        signup_pass = (EditText)findViewById(R.id.signup_pass);
        signup_passConf = (EditText)findViewById(R.id.signup_passConfirm);
    }

    public void onClickSignUp(View v){
        String id = signup_id.getText().toString();
        String pass = signup_pass.getText().toString();
        String passConf = signup_passConf.getText().toString();

        if(!pass.equals(passConf)){
            Toast.makeText(this,"비밀번호와 비밀번호 확인이 일치하지 않습니다.",Toast.LENGTH_SHORT).show();

            signup_pass.setText("");
            signup_passConf.setText("");
            return;
        }
        else{
            //TODO SignUp
            //회원가입 DB
            finish();
        }
    }

    public void onClickBack(View v){
        finish();
    }
}
