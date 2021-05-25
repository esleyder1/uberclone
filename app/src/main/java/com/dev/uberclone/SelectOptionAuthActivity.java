package com.dev.uberclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SelectOptionAuthActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btnGoToLogin, btnGoToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_auth);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.choose_option);

        btnGoToLogin = findViewById(R.id.btnGoToLogin);
        btnGoToRegister = findViewById(R.id.btnGoToRegister);

        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });

        btnGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }
        });


    }

    private void goToLogin() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    private void goToRegister() {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
}