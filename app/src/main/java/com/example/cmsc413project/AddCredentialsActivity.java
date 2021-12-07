package com.example.cmsc413project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class AddCredentialsActivity extends AppCompatActivity {

    private UserPreferencesManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credentials);
        manager = new UserPreferencesManager(this);

        Button closeAddCredentials = findViewById(R.id.closeAddCredentials);
        closeAddCredentials.setOnClickListener(view -> closeNewCredentialsPage());

        EditText account = findViewById(R.id.accountInput);
        EditText email = findViewById(R.id.usernameEmailInput);
        EditText password = findViewById(R.id.passwordInput);

        Button addCredentials = findViewById(R.id.createCredentialsButton);
        addCredentials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewLoginCredential(account.getText().toString(), email.getText().toString(), password.getText().toString());
                closeNewCredentialsPage();
            }
        });
    }

    public void createNewLoginCredential(String account, String email, String password) {
            int id = manager.newID();
            LoginCredentials lc = new LoginCredentials(id, account, email, password);
            manager.addLoginCredentials(lc);
    }

    private void closeNewCredentialsPage() {
        Intent newMainActivityPage = new Intent(this, MainActivity.class);
        startActivity(newMainActivityPage);
    }
}