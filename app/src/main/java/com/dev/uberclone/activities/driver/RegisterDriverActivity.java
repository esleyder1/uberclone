package com.dev.uberclone.activities.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dev.uberclone.R;
import com.dev.uberclone.activities.client.RegisterActivity;
import com.dev.uberclone.includes.MyToolbar;
import com.dev.uberclone.models.Client;
import com.dev.uberclone.models.Driver;
import com.dev.uberclone.providers.AuthProvider;
import com.dev.uberclone.providers.DriverProvider;
import com.dev.uberclone.providers.DriverProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class RegisterDriverActivity extends AppCompatActivity {

    AuthProvider authProvider;
    DriverProvider driverProvider;
    Button btnRegister;
    DatabaseReference database;
    TextInputEditText txtInputName, txtInputEmail, txtInputPassword, txtInputVehicleBrand,txtInputVehiclePlate;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_driver);

        dialog = new SpotsDialog.Builder().setContext(this).setMessage(R.string.wait_moment).build();

        MyToolbar.show(this, R.string.msg_register_driver, true);
        authProvider = new AuthProvider();
        driverProvider = new DriverProvider();
        btnRegister = findViewById(R.id.btnRegister);
        txtInputName = findViewById(R.id.txtInputName);
        txtInputEmail = findViewById(R.id.txtInputEmail);
        txtInputPassword = findViewById(R.id.txtInputPassword);
        txtInputVehicleBrand = findViewById(R.id.txtInputVehicleBrand);
        txtInputVehiclePlate = findViewById(R.id.txtInputVehiclePlate);

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
        String brand = Objects.requireNonNull(txtInputVehicleBrand.getText()).toString();
        String plate = Objects.requireNonNull(txtInputVehiclePlate.getText()).toString();

        if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !brand.isEmpty() && !plate.isEmpty()) {
            if(password.length() >= 6) {
                dialog.show();
                register(name, email, password, brand, plate);
            }else{
                Toast.makeText(this, "La contraseña debe tener almenos 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void register(String name, String email, String password, String brand, String plate) {
        authProvider.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if( task.isSuccessful() ) {
                    //obtener el id del usuario logeado
                    String id = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                    Driver driver = new Driver(id,name,email,brand,plate);
                    create(driver);
                }else {
                    Toast.makeText(RegisterDriverActivity.this,
                            "No se pudo registrar el usuario",
                            Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }

    void create(Driver driver) {
        driverProvider.create(driver).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent i = new Intent(RegisterDriverActivity.this, MapDriverActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }else{
                    Toast.makeText(RegisterDriverActivity.this,
                            "No se pudo crear el cliente",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}