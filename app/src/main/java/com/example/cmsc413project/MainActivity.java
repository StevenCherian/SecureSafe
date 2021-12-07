package com.example.cmsc413project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<LoginCredentials> loginCredentialsArrayList;
    UserPreferencesManager manager;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.home:
                    return true;

                case R.id.settings:
                    startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });

        Button addButton = findViewById(R.id.addCredentialsButton);
        addButton.setOnClickListener(view -> openNewCredentialsPage());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        manager = new UserPreferencesManager(MainActivity.this);
        loginCredentialsArrayList = manager.getLoginCredentials();
        recyclerView.setAdapter(new Adapter(this, loginCredentialsArrayList));
    }


    private void openNewCredentialsPage() {
        Intent newCredentialsPage = new Intent(this, AddCredentialsActivity.class);
        startActivity(newCredentialsPage);
    }


    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
}