package com.dev.uberclone.activities.client;

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
import com.dev.uberclone.activities.driver.MapDriverActivity;
import com.dev.uberclone.activities.driver.RegisterDriverActivity;
import com.dev.uberclone.includes.MyToolbar;
import com.dev.uberclone.models.Client;
import com.dev.uberclone.providers.AuthProvider;
import com.dev.uberclone.providers.ClientProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {

    AuthProvider authProvider;
    ClientProvider clientProvider;
    Button btnRegister;
    DatabaseReference database;
    TextInputEditText txtInputName, txtInputEmail, txtInputPassword;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dialog = new SpotsDialog.Builder().setContext(this).setMessage(R.string.wait_moment).build();

        MyToolbar.show(this, R.string.msg_register, true);
        authProvider = new AuthProvider();
        clientProvider = new ClientProvider();

        btnRegister = findViewById(R.id.btnRegister);

        txtInputName = findViewById(R.id.txtInputName);
        txtInputEmail = findViewById(R.id.txtInputEmail);
        txtInputPassword = findViewById(R.id.txtInputPassword);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRegister();
            }
        });
    }

    private void clickRegister() {
        String name = Objects.requireNonNull(txtInputName.getText()).toString();
        String email = Objects.requireNonNull(txtInputEmail.getText()).toString();
        String password = Objects.requireNonNull(txtInputPassword.getText()).toString();

        if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            if(password.length() >= 6) {
                dialog.show();
                 register(name, email, password);
            }else{
                Toast.makeText(this, "La contraseña debe tener almenos 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void register(String name, String email, String password) {
        authProvider.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if( task.isSuccessful() ) {
                    //obtener el id del usuario logeado
                    String id = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                    Client client = new Client(id,name,email);
                    create(client);
                }else {
                    Toast.makeText(RegisterActivity.this,
                            "No se pudo registrar el usuario",
                            Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }

    void create(Client client) {
        clientProvider.create(client).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent i = new Intent(RegisterActivity.this, MapClientActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }else{
                    Toast.makeText(RegisterActivity.this,
                            "No se pudo crear el cliente",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });
    }


    /*
    private void saveUser(String id, String name, String email) {
        pref = this.getSharedPreferences("typeUser", MODE_PRIVATE);
        String selectedUser = pref.getString("user", "");

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        if(selectedUser.equals("driver")) {
            database.child("Users").child("Drivers").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Registro de conductor exitoso", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RegisterActivity.this, "Error al registrar el conductor", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else if(selectedUser.equals("client")){
            database.child("Users").child("Clients").push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Registro de cliente exitoso", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RegisterActivity.this, "Error al registrar el cliente", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }*/
}