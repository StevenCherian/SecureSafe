package com.example.cmsc413project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.annotation.SuppressLint;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Sets bottom navigation bar actions
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.settings);

        //Gives functionality for change passcode button to open new page to change passcode
        Button updatePasswordButton = findViewById(R.id.changePasswordButton);
        updatePasswordButton.setOnClickListener(view -> openUpdatePasswordPage());

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.password_gen:
                    startActivity(new Intent(getApplicationContext(), PasswordGenActivity.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.settings:
                    return true;
            }
            return false;
        });
    }

    private void openUpdatePasswordPage(){
        Intent updatePasswordPage = new Intent (this, UpdatePasscode.class);
        startActivity(updatePasswordPage);
    }
}