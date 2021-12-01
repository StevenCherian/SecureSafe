package com.example.cmsc413project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Passcodes> passcodesarrayList;
    Adapter adapter;
    String[] appHeading;
    String[] pwdHeading;
    int[] imageResourceId;

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
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        passcodesarrayList = new ArrayList<Passcodes>();

        adapter = new Adapter(this, passcodesarrayList);
        recyclerView.setAdapter((adapter));

        appHeading = new String[]{
                "Amazon",
                "Apple",
                "Google",
                "VCU",
                "Netflix"
        };
        pwdHeading = new String[]{
                "AmazonPwd1234",
                "ApplePwd5678",
                "GooglePwd1357",
                "VCUPwd2468",
                "NetflixPwd9876"
        };

        imageResourceId = new int[]{
                R.drawable.amazon,
                R.drawable.apple,
                R.drawable.google,
                R.drawable.vcu,
                R.drawable.netflix,
        };

        getData();
        
    }

    private void getData() {
        for(int i = 0;i<appHeading.length;i++){
            Passcodes passcodes = new Passcodes(appHeading[i], pwdHeading[i], imageResourceId[i]);
            passcodesarrayList.add(passcodes);
        }

        adapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
}