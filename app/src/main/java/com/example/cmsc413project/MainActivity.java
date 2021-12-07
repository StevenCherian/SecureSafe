package com.example.cmsc413project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<LoginCredentials> loginCredentialsArrayList;
    Adapter adapter;
    String[] appHeading;
    String[] emailHeading;
    String[] pwdHeading;

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

        loginCredentialsArrayList = new ArrayList<LoginCredentials>();

        adapter = new Adapter(this, loginCredentialsArrayList);
        recyclerView.setAdapter((adapter));

        appHeading = new String[] {
                "Amazon",
                "Apple",
                "Google",
                "VCU",
                "Netflix",
                "Amazon",
                "Apple",
                "Google",
                "VCU",
                "Netflix",
                "Amazon",
                "Apple",
                "Google",
                "VCU",
                "Netflix"
        };

        emailHeading = new String[] {
                "Amazon@gmail.com",
                "Apple@gmail.com",
                "Google@gmail.com",
                "VCU@gmail.com",
                "Netflix@gmail.com",
                "Amazon@gmail.com",
                "Apple@gmail.com",
                "Google@gmail.com",
                "VCU@gmail.com",
                "Netflix@gmail.com",
                "Amazon@gmail.com",
                "Apple@gmail.com",
                "Google@gmail.com",
                "VCU@gmail.com",
                "Netflix@gmail.com"
        };

        pwdHeading = new String[] {
                "AmazonPwd1234",
                "ApplePwd5678",
                "GooglePwd1357",
                "VCUPwd2468",
                "NetflixPwd9876",
                "AmazonPwd1234",
                "ApplePwd5678",
                "GooglePwd1357",
                "VCUPwd2468",
                "NetflixPwd9876",
                "AmazonPwd1234",
                "ApplePwd5678",
                "GooglePwd1357",
                "VCUPwd2468",
                "NetflixPwd9876"
        };

        getData();
    }

    private void getData() {
        for(int i = 0; i < appHeading.length; i++) {
            LoginCredentials loginCredentials = new LoginCredentials(appHeading[i], emailHeading[i], pwdHeading[i]);
            loginCredentialsArrayList.add(loginCredentials);
        }

        adapter.notifyDataSetChanged();
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