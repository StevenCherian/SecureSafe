package com.example.cmsc413project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddCredentialsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credentials);

        Button closeAddCredentials = findViewById(R.id.closeAddCredentials);
        closeAddCredentials.setOnClickListener(view -> closeNewCredentialsPage());
    }

    private void closeNewCredentialsPage() {
        Intent newMainActivityPage = new Intent(this, MainActivity.class);
        startActivity(newMainActivityPage);
    }
}