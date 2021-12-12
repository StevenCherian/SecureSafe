package com.example.cmsc413project;

import androidx.annotation.NonNull;

public class LoginCredentials {
    int id;
    String appName;
    String email;
    String password;

    public LoginCredentials(int id, String appName, String email, String password) {
        this.id = id;
        this.appName = appName;
        this.email = email;
        this.password = encrypt(password);
    }

    public String encrypt(String password){
        return password;
    }

    public String decrypt(String password){
        return password;
    }

    @NonNull
    @Override
    public String toString() {
        return appName;
    }

    @Override
    public boolean equals(Object c){
        return c.toString().equals(this.toString());
    }
}
