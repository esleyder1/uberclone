package com.dev.uberclone.providers;

import com.google.firebase.auth.FirebaseAuth;

public class AuthProvider {
    FirebaseAuth auth;

    public AuthProvider() {
        auth = FirebaseAuth.getInstance();
    }

    public com.google.android.gms.tasks.Task<com.google.firebase.auth.AuthResult> register(String email, String password) {
        return auth.createUserWithEmailAndPassword(email, password);
    }

    public com.google.android.gms.tasks.Task<com.google.firebase.auth.AuthResult> login(String email, String password) {
        return auth.signInWithEmailAndPassword(email, password);
    }

    public void logout() {
        auth.signOut();
    }
}
