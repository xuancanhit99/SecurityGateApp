package com.xuancanhit.securitygateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SoundActivity extends AppCompatActivity {

    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        ivBack = findViewById(R.id.iv_sound_back);
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
        startActivity(new Intent(SoundActivity.this, MainMenuActivity.class));
        finish();
    }
}