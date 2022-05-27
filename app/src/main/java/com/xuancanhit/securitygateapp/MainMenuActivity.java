package com.xuancanhit.securitygateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    private Button btnMenuGarage, btnMenuDoor, btnMenuGate, btnMenuLight, btnMenuExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        initView();

    }

    private void initView() {
        btnMenuDoor = findViewById(R.id.btn_menu_door);
        btnMenuExit = findViewById(R.id.btn_menu_exit);
        btnMenuGarage = findViewById(R.id.btn_menu_garage);
        btnMenuLight = findViewById(R.id.btn_menu_light);
        btnMenuGate = findViewById(R.id.btn_menu_gate);


        btnMenuGarage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, GarageActivity.class));
                finish();
            }
        });

        btnMenuGate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, MainActivity2.class));
                finish();
            }
        });

        btnMenuExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}