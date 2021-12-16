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

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                //If home button is clicked on nav bar, "My safe" page is opened
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                //If settings button is clicked on nav bar, nothing happens, since user was already on the page
                case R.id.settings:
                    return true;
            }
            return false;
        });

        //Gives functionality for change passcode button to open new page to change passcode
        Button updatePasswordButton = findViewById(R.id.changePasswordButton);
        updatePasswordButton.setOnClickListener(view -> openUpdatePasswordPage());

    }

    //Opens the update passcode page
    private void openUpdatePasswordPage(){
        Intent updatePasswordPage = new Intent (this, ChangePassword.class);
        startActivity(updatePasswordPage);
    }
}