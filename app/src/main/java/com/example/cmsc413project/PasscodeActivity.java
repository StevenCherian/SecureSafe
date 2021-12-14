package com.example.cmsc413project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hanks.passcodeview.PasscodeView;

public class PasscodeActivity extends AppCompatActivity {
    PasscodeView passcodeView;
    public static String password = "123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);


        passcodeView = findViewById(R.id.passcodeView);
        //password = "123456";
        passcodeView.setPasscodeLength(password.length())
                .setLocalPasscode(password)
                .setListener(new PasscodeView.PasscodeViewListener() {
                    @Override
                    public void onFail() {
                    }

                    @Override
                    public void onSuccess(String number) {
                        startActivity(new Intent(PasscodeActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }
}