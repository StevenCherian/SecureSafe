package com.example.cmsc413project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class EditCredentialsActivity extends AppCompatActivity {

    private UserPreferencesManager manager;
    private int credentialsID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_credentials);
        manager = new UserPreferencesManager(this);

        credentialsID = getIntent().getIntExtra("credentialsID", -1);

        LoginCredentials lc = manager.getLoginCredentialsByID(credentialsID);

        if(lc.id == -1){
            closeNewCredentialsPage();
        }

        Button closeAddCredentials = findViewById(R.id.closeEditCredentials);
        closeAddCredentials.setOnClickListener(view -> closeNewCredentialsPage());

        EditText account = findViewById(R.id.editAccountInput);
        account.setText(lc.appName);

        EditText email = findViewById(R.id.editUsernameEmailInput);
        email.setText(lc.email);

        EditText password = findViewById(R.id.editPasswordInput);
        password.setText(lc.password);

        Button saveCredentials = findViewById(R.id.editCredentialsButton);
        saveCredentials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(account.getText())) { account.setError("Account is required."); }
                else if(TextUtils.isEmpty(email.getText())) { email.setError("Username or email is required."); }
                else if(TextUtils.isEmpty(password.getText().toString().replace(" ", ""))) { password.setError("Password is required."); }
                else {
                    updateLoginCredential(account.getText().toString(), email.getText().toString(), password.getText().toString());
                    closeNewCredentialsPage();
                }
            }
        });
    }

    public void updateLoginCredential(String account, String email, String password) {
        if(credentialsID != -1)
            manager.updateLoginCredentials(credentialsID, account, email, password);
    }

    private void closeNewCredentialsPage() {
        Intent newMainActivityPage = new Intent(this, MainActivity.class);
        startActivity(newMainActivityPage);
    }
}
