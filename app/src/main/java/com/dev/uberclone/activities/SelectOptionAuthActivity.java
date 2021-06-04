package com.dev.uberclone.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.dev.uberclone.R;
import com.dev.uberclone.activities.client.RegisterActivity;
import com.dev.uberclone.activities.driver.RegisterDriverActivity;
import com.dev.uberclone.includes.MyToolbar;

public class SelectOptionAuthActivity extends AppCompatActivity {
    SharedPreferences pref;
    Button btnGoToLogin, btnGoToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_auth);

        MyToolbar.show(this, R.string.choose_option, true);

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

        pref = this.getSharedPreferences("typeUser", MODE_PRIVATE);

    }

    private void goToLogin() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    private void goToRegister() {
        String typeUser = pref.getString("user", "");
        Intent i;
        if(typeUser.equals("client")){
            i = new Intent(this, RegisterActivity.class);
        }else{
            i = new Intent(this, RegisterDriverActivity.class);
        }
        startActivity(i);
    }
}