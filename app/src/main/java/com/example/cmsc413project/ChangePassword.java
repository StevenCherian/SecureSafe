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

    //upon clicking "change login password" in settings page, do this:
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);    //set layout to custom change password UI screen
        manager = new UserPreferencesManager(this);

        //create button in UI to exit out of "change password" screen if user decides not to change password
        Button closeUpdatePassword = findViewById(R.id.closeUpdatePassword);
        /*
            on click listener calls the closeNewPasswordPage method
            That method closes the change password screen and returns user
            to the settings page without modifying the default/last set password
         */
        closeUpdatePassword.setOnClickListener(view -> closeNewPasswordPage());

        //old password input text box
        EditText oldPassword = findViewById(R.id.oldPasswordInput);
        //new password input text box
        EditText newPassword = findViewById(R.id.newPasswordInput);

        //update password button created, references the XML file custom UI button
        Button updatePassword = findViewById(R.id.updatepasswordbutton);

        /*
            When update password is clicked:
            - check if old password and new password fields are not empty
            - check if old password input matches current last set password or default password if user hasn't changed password before
            - give appropriate error messages to tell user what is going wrong
            - if everything checks out, update the login password from newpassword text field
            - close page and return to settings screen
         */
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

    /*
        this method is called from the onclick listener
        after everything was verified to be correct, new local password is updated
        the password string variable in passcodeActivity file is changed to be the new password string
     */
    public void createNewPassword (String newpassword){
        passcodeActivity.password = newpassword;
    }

    /*
        this method is called if the user decides to not change the password and close the udpate password screen
        returns to original settings page
     */
    private void closeNewPasswordPage(){
        Intent newSettingsPage = new Intent(this, SettingsActivity.class);
        startActivity(newSettingsPage);
    }
}
