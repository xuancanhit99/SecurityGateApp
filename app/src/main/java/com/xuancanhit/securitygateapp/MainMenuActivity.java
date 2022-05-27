package com.xuancanhit.securitygateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity {

    private Button btnMenuGarage, btnMenuDoor, btnMenuGate, btnMenuLight, btnMenuExit, btnMenuSound;
    private TextView tvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        initView();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String userName = bundle.getString("USER_NAME");
            tvUserName.setText(userName);
            //Toast.makeText(this, userName, Toast.LENGTH_LONG).show();
        }

    }

    private void initView() {
        btnMenuDoor = findViewById(R.id.btn_menu_door);
        btnMenuExit = findViewById(R.id.btn_menu_exit);
        btnMenuGarage = findViewById(R.id.btn_menu_garage);
        btnMenuLight = findViewById(R.id.btn_menu_light);
        btnMenuGate = findViewById(R.id.btn_menu_gate);
        btnMenuSound = findViewById(R.id.btn_menu_sound);

        tvUserName = findViewById(R.id.tv_user_name);


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
                startActivity(new Intent(MainMenuActivity.this, GateActivity.class));
                finish();
            }
        });

        btnMenuDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, DoorActivity.class));
                finish();
            }
        });

        btnMenuLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, MainActivity3.class));
                finish();
            }
        });



        btnMenuSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, SoundActivity.class));
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