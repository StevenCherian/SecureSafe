package com.example.cmsc413project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

public class AddCredentialsActivity extends AppCompatActivity {

    private UserPreferencesManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credentials);
        manager = new UserPreferencesManager(this);

        //Functionality for closing add credentials page
        Button closeAddCredentials = findViewById(R.id.closeAddCredentials);
        closeAddCredentials.setOnClickListener(view -> closeNewCredentialsPage());

        //Gets the account, email, and password views
        EditText account = findViewById(R.id.accountInput);
        EditText email = findViewById(R.id.usernameEmailInput);
        EditText password = findViewById(R.id.passwordInput);

        //Gives functionality for the create new credentials button to add the new credentials to the credentials arraylist
        Button addCredentials = findViewById(R.id.createCredentialsButton);
        addCredentials.setOnClickListener(view -> {
            //Makes sure no fields are left empty
            if(TextUtils.isEmpty(account.getText())) { account.setError("Account is required."); }
            else if(TextUtils.isEmpty(email.getText())) { email.setError("Username or email is required."); }
            else if(TextUtils.isEmpty(password.getText().toString().replace(" ", ""))) { password.setError("Password is required."); }
            else {
                createNewLoginCredential(account.getText().toString(), email.getText().toString(), password.getText().toString());
                closeNewCredentialsPage();
            }
        });
    }

    //Creates the new credential and adds it to shared preferences
    public void createNewLoginCredential(String account, String email, String password) {
        //Creates new ID for the created credentials
        int id = manager.newID();
        LoginCredentials lc = new LoginCredentials(id, account, email, password);
        manager.addLoginCredentials(lc);
    }

    private void closeNewCredentialsPage() {
        Intent newMainActivityPage = new Intent(this, MainActivity.class);
        startActivity(newMainActivityPage);
    }
}