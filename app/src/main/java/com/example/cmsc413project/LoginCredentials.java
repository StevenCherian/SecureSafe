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
        String encryptedPassword = "";
        try {
            char[] result = new char[password.length()];
            for (int i = 0; i < result.length; i++) {
                result[i] = (char) (password.charAt(i) + 13);
            }
            encryptedPassword = new String(result);
        }catch(Exception ioe){
            ioe.printStackTrace();
        }
        return (encryptedPassword);
    }

    public static String decrypt(String password){
        String decryptedPassword = "";
        try {
            char[] result = new char[password.length()];
            for (int i = 0; i < result.length; i++) {
                result[i] = (char) (password.charAt(i) - 13);
            }
            decryptedPassword = new String(result);
        }catch(Exception ioe){
            ioe.printStackTrace();
        }
        return decryptedPassword;
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
