package com.example.cmsc413project;
//all necessary imports
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.hanks.passcodeview.PasscodeView;

public class PasscodeActivity extends AppCompatActivity {
    PasscodeView passcodeView;
    public static String password = "123456";       //setting default password to 123456, this can be changed by user

    //things to do when an instance is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);     //set the layout to the passcode entering screen
        passcodeView = findViewById(R.id.passcodeView);

        /*
            setting passcode length to match the password length
            this length updates if the user changes the password in the settings page
            the field will only accept the correct password, of the correct lenght
         */
        passcodeView.setPasscodeLength(password.length())
                .setLocalPasscode(password)
                .setListener(new PasscodeView.PasscodeViewListener() {

                    /*
                        if the passcode is wrong,
                        throw an error
                        this can be modified to show a custom message, or can be left as default error screen message
                     */
                    @Override
                    public void onFail() {
                    }

                    /*
                        with successful password, authorize login
                        start mainactivity and also remember the previous state's stored accounts/passwords
                     */
                    @Override
                    public void onSuccess(String number) {
                        startActivity(new Intent(PasscodeActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }
}