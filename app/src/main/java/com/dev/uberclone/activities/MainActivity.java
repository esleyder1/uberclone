package com.dev.uberclone.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dev.uberclone.R;
import com.dev.uberclone.activities.client.MapClientActivity;
import com.dev.uberclone.activities.driver.MapDriverActivity;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    Button btnIamDriver, btnIamClient;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = this.getSharedPreferences("typeUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        btnIamDriver = findViewById(R.id.btnIamDriver);
        btnIamClient = findViewById(R.id.btnIamClient);

        btnIamDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("user", "driver");
                editor.apply();
                goToSelectAuth();

            }
        });

        btnIamClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("user", "client");
                editor.apply();
                goToSelectAuth();
            }
        });
    }

    private void goToSelectAuth() {
        Intent i = new Intent(this,SelectOptionAuthActivity.class);
        startActivity(i);
    }


    //se sobrescribe el metodo OnStart para detectar si hay un usuario logueado o no con firebase instance.

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            String typeUser = pref.getString("user", "");
            Intent i;
            if(typeUser.equals("client")){
                i = new Intent(MainActivity.this, MapClientActivity.class);
                //no deja aplicaciones existentes con FLAG_ACTIVITY_NEW_TASK
            }else{
                i = new Intent(MainActivity.this, MapDriverActivity.class);
            }
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }
}