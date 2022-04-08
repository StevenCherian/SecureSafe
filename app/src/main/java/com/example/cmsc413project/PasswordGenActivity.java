package com.example.cmsc413project;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PasswordGenActivity extends AppCompatActivity {

    private SwitchCompat azSwitch;
    private SwitchCompat AZSwitch;
    private SwitchCompat digitsSwitch;
    private SwitchCompat specialCharsSwitch;
    private TextView passwordLengthText;
    private EditText generatedPassword;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_gen);

        azSwitch = findViewById(R.id.azSwitch);
        AZSwitch = findViewById(R.id.AZSwitch);
        digitsSwitch = findViewById(R.id.digitsSwitch);
        specialCharsSwitch = findViewById(R.id.specialCharsSwitch);

        passwordLengthText = findViewById(R.id.pwdLength);
        SeekBar seekBar = findViewById(R.id.seekBar);

        generatedPassword = findViewById(R.id.generatedPassword);
        AppCompatButton copyButton = findViewById(R.id.copyButton);

        //Get progress bar progress and displays it
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                passwordLengthText.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        AppCompatButton generateButton = findViewById(R.id.generateButton);
        generateButton.setOnClickListener(view -> {
            Boolean azChecked = azSwitch.isChecked();
            Boolean AZChecked = AZSwitch.isChecked();
            Boolean digitsChecked = digitsSwitch.isChecked();
            Boolean specialsChecked = specialCharsSwitch.isChecked();

            PasswordGenerator passwordGenerator = new PasswordGenerator(azChecked, AZChecked, digitsChecked, specialsChecked);
            int passwordLength = seekBar.getProgress();
            String password = passwordGenerator.generatePassword(passwordLength);
            generatedPassword.setText(password);

            if((!azChecked) && (!AZChecked) && (!digitsChecked) && (!specialsChecked))
                Toast.makeText(PasswordGenActivity.this, "Please select a password requirement",
                        Toast.LENGTH_SHORT).show();
        });

        //Copy generated password to clipboard
        copyButton.setOnClickListener(view -> {
            String passwordToString = generatedPassword.getText().toString();
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("String", passwordToString);
            clipboardManager.setPrimaryClip(clipData);
            clipData.getDescription();

            Toast.makeText(PasswordGenActivity.this, "Password Copied", Toast.LENGTH_SHORT).show();
        });

        //Sets bottom navigation bar actions
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.password_gen);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                //If home button is clicked on nav bar, "My safe" page is opened
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.password_gen:
                    return true;

                //If settings button is clicked on nav bar, nothing happens, since user was already on the page
                case R.id.settings:
                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });
    }
}
