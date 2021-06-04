package com.dev.uberclone.providers;

import com.dev.uberclone.models.Client;
import com.dev.uberclone.models.Driver;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverProvider {

    DatabaseReference database;

    public DriverProvider() {
        database = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers");
    }

    public Task<Void> create(Driver driver){
        return database.child(driver.getId()).setValue(driver);
    }

}
