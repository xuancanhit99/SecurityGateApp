package com.xuancanhit.securitygateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class GarageActivity extends AppCompatActivity {

    private Button btnValue, btnUp, btnDown, btnA, btnB, btnC, btnD;
    private TextView tvPtOpen;
    private ImageView ivBack;

    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int i = 0;

    double value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage);

        initView();
    }

    private void initView() {
        btnDown = findViewById(R.id.btn_garage_down);
        btnValue = findViewById(R.id.btn_garage_value);
        btnUp = findViewById(R.id.btn_garage_up);
        btnA = findViewById(R.id.btn_a);
        btnB = findViewById(R.id.btn_b);
        btnC = findViewById(R.id.btn_c);
        btnD = findViewById(R.id.btn_d);
        tvPtOpen = findViewById(R.id.tv_pt_open);
        mProgressBar = findViewById(R.id.progressbar);
        ivBack = findViewById(R.id.iv_gr_back);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMenu();
            }
        });


        btnValue.setText(String.valueOf(value));
        tvPtOpen.setText("0%");

        btnUp.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"SetTextI18n", "ResourceAsColor"})
            @Override
            public void onClick(View view) {
                if (value == 0) {
                    value = 0.25;
                    btnValue.setText("1/4");
                    btnD.setVisibility(View.GONE);
                    progressBarAuto(); i=0;
                } else if (value == 0.25) {
                    value = 0.5;
                    btnValue.setText("1/2");
                    btnD.setVisibility(View.GONE);
                    btnC.setVisibility(View.GONE);
                    progressBarAuto(); i=0;
                } else if (value == 0.5) {
                    value = 0.75;
                    btnValue.setText("3/4");
                    btnD.setVisibility(View.GONE);
                    btnC.setVisibility(View.GONE);
                    btnB.setVisibility(View.GONE);
                    progressBarAuto(); i=0;
                } else if (value == 0.75) {
                    value = 1;
                    btnValue.setText("Полностью открытый");;
                    btnD.setVisibility(View.GONE);
                    btnC.setVisibility(View.GONE);
                    btnB.setVisibility(View.GONE);
                    btnA.setVisibility(View.GONE);
                    progressBarAuto(); i=0;
                }
                //Toast.makeText(GarageActivity.this, String.valueOf(value), Toast.LENGTH_SHORT).show();
            }
        });

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (value == 0.25) {
                    value = 0;
                    btnValue.setText("Полностью закрытый");
                    btnD.setVisibility(View.VISIBLE);
                    btnC.setVisibility(View.VISIBLE);
                    btnB.setVisibility(View.VISIBLE);
                    btnA.setVisibility(View.VISIBLE);
                    progressBarAuto(); i=0;

                } else if (value == 0.5) {
                    value = 0.25;
                    btnValue.setText("1/4");
                    btnC.setVisibility(View.VISIBLE);
                    btnB.setVisibility(View.VISIBLE);
                    btnA.setVisibility(View.VISIBLE);
                    progressBarAuto(); i=0;
                } else if (value == 0.75) {
                    value = 0.5;
                    btnValue.setText("1/2");
                    btnB.setVisibility(View.VISIBLE);
                    btnA.setVisibility(View.VISIBLE);
                    progressBarAuto(); i=0;
                } else if (value == 1) {
                    value = 0.75;
                    btnValue.setText("3/4");
                    btnA.setVisibility(View.VISIBLE);
                    progressBarAuto(); i=0;
                }

                //Toast.makeText(GarageActivity.this, String.valueOf(value), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void progressBarAuto() {
        mProgressBar.setProgress(i);
        mCountDownTimer = new CountDownTimer(10000, 1000) {

            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress" + i + millisUntilFinished);
                i++;
                int val = (int) i * 100 / (5000 / 1000);
                mProgressBar.setProgress(val);
                if(val <= 100)
                    tvPtOpen.setText(val +"%");

            }
            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                //Do what you want
                i++;
                mProgressBar.setProgress(100);
                tvPtOpen.setText("100%");
            }
        };
        mCountDownTimer.start();
    }

    @Override
    public void onBackPressed() {
        backToMenu();
    }

    private void backToMenu() {
        startActivity(new Intent(GarageActivity.this, MainMenuActivity.class));
        finish();
    }
}