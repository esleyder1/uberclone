package com.dev.uberclone.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dev.uberclone.R;
import com.dev.uberclone.activities.client.MapClientActivity;
import com.dev.uberclone.activities.client.RegisterActivity;
import com.dev.uberclone.activities.driver.MapDriverActivity;
import com.dev.uberclone.activities.driver.RegisterDriverActivity;
import com.dev.uberclone.includes.MyToolbar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText txtInputEmail, txtInputPassword;
    Button btnLogin;
    FirebaseAuth auth;
    DatabaseReference database;
    AlertDialog dialog;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MyToolbar.show(this, R.string.login, true);

        txtInputEmail = findViewById(R.id.txtInputEmail);
        txtInputPassword = findViewById(R.id.txtInputPassword);
        btnLogin = findViewById(R.id.btnLogin);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        dialog = new SpotsDialog.Builder().setContext(this).setMessage(R.string.wait_moment).build();

        pref = this.getSharedPreferences("typeUser", MODE_PRIVATE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String email = txtInputEmail.getText().toString();
        String password = txtInputPassword.getText().toString();

        if(!email.isEmpty() && !password.isEmpty()){
            if(password.length() >= 6 ){
                dialog.show();
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            String typeUser = pref.getString("user", "");
                            Intent i;
                            if(typeUser.equals("client")){
                                i = new Intent(LoginActivity.this, MapClientActivity.class);
                                //no deja aplicaciones existentes con FLAG_ACTIVITY_NEW_TASK
                            }else{
                                i = new Intent(LoginActivity.this, MapDriverActivity.class);
                            }
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        }else {
                            Toast.makeText(LoginActivity.this, "Error en el correo o contraseña", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
            }else {
                Toast.makeText(LoginActivity.this, "La contraseña debe tener mas de 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(LoginActivity.this, "El correo y la contraseña con obligatorios", Toast.LENGTH_SHORT).show();
        }
    }
}