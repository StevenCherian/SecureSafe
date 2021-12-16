package com.example.cmsc413project;

import static android.content.Context.MODE_PRIVATE;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class UserPreferencesManager {

    private final SharedPreferences userPrefs;
    public ArrayList<LoginCredentials> loginCredentials;
    public int latestID;
    private static final String PREFS_NAME = "prefs";
    private static final String PREFS_LOGIN_CREDENTIALS = "login_credentials";
    private static final String PREFS_LATEST_ID = "latestID";

    //Constructor for user preference class
    UserPreferencesManager(Context c) {
        //Passes name of shared preferences file to shared preferences. Mode private means it can only be accessed when app is called.
        userPrefs = c.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        //ID is given to each login credential. This gets that ID
        latestID = userPrefs.getInt(PREFS_LATEST_ID, 0);
        //Gets login credentials arraylist data
        loginCredentials = getLoginCredentials();
    }

    //Adds newly created credentials to shared preferences file
    void addLoginCredentials(LoginCredentials newLoginCredentials) {
        //Allows for editing of the file. Gson is used to store objects on the file.
        SharedPreferences.Editor editor = userPrefs.edit();
        Gson gson = new Gson();

        //Adds passed in login credentials to arraylist of credentials store on file.
        loginCredentials.add(newLoginCredentials);
        String loginCredentialsJson = gson.toJson(loginCredentials);
        editor.putString(PREFS_LOGIN_CREDENTIALS, loginCredentialsJson);
        //Applies changes
        editor.apply();
        loginCredentials = getLoginCredentials();
    }

    //Removes login credentials by given ID
    void removeLoginCredentialsByID(int id) {
        //Allows for editing of the file. Gson is used to store objects on the file.
        SharedPreferences.Editor editor = userPrefs.edit();
        Gson gson = new Gson();

        //Parses through the login credentials arraylist by ID, once passed in ID is found, it is removed from the arraylist
        for(int i = 0; i < loginCredentials.size(); i++) {
            if(loginCredentials.get(i).id == id) {
                loginCredentials.remove(i);
                break;
            }
        }

        //Applies changes
        String loginCredentialsJson = gson.toJson(loginCredentials);
        editor.putString(PREFS_LOGIN_CREDENTIALS, loginCredentialsJson);
        editor.apply();
        loginCredentials = getLoginCredentials();
    }

    //Gets login credentials by passed in login credential ID, used for editing credentials
    LoginCredentials getLoginCredentialsByID(int id) {
        //New login credentials object is created and is given data from an existing credential by ID
        LoginCredentials lc = new LoginCredentials(-1, "", "", "");
        for(int i = 0; i < loginCredentials.size(); i++) {
            if(loginCredentials.get(i).id == id) {
                lc = loginCredentials.get(i);
                break;
            }
        }

        return lc;
    }

    //Updates given login credentials.
    void updateLoginCredentials(int id, String newAccountTitle, String newEmail, String newPassword) {
        //Allows for editing of the file. Gson is used to store objects on the file.
        SharedPreferences.Editor editor = userPrefs.edit();
        Gson gson = new Gson();

        //Login credentials object is instantiated. Given data from parameters regarding the changes made to the credentials
        LoginCredentials lc;
        for(int i = 0; i < loginCredentials.size(); i++) {
            if(loginCredentials.get(i).id == id) {
                //Sets created credentials object to the credential with matching ID
                lc = loginCredentials.get(i);
                //Sets updated account name
                lc.appName = newAccountTitle;
                //Once password is updated, it is re-encrypted
                lc.password = lc.encrypt(newPassword);
                //Sets updated email
                lc.email = newEmail;
                break;
            }
        }
        //Applies changes
        String loginCredentialsJson = gson.toJson(loginCredentials);
        editor.putString(PREFS_LOGIN_CREDENTIALS, loginCredentialsJson);
        editor.apply();
        loginCredentials = getLoginCredentials();
    }

    //Gives a new ID to a newly created login credential
    int newID() {
        SharedPreferences.Editor editor = userPrefs.edit();
        //Increments ID stored in shared preferences file and applies changes
        latestID+=1;
        //Applies changes
        editor.putInt(PREFS_LATEST_ID, latestID);
        editor.apply();
        return latestID;
    }

    //Gets login credentials arraylist and passes it to shared preferences file
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
