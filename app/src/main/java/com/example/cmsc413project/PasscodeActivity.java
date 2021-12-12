package com.example.cmsc413project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.hanks.passcodeview.PasscodeView;

public class PasscodeActivity extends AppCompatActivity {
    PasscodeView passcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);

        passcodeView = findViewById(R.id.passcodeView);
        passcodeView.setPasscodeLength(1)
                .setLocalPasscode("1")
                .setListener(new PasscodeView.PasscodeViewListener() {
                    @Override
                    public void onFail() {}

                    @Override
                    public void onSuccess(String number) {
                        startActivity(new Intent(PasscodeActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }
}