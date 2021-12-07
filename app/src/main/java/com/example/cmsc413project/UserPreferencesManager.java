package com.example.cmsc413project;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class UserPreferencesManager {

    private SharedPreferences userPrefs;
    public ArrayList<LoginCredentials> loginCredentials;
    public int latestID;
    private static final String PREFS_NAME = "prefs";
    private static final String PREFS_LOGIN_CREDENTIALS = "login_credentials";
    private static final String PREFS_LATEST_ID = "latestID";

    UserPreferencesManager(Context c) {
        userPrefs = c.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        latestID = userPrefs.getInt(PREFS_LATEST_ID, 0);
        loginCredentials = getLoginCredentials();
    }

    void addLoginCredentials(LoginCredentials newLoginCredentials) {
        SharedPreferences.Editor editor = userPrefs.edit();
        Gson gson = new Gson();

        loginCredentials.add(newLoginCredentials);
        String loginCredentialsJson = gson.toJson(loginCredentials);
        editor.putString(PREFS_LOGIN_CREDENTIALS, loginCredentialsJson);
        editor.apply();
        loginCredentials = getLoginCredentials();
    }

    void removeLoginCredentialsByID(int id) {
        SharedPreferences.Editor editor = userPrefs.edit();
        Gson gson = new Gson();

        for(int i = 0; i < loginCredentials.size(); i++){
            if(loginCredentials.get(i).id == id)
                loginCredentials.remove(i);
        }

        String loginCredentialsJson = gson.toJson(loginCredentials);
        editor.putString(PREFS_LOGIN_CREDENTIALS, loginCredentialsJson);
        editor.apply();
        loginCredentials = getLoginCredentials();
    }

//    void updateLoginCredentials(LoginCredentials loginCredential){
//        SharedPreferences.Editor editor = userPrefs.edit();
//        Gson gson = new Gson();
//        if(loginCredentials.contains(loginCredential))
//        String loginCredentialsJson = gson.toJson(loginCredentials);
//        editor.putString(PREFS_LOGIN_CREDENTIALS, loginCredentialsJson);
//        editor.apply();
//        loginCredentials = getLoginCredentials();
//    }

    int newID(){
        SharedPreferences.Editor editor = userPrefs.edit();
        latestID+=1;
        editor.putInt(PREFS_LATEST_ID, latestID);
        editor.apply();
        return latestID;
    }

    ArrayList<LoginCredentials> getLoginCredentials() {
        Gson gson = new Gson();
        String loginCredentialsJson = userPrefs.getString(PREFS_LOGIN_CREDENTIALS, null);
        Type type = new TypeToken<ArrayList<LoginCredentials>>() {
        }.getType();
        loginCredentials = gson.fromJson(loginCredentialsJson, type);

        if (loginCredentials == null) { loginCredentials = new ArrayList<>(); }

        return loginCredentials;
    }
}
