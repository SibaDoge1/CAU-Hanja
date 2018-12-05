package com.example.melon.cauhanja;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private EditText signup_id, signup_pass, signup_passConf, signup_name, signup_birth, signup_email;
    private Button signup_validate;
    private AlertDialog dialog;
    boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signup_id = (EditText)findViewById(R.id.signup_id);
        signup_validate = (Button) findViewById(R.id.button_validate);
        signup_pass = (EditText)findViewById(R.id.signup_pass);
        signup_passConf = (EditText)findViewById(R.id.signup_passConfirm);
        signup_name = (EditText)findViewById(R.id.signup_name);
        signup_birth = (EditText)findViewById(R.id.signup_birth);
        signup_email = (EditText)findViewById(R.id.signup_email);
    }

    public void onClickValidate(View v) {
        final String memberID = signup_id.getText().toString();
        if (validate) {
            return;
        }
        if (memberID.length() < 5) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
            dialog = builder.setMessage("아이디는 5자 이상이어야 합니다.").setNegativeButton("Retry", null).create();
            dialog.show();
        }
        else {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                        if (success) {
                            Toast.makeText(getApplicationContext(), "사용할 수 있는 아이디입니다.", Toast.LENGTH_SHORT).show();
                            validate = true;
                            signup_validate.setBackgroundColor(Color.GRAY);
                        } else {
                            dialog = builder.setMessage("이미 사용 중인 아이디입니다.").setNegativeButton("Retry", null).create();
                            dialog.show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            ValidateRequest validateRequest = new ValidateRequest(memberID, responseListener);
            RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
            queue.add(validateRequest);
        }
    }

    public void onClickSignUp(View v) {
        String id = signup_id.getText().toString();
        String pass = signup_pass.getText().toString();
        String passConf = signup_passConf.getText().toString();
        String name = signup_name.getText().toString();
        String birth = signup_birth.getText().toString();
        String email = signup_email.getText().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);

        if (!validate) {
            dialog = builder.setMessage("아이디 중복 체크를 해주세요.").setPositiveButton("OK", null).create();
            dialog.show();
            return;
        }
        else if(!pass.equals(passConf)){
            dialog = builder.setMessage("비밀번호 확인이 일치하지 않습니다.").setPositiveButton("OK", null).create();
            dialog.show();

            signup_pass.setText("");
            signup_passConf.setText("");
            return;
        }
        else if (pass.length() < 4) {
            dialog = builder.setMessage("비밀번호는 4자 이상이어야 합니다.").setPositiveButton("OK", null).create();
            dialog.show();
            return;
        }
        else if (id.equals("") || pass.equals("") || passConf.equals("")) {
            dialog = builder.setMessage("빈 칸 없이 입력해주세요.").setNegativeButton("OK", null).create();
            dialog.show();
            return;
        }
        else{
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                        if (success) {
                            builder.setMessage("회원등록이 완료되었습니다!");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });
                            builder.create();
                            builder.show();
                        } else {
                            dialog = builder.setMessage("Failed").setNegativeButton("Retry", null).create();
                            dialog.show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            RegisterRequest registerRequest = new RegisterRequest(id, pass, name, birth, email, responseListener);
            RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
            queue.add(registerRequest);
        }
    }

    public void onClickBack(View v){
        finish();
    }
}

class RegisterRequest extends StringRequest {
    final static private String URL = "https://jeffjks.cafe24.com/memberRegister.php";
    private Map<String, String> parameters;

    public RegisterRequest(String id, String pw, String name, String birth, String email, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("memberID", id);
        parameters.put("memberPW", pw);
        parameters.put("name", name);
        parameters.put("birth", birth);
        parameters.put("email", email);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}


class ValidateRequest extends StringRequest {
    final static private String URL = "https://jeffjks.cafe24.com/memberValidate.php";
    private Map<String, String> parameters;

    public ValidateRequest(String id, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        Log.d("Tag", "ValidateRequest");
        parameters = new HashMap<>();
        parameters.put("memberID", id);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}