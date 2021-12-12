package com.example.cmsc413project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<LoginCredentials> loginCredentialsArrayList;
    UserPreferencesManager manager;
    Adapter adapter;
    EditText searchCredentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = new UserPreferencesManager(MainActivity.this);
        loginCredentialsArrayList = manager.getLoginCredentials();

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

        adapter = new Adapter(this, loginCredentialsArrayList);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        searchCredentials = findViewById(R.id.searchInput);
        searchCredentials.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        LinearLayout noCredentialsView = findViewById(R.id.noCredentialsView);

        Collections.sort(loginCredentialsArrayList, new Comparator<LoginCredentials>() {
            @Override
            public int compare(LoginCredentials lc1, LoginCredentials lc2) {
                return lc1.appName.compareToIgnoreCase(lc2.appName);
            }
        });

        if(loginCredentialsArrayList.size()>0)
            noCredentialsView.setVisibility(View.GONE);

        LinearLayout searchView = findViewById(R.id.searchView);

        AppCompatButton searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator animation = ObjectAnimator.ofFloat(searchView, "translationY", 15f);
                animation.setDuration(125);
                animation.start();
                searchCredentials.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(searchCredentials, InputMethodManager.SHOW_IMPLICIT);

            }
        });

        AppCompatButton closeSearchView = findViewById(R.id.closeSearchView);
        closeSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
                searchCredentials.setText("");

                ObjectAnimator animation = ObjectAnimator.ofFloat(searchView, "translationY", -300f);
                animation.setDuration(250);
                animation.start();

                //CLEAR FILTER
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void openNewCredentialsPage() {
        Intent newCredentialsPage = new Intent(this, AddCredentialsActivity.class);
        startActivity(newCredentialsPage);
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();

        if (view != null) {
            InputMethodManager manager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
}