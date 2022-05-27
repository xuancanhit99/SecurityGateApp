package com.xuancanhit.securitygateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button btnUserLogin;
    private EditText edtUserName, edtUserPassword;

    String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        btnUserLogin = findViewById(R.id.btn_user_login);
        edtUserName = findViewById(R.id.edt_user_name);
        edtUserPassword = findViewById(R.id.edt_user_password);

        btnUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Login", Toast.LENGTH_SHORT).show();
//                userName = edtUserName.getText().toString();
//                if(edtUserPassword.getText().toString() == "Vorota") {
//                    Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
//                    intent.putExtra("USER_NAME", userName);
//                    startActivity(intent);
//                }
            }
        });
    }
}