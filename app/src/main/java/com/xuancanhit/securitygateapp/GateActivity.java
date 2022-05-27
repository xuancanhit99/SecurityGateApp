package com.xuancanhit.securitygateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class GateActivity extends AppCompatActivity {

    ImageView ivBack;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gate);

        ivBack = findViewById(R.id.iv_gate_back);
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
        startActivity(new Intent(GateActivity.this, MainMenuActivity.class));
        finish();
    }
}