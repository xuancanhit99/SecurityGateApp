package com.xuancanhit.securitygateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button btnUserLogin;
    private EditText edtUserName, edtUserPassword;
    private ImageView ivClose;
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
        ivClose = findViewById(R.id.iv_user_login_close);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = edtUserName.getText().toString();
                if(edtUserPassword.getText().toString().equals("vorota")) {
                    Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                    intent.putExtra("USER_NAME", userName);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Авторизовался успешно!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Неверный пароль, проверьте еще раз!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}