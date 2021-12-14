package com.example.cmsc413project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

public class ChangePassword extends AppCompatActivity {
    private UserPreferencesManager manager;
    PasscodeActivity passcodeActivity = new PasscodeActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);
        manager = new UserPreferencesManager(this);

        Button closeUpdatePassword = findViewById(R.id.closeUpdatePassword);
        closeUpdatePassword.setOnClickListener(view -> closeNewPasswordPage());

        EditText oldPassword = findViewById(R.id.oldPasswordInput);
        EditText newPassword = findViewById(R.id.newPasswordInput);


        Button updatePassword = findViewById(R.id.updatepasswordbutton);
        updatePassword.setOnClickListener(view -> {
            if(TextUtils.isEmpty(oldPassword.getText())) { oldPassword.setError("Old Password is required."); }
            else if(TextUtils.isEmpty(newPassword.getText())) { newPassword.setError("New Password is required."); }
            else {
                if(!oldPassword.getText().toString().equals(PasscodeActivity.password)){
                    oldPassword.setError("Old Password is wrong.");
                }
                else {
                    createNewPassword(newPassword.getText().toString());
                    closeNewPasswordPage();
                }
            }
        });
    }

    public void createNewPassword (String newpassword){
        passcodeActivity.password = newpassword;
    }
    private void closeNewPasswordPage(){
        Intent newSettingsPage = new Intent(this, SettingsActivity.class);
        startActivity(newSettingsPage);

    }

}
