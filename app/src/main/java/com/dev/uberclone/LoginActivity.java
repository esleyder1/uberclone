package com.dev.uberclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.login);

        txtInputEmail = findViewById(R.id.txtInputEmail);
        txtInputPassword = findViewById(R.id.txtInputPassword);
        btnLogin = findViewById(R.id.btnLogin);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        dialog = new SpotsDialog.Builder().setContext(this).setMessage(R.string.wait_moment).build();

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
                            Toast.makeText(LoginActivity.this, "Login correcto", Toast.LENGTH_SHORT).show();
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