package com.dev.uberclone.activities.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dev.uberclone.R;
import com.dev.uberclone.activities.MainActivity;
import com.dev.uberclone.providers.AuthProvider;

public class MapClientActivity extends AppCompatActivity {

    Button btnLogout;
    AuthProvider authProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_client);

        btnLogout = findViewById(R.id.btnLogout);
        authProvider = new AuthProvider();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authProvider.logout();
                Intent i = new Intent(MapClientActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}