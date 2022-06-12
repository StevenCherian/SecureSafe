package com.example.cmsc413project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
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

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<LoginCredentials> loginCredentialsArrayList;
    UserPreferencesManager manager;
    Adapter adapter;
    EditText searchCredentials;
    //test push comment

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instantiates manager and fills login credentials arraylist with saved login credential data
        manager = new UserPreferencesManager(MainActivity.this);
        loginCredentialsArrayList = manager.getLoginCredentials();

        //Gives functionality to add credentials button
        Button addButton = findViewById(R.id.addCredentialsButton);
        addButton.setOnClickListener(view -> openNewCredentialsPage());

        //Instantiates the recycler view adapter and gives login credentials data
        adapter = new Adapter(this, loginCredentialsArrayList);

        //Gets the recycler view and sets layout manager and adapter
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        //Gives functionality to search button and input box
        searchCredentials = findViewById(R.id.searchInput);
        searchCredentials.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            /*
             * As each character is typed, the filter method in adapter is called to refresh the arraylist
             * when searching so the correct credentials show up on the screen
             */
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        //If there are no saved credentials, a view is shown saying that no credentials are saved
        LinearLayout noCredentialsView = findViewById(R.id.noCredentialsView);

        //Sorts recycler view in alphabetical order by app name
        Collections.sort(loginCredentialsArrayList, (lc1, lc2) -> lc1.appName.compareToIgnoreCase(lc2.appName));

        //If there are saved credentials, removes "no credentials" view
        if(loginCredentialsArrayList.size()>0)
            noCredentialsView.setVisibility(View.GONE);

        //Sets on click listener and slide animation to search button and close search button
        LinearLayout searchView = findViewById(R.id.searchView);

        AppCompatButton searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(view -> {
            //Once clicked, animation is played to open search bar
            ObjectAnimator animation = ObjectAnimator.ofFloat(searchView, "translationY", 7f);
            animation.setDuration(125);
            animation.start();
            searchCredentials.requestFocus();

            //Opens keyboard as search box slides into screen
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(searchCredentials, InputMethodManager.SHOW_IMPLICIT);

        });

        AppCompatButton closeSearchView = findViewById(R.id.closeSearchView);
        closeSearchView.setOnClickListener(view -> {
            closeKeyboard();
            searchCredentials.setText("");

            //Once clicked, animation is played to close search bar
            ObjectAnimator animation = ObjectAnimator.ofFloat(searchView, "translationY", -300f);
            animation.setDuration(250);
            animation.start();

            //Clears filter for searching
            recyclerView.setAdapter(adapter);
        });

        //Sets bottom navigation bar and actions
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    return true;

                case R.id.password_gen:
                    startActivity(new Intent(getApplicationContext(), PasswordGenActivity.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.settings:
                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });
    }

    //Opens add credentials page
    private void openNewCredentialsPage() {
        Intent newCredentialsPage = new Intent(this, AddCredentialsActivity.class);
        startActivity(newCredentialsPage);
    }

    //Closes keyboard
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