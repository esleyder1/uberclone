package com.dev.uberclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button btnIamDriver, btnIamClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIamDriver = findViewById(R.id.btnIamDriver);
        btnIamClient = findViewById(R.id.btnIamClient);

        btnIamDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSelectAuth();
            }
        });

        btnIamClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSelectAuth();
            }
        });
    }

    private void goToSelectAuth() {
        Intent i = new Intent(this,SelectOptionAuthActivity.class);
        startActivity(i);
    }
}