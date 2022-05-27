package com.xuancanhit.securitygateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity3 extends AppCompatActivity {

    //Light


    ImageView ivBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        ivBack = findViewById(R.id.iv_light_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMenu();
            }
        });
    }

    @Override
    public void onBackPressed() {
        backToMenu();
    }

    private void backToMenu() {
        startActivity(new Intent(MainActivity3.this, MainMenuActivity.class));
        finish();
    }
}